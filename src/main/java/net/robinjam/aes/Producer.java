package net.robinjam.aes;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.cxf.wsn.client.NotificationBroker;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Producer {

	public static void main(String[] args) throws Exception {
		if(args.length != 1) {
			System.err.println("Usage: mvn -Pproducer package exec:java -Dexec.args \"[Broker Address]\"");
			System.exit(1);
		} else {
			NotificationBroker broker = new NotificationBroker(args[0]);
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			Document doc = dbf.newDocumentBuilder().parse(Producer.class.getResourceAsStream("/delta.xml"));
			
			NodeList events = doc.getElementsByTagNameNS("http://www.aixm.aero/schema/5.1/event", "Event");
			for (int i = 0; i < events.getLength(); ++i) {
				Element event = (Element) events.item(i);
				String topic = event.getElementsByTagNameNS("http://www.aixm.aero/schema/5.1/event", "location").item(0).getTextContent();
				broker.notify(topic, event);
			}
		}
	}
}
