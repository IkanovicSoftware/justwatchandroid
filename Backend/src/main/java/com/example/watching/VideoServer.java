package com.example.watching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.websocket.Session;

public class VideoServer {
	
	// Server's id connected to Party GID
	private String groupID;
	// List of users watching on this server
	private List<VideoUser> watching = new ArrayList<>();
	
	private HashMap<String, Integer> IDToIndex = new HashMap<String, Integer>();
	
	public VideoServer(String groupID) {
		this.groupID = groupID;
		
		// Start thread to sync all users
		Thread syncth = new Thread(new SyncVideoThread());
		syncth.start();
	}
	
	
	/***************************************************************\
	 * User Methods
	\***************************************************************/
	
	/**
	 * Adds new user to server
	 * @param newUser - Session of user to add
	 */
	public void addVideoUser(VideoUser newUser) {
		// User already exists on server
		if (IDToIndex.get(newUser.getSession().getId()) != null) return;
		
		// Map session id to index in watching
		IDToIndex.put(newUser.getSession().getId(), watching.size());
		
		// Add user to watching
		watching.add(newUser);
	}
	/**
	 * Removes user from server
	 * @param session - session of user to remove
	 */
	public void removeVideoUser(Session session) {
		// Return if session doesn't exist
		if (IDToIndex.get(session.getId()) == null) return;
		
		// Get session index in watching
		int sessionIndex = IDToIndex.get(session.getId());
		
		// Remove from server
		watching.remove(sessionIndex);
	}
	/**
	 * Returns User on server from its session
	 * @param session - Session of the desired user
	 * @return the object of VideoUser corresponding to the session on the server
	 */
	public VideoUser getUserFromSession(Session session) {
		return IDToIndex.get(session.getId()) == null ? null : watching.get(IDToIndex.get(session.getId()));
	}
	/**
	 * Returns User on server given its username
	 * @param username - Username of desired user
	 * @return desired session
	 */
	public VideoUser getUserFromUsername(String username) {
		for (VideoUser u : watching) {
			if (u.getUsername().equals(username)) {
				return u;
			}
		}
		
		return null;
	}
	
	
	/***************************************************************\
	 * Server Methods
	\***************************************************************/
	
	/**
	 * Returns This server's ID
	 * @return groupID
	 */
	public String getGroupID() {
		return groupID;
	}
	/**
	 * Returns the number of users currently watching
	 * @return current size of watching
	 */
	public int usersWatching() {
		return watching.size();
	}
	
	
	
	
	/***************************************************************\
	 * Watching Methods
	\***************************************************************/
	
	// **Threads**
	/**
	 * Thread for handling syncing users
	 * @author Jordan
	 *
	 */
	private class SyncVideoThread implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					int sync = checkSynced();
					if (checkSynced() > 0) {
						pauseAll();
						
						long ctime = System.currentTimeMillis();
						long ntime = ctime + 1500;
						
						for (VideoUser vu : watching) {
							vu.sendMessage("timeat " + ntime + " " + sync);
						}
					}
				
					Thread.sleep(500);
				} catch (InterruptedException e) {
					System.out.println("Error: " + e.getMessage());
				}
			}
		}
	}
	
	/**
	 * Pauses all users watching
	 */
	public void pauseAll() {
		for (VideoUser vu : watching) {
			vu.sendMessage("pause");
		}
	}
	/**
	 * Sets all watching to set time
	 * @param time - Time to seek to 
	 */
	public void updateAllTime(int time) {
		for (VideoUser vu : watching) {
			vu.sendMessage("time " + Integer.toString(time));
		}
	}
	
	/**
	 * Checks if any users lagged behind in watch time
	 * @return time of user thats behind, -1 if all are in sync
	 */
	public int checkSynced() {
		for (int i = 0; i < watching.size() - 1; i++) {
			if (i < watching.size() - 1) {
				VideoUser u1 = watching.get(i);
				VideoUser u2 = watching.get(i + 1);
				
				int t1 = u1.getCurrentTime();
				int t2 = u1.getCurrentTime();
				
				if (t1 < t2) {
					System.out.println(u1.getUsername() + " is behind " + u2.getUsername());
					return t1;
				} else if (t1 > t2) {
					System.out.println(u2.getUsername() + " is behind " + u1.getUsername());
					return t2;
				}
			}
		}
		
		return -1;
	}
	
	/**
	 * Main controller for handling messages to each video server
	 * @param session - Session that sent message
	 * @param message - Message that the session sent
	 */
	public void receiveMessage(Session session, String message) { 
		VideoUser user = watching.get(IDToIndex.get(session.getId()));
		
		System.out.println(user.getUsername() + " said " + message + " on server " + groupID);
		
		String msgs[] = message.split(" ");
		String cmd = msgs[0];
		
		switch (cmd) {
			case "list":
				for (VideoUser vu : watching) {
					System.out.print(vu.getUsername() + " ");
				}
				System.out.println("are watching on server " + groupID);
				break;
			case "time":
				// Update current users time
				int time = Integer.parseInt(msgs[1]);
				user.setCurrentTime(time);
				
				break;
			case "pause":
				// Update user paused state
				boolean paused = msgs[1].equals("true");
				user.setPaused(paused);
				
				if (paused) {
					pauseAll();
				}
				break;
			default: break;
		}
	}
	
}
