package net.robinjam.aes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.apache.cxf.wsn.services.Service;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;



public class Stepdefs {
	
	protected static class NotificationHandler {
		
		public volatile List<Element> notifications;
		
		public NotificationHandler() {
			this.notifications = new ArrayList<Element>();
		}
		
		public void notify(Element o) {
			this.notifications.add(o);
		}
		
	}
	
	private String brokerAddress;
	private NotificationHandler notificationHandler;

	@Given("^a broker is running$")
	public void broker_is_running() throws Exception {
		
		Runnable broker = new Runnable() {
			
			@Override
			public void run() {
				try {
					new Service(new String[]{}).start();
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(1);
				}
			}
		};
		
		Thread brokerThread = new Thread(broker);
		brokerThread.start();
		
	}
	
	@When("^I start a consumer with topics \"(.+)\"$")
	public void start_a_consumer(final List<String> topics) throws Exception {

		brokerAddress = "http://localhost:9000/wsn/NotificationBroker";
		String bindAddress = "http://localhost:9001/TestConsumer";
		
		notificationHandler = new NotificationHandler();
		
		Consumer.getNew(brokerAddress, bindAddress, topics.toArray(new String[topics.size()]), notificationHandler);
		
	}
	
	@And("^I start a producer with xml \".+.xml\"$")
	public void start_a_producer() throws Exception {
		
		Producer producer = new Producer(brokerAddress);
		
		producer.sendXML(Stepdefs.class.getResourceAsStream("/delta.xml"));
		
	}

	@Then("^the consumer receives events with locations \"(.+)\"$")
	public void consumer_receives_events(List<String> topics) throws Exception {
		System.err.println("Received Messages " + notificationHandler.notifications.size());
		
		for(int i = 0; i < topics.size(); i++) {
			
			String topic = topics.get(i);
			
			for(Element element:notificationHandler.notifications) {
				NodeList elementsByTagName = element.getElementsByTagNameNS("http://www.aixm.aero/schema/5.1/event", "location");
				
				org.junit.Assert.assertEquals(topic, elementsByTagName.item(0).getTextContent());
					
			}
		}
		
	}
	
	@And("^the consumer does not receive an event with location \"(.+)\"$")
	public void consumer_doesnt_receive_event(List<String> topics) throws Exception {
		System.err.println("Received Messages " + notificationHandler.notifications.size());
		
		for(int i = 0; i < topics.size(); i++) {
			
			String topic = topics.get(i);
			
			for(Element element:notificationHandler.notifications) {
				NodeList elementsByTagName = element.getElementsByTagNameNS("http://www.aixm.aero/schema/5.1/event", "location");
				
				org.junit.Assert.assertNotEquals(topic, elementsByTagName.item(0).getTextContent());
					
			}
		}
	}
	
	@After
	public void end() {
	}
	
}