package net.robinjam.aes;

import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.cxf.wsn.client.Consumer;
import org.apache.cxf.wsn.client.NotificationBroker;
import org.apache.cxf.wsn.client.Subscription;
import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;
import org.w3c.dom.Element;

public class Client implements Consumer.Callback {

	public static void main(String[] args) throws Exception {
		Consumer consumer = new Consumer(new Client(), "http://localhost:9001/TestConsumer");
		NotificationBroker broker = new NotificationBroker("http://localhost:9000/wsn/NotificationBroker");
		Subscription subscription = broker.subscribe(consumer, "MyTopic");
		Thread.sleep(1000 * 60);
		subscription.unsubscribe();
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
	
	private String getSource(Element doc) throws TransformerException {
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(doc), new StreamResult(writer));
		return writer.getBuffer().toString();
	}
}
