package net.robinjam.aes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {

	public static List<Element> parseNOTAMS(InputStream inputStream) throws SAXException, IOException, ParserConfigurationException {
		
		List<Element> notams = new ArrayList<Element>();
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		Document doc = dbf.newDocumentBuilder().parse(inputStream);
		
		NodeList events = doc.getElementsByTagNameNS("http://www.aixm.aero/schema/5.1/event", "Event");
		
		//parse airport locations
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc1 = db.parse(new File("src/test/resources/AirportLocations.xml"));
		NodeList nList = doc1.getElementsByTagName("aixm:AirportHeliportTimeSlice");
		
		for (int i = 0; i < events.getLength(); ++i) {
			Element event = (Element) events.item(i);
			event.getNodeName();
			Node designator = event.getElementsByTagName("designator").item(0);
			for (int j = 0; j < nList.getLength(); j++) {
				Element element = (Element) nList.item(j);
				Node currentAirport = element.getElementsByTagName("aixm:designator").item(0);
				if (currentAirport.getTextContent().equals(designator.getTextContent()))
				{
					Node position = element.getElementsByTagName("gml:pos").item(0);
					Node imported = doc.adoptNode(position);
					designator.getParentNode().appendChild(imported);
					break;
				}
			}

 			
			notams.add(event);
		}
		
		return notams;
		
	}
	
	public static List<Element> parseElement(InputStream inputStream, String namespace, String name) throws SAXException, IOException, ParserConfigurationException {
		
		List<Element> notams = new ArrayList<Element>();
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		Document doc = dbf.newDocumentBuilder().parse(inputStream);
		
		NodeList events = doc.getElementsByTagNameNS(namespace, name);
	
		for (int i = 0; i < events.getLength(); ++i) {
			Element event = (Element) events.item(i);
			notams.add(event);
		}
		
		return notams;
		
	}
	
}
