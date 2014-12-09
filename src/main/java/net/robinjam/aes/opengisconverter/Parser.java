package net.robinjam.aes.opengisconverter;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Parser {

	public static void main(String args[]) {
		System.out.println(new Parser().parse("testfilter.xml"));
	}
	
	public String parse(String path) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File(path));
			NodeList nList = doc.getElementsByTagName("ogc:Filter");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Element e = (Element) nList.item(temp);
				if (e.getNodeType() == Node.ELEMENT_NODE) {
					String[] lowerCornerStr = e.getElementsByTagName("gml:lowerCorner").item(0).getTextContent().split("\\s+");
					float[] lowerCorner = {Float.valueOf(lowerCornerStr[0]), Float.valueOf(lowerCornerStr[1])};
					String[] upperCornerStr = e.getElementsByTagName("gml:upperCorner").item(0).getTextContent().split("\\s+");
					float[] upperCorner = {Float.valueOf(upperCornerStr[0]), Float.valueOf(upperCornerStr[1])};
					
					return "lat>" + upperCorner[0] + " AND lat<" + lowerCorner[0] + " AND lon>" + lowerCorner[1] + " AND lon<" + upperCorner[1];
				}
			}

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
