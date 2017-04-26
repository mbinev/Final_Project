package com.example.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.DatatypeConverter;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class User {

	private long userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String avatarLink;
	private boolean isVerified;
	private LocalDateTime registrationTime;
	private String registrationCode;
	
	public User(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.registrationTime = LocalDateTime.now();
		this.isVerified = false;
	} 
	
	public User(String firstName, String lastName, String email, String password, LocalDateTime regTime, boolean isVerified) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.registrationTime = regTime;
		this.isVerified = isVerified;
	} 
	
	public long getUserId() {
		return userId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getAvatarLink() {
		return avatarLink;
	}
	
	public boolean getIsVerified() {
		return isVerified;
	}
	
	public LocalDateTime getRegistrationTime() {
		return this.registrationTime;
	}
	
	public String getRegistrationCode() {
		return registrationCode;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public void setAvatarLink(String avatarLink) {
		this.avatarLink = avatarLink;
	}
	
	public void setIsVerified() {
		this.isVerified = true;
	}
	
	public void setRegistrationTime() {
		this.registrationTime = LocalDateTime.now();
	}
	
	public void setRegistrationCode(String registrationCode) {
		this.registrationCode = registrationCode;
	}
	
	public static String hashPassword(String password) throws NoSuchAlgorithmException{
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update(password.getBytes());
		byte[] digest = m.digest();
		String hashtext = DatatypeConverter.printHexBinary(digest).toLowerCase();
		
		return hashtext;
	}
}
