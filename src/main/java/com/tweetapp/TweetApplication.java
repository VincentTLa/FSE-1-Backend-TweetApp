package com.tweetapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableMongoRepositories
public class TweetApplication{
	final Logger log = LoggerFactory.getLogger(TweetApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(TweetApplication.class, args);
	}

}
