package com.example.sockets;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/login/{UID}")
public class _ArchiveTest {
	
	// Container for all sessions on the server socket
	private static Set<_ArchiveTest> logins = new CopyOnWriteArraySet<>();
	
	@OnOpen
	public void onOpen(Session session) throws IOException {		
		// Add to list of logins
		logins.add(this);
		
		// Return connected message
		sendMessageToUser(session, "Connected");
	}
	
	@OnMessage
	public void onMessage(Session session, String message) throws IOException {
		System.out.println("Got message \"" + message + "\" from client " + session.getId());
	}
	
	@OnClose
	public void onClose(Session session) throws IOException {
		System.out.println("Client " + session.getId() + " connection closed");
	}
	
	@OnError
	public void onError(Session session, Throwable throwable) {
		System.out.println("Client " + session.getId() + " errored. Error: " + throwable.getMessage());
	}
	
	
	private static void sendMessageToUser(Session session, String msg) throws IOException {
		session.getBasicRemote().sendText(msg);
	}
	
	/*
	private static void sendMessageToAll(String message) throws IOException, EncodeException {
		logins.forEach(login -> {
            synchronized (login) {
                try {
                    sendMessageToUser(login.getSession(), message);
                } catch (IOException e) {
                    
                }
            }
        });
	} */
}
