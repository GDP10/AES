package net.robinjam.aes;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.TextMessage;

import net.robinjam.aes.Consumer.Callback;

import org.apache.activemq.broker.BrokerService;
import org.junit.Assert;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Stepdefs {

	private static final String BROKER_ADDRESS = "tcp://localhost:61616";

	private TestCallback consumerCallback;
	private BrokerService broker;
	
	private static class TestCallback implements Callback {
		
		public List<String> messages = new ArrayList<String>();
		
		@Override
		public void recieveMessage(TextMessage message) throws JMSException {
			this.messages.add(message.getText());
		}
		
	}
	
	@Given("^a broker is running$")
	public void broker_is_running() throws Exception {
		
		broker = new BrokerService();
		broker.addConnector("tcp://localhost:61616");
		broker.start();
		
	}
	
	@When("^I start a consumer with a temporal range of \"(.+)\"$")
	public void start_a_consumer(final List<String> consumerTimes) throws JMSException {
		
		Runnable consumerRunnable = new Runnable() {
			
			@Override
			public void run() {
				consumerCallback = new TestCallback();
				try {
					Consumer consumer = new Consumer(consumerCallback, BROKER_ADDRESS, consumerTimes.get(0), consumerTimes.get(1));
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		};
		
		Thread consumerThread = new Thread(consumerRunnable);
		consumerThread.start();
	}
	
	@And("^I start a producer with xml \"(.+)\"$")
	public void start_a_producer(List<String> notamFiles) throws Exception {
		
		Producer producer = new Producer(BROKER_ADDRESS, Stepdefs.class.getResourceAsStream(notamFiles.get(0)));

	}

	@Then("^the consumer receives events with location \"(.+)\"$")
	public void consumer_receives_events(List<String> locations) throws Exception {
	
		String messageFound;
		
		for(String location:locations) {
			
			messageFound = "";
			
			for(String message:consumerCallback.messages) {
				
				if(message.contains(location))
					messageFound = location;
				
			}
			
			Assert.assertEquals(location, messageFound);
		}
		
	}
	
	@And("^the consumer does not receive an event with location \"(.+)\"$")
	public void consumer_doesnt_receive_event(List<String> locations) throws Exception {

		String messageFound;
		
		for(String location:locations) {
			
			messageFound = "";
			
			for(String message:consumerCallback.messages) {
				
				if(message.contains(location))
					messageFound = location;
				
			}
			
			Assert.assertNotEquals(location, messageFound);
		}
		
	}
	
	@After
	public void end() throws Exception {
		System.out.println("END");
		if(broker != null)
			broker.stop();
	}
	
}