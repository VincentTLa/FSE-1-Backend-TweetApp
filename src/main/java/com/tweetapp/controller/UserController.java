package com.tweetapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.customexception.ResourceNotFoundException;
import com.tweetapp.models.User;
import com.tweetapp.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1.0/tweets")
public class UserController {

	final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	/****************
	 ***** USERS ****
	 ****************/

	@GetMapping("/users/all")
	public List<User> getAllUsers() {
		log.info("Retrieved list of users");
		return userService.getAllUsers();
	}

	@PostMapping("/register")
	public void registerUser(@RequestBody User theUser) {
		String inputUsername = theUser.getUsername();
		String inputEmail = theUser.getEmail();

		// Checks if username or email exists in database
		if (userService.userExistsInDatabaseWithUsername(inputUsername)
				|| userService.userExistsInDatabaseWithEmail(inputEmail)) {
			log.info("User already Exists! Try a different username or password");
		}
		// Saves user to database
		else {
			userService.save(theUser);
			log.info("User has been saved into ");
		}
	}

	// Login for User
	@GetMapping("/login")
	public void checkLogin(@RequestBody User loginInput) {

		// Get username and password from RequestBody
		String inputUsername = loginInput.getUsername();
		String inputPassword = loginInput.getPassword();

		// Checks if username exists
		if (userService.userExistsInDatabaseWithUsername(inputUsername)) {
			User attemptedUser = userService.getUserByUsername(inputUsername);

			// Checks input password with password from db
			if (attemptedUser.getPassword().equals(inputPassword)) {
				log.info("Login successful");
			} else {
				log.info("Incorrect username or password");
			}

		}
		// Error message if username does not exist
		else {
			log.info("This username does not exist!");
		}
	}

	// Searches and lists all users that is associated with username
	@GetMapping("/user/search/{username}")
	public List<User> searchUser(@PathVariable String username) {
		log.info("Users containing this username: '{}', has been retrieved", username);
		return userService.getUsersByUsername(username);
	}

	// Retrieves the password associated with the username
	@PostMapping("/{username}/forgot")
	public void getUserPassword(@PathVariable String username, @RequestBody String newPassword) {
		try {
			// Get user by username
			User theUser = userService.getUserByUsername(username);
			// Set password once User retrieved
			theUser.setPassword(newPassword);
			userService.save(theUser);
			log.info("Password has been changed");
		} catch (RuntimeException err) {
			log.info("Could not find user {}", username);
			throw new ResourceNotFoundException("Could not find user: " + username);
		}

	}

}
