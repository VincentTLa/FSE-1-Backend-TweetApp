package com.tweetapp.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tweets")
public class Tweet {
	
	@Id
	private String id;
	private String username;
	private String description;
	private String datetime;
	private int likes;
	private List<Tweet> replies;
	
	public Tweet() {
		
	}

	public Tweet(String username, String description, String datetime, int likes) {
		super();
		this.username = username;
		this.description = description;
		this.datetime = datetime;
		this.likes = likes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	
	public List<Tweet> getReplies() {
		return replies;
	}

	public void setReplies(List<Tweet> replies) {
		this.replies = replies;
	}

	@Override
	public String toString() {
		return "Tweet [id=" + id + ", username=" + username + ", description=" + description + ", datetime=" + datetime
				+ ", likes=" + likes + "]";
	}



	
	
	
	
}
