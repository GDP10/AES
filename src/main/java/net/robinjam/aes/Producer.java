package net.robinjam.aes;

import java.io.File;
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
			
			// Convert the element to a string
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			StringWriter buffer = new StringWriter();
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			transformer.transform(new DOMSource(notams.get(i)), new StreamResult(buffer));
			String notamString = buffer.toString();
			
			// Send the string extracted from the element
			TextMessage message = session.createTextMessage(notamString);
	
//			System.out.println(notamString);
			
			// TODO Get lat,lon,time from Element
			message.setIntProperty("lat", 90);
			message.setIntProperty("lon", 33);
			
			// This if is just for testing purposes so only 1 message should be received because of time selector!!!
			if(i == 0)
				message.setIntProperty("time", 35);
			else
				message.setIntProperty("time", 24);
			
			producer.send(message);
		}
		
		session.close();
		connection.close();
		
	}
}
