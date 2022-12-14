package com.tweetapp.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/*
 * The model framework for a User
 */
@Document("users")
public class User {

	/*
	 * The user's primary key, which is used for search queries
	 */
	@Id
	private String id;
	
	/*
	 * The user's details containing their First Name, Last Name
	 * Email, Password, Contact Number and profile image
	 * 
	 */
	private String firstName;
	private String lastName;
	
	@Indexed(unique=true)
	private String username;
	
	@Indexed(unique=true)
	private String email;
	
	private String password;
	private String contactNumber;
	private String avatar;
	
	public User() {
		
	}
	
	public User(String firstName, String lastName, String username, String email, String password,
			String contactNumber, String avatar) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.contactNumber = contactNumber;
		this.avatar = avatar;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
				+ ", email=" + email + ", password=" + password + ", contactNumber=" + contactNumber + ", avatar="
				+ avatar + "]";
	}

	
	
}
