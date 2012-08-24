package de.pragma42.osgi.sim.gpio;

import java.util.Dictionary;
//import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.log.LogService;

//import de.pragma42.osgi.gpio.GpioService;

public class GpioServiceImpl extends TimerTask /*implements GpioService*/{

	private class NullLogService implements LogService{
		@Override public void log(int level, String message) {
			System.out.println(message);
		}
		@Override public void log(int level, String message, Throwable exception) {}
		@SuppressWarnings("rawtypes") 
		@Override public void log(ServiceReference sr, int level, String message) {}
		@SuppressWarnings("rawtypes")
		@Override public void log(ServiceReference sr, int level, String message, Throwable exception) {}
	}
	private EventAdmin eventAdmin=null;
	private Timer timer=null;
	private long edgeCounter = 0;
	private LogService logService = new NullLogService();
	
	public void setLogService(LogService logservice){
		this.logService = logservice;
	}
	
	public void unsetLogService(LogService logservice){
		this.logService = new NullLogService();
	}
	
	public void setEventAdmin(EventAdmin eventAdmin){
		this.eventAdmin = eventAdmin;
	}

	public void unsetEventAdmin(EventAdmin eventAdmin){
		this.eventAdmin = null;
	}

	public void activate(ComponentContext componentContext){
		timer = new Timer();
		timer.scheduleAtFixedRate(this,0,1000);
		System.out.println("start bundle "+ componentContext.getBundleContext().getBundle().getSymbolicName());
	}
	
//	@Override
//	public boolean read(int number) {
//		// TODO Auto-generated method stub
//		return false;
//	}

	@Override
	public void run() {
		java.util.Date now = new java.util.Date();
		long unixnow = now.getTime();
		edgeCounter++;
		Dictionary<String,Long> eventProperties = new java.util.Hashtable<String,Long>();
		eventProperties.put("channel", (long)1);
		eventProperties.put("time", unixnow);
		eventProperties.put("level", (long)0);
		eventProperties.put("counter",edgeCounter);
		Event event = new Event("de/pragma42/osgi/gpio",eventProperties);
		eventAdmin.sendEvent(event);
		logService.log(LogService.LOG_DEBUG, "Timer fired");
	}
}
