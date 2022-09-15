package com.tweetapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.models.Tweet;
import com.tweetapp.service.TweetService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/v1.0/tweets")
public class TweetController {

	@Autowired
	private TweetService tweetService;

	// Retrieve all tweets
	@GetMapping("/all")
	public List<Tweet> getAllTweets() {
		return tweetService.getAllTweets();
	}

	// Retrieve tweets from username
	@GetMapping("/{username}")
	public List<Tweet> getUserTweet(@PathVariable String username) {
		return tweetService.getTweetsFromUsername(username);
	}

	// Retrieve single tweet by Id
	@GetMapping("/main/{tweetId}")
	public Tweet getSingleTweet(@PathVariable String tweetId) {
		return tweetService.getTweetById(tweetId);
	}

	// Add a tweet
	@PostMapping("/{username}/add")
	public Tweet addTweet(@PathVariable String username, @RequestBody Tweet theTweet) {
		return tweetService.save(username, theTweet);
	}

	// Update a tweet
	@PutMapping("/{username}/update/{tweetId}")
	public Tweet updateTweet(@PathVariable String username, @PathVariable String tweetId, @RequestBody Tweet theTweet) {
		return tweetService.updateTweet(username, tweetId, theTweet);
	}

	// Reply feature where username is the logged in user
	@PutMapping("/{username}/reply/{tweetId}")
	public void replyToTweet(@PathVariable String username, @PathVariable String tweetId,
			@RequestBody Tweet replyTweet) {
		tweetService.replyToTweet(username, tweetId, replyTweet);
	}

	// Delete a tweet
	@DeleteMapping("/{username}/delete/{tweetId}")
	public void deleteTweet(@PathVariable String tweetId) {
		tweetService.deleteTweet(tweetId);
	}

	// Like a tweet
	@PutMapping("/{username}/like/{tweetId}")
	public boolean likeTweet(@PathVariable String username, @PathVariable String tweetId) {
		return tweetService.likeTweet(username, tweetId);
	}

	@PutMapping("/{username}/unlike/{tweetId}")
	public boolean unlikeTweet(@PathVariable String username, @PathVariable String tweetId) {
		return tweetService.unlikeTweet(username, tweetId);
	}
}
