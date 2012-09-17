package de.pragma42.osgi.log.browser;

import org.eclipse.jetty.server.Server;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private HttpServiceTracker serviceTracker;

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		System.out.println("Hello World!!");
		serviceTracker = new HttpServiceTracker(context);
		serviceTracker.open();
		Server server;
		server = new Server();
		System.err.println("Starting jetty "
				+ server.getClass().getPackage().getImplementationVersion()
				+ " ...");
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		System.out.println("Goodbye World!!");
		serviceTracker.close();
		serviceTracker = null;
	}
}
