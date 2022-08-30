package com.tweetapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tweetapp.models.User;
import com.tweetapp.service.UserService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Test
	void testFindUserByTheirUsername() {
		String username = "testuser123";
		User user = new User("Test", "User", "testuser123", "testuser@mail.com", "password123", "0401234567", "tester.jpeg");
		userService.save(user);
		User foundUser = userRepository.findUserByUsername(username);
		assertEquals(foundUser.getUsername(), username);
		assertNotNull(foundUser);
		userService.deleteUserWithUsername(username);
		
	}
	
	@Test
	void testFindUserByTheirEmail() {
		String email = "testuser@mail.com";
		User user = new User("Test", "User", "testuser123", "testuser@mail.com", "password123", "0401234567", "tester.jpeg");
		userService.save(user);
		User foundUser = userRepository.findUserByEmail(email);
		assertEquals(foundUser.getEmail(), email);
		assertNotNull(foundUser);
		userService.deleteUserWithUsername(user.getUsername());
	}	
	
	@Test
	void testFindTheListOfUsersBasedOnSearch() {
		String search = "test";
		
		User user1 = new User("Test", "User", "testuser321", "testuser321@mail.com", "password123", "0401234567", "tester.jpeg");
		userService.save(user1);
		
		User user2 = new User("Test", "User", "testuser123", "testuser123@mail.com", "password123", "0401234567", "tester.jpeg");
		userService.save(user2);
		List<User> listUsers = userRepository.findByUsernameIsLike(search);
		
		assertNotNull(listUsers);
		assertTrue(!listUsers.isEmpty());
		for(User user: listUsers) {
			assertTrue(user.getUsername().contains(search));
			userService.deleteUserWithUsername(user.getUsername());
		}
		
	}	
}
