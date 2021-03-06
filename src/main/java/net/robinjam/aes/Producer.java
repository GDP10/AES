package net.robinjam.aes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class Producer {
	
	public static void main(String[] args) throws JMSException, SAXException, IOException, ParserConfigurationException, TransformerException {
		new Producer(args[0], new FileInputStream(args[1]));
	}
	
	public Producer(String brokerAddress, InputStream inputStreamMaster) throws JMSException, SAXException, IOException, ParserConfigurationException, TransformerException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerAddress);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("AES");
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		
		// Allows us to re-use the input stream for multiple parses
		CopyInputStream inputStream = new CopyInputStream(inputStreamMaster);
		
		List<Element> notams = XMLParser.parseNOTAMS(inputStream.getCopy()); 
		
		for(int i = 0; i < notams.size(); i++) {
			
			// Send the string extracted from the element
			TextMessage message = session.createTextMessage(getElementAsString(notams.get(i)));
	
			long startTime, endTime;
			
			try {
				String beginPositionString = XMLParser.parseElement(inputStream.getCopy(), "http://www.opengis.net/gml/3.2", "beginPosition").get(0).getTextContent();
				startTime = javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(beginPositionString).toGregorianCalendar().getTimeInMillis() / 1000;
			}
			catch(Exception e) {
				startTime = Integer.MIN_VALUE;
			}
			
			try {
				String endPositionString = XMLParser.parseElement(inputStream.getCopy(), "http://www.opengis.net/gml/3.2", "endPosition").get(0).getTextContent();
				endTime = javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(endPositionString).toGregorianCalendar().getTimeInMillis() / 1000;
			}
			catch(Exception e) {
				endTime = Integer.MAX_VALUE;
			}
			
			
			// TODO Get lat,lon from Element
			if(i == 0) {
			    message.setDoubleProperty("lat", 0.51);
			    message.setDoubleProperty("lon", 0.51);
			} else {
			    message.setDoubleProperty("lat", -1.0);
			    message.setDoubleProperty("lon", -1.0);
			}
			message.setLongProperty("startTime", startTime);
			message.setLongProperty("endTime", endTime);
			
			producer.send(message);
		}
		
		session.close();
		connection.close();
	}
	
	private static String getElementAsString(Element element) throws TransformerException {
		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transformer = transFactory.newTransformer();
		StringWriter buffer = new StringWriter();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.transform(new DOMSource(element), new StreamResult(buffer));
		
		String elementString = buffer.toString();
		
		return elementString;
	}
	
	private static class CopyInputStream {
	    private InputStream _is;
	    private ByteArrayOutputStream _copy = new ByteArrayOutputStream();

	    public CopyInputStream(InputStream is) {
	        _is = is;

	        try {
	            copy();
	        }
	        catch(IOException ex) {
	        }
	    }

	    private int copy() throws IOException {
	        int read = 0;
	        int chunk = 0;
	        byte[] data = new byte[256];

	        while(-1 != (chunk = _is.read(data))) {
	            read += data.length;
	            _copy.write(data, 0, chunk);
	        }

	        return read;
	    }

	    public InputStream getCopy() {
	        return (InputStream)new ByteArrayInputStream(_copy.toByteArray());
	    }
	}
}
