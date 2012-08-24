package de.pragma42.osgi.ferraris;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class GpioReceiveComponent implements EventHandler{

	@Override
	public void handleEvent(Event event) {
		System.out.println("got event");
	}
}
