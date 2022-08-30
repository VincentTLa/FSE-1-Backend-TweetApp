package com.tweetapp.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("likes")
public class Like {
	
	private String username;
	private boolean value;
	
	public Like() {
		
	}
	
	public Like(String username, boolean value) {
		super();
		this.username = username;
		this.value = value;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	
	
}
