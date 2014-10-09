package net.robinjam.aes;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.cxf.wsn.client.NotificationBroker;
import org.w3c.dom.Document;

public class Main {

	public static void main(String[] args) throws Exception {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(Main.class.getResourceAsStream("/event.xml"));
		NotificationBroker broker = new NotificationBroker("http://localhost:9000/wsn/NotificationBroker");
		broker.notify("MyTopic", doc.getDocumentElement());
	}
}
