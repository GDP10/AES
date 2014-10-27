package net.robinjam.aes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.cxf.wsn.client.NotificationBroker;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Producer {

	private NotificationBroker broker;
	
	public static void main(String[] args) throws Exception {
		if(args.length != 2) {
			System.err.println("Usage: mvn -Pproducer package exec:java -Dexec.args=\"[Broker Address] [Delta Location]\"");
			System.exit(1);
		} else {
			NotificationBroker broker = new NotificationBroker(args[0]);
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			Document doc = dbf.newDocumentBuilder().parse(new FileInputStream(args[1]));
			System.out.println("Loaded XML delta from " + args[1]);
			
			NodeList events = doc.getElementsByTagNameNS("http://www.aixm.aero/schema/5.1/event", "Event");
			for (int i = 0; i < events.getLength(); ++i) {
				Element event = (Element) events.item(i);
				String topic = event.getElementsByTagNameNS("http://www.aixm.aero/schema/5.1/event", "location").item(0).getTextContent();
				broker.notify(topic, event);
				System.out.println("Sent event on topic " + topic);
			}
		}
	}
	
	public Producer(String address) {
		broker = new NotificationBroker(address);
	}
	
	public void sendXML(String xmlFile) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		Document doc = dbf.newDocumentBuilder().parse(new FileInputStream(xmlFile));
		System.out.println("Loaded XML delta from " + xmlFile);
		
		NodeList events = doc.getElementsByTagNameNS("http://www.aixm.aero/schema/5.1/event", "Event");
		for (int i = 0; i < events.getLength(); ++i) {
			Element event = (Element) events.item(i);
			String topic = event.getElementsByTagNameNS("http://www.aixm.aero/schema/5.1/event", "location").item(0).getTextContent();
			broker.notify(topic, event);
			System.out.println("Sent event on topic " + topic);
		}
	}
}
