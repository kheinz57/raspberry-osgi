/*******************************************************************************
 * Copyright (c) 2007-2008 Wuetherich/Hartmann/Kolb/Luebken.
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Gerd Wuetherich, Nils Hartmann, Bernd Kolb, Matthias Luebken -
 * initial implementation
 ******************************************************************************/
 package de.pragma42.osgi.log.browser;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;

public class LoggerComponent implements LogListener {

	private LogReaderService logReaderService;

	public void setLogReaderService(LogReaderService logReaderService) {
		this.logReaderService = logReaderService;
	}

	public void unsetLogReaderService(LogReaderService logReaderService) {
		this.logReaderService = null;
	}

	protected void activate(ComponentContext componentContext) {
		logReaderService.addLogListener(this);
	}

	protected void deactivate(ComponentContext componentContext) {
		logReaderService.removeLogListener(this);
	}

	/**
	 * Gibt den uebergebenen Log-Eintrag auf der Konsole aus
	 */
	public void logged(LogEntry entry) {

		// Log-Informationen und -Nachricht in String einfuegen
		String log = String.format("{%s} <%s> %s", getLevelAsString(entry
		    .getLevel()), entry.getBundle().getSymbolicName(), entry.getMessage());

		// Ausgeben
		System.out.println(log);
		WebSocketChatWebSocketServlet.sendLog(log);

		// Stacktrace ausgeben, wenn Exception vorhanden
		Throwable exception = entry.getException();
		if (exception != null) {
			exception.printStackTrace();
		}
	}

	/**
	 * Gibt das uebergebene LogLevel aus dem LogEntry als String zurueck
	 * 
	 * @param level
	 *          Ein Log-Level
	 * @return Das Log-Level als String
	 */
	protected String getLevelAsString(int level) {

		switch (level) {
		case LogService.LOG_DEBUG:
			return "DEBUG";
		case LogService.LOG_INFO:
			return "INFO";
		case LogService.LOG_WARNING:
			return "WARN";
		case LogService.LOG_ERROR:
			return "ERROR";
		default:
		}
		return "UNKNOWN";
	}
}