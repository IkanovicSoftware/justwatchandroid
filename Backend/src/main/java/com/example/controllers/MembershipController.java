package com.example.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mysql.Friend;
import com.example.mysql.Membership;
import com.example.repos.MembershipRepository;

@RestController
public class MembershipController {
	
	@Autowired
	private MembershipRepository repository;
	
	// Takes gid, uid, isHost, and isMod and adds user to group
	/**
	 * Creates new membership in party
	 * Path to send to: "/membership/new"
	 * Type: Post
	 * @param gid int gid
	 * @param uid int uid
	 * @param isHost Boolean if they are the host of the group
	 * @param isMod Boolean if they are a mod of the group
	 * @return A string confirming it went through
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/membership/new")
	public String saveParty(@RequestParam int gid, @RequestParam int uid, @RequestParam boolean isHost, @RequestParam boolean isMod) {
		Membership membership = new Membership();
		membership.setGID(gid);
		membership.setUID(uid);
		membership.setIsHost(isHost);
		membership.setIsMod(isMod);
		repository.save(membership);
		return "Added new membership: " + membership.getGID();
	}
	
	// Returns a list of all users and what groups they are in
	/**
	 * Lists all users and their groups
	 * Path to send to: "/membership"
	 * Type: Get
	 * @return JSON file of all users and their groups
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/membership")
	public List<Membership> getAllUsers() {
		return (List<Membership>) repository.findAll();
	}
	
	/**
	 * Lists all members of a group
	 * Path to send to: "/membership/{gid}"
	 * Type: Get
	 * @param id int GroupID
	 * @return JSON file with all members of group
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/membership/{gid}")
	public List<Membership> findPartyByID(@PathVariable("gid") Integer id) {
		return (List<Membership>) repository.findAllByGid(id);
	}
	
	// Returns a list of all groups a user is in
	/**
	 * Lists all parties a user is in
	 * Path to send to: "/membership/user/{UID}"
	 * Type: Get
	 * @param id UserID
	 * @return JSON file containing membership info for each group
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/membership/user/{uid}")
	public List<Membership> findUserByID(@PathVariable("uid") Integer id) {
		return (List<Membership>) repository.findAllByUid(id);
	}
}
