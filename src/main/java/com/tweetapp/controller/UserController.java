package com.tweetapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.models.User;
import com.tweetapp.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1.0/tweets")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users/all")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@PostMapping("/register")
	public User registerUser(@RequestBody User theUser) {
		return userService.save(theUser);
	}

	// Login for User
	@PostMapping("/login")
	public User checkLogin(@RequestBody User loginInput) {
		return userService.login(loginInput);
	}

	// Searches and lists all users that is associated with username
	@GetMapping("/user/search/{username}")
	public List<User> searchUser(@PathVariable String username) {
		return userService.getUsersBySearchingUsername(username);
	}

	// Retrieves the password associated with the username
	@PostMapping("/{username}/forgot")
	public void updateUserPassword(@PathVariable String username, @RequestBody String password) {
		userService.updateUserPassword(username, password);
	}

}
