package de.pragma42.osgi.sim.gpio;

import java.util.Dictionary;
//import java.util.HashMap;
import java.util.Random;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.log.LogService;

//import de.pragma42.osgi.gpio.GpioService;


public class GpioServiceImpl implements Runnable /*implements GpioService*/{
	private Random random = new Random();
    private Thread thread;
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
		thread = new Thread(this);
		thread.start();
		System.out.println("start bundle "+ componentContext.getBundleContext().getBundle().getSymbolicName());
	}
	
//	@Override
//	public boolean read(int number) {
//		// TODO Auto-generated method stub
//		return false;
//	}

	@Override
	public void run() {
		Dictionary<String,Long> eventProperties = new java.util.Hashtable<String,Long>();
		eventProperties.put("channel", (long)1);
		eventProperties.put("level", (long)0);
		while(true){
			java.util.Date now = new java.util.Date();
			long unixnow = now.getTime();
			edgeCounter++;
			eventProperties.put("time", unixnow);
			eventProperties.put("counter",edgeCounter);
			Event event = new Event("de/pragma42/osgi/gpio",eventProperties);
			eventAdmin.sendEvent(event);
			logService.log(LogService.LOG_DEBUG, "Timer fired from sim");
			long delay = random.nextInt(5001);
//			System.out.println(String.format("delay=%d",delay));
			try{
				Thread.sleep(delay);
			}catch(Exception e){};
		}
	}
}
