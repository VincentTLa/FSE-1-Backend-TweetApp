package com.tweetapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.models.Tweet;
import com.tweetapp.repository.TweetRepository;

@Service
public class TweetService {

	@Autowired
	private TweetRepository tweetRepo;

	public List<Tweet> getAllTweets() {
		return tweetRepo.findAll();
	}

	public Tweet getTweetById(String tweetId) {
		return tweetRepo.findTweetById(tweetId); 
	}
	
	public boolean checkIfTweetExists(String tweetId) {
		if(tweetRepo.findTweetById(tweetId) != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void save(Tweet theTweet) {
		tweetRepo.save(theTweet);
	}

	public List<Tweet> getTweetsFromUsername(String username) {
		return tweetRepo.findByUsername(username);
	}
	
	public void deleteTweet(String tweetId) {
		tweetRepo.deleteById(tweetId);
	}

}
