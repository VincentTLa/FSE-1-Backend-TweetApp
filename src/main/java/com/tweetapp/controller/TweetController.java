package com.tweetapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/api/v1.0/tweets")
public class TweetController {

	final Logger log = LoggerFactory.getLogger(TweetController.class);

	@Autowired
	private TweetService tweetService;

	/****************
	 **** TWEETS ****
	 ****************/

	// Retrieve all tweets
	@GetMapping("/all")
	public List<Tweet> getAllTweets() {
		log.info("Retrieved all tweets");
		return tweetService.getAllTweets();
	}

	// Retrieve tweets from username
	@GetMapping("/{username}")
	public List<Tweet> getUserTweet(@PathVariable String username) {
		log.info("Retrieved all tweets from user: {}", username);
		return tweetService.getTweetsFromUsername(username);
	}

	// Add a tweet
	@PostMapping("/{username}/add")
	public void addTweet(@PathVariable String username, @RequestBody Tweet theTweet) {
		theTweet.setUsername(username);
		tweetService.save(theTweet);
		log.info("Tweet has been created");
	}

	// Update a tweet
	@PutMapping("/{username}/update/{tweetId}")
	public void updateTweet(@PathVariable String username, @PathVariable String tweetId, @RequestBody Tweet theTweet) {

		// Checks if the Tweet exists
		if (tweetService.checkIfTweetExists(tweetId)) {
			theTweet.setUsername(username);
			theTweet.setId(tweetId);
			tweetService.save(theTweet);
			log.info("Updated tweet with id: '{}'", tweetId);
		} else {
			log.info("Tweet not found");
		}
	}

	// Increases like of tweet by 1 // Will need to work on a boolean aspect
	@PutMapping("/{username}/like/{tweetId}")
	public void likeTweet(@PathVariable String username, @PathVariable String tweetId) {
		// Checks if the tweet exists
		if (tweetService.checkIfTweetExists(tweetId)) {
			// Retrieves the tweet from id and increases the likes
			Tweet theTweet = tweetService.getTweetById(tweetId);
			int likes = theTweet.getLikes();
			theTweet.setLikes(likes + 1);
			tweetService.save(theTweet);
			log.info("User : '{}' has liked the tweet. Amount of likes on this tweet is now: {}", username,
					theTweet.getLikes());
		} else {
			log.info("Could not find the tweet with id: '{}'", tweetId);
		}
	}

	@PutMapping("/{username}/unlike/{tweetId}")
	public void unlikeTweet(@PathVariable String username, @PathVariable String tweetId) {
		if (tweetService.checkIfTweetExists(tweetId)) {
			Tweet theTweet = tweetService.getTweetById(tweetId);
			int likes = theTweet.getLikes();
			if (likes <= 0) {
				theTweet.setLikes(0);
				log.info("Tweet likes cannot go below 0");
			}
			theTweet.setLikes(likes - 1);
			tweetService.save(theTweet);
			log.info("Tweet has been unliked. Tweet now has {} likes", theTweet.getLikes());
		} else {
			log.info("Could not find the Tweet with id: {}", tweetId);
		}
	}

	// Reply feature where username is the logged in user
	@PutMapping("/{username}/reply/{tweetId}")
	public void replyToTweet(@PathVariable String username, @PathVariable String tweetId,
			@RequestBody Tweet replyTweet) {

		if (tweetService.checkIfTweetExists(tweetId)) {
			log.info(tweetId);
			// Checks if the tweet exists

			Tweet mainTweet = tweetService.getTweetById(tweetId);
			// If the mainTweet has no replies, create a field for replies to be added into
			if (mainTweet.getReplies() == null) {
				List<Tweet> replies = new ArrayList<>();
				mainTweet.setReplies(replies);
			}

			// Adds the request body reply to reply list
			List<Tweet> addedReply = mainTweet.getReplies();
			addedReply.add(replyTweet);

			// Sets the logged in username onto reply tweet and saves
			replyTweet.setUsername(username);
			tweetService.save(replyTweet);

			// Adds the reply tweet to the main tweet
			mainTweet.setReplies(addedReply);
			tweetService.save(mainTweet);
			log.info("Reply added to main tweet: {}", mainTweet);
		} else {
			log.info("This tweet could not be found");
		}

	}

	// Delete a tweet
	@DeleteMapping("/{username}/delete/{tweetId}")
	public void deleteTweet(@PathVariable String username, @PathVariable String tweetId) {
		// Checks if tweet exists
		if (tweetService.checkIfTweetExists(tweetId)) {
			// Deletes the tweet using Tweet's id
			tweetService.deleteTweet(tweetId);
			log.info("Deleted the tweet with id: {}", tweetId);
		} else {
			log.info("Could not find the Tweet with id: {}", tweetId);
		}
	}

}
