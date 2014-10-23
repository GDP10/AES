package net.robinjam.aes;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType;
import org.oasis_open.docs.wsn.b_2.NotificationMessageHolderType.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ConsumerTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testNotify() throws ParserConfigurationException {
		Consumer consumer = new Consumer();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		System.setOut(new PrintStream(out));
		NotificationMessageHolderType messageHolder = new NotificationMessageHolderType();
		Message message = new Message();
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		doc.appendChild(doc.createElement("test"));
		message.setAny(doc.getDocumentElement());
		messageHolder.setMessage(message );
		consumer.notify(messageHolder);
		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><test/>\n";
		String actual = out.toString();
		assertEquals(expected, actual);
	}

	@Test
	public void testGetSource() throws ParserConfigurationException, TransformerException {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		doc.appendChild(doc.createElement("test"));
		String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><test/>";
		String actual = Consumer.getSource(doc.getDocumentElement());
		assertEquals(expected, actual);
	}

}
