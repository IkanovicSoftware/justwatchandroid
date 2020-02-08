package com.example.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mysql.User;
import com.example.repos.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository repository;
	
	// Takes a username, password, and DOB and creates a new user
	/**
	 * Creates new user
	 * Path to send to: "/users/new"
	 * Type: Post
	 * @param username Username
	 * @param password Password
	 * @param dob Date of birth
	 * @return String with confirmation
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/users/new")
	public String saveUser(@RequestParam String username, @RequestParam String password, @RequestParam String dob) {
		User user = new User(username, password, dob);
		if(repository.findByUsername(user.getUsername()).size() == 0) {
			repository.save(user);
		}else {
			return "username taken";
		}
		return "Added new user: " + user.getUsername();
	}
	
	// Returns a list of all users and their attributes.
	/**
	 * Lists all users
	 * Path to send to: "/users"
	 * Type: Get
	 * @return JSON file containing all users
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/users")
	public List<User> getAllUsers() {
		return (List<User>) repository.findAll();
	}
	
	// Returns a user when given a UID
	/**
	 * Returns a user when given a UID
	 * Path to send to: "/users/uid/{uid}"
	 * Type: Get
	 * @param id uid
	 * @return JSON file containing user info
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/users/uid/{uid}")
	public Optional<User> findUserByID(@PathVariable(value = "uid") Integer id) {		
		return repository.findById(id);
	}
	
	// Returns a user when given a username
	/**
	 * Returns a user when given a username
	 * Path to send to: "/users/username/{username}"
	 * Type: Get
	 * @param username Username
	 * @return JSON object of user
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/users/username/{username}")
	public List<User> findUserByUsername(@PathVariable(value = "username") String username) {		
		return repository.findByUsername(username);
	}
	
	// Takes in a username and password and returns whether or not they match
	/**
	 * Takes in a username and password and returns whether or not they match
	 * Path to send to: "/login"
	 * Type: Post
	 * @param username Username
	 * @param password Password
	 * @return String indicating success or failure
	 */
	@RequestMapping(method = RequestMethod.POST, path = "/login")
	public String userLogin(@RequestParam String username, @RequestParam String password) {
		User user = new User(username, password);
		List<User> userwp = repository.findByUsernameContaining(user.getUsername());
		
		if (userwp.size() > 0) {
			User sqluser = userwp.get(0);
			return sqluser.getPassword().equals(user.getPassword()) ? "success" : "failure";
		} else {
			return "invaliduser";
		}
		
	}
}
