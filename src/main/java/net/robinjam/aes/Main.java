package net.robinjam.aes;

import javax.xml.ws.Endpoint;

public class Main {
	Endpoint ep;
	
	protected Main() throws Exception {
		ep = Endpoint.publish("http://localhost:8080/aes/producer", new NotificationProducer());
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				shutdown();
			}
		});
	}
	
	public void shutdown() {
		if (ep != null) {
			ep.stop();
			ep = null;
		}
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("Server starting");
		Main m = new Main();
		System.out.println("Server started");
		try {
			Thread.sleep(20 * 60 * 1000);
			System.out.println("Server exiting");
		}
		finally {
			m.shutdown();
		}
	}
}
