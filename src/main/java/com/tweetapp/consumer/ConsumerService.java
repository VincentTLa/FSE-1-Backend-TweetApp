package com.tweetapp.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.tweetapp.service.TweetService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConsumerService {

	private static final Logger log = LoggerFactory.getLogger(ConsumerService.class);
	
	@Autowired
	private TweetService tweetService;

	public ConsumerService(TweetService tweetService) {
		this.tweetService = tweetService;
	}

	@KafkaListener(topics = "deleteTweet", containerFactory = "kafkaListenerContainerFactory")
	public void consume(String tweetId) {
		log.info("Deleting tweet with id: {}", tweetId);
		tweetService.deleteTweet(tweetId);

	}

}
