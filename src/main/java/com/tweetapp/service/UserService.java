package com.tweetapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.models.User;
import com.tweetapp.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	public List<User> getAllUsers(){
		return userRepo.findAll();
	}
	
	public User save(User theUser) {
		return userRepo.save(theUser);
	}
	
	public boolean userExistsInDatabaseWithUsername(String username) {
		if(userRepo.findUserByUsername(username) != null) {
			return true;			
		} else {
			return false;
		}
	}
	
	public boolean userExistsInDatabaseWithEmail(String email) {
		if(userRepo.findUserByEmail(email) != null) {
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
	
	public List<User> getUsersByUsername(String username){
		return userRepo.findByUsernameIsLike(username);
	}
	
}
