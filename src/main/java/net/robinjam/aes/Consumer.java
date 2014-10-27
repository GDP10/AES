package net.robinjam.aes;

import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.cxf.wsn.client.NotificationBroker;
import org.apache.cxf.wsn.client.Subscription;
import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;
import org.oasis_open.docs.wsn.bw_2.InvalidFilterFault;
import org.oasis_open.docs.wsn.bw_2.InvalidMessageContentExpressionFault;
import org.oasis_open.docs.wsn.bw_2.InvalidProducerPropertiesExpressionFault;
import org.oasis_open.docs.wsn.bw_2.InvalidTopicExpressionFault;
import org.oasis_open.docs.wsn.bw_2.NotifyMessageNotSupportedFault;
import org.oasis_open.docs.wsn.bw_2.SubscribeCreationFailedFault;
import org.oasis_open.docs.wsn.bw_2.TopicExpressionDialectUnknownFault;
import org.oasis_open.docs.wsn.bw_2.TopicNotSupportedFault;
import org.oasis_open.docs.wsn.bw_2.UnacceptableInitialTerminationTimeFault;
import org.oasis_open.docs.wsn.bw_2.UnrecognizedPolicyRequestFault;
import org.oasis_open.docs.wsn.bw_2.UnsupportedPolicyRequestFault;
import org.oasis_open.docs.wsrf.rw_2.ResourceUnknownFault;
import org.w3c.dom.Element;

public class Consumer implements org.apache.cxf.wsn.client.Consumer.Callback {
	
	public interface Callback {
		public void notify(Element element);
	}

	public static org.apache.cxf.wsn.client.Consumer getNew(String brokerAddress, String bindAddress, final String[] topics, Callback callback) throws TopicExpressionDialectUnknownFault, InvalidFilterFault, TopicNotSupportedFault, UnacceptableInitialTerminationTimeFault, SubscribeCreationFailedFault, InvalidMessageContentExpressionFault, InvalidTopicExpressionFault, ResourceUnknownFault, UnsupportedPolicyRequestFault, UnrecognizedPolicyRequestFault, NotifyMessageNotSupportedFault, InvalidProducerPropertiesExpressionFault {
	
		final org.apache.cxf.wsn.client.Consumer consumer = new org.apache.cxf.wsn.client.Consumer(new Consumer(callback), bindAddress);
		NotificationBroker broker = new NotificationBroker(brokerAddress);
		
		final Subscription[] subscriptions = new Subscription[topics.length];
		for(int i=0; i< topics.length; i++){
			subscriptions[i] = broker.subscribe(consumer, topics[i]);
			System.out.println("Subscribed to " + topics[i]);
		}
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				for(int i = 0; i < topics.length; i++){
					try { subscriptions[i].unsubscribe(); } catch (Exception e) {}
					System.out.println("Unsubscribed from " + topics[i]);
				}
				consumer.stop();
			}
		}));
		
		return consumer;
		
	}
	
	public static void main(String[] args) throws Exception {
		final String[] topics;
		if (args.length < 3) {
			System.err.println("Usage: mvn -Pconsumer package exec:java -Dexec.args=\"[Broker Address] [Bind Address] [Subscription Topic]+\"");
			System.exit(1);
		} else {
			String brokerAddress = args[0];
			String bindAddress = args[1];
			final int topic_count = args.length - 2;
			topics = new String[topic_count];
			for (int i = 2; i < args.length; i++) {
				topics[i-2] = args[i];
			}
			
			final org.apache.cxf.wsn.client.Consumer consumer = new org.apache.cxf.wsn.client.Consumer(new Consumer(new Callback() {
				@Override
				public void notify(Element element) {
					try {
						System.out.println(getSource(element));
					} catch (TransformerException e) {
						e.printStackTrace();
						System.exit(1);
					}
				}
				
			}), bindAddress);
			NotificationBroker broker = new NotificationBroker(brokerAddress);
			
			final Subscription[] subscriptions = new Subscription[topic_count];
			for(int i=0; i<topic_count; i++){
				subscriptions[i] = broker.subscribe(consumer, topics[i]);
				System.out.println("Subscribed to " + topics[i]);
			}
			
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				@Override
				public void run() {
					for(int i=0; i<topic_count; i++){
						try { subscriptions[i].unsubscribe(); } catch (Exception e) {}
						System.out.println("Unsubscribed from " + topics[i]);
					}
					consumer.stop();
				}
			}));
		}
	}

	private Callback callback;
	
	public Consumer(Callback callback) {
		this.callback = callback;
	}

	@Override
	public void notify(NotificationMessageHolderType message) {
		Object o = message.getMessage().getAny();
		if (o instanceof Element) {
			callback.notify((Element) o);
		}
	}
	
	public static String getSource(Element doc) throws TransformerException {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		return writer.getBuffer().toString();
	}
}
