package com.tweetapp.service;

import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.tweetapp.customexception.ResourceNotFoundException;
import com.tweetapp.customexception.UserInputIncorrectException;
import com.tweetapp.models.User;
import com.tweetapp.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

	final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepo;

	// Retrieving list of users from db
	public List<User> getAllUsers() {
		// Sorts the find all method by username in ascending order
		List<User> listOfUsers = userRepo.findAll(Sort.by(Direction.ASC, "username"));
		if (listOfUsers.isEmpty()) {
			log.info("Returned empty list of users.");
			throw new ResourceNotFoundException("No users could be found!");
		} else {
			log.info("List of users retrieved");
			return listOfUsers;
		}

	}

	// Saving a user into db
	public User save(User theUser) {

		String inputFirstname = theUser.getFirstName();
		log.info(inputFirstname);
		String inputLastname = theUser.getLastName();
		log.info(inputLastname);
		String inputUsername = theUser.getUsername();
		String inputEmail = theUser.getEmail();
		String inputContactNum = theUser.getContactNumber();
		String inputPassword = theUser.getPassword();
		
		checkFirstname(inputFirstname);
		checkLastname(inputLastname);
		checkEmail(inputEmail);
		checkContactNum(inputContactNum);
		checkUsername(inputUsername);
		checkPassword(inputPassword);
		
		// Saves user to database
		userRepo.save(theUser);
		log.info("User has been saved into database");
		return theUser;
	}

	// Logging a user with credentials from db
	public User login(User theUser) {
		// Get username and password from RequestBody
		String inputUsername = theUser.getUsername();
		String inputPassword = theUser.getPassword();

		// Checks if username exists
		if (userExistsInDatabaseWithUsername(inputUsername)) {
			User attemptedUser = getUserByUsername(inputUsername);

			// Checks input password with password from db
			if (attemptedUser.getPassword().equals(inputPassword)) {
				log.info("Login successful");
				return attemptedUser;
			} else {
				log.info("Incorrect username or password");
				throw new ResourceNotFoundException("Incorrect Username or Password");
			}
		}
		// Error message if username does not exist
		else {
			log.info("This username does not exist!");
			throw new ResourceNotFoundException("This username does not exist");
		}
	}

	// Searching for all users that resembles input
	public List<User> getUsersBySearchingUsername(String username) {
		List<User> listOfUsers = userRepo.findByUsernameIsLike(username);

		log.info("Users containing this username: '{}', has been retrieved", username);
		return listOfUsers;
	}

	public void updateUserPassword(String username, String inputPassword) {
		if(userExistsInDatabaseWithUsername(username)) {
			User theUser = getUserByUsername(username);
			String newPassword = inputPassword.substring(0, inputPassword.length() -1);
			checkPassword(newPassword);
			theUser.setPassword(newPassword);
			userRepo.save(theUser);
			log.info("Password has been changed");
		} else {
			throw new ResourceNotFoundException("The username you have entered is incorrect. Please enter your correct username");
		}
	}

	// Helper functions
	public boolean userExistsInDatabaseWithUsername(String username) {
		if (userRepo.findUserByUsername(username) != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean userExistsInDatabaseWithEmail(String email) {
		if (userRepo.findUserByEmail(email) != null) {
			return true;
		} else {
			return false;
		}
	}

	public User getUserByUsername(String username) {
		// Get user from db with username
		User theUser = userRepo.findUserByUsername(username);
		return theUser;
	}

	public void deleteUserWithUsername(String username) {
		User theUser = userRepo.findUserByUsername(username);
		userRepo.delete(theUser);
	}

	// Register validation
	public void checkFirstname(String inputFirstname) {
		// Checks if the input is empty
		if (inputFirstname.isEmpty()) {
			log.info("Firstname should not be empty!");
			throw new UserInputIncorrectException("Firstname should not be empty!");
		} 
		// Checks to see if there is a digit within the input
		else if (!Pattern.matches("\\D+", inputFirstname)) {
			log.info("First name should not have any digits!");
			throw new UserInputIncorrectException("First name should not have any digits!");
		}
	}

	public void checkLastname(String inputLastname) {
		// Checks if the input is empty
		if (inputLastname.isEmpty()) {
			log.info("Lastname should not be empty!");
			throw new UserInputIncorrectException("Lastname should not be empty!");
		}
		// Checks to see if there is a digit within the input
		else if (!Pattern.matches("\\D+", inputLastname)) {
			log.info("Last name should not have any digits!");
			throw new UserInputIncorrectException("Last name should not have any digits!");
		}
	}

	public void checkContactNum(String inputContactNum) {
		// Checks if the input is empty
		if (inputContactNum.isEmpty()) {
			log.info("Phone Number should not be empty!");
			throw new UserInputIncorrectException("Phone Number should not be empty!");
		} 
		// Checks to see if the phone number has any letters
		else if (!Pattern.matches("\\d+", inputContactNum)) {
			log.info("Phone Number should not have any letters!");
			throw new UserInputIncorrectException("Phone Number should only have digits!");
		}
	}

	public void checkUsername(String inputUsername) {
		// Checks if the input is empty
		if (inputUsername.isEmpty()) {
			log.info("Username should not be empty!");
			throw new UserInputIncorrectException("Username should not be empty!");
		} 
		// Checks if user name exists in database
		else if (userExistsInDatabaseWithUsername(inputUsername)) {
			log.info("This username is already in use. Try a different username");
			throw new UserInputIncorrectException("This username is already in use. Try a different username");
		}
	}

	public void checkEmail(String inputEmail) {
		// Checks if email exists in database
		if (inputEmail.isEmpty()) {
			log.info("Email should not be empty!");
			throw new UserInputIncorrectException("Email should not be empty!");
		} 
		// Checks if the email exists in database
		else if (userExistsInDatabaseWithEmail(inputEmail)) {
			log.info("This email is already in use. Try a different email");
			throw new UserInputIncorrectException("This email is already in use. Try a different email");
		}
	}

	public void checkPassword(String inputPassword) {
		// Checks if the input is empty
		if (inputPassword.isEmpty()) {
			log.info("Password should not be empty!");
			throw new UserInputIncorrectException("Password should not be empty!");
		} 
		// Checks if the password is at least 6 characters and not empty
		else if (inputPassword.length() < 6) {
			log.info("Password to short! Must be at least 6 characters");
			throw new UserInputIncorrectException("Password must be at least 6 characters!");
		}
	}

}
