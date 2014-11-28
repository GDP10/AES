package net.robinjam.aes;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.security.auth.callback.Callback;
import javax.xml.datatype.DatatypeConfigurationException;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Consumer implements Callback {
	
	public static void main(String[] args) throws JMSException {
		new Consumer(args[0], args[1], args[2]);
	}
	
	private Callback callback;
	
	public Consumer(Callback callback, String brokerAddress, String startDateString, String endDateString) throws JMSException {
		this.callback = callback;
		this.startListening(brokerAddress, startDateString, endDateString);
	}
	
	public Consumer(String brokerAddress, String startDateString, String endDateString) throws JMSException {
		this.callback = new SystemOutCallback();
		this.startListening(brokerAddress, startDateString, endDateString);
	}
	
	public Consumer(Callback callback, String brokerAddress, String latString, String lonString, String rangeString) throws JMSException {
		this.callback = callback;
		this.startListening(brokerAddress, latString, lonString, rangeString);
	}
	
	public Consumer(String brokerAddress, String latString, String lonString, String rangeString) throws JMSException {
		this.callback = new SystemOutCallback();
		this.startListening(brokerAddress, latString, lonString, rangeString);
	}
	
	public Consumer(Callback callback, String brokerAddress, String lat1String, String lon1String, String lat2String, String lon2String) throws JMSException {
		this.callback = callback;
		this.startListening(brokerAddress, lat1String, lon1String, lat2String, lon2String);
	}
	
	public Consumer(String brokerAddress, String lat1String, String lon1String, String lat2String, String lon2String) throws JMSException {
		this.callback = new SystemOutCallback();
		this.startListening(brokerAddress, lat1String, lon1String, lat2String, lon2String);
	}
	
	private void startListening(String brokerAddress, String startDateString, String endDateString) throws JMSException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerAddress);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("AES");
		
		long startTimeSub, endTimeSub;
		
		try {
			startTimeSub = javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(startDateString).toGregorianCalendar().getTimeInMillis() / 1000;
		}
		catch(DatatypeConfigurationException e) {
			startTimeSub = Integer.MIN_VALUE;
		}
		
		try {
			endTimeSub = javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(endDateString).toGregorianCalendar().getTimeInMillis() / 1000;
		}
		catch(DatatypeConfigurationException e) {
			endTimeSub = Integer.MAX_VALUE;
		}
		
		MessageConsumer consumer = session.createConsumer(destination, "startTime < " + endTimeSub + " AND endTime > " + startTimeSub);
		
		Message message = consumer.receive(60000);
		
		if (message instanceof TextMessage) {
			callback.recieveMessage((TextMessage) message);
		}
		
		consumer.close();
		session.close();
		connection.close();
	}
	
	private void startListening(String brokerAddress, String latString, String lonString, String rangeString) throws JMSException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerAddress);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("AES");
		
		double targetLat = Double.valueOf(latString);
		double targetLon = Double.valueOf(lonString);
		double range = Double.valueOf(rangeString);
		double squaredRange = Math.pow(range, 2.0);
		
		MessageConsumer consumer = session.createConsumer(destination,
			"((" + targetLat + "-lat)*(" + targetLat + "-lat))+((" + targetLon + "-lon)*(" + targetLon + "-lon))<" + squaredRange);
		
		Message message = consumer.receive(60000);
		
		if (message instanceof TextMessage) {
			callback.recieveMessage((TextMessage) message);
		}
		
		consumer.close();
		session.close();
		connection.close();
	}
	
	private void startListening(String brokerAddress, String lat1String, String lon1String, String lat2String, String lon2String) throws JMSException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerAddress);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("AES");

		double lat1 = Double.valueOf(lat1String);
		double lon1 = Double.valueOf(lon1String);
		double lat2 = Double.valueOf(lat2String);
		double lon2 = Double.valueOf(lon2String);

		MessageConsumer consumer = session.createConsumer(destination, "lat>" + lat1 + " AND lat<" + lat2 + " AND lon>" + lon1 + " AND lon<" + lon2);

		Message message = consumer.receive(60000);

		if (message instanceof TextMessage) {
			callback.recieveMessage((TextMessage) message);
		}

		consumer.close();
		session.close();
		connection.close();
	}
	
	public static interface Callback {
		public void recieveMessage(TextMessage message) throws JMSException;
	}
	
	private static class SystemOutCallback implements Callback {
		@Override
		public void recieveMessage(TextMessage message) throws JMSException {
			System.out.println(message.getText());
		}
	}
}
