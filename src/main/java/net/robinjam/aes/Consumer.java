package net.robinjam.aes;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer {
	
	public static void main(String[] args) throws JMSException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(args[0]);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("AES");
		MessageConsumer consumer = session.createConsumer(destination, "time > 30");
		Message message = consumer.receive(60000);
		
		if (message instanceof TextMessage) {
			System.out.println(((TextMessage) message).getText());
		}
		
		consumer.close();
		session.close();
		connection.close();
	}
}
