package com.tweetapp.demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tweetapp.models.Tweet;
import com.tweetapp.models.User;
import com.tweetapp.service.TweetService;
import com.tweetapp.service.UserService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class TweetApplicationTests {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TweetService tweetService;
	
	@Test
	void testGetAllUsers() {
		List<User> users = userService.getAllUsers();
		assertNotNull(users);
		assertTrue(!users.isEmpty());		
	}
	
	@Test
	void testSaveUser() {
		User user = new User("Mary", "Poppins", "marypoppins123", "marypoppin@mail.com", "password123", "0401234567", "mary.jpeg");
		User savedUser = userService.save(user);
		User newUser = userService.getUserByUsername(savedUser.getUsername());
		assertEquals("Mary", newUser.getFirstName());
		assertEquals("Poppins", newUser.getLastName());
		assertEquals("marypoppins123", newUser.getUsername());
		assertEquals("marypoppin@mail.com", newUser.getEmail());
		assertEquals("password123", newUser.getPassword());
		assertEquals("0401234567", newUser.getContactNumber());
		assertEquals("mary.jpeg", newUser.getAvatar());
	}
	
	@Test
	void testUserExistsInDatabaseUsingUsernameMethod() {
	}
	
	@Test
	void testUserExistsInDatabaseUsingEmailMethod() {
		
	}
	
	@Test
	void testGetUserByUsernameMethod() {
		
	}
	
	@Test
	void testGetListUsersUsingUsernameWithLikeOperator() {
		
	}
	
	@Test
	void testGetAllTweetsMethod() {
		List<Tweet> tweets = tweetService.getAllTweets();
		assertNotNull(tweets);
		assertTrue(!tweets.isEmpty());
	}

}
