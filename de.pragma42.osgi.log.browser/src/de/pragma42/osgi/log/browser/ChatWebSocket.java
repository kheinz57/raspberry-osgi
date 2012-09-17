package de.pragma42.osgi.log.browser;

import java.util.Set;
import org.eclipse.jetty.websocket.WebSocket.OnTextMessage;
import java.util.Timer;
import java.util.TimerTask;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatWebSocket implements OnTextMessage{
	private Connection connection;
	private Set<ChatWebSocket> users;

	public ChatWebSocket(Set<ChatWebSocket> users) {
		this.users = users;
		System.out.println("new ChatWebsocket");
	}

	public void onMessage(String data) {
		for (ChatWebSocket user : users) {
			sendMessage(">"+data);
		}
	}

	public void sendMessage(String data) {
		try {
			connection.sendMessage(">"+data);
		} catch (Exception e) {
		}
	}

	@Override
	public void onOpen(Connection connection) {
		this.connection = connection;
		users.add(this);
		System.out.println("ChatWebsocket open");
	}

	@Override
	public void onClose(int closeCode, String message) {
		users.remove(this);
		System.out.println("ChatWebsocket close");
	}
}
