package com.tweetapp.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.tweetapp.customexception.ResourceNotFoundException;
import com.tweetapp.models.Like;
import com.tweetapp.models.Tweet;
import com.tweetapp.repository.TweetRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TweetService {

	final Logger log = LoggerFactory.getLogger(TweetService.class);

	@Autowired
	private TweetRepository tweetRepo;

	public List<Tweet> getAllTweets() {
		// Sorts the list of all tweets by date time in descending order
		List<Tweet> listOfTweets = tweetRepo.findAll(Sort.by(Direction.DESC, "datetime"));

		if (listOfTweets.isEmpty()) {
			log.info("Returned a list of empty Tweets.");
		}
		log.info("List of tweets returned");
		return listOfTweets;
	}

	public List<Tweet> getTweetsFromUsername(String username) {
		// Sorts the list of tweets of a user from descending order using date time
		List<Tweet> listOfTweets = tweetRepo.findByUsername(username, Sort.by(Direction.DESC, "datetime"));
		if (listOfTweets.isEmpty()) {
			log.info("No tweets could be found with the username {}", username);
			throw new ResourceNotFoundException("No tweets could be found with this user!");
		}
		log.info("Tweets from {} have been retrieved", username);
		return listOfTweets;
	}

	public Tweet save(String username, Tweet theTweet) {
		theTweet.setUsername(username);
		log.info("Tweet has been created");
		tweetRepo.save(theTweet);
		return theTweet;
	}

	public Tweet updateTweet(String username, String tweetId, Tweet theTweet) {

		// Checks if the Tweet exists
		if (checkIfTweetExists(tweetId)) {
			Tweet originalTweet = getTweetById(tweetId);
			List<Like> likes = originalTweet.getLikes();
			// Set the username and tweetId so a new tweet is not created
			theTweet.setUsername(username);
			theTweet.setId(tweetId);

			theTweet.setLikes(likes);

			save(username, theTweet);
			log.info("Updated tweet with id: '{}'", tweetId);
			return theTweet;

		} else {
			log.info("Tweet not found");
			throw new ResourceNotFoundException("Tweet could not be found!");
		}
	}

	public Tweet replyToTweet(String username, String tweetId, Tweet replyTweet) {

		// Checks if the tweet exists
		if (checkIfTweetExists(tweetId)) {
			Tweet mainTweet = getTweetById(tweetId);

			// If the mainTweet has no replies, create a field for replies to be added into
			if (mainTweet.getReplies() == null) {
				List<Tweet> replies = new ArrayList<>();
				mainTweet.setReplies(replies);
			}

			// Adds the tweet reply to reply list
			List<Tweet> addedReply = mainTweet.getReplies();
			addedReply.add(replyTweet);

			// Sets the logged in username onto reply tweet and saves
			replyTweet.setUsername(username);
			save(username, replyTweet);

			// Adds the reply tweet to the main tweet
			mainTweet.setReplies(addedReply);
			save(mainTweet.getUsername(), mainTweet);
			log.info("Reply added to main tweet: {}", mainTweet);
			return mainTweet;
		} else {
			log.info("This tweet could not be found");
			throw new ResourceNotFoundException("Tweet could not be found.");
		}
	}

	public void deleteTweet(String tweetId) {
		// Checks if tweet exists
		if (checkIfTweetExists(tweetId)) {
			
			Tweet theTweet = getTweetById(tweetId);
			setReplyField(theTweet);

			// Check if tweet has replies
			if (!theTweet.getReplies().isEmpty()) {
				// Delete the replies of main tweet
				for (Tweet replies : theTweet.getReplies()) {
					tweetRepo.deleteById(replies.getId());
					log.info("Deleting reply with id: {}", replies.getId());
				}
			}

			// Deletes the tweet using Tweet's id
			tweetRepo.deleteById(tweetId);
			log.info("Deleted the tweet with id: {}", tweetId);

		} else {
			log.info("Could not find the Tweet with id: {}", tweetId);
			throw new ResourceNotFoundException("Could not find the tweet!");
		}
	}

	public boolean likeTweet(String username, String tweetId) {
		checkIfTweetExists(tweetId);
		Tweet theTweet = tweetRepo.findTweetById(tweetId);
		setLikeField(theTweet);

		List<Like> currentLikes = theTweet.getLikes();
		// Searches through the like list to see if the current user has liked it
		for (Like like : currentLikes) {
			if (like.getUsername().equals(username) && like.isValue() == true) {
				// If the user has liked this post already then throw exception
				throw new ResourceNotFoundException("You have already liked this tweet!");
			}
			if (like.getUsername().equals(username) && like.isValue() == false) {
				// If the user has liked -> unliked -> like again
				like.setValue(true);
				log.info("Reliked the tweet");
				tweetRepo.save(theTweet);
			}
		}

		// Delete any duplicates first before adding the like
		for (int i = 0; i < currentLikes.size(); i++) {
			Like thisLike = currentLikes.get(i);
			if (thisLike.getUsername().equals(username)) {
				currentLikes.remove(i);
				tweetRepo.save(theTweet);
			}
		}

		// Add a like from logged in user to like list
		Like newLike = new Like(username, true);
		currentLikes.add(newLike);
		tweetRepo.save(theTweet);
		log.info("Added a like to tweet");
		return true;
	}

	public boolean unlikeTweet(String username, String tweetId) {
		checkIfTweetExists(tweetId);
		Tweet theTweet = tweetRepo.findTweetById(tweetId);

		List<Like> currentLikes = theTweet.getLikes();
		int size = currentLikes.size();
		log.info("This is the amount of likes currently: " + size);

		// Searches through the like list to see if the current user has liked it
		for (Like like : currentLikes) {
			// If the user has already liked post then unlike
			if (like.getUsername().equals(username)) {
				like.setValue(false);
				log.info("Unliked the tweet");
				tweetRepo.save(theTweet);
			}
		}
		return false;
	}

	// Helper functions
	public Tweet getTweetById(String tweetId) {
		log.info("Tweet id '{}' found!", tweetId);
		return tweetRepo.findTweetById(tweetId);
	}

	public void setReplyField(Tweet theTweet) {
		// If the tweet has no replies, create a field for replies to be added into
		if (theTweet.getReplies() == null) {
			List<Tweet> replies = new ArrayList<>();
			theTweet.setReplies(replies);
			tweetRepo.save(theTweet);
			log.info("Tweet reply field has been stored");
		}
	}

	public void setLikeField(Tweet theTweet) {
		// If the Tweet has no likes, create a field for likes to be added into
		if (theTweet.getLikes() == null) {
			List<Like> likes = new ArrayList<>();
			theTweet.setLikes(likes);
			tweetRepo.save(theTweet);
			log.info("Tweet with no likes value has been stored");
		}
	}

	public boolean checkIfTweetExists(String tweetId) {
		if (tweetRepo.findTweetById(tweetId) != null) {
			return true;
		} else {
			throw new ResourceNotFoundException("Tweet not found!");
		}
	}
}
