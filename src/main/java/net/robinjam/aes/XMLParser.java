package net.robinjam.aes;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {

	public static List<Element> parseNOTAMS(InputStream inputStream) throws SAXException, IOException, ParserConfigurationException {
		
		List<Element> notams = new ArrayList<Element>();
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		Document doc = dbf.newDocumentBuilder().parse(inputStream);
		
		NodeList events = doc.getElementsByTagNameNS("http://www.aixm.aero/schema/5.1/event", "Event");
	
		for (int i = 0; i < events.getLength(); ++i) {
			Element event = (Element) events.item(i);
			notams.add(event);
		}
		
		return notams;
		
	}
	
}
