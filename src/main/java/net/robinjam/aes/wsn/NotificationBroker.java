package net.robinjam.aes.wsn;

import java.util.List;
import java.util.logging.Logger;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.xml.namespace.QName;
import javax.xml.ws.wsaddressing.W3CEndpointReference;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.oasis_open.docs.wsn.b_2.GetCurrentMessage;
import org.oasis_open.docs.wsn.b_2.GetCurrentMessageResponse;
import org.oasis_open.docs.wsn.b_2.Notify;
import org.oasis_open.docs.wsn.b_2.Subscribe;
import org.oasis_open.docs.wsn.b_2.SubscribeResponse;
import org.oasis_open.docs.wsn.br_2.RegisterPublisher;
import org.oasis_open.docs.wsn.br_2.RegisterPublisherResponse;
import org.oasis_open.docs.wsn.brw_2.PublisherRegistrationFailedFault;
import org.oasis_open.docs.wsn.brw_2.PublisherRegistrationRejectedFault;
import org.oasis_open.docs.wsn.bw_2.InvalidFilterFault;
import org.oasis_open.docs.wsn.bw_2.InvalidMessageContentExpressionFault;
import org.oasis_open.docs.wsn.bw_2.InvalidProducerPropertiesExpressionFault;
import org.oasis_open.docs.wsn.bw_2.InvalidTopicExpressionFault;
import org.oasis_open.docs.wsn.bw_2.MultipleTopicsSpecifiedFault;
import org.oasis_open.docs.wsn.bw_2.NoCurrentMessageOnTopicFault;
import org.oasis_open.docs.wsn.bw_2.NotificationConsumer;
import org.oasis_open.docs.wsn.bw_2.NotifyMessageNotSupportedFault;
import org.oasis_open.docs.wsn.bw_2.SubscribeCreationFailedFault;
import org.oasis_open.docs.wsn.bw_2.TopicExpressionDialectUnknownFault;
import org.oasis_open.docs.wsn.bw_2.TopicNotSupportedFault;
import org.oasis_open.docs.wsn.bw_2.UnacceptableInitialTerminationTimeFault;
import org.oasis_open.docs.wsn.bw_2.UnrecognizedPolicyRequestFault;
import org.oasis_open.docs.wsn.bw_2.UnsupportedPolicyRequestFault;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;
import org.w3c.dom.Element;

@javax.jws.WebService(
	serviceName = "NotificationBrokerService",
	portName = "NotificationBrokerPort",
	targetNamespace = "http://robinjam.net/aes/wsn",
	wsdlLocation = "http://robinjam.net/aes/wsn.wsdl",
	endpointInterface = "org.oasis_open.docs.wsn.brw_2.NotificationBroker"
)
public class NotificationBroker implements org.oasis_open.docs.wsn.brw_2.NotificationBroker {

	private static final Logger LOG = Logger.getLogger(NotificationBroker.class.getName());
	
	private static final String broker_uri = "tcp://localhost:61616";
	
	private final Connection connection;
	private final Session session;
	private final Queue queue;
	
	public NotificationBroker() throws JMSException {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(broker_uri);
		connection = connectionFactory.createConnection();
		connection.start();
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		queue = session.createQueue("AES");
		// TODO: close connection on exit
	}

	public void notify(Notify notify) {
		throw new UnsupportedOperationException();
	}

	public GetCurrentMessageResponse getCurrentMessage(GetCurrentMessage getCurrentMessageRequest) throws ResourceUnknownFault, InvalidTopicExpressionFault, NoCurrentMessageOnTopicFault, TopicExpressionDialectUnknownFault, TopicNotSupportedFault, MultipleTopicsSpecifiedFault {
		throw new UnsupportedOperationException();
	}

	public RegisterPublisherResponse registerPublisher(RegisterPublisher registerPublisherRequest) throws UnacceptableInitialTerminationTimeFault, ResourceUnknownFault, InvalidTopicExpressionFault, PublisherRegistrationFailedFault, TopicNotSupportedFault, PublisherRegistrationRejectedFault {
		throw new UnsupportedOperationException();
	}

	public SubscribeResponse subscribe(Subscribe subscribeRequest) throws NotifyMessageNotSupportedFault, UnacceptableInitialTerminationTimeFault, ResourceUnknownFault, InvalidTopicExpressionFault, InvalidMessageContentExpressionFault, TopicNotSupportedFault, TopicExpressionDialectUnknownFault, InvalidProducerPropertiesExpressionFault, SubscribeCreationFailedFault, UnrecognizedPolicyRequestFault, InvalidFilterFault, UnsupportedPolicyRequestFault {
		List<Object> filters = subscribeRequest.getFilter().getAny();
		// TODO: throw exception if wrong number of filters
		Element filter = (Element) filters.get(0);
		//QName filterType = new QName(filter.getNamespaceURI(), filter.getLocalName());
		//if (!filterType.equals(new QName("TODO: add namespace here", "Filter"))) {
		//	throw new TopicExpressionDialectUnknownFault(); // TODO: is this correct?
		//}
		
		String selector = filterToSelector(filter);
		LOG.info(selector);
		W3CEndpointReference consumerReference = subscribeRequest.getConsumerReference();
		LOG.info(consumerReference.toString());
		NotificationConsumer notificationConsumer = consumerReference.getPort(NotificationConsumer.class);
		LOG.info("Got consumer reference");
		JmsConsumer jmsConsumer = new JmsConsumer(notificationConsumer);
		try {
			MessageConsumer c = session.createConsumer(queue, selector);
			c.setMessageListener(jmsConsumer);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		SubscribeResponse subscribeResponse = new SubscribeResponse();
		return subscribeResponse;
	}
	
	private String filterToSelector(Element filter) {
		String[] lowerCornerStr = filter.getElementsByTagNameNS("http://www.opengis.net/gml", "lowerCorner").item(0).getTextContent().split("\\s+");
		float[] lowerCorner = {Float.valueOf(lowerCornerStr[0]), Float.valueOf(lowerCornerStr[1])};
		String[] upperCornerStr = filter.getElementsByTagNameNS("http://www.opengis.net/gml", "upperCorner").item(0).getTextContent().split("\\s+");
		float[] upperCorner = {Float.valueOf(upperCornerStr[0]), Float.valueOf(upperCornerStr[1])};
		
		return "lat<" + upperCorner[0] + " AND lat>" + lowerCorner[0] + " AND lon>" + lowerCorner[1] + " AND lon<" + upperCorner[1];

	}

}
