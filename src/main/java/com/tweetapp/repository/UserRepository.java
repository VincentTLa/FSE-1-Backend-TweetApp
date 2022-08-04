package com.tweetapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.tweetapp.models.User;

public interface UserRepository extends MongoRepository<User, String> {
	
	@Query("{username:'?0'}")
	User findUserByUsername(String username);

	@Query("{email:'?0'}")
	User findUserByEmail(String email);
	
	List<User> findByUsernameIsLike(String username);
	
}
