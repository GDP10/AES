package net.robinjam.aes.wsn;

import java.io.StringReader;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;
import org.oasis_open.docs.wsn.b_2.Notify;
import org.oasis_open.docs.wsn.bw_2.NotificationConsumer;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class JmsConsumer implements MessageListener {
	
	private final NotificationConsumer notificationConsumer;
	
	public JmsConsumer(NotificationConsumer notificationConsumer) {
		this.notificationConsumer = notificationConsumer;
	}

	@Override
	public void onMessage(Message jmsMessage) {
		try {
		if (jmsMessage instanceof TextMessage) {
			Notify notification = new Notify();
			NotificationMessageHolderType messageHolder = new NotificationMessageHolderType();
			org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType.Message wsnMessage = new NotificationMessageHolderType.Message();
			String messageText = ((TextMessage) jmsMessage).getText();
			Element element = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(messageText))).getDocumentElement();
			wsnMessage.setAny(element);
			messageHolder.setMessage(wsnMessage);
			notification.getNotificationMessage().add(messageHolder);
			notificationConsumer.notify(notification);
		}
		}
		catch (Exception ex) {
		}
	}

}
