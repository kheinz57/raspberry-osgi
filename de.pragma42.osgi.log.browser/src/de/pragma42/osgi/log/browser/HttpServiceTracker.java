package de.pragma42.osgi.log.browser;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

@SuppressWarnings("rawtypes")
public class HttpServiceTracker extends ServiceTracker {

	@SuppressWarnings("unchecked")
	public HttpServiceTracker(BundleContext context) {
		super(context, HttpService.class.getName(), null);
	}

	public Object addingService(ServiceReference reference) {
		@SuppressWarnings("unchecked")
		HttpService httpService = (HttpService) super.addingService(reference);
		if (httpService == null)
			return null;

		try {
			httpService.registerServlet("/WebSocketChat", new WebSocketChatWebSocketServlet(), null,null);
			httpService.registerResources("/", "/res/html", null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return httpService;
	}

	@SuppressWarnings("unchecked")
	public void removedService(ServiceReference reference, Object service) {
		HttpService httpService = (HttpService) service;

		System.out.println("Unregistering /simple");
		httpService.unregister("/simple");

		super.removedService(reference, service);
	}
}

