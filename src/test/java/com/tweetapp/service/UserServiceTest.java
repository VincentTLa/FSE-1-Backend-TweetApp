package com.tweetapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tweetapp.models.User;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Test
	void testGetAllUsers() {
		List<User> users = userService.getAllUsers();
		assertNotNull(users);
		assertTrue(!users.isEmpty());		
	}
	
	@Test
	@Order(1)
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
	@Order(2)
	void testUserExistsInDatabaseUsingUsernameMethod() {
		assertFalse(userService.userExistsInDatabaseWithUsername("diozflcujgsdjkf"));
		assertTrue(userService.userExistsInDatabaseWithUsername("marypoppins123"));
	}
	
	@Test
	@Order(3)
	void testUserExistsInDatabaseUsingEmailMethod() {
		System.out.println(userService.userExistsInDatabaseWithEmail("marypoppin@mail.com"));
		assertFalse(userService.userExistsInDatabaseWithEmail("notarealemail@email.com"));
		assertTrue(userService.userExistsInDatabaseWithEmail("marypoppin@mail.com"));
	}
	
	@Test
	@Order(4)
	void testGetUserByUsernameMethod() {
		User theUser = userService.getUserByUsername("marypoppins123");
		assertEquals("Mary", theUser.getFirstName());
		assertEquals("Poppins", theUser.getLastName());
		assertEquals("marypoppins123", theUser.getUsername());
		assertEquals("marypoppin@mail.com", theUser.getEmail());
		assertEquals("password123", theUser.getPassword());
		assertEquals("0401234567", theUser.getContactNumber());
		assertEquals("mary.jpeg", theUser.getAvatar());
	}
	
	@Test
	@Order(5)
	void testGetListUsersUsingUsernameWithLikeOperator() {
		List<User> results = userService.getUsersBySearchingUsername("mar");
		assertNotNull(results);
		assertTrue(!results.isEmpty());
	}
	
	@Test
	@Order(6)
	void testDeletingAUserMethod() {
		userService.deleteUserWithUsername("marypoppins123");
		assertEquals(null, userService.getUserByUsername("marypoppins123"));
	}

}
