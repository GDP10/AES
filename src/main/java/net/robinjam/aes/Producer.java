package net.robinjam.aes;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {
	
	public static void main(String[] args) throws JMSException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(args[0]);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("AES");
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		TextMessage message = session.createTextMessage("Hello world! This is message 1.");
		message.setIntProperty("lat", 90);
		message.setIntProperty("lon", 33);
		message.setIntProperty("time", 24);
		producer.send(message);
		message = session.createTextMessage("Hello world! This is message 2.");
		message.setIntProperty("lat", 90);
		message.setIntProperty("lon", 33);
		message.setIntProperty("time", 42);
		producer.send(message);
		session.close();
		connection.close();
	}
}
