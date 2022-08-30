package com.tweetapp.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.tweetapp.models.Tweet;

public interface TweetRepository extends MongoRepository<Tweet, String> {

	List<Tweet> findByUsername(String username, Sort sort);
	
	Tweet findTweetById(String tweetId);
	
	
	
}
