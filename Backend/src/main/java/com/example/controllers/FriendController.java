package com.example.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mysql.Friend;
import com.example.repos.FriendRepository;

@RestController
public class FriendController {
	
	@Autowired
	private FriendRepository repository;
	
	// Takes two UIDs and makes a friend relationship
	/**
	 * Takes two UIDs and creates a friend relation between them.
	 * Path to send to: "/friend/new"
	 * Type: Post
	 * @param uid Your UID
	 * @param fid Friend's UID
	 * @return A string saying it worked
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/friend/new")
	public String saveFriend(@RequestParam int uid, @RequestParam int fid) {
		Friend friend = new Friend();
		Friend friend2 = new Friend();
		friend.setUID(uid);
		friend.setFID(fid);
		friend2.setUID(fid);
		friend2.setFID(uid);
		repository.save(friend);
		repository.save(friend2);
		return "Added new friend: " + friend.getFID();
	}
	
	// Returns a list of all friend connections
	/**
	 * Returns list of all friend relations
	 * Path to send to: "/friend"
	 * Type: Get
	 * @return List of all friend relations
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/friend")
	public List<Friend> getAllUsers() {		
		return (List<Friend>) repository.findAll();
	}
	
	// Returns a list of all friends of a user
	/**
	 * Get all friends of a specific user
	 * Path to send to: "/friend/{UID}"
	 * Type: Get
	 * @param id UID of user you want list of
	 * @return List of Friend JSON objects
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/friend/{uid}")
	public List<Friend> findUserByID(@PathVariable("uid") int id) {
		return (List<Friend>) repository.findByUid(id);
	}
}
