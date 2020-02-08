package com.example.sockets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import com.example.watching.VideoServer;
import com.example.watching.VideoUser;


@ServerEndpoint(value = "/websockets/watch/{username}/{sid}")
@Component
public class SocketController {
	private static List<VideoServer> servers = new ArrayList<>();
	private static HashMap<String, String> IDToSID = new HashMap<String, String>();
	private static HashMap<String, Integer> SIDToServer = new HashMap<String, Integer>();
	
	private boolean sessionConnectedToServer(Session session, VideoServer server) {		
		return !(server.getUserFromSession(session) == null);
	}
	private boolean userConnectedToServer(String username, VideoServer server) {
		return !(server.getUserFromUsername(username) == null);
	}
	
	
	@OnOpen
	public void onOpen(Session session, @PathParam(value = "username") String username, @PathParam(value = "sid") String serverid) throws IOException {
		VideoUser newUser = new VideoUser(session, username);
		
		if (SIDToServer.get(serverid) == null) {
			// Create new server
			VideoServer newServer = new VideoServer(serverid);
			
			// Grab index in servers
			int serverIndex = servers.size();
			
			// Map session ID to SID
			IDToSID.put(session.getId(), serverid);
			// Map SID to place index in servers
			SIDToServer.put(serverid, serverIndex);
			
			// Add to list of servers
			servers.add(newServer);
			
			// Add user to server
			newServer.addVideoUser(newUser);
			
			//System.out.println(username + "_s-" + session.getId() + " created server " + serverid);
		} else {
			// Get server from given SID
			VideoServer vServer = servers.get(SIDToServer.get(serverid));
			
			if (userConnectedToServer(username, vServer)) {
				System.out.println("Error adding user to server" + serverid + ": User already exists on server");
			} else {
				// Map session ID to SID
				IDToSID.put(session.getId(), serverid);
				
				// Add user to server
				vServer.addVideoUser(newUser);
				
				//System.out.println(username + "_s-" + session.getId() + " joined server " + serverid);
			}
		}
	}
	
	@OnMessage
	public void onMessage(Session session, String message) throws IOException {	
		// Get Users server 
		VideoServer vServer = servers.get(SIDToServer.get(IDToSID.get(session.getId())));
		
		// Send message to server
		vServer.receiveMessage(session, message);
	}
	
	@OnClose 
	public void onClose(Session session) throws IOException {
		VideoServer vServer = servers.get(SIDToServer.get(IDToSID.get(session.getId())));
		String SID = vServer.getGroupID();
		
		// Remove session from server
		vServer.removeVideoUser(session);
		
		// Remove ID from hashmap
		IDToSID.remove(session.getId());
		
		// Remove server if server is empty
		if (vServer.usersWatching() < 1) {
			// Remove SID from hashmap
			SIDToServer.remove(SID);
			
			// Update HashIDs above removed hash 
			//   -- Not totally speed efficient but works for a project this scale - might update later
			int nSID = Integer.parseInt(SID);
			for (Map.Entry<String, Integer> map : SIDToServer.entrySet()) {
				String key = map.getKey();
				Integer val = map.getValue();
				
				if (val > nSID) SIDToServer.put(key, val - 1);
			}
			
			// Remove server from server list
			System.out.println("Closing server " + vServer.getGroupID());
			servers.remove(vServer);
			
			System.out.println("There are " + servers.size() + " servers running now");
		}
	}
	
	@OnError
	public void onError(Session session, Throwable throwable) {
		throwable.printStackTrace(System.out);
	}
}
