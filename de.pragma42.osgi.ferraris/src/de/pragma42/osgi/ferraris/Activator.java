package de.pragma42.osgi.ferraris;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.service.event.EventAdmin;
public class Activator implements BundleActivator {

	private static BundleContext context;
//	private ServiceTracker eventServiceTracker;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		System.out.println("start bundle "+ context.getBundle().getSymbolicName());
	
//		// create a tracker and track the service
//		eventServiceTracker = new ServiceTracker(context, EventAdmin.class.getName(), null);
//		eventServiceTracker.open();
//
//		// have a service listener to implement the whiteboard pattern
//	    fContext.addServiceListener(this, "(objectclass=" + Dictionary.class.getName() + ")");
//		
//		// grab the service
//		EventAdmin service = (EventAdmin) eventServiceTracker.getService();
//		service.
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
