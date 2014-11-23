package net.robinjam.aes;

	import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
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
		
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(args[0]);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("AES");
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		
		InputStream inputStream = new FileInputStream(new File(args[1]));
		List<Element> notams = XMLParser.parseNOTAMS(inputStream); 
		
		for(int i = 0; i < notams.size(); i++) {
			
			// Send the string extracted from the element
			TextMessage message = session.createTextMessage(getElementAsString(notams.get(i)));
	
			// Re-make the stream so we can parse again
			inputStream = new FileInputStream(new File(args[1]));

			long startTime, endTime;
			
			try {
				Date startDate = new SimpleDateFormat("yyyy-dd-MM'T'HH:mm:ss", Locale.ENGLISH).parse(XMLParser.parseElement(inputStream, "http://www.opengis.net/gml/3.2", "beginPosition").get(0).getTextContent());
				// time is given in milliseconds, divide by 1000 to get a unix timestamp in seconds
				startTime = startDate.getTime();
			}
			catch(ParseException e) {
				startTime = -1;
			}
			
			inputStream = new FileInputStream(new File(args[1]));
			
			try {
				Date endDate = new SimpleDateFormat("yyyy-dd-MM'T'HH:mm:ss", Locale.ENGLISH).parse(XMLParser.parseElement(inputStream, "http://www.opengis.net/gml/3.2", "endPosition").get(0).getTextContent());
				// time is given in milliseconds, divide by 1000 to get a unix timestamp in seconds
				endTime = endDate.getTime() / 1000;
			}
			catch(ParseException e) {
				endTime = -1;
			}
			
			
			// TODO Get lat,lon,time from Element
			message.setIntProperty("lat", 90);
			message.setIntProperty("lon", 33);
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
}
