package com.example.tests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import javax.websocket.Session;

import org.junit.Test;

import com.example.watching.VideoServer;
import com.example.watching.VideoUser;



public class MockitoTests {
	
	@Test
	public void addUsers() {
		VideoServer service = new VideoServer("9");
		Session s1 = mock(Session.class);
		Session s2 = mock(Session.class);
		VideoUser user1 = new VideoUser(s1, "Kyle");
		VideoUser user2 = new VideoUser(s2, "Jordan");
		
		service.addVideoUser(user1);
		assertEquals(1, service.usersWatching());
		service.addVideoUser(user2);
		assertEquals(2, service.usersWatching());
		service.removeVideoUser(s1);
		assertEquals(1, service.usersWatching());
		service.removeVideoUser(s2);
		assertEquals(0, service.usersWatching());
	}
	
}
