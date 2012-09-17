package de.pragma42.osgi.log.browser;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

public class WebSocketChatWebSocketServlet extends WebSocketServlet {
	private static final long serialVersionUID = 2L;
	public static final Set<ChatWebSocket> users = new CopyOnWriteArraySet<ChatWebSocket>();
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet");
	}

	@Override
	public WebSocket doWebSocketConnect(HttpServletRequest arg0, String arg1) {
		System.out.println("doWebSocketConnect");
		return new ChatWebSocket(users);
	}

	public static void sendLog(String msg) {
		for (ChatWebSocket user : users) {
			user.sendMessage(">" + msg);
		}
	}
}
