package com.example.watching;

import java.io.IOException;

import javax.websocket.Session;

public class VideoUser {
	
	private Session session;
	private String username;
	
	private int currentTime;
	private boolean paused;
	
	public VideoUser(Session session, String username) {
		this.session = session;
		this.username = username;
		
		this.currentTime = 0;
		this.paused = false;
	}
	
	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
	}
	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	
	public String getUsername() {
		return username;
	}
	public Session getSession() {
		return session;
	}
	public int getCurrentTime() {
		return currentTime;
	}
	public boolean isPaused() {
		return paused;
	}
	
	/**
	 * Sends message to client
	 * @param message - Message to send
	 */
	public void sendMessage(String message) {
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			System.out.println("Failed to send message: " + e.getMessage());
		}
	}
}
