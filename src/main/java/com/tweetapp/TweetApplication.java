package com.tweetapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableMongoRepositories
public class TweetApplication{
	
	public static void main(String[] args) {
		SpringApplication.run(TweetApplication.class, args);
	}

}
