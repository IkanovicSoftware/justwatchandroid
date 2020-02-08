package com.example.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mysql.Party;
import com.example.repos.PartyRepository;

@RestController
public class PartyController {
	
	@Autowired
	private PartyRepository repository;
	
	// Takes a name and makes a group
	/**
	 * Creates new party
	 * Path to send to: "/parties/new"
	 * Type: Post
	 * @param String name Name of group
	 * @return String confirming party was made
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/parties/new")
	public String saveParty(@RequestParam String name) {
		Party party = new Party(name);
		repository.save(party);
		return "Added new party: " + party.getName();
	}
	
	// Returns a list of all parties
	/**
	 * Lists all parties
	 * Path to send to: "/parties"
	 * Type: Get
	 * @return JSON file containing party info
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/parties")
	public List<Party> getAllUsers() {
		return (List<Party>) repository.findAll();
	}
	
	// Returns all info on a particular party based on GID
	/**
	 * Gets info on certain party
	 * Path to send to: "/parties/{gid}"
	 * Type: Get
	 * @param id GID
	 * @return JSON file containing party info
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/parties/{gid}")
	public Optional<Party> findUserByID(@PathVariable("gid") Integer id) {
		return repository.findById(id);
	}
}
