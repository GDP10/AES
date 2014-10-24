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
import org.w3c.dom.Element;

public class Consumer implements org.apache.cxf.wsn.client.Consumer.Callback {

	public static void main(String[] args) throws Exception {
		String[] topics;
		if (args.length < 3) {
			System.err.println("Usage: mvn -Pconsumer package exec:java -Dexec.args=\"[Broker Address] [Bind Address] [Subscription Topic]+\"");
			System.exit(1);
		} else {
			String brokerAddress = args[0];
			String bindAddress = args[1];
			int topic_count = args.length - 2;
			topics = new String[topic_count];
			for (int i = 2; i < args.length; i++) {
				topics[i-2] = args[i];
			}
			
			org.apache.cxf.wsn.client.Consumer consumer = new org.apache.cxf.wsn.client.Consumer(new Consumer(), bindAddress);
			NotificationBroker broker = new NotificationBroker(brokerAddress);
			
			Subscription[] subscriptions = new Subscription[topic_count];
			for(int i=0; i<topic_count; i++){
				subscriptions[i] = broker.subscribe(consumer, topics[i]);
				System.out.println("Subscribed to " + topics[i]);
			}
			
			Thread.sleep(1000 * 60);
			for(int i=0; i<topic_count; i++){
				subscriptions[i].unsubscribe();
				System.out.println("Unsubscribed from " + topics[i]);
			}
			consumer.stop();
		}
	}

	@Override
	public void notify(NotificationMessageHolderType message) {
		Object o = message.getMessage().getAny();
		if (o instanceof Element) {
			try {
				System.out.println(getSource((Element) o));
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static String getSource(Element doc) throws TransformerException {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		return writer.getBuffer().toString();
	}
}
