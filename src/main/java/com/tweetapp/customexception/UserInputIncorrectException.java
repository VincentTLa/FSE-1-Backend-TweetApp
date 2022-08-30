package com.tweetapp.customexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserInputIncorrectException extends RuntimeException{

	public UserInputIncorrectException() {
		this("Bad Request!");
	}

	public UserInputIncorrectException(String message) {
		this(message, null);
	}

	public UserInputIncorrectException(String message, Throwable cause) {
		super(message, cause);
	}

}
