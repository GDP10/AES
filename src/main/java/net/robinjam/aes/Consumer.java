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
		Consumer consumer = new Consumer(args[0], args[1], args[2]);
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
	
	private void startListening(String brokerAddress, String startDateString, String endDateString) throws JMSException {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerAddress);
		Connection connection = connectionFactory.createConnection();
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("AES");
		
		long startTimeSub, endTimeSub;
		
		try {
			startTimeSub = javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(startDateString).toGregorianCalendar().getTimeInMillis() / 1000;
			endTimeSub = javax.xml.datatype.DatatypeFactory.newInstance().newXMLGregorianCalendar(endDateString).toGregorianCalendar().getTimeInMillis() / 1000;
		}
		catch(DatatypeConfigurationException e) {
			startTimeSub = -1;
			endTimeSub = -1;
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
