package de.pragma42.osgi.ferraris;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogService;

public class LogWriterComponent {

	public static void log(int level, String message) {
		if (logService != null) {
			logService.log(level, message);
		}
	}

	public static void log(int level, String message, Throwable exception) {
		if (logService != null) {
			logService.log(level, message, exception);
		}else{
			System.out.println("LogService not connectd! " + message);
		}
	}

	private static LogService logService = null;

	public void setLogService(LogService logSrv) {
		logService = logSrv;
	}

	public void unsetLogService(LogService logSrv) {
		logService = null;
	}

	protected void activate(ComponentContext context) {
		log(LogService.LOG_INFO, "Komponente wird aktiviert");
	}

	protected void deactivate(ComponentContext context) {
		log(LogService.LOG_INFO, "Komponente wird deaktiviert");
	}
}
