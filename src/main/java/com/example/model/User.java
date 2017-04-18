package com.example.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.DatatypeConverter;

import org.hibernate.validator.constraints.NotBlank;

public class User {

	private long userId;
	@Valid @NotBlank @Size(min=2, max=30) 
	private String firstName;
	@Valid @NotBlank @Size(min=2, max=30) 
	private String lastName;
	@Valid @NotBlank @Pattern(regexp="^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$")
	private String email;
	@Valid @NotBlank @Pattern(regexp="(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{8,}")
	private String password;
	private String avatarLink;
	private boolean isVerified;
	private LocalDateTime registrationTime;
	
	public User() {
		
	}
	
	public User(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.registrationTime = LocalDateTime.now();
		this.isVerified = false;
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
		return registrationTime;
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
	
	public static String hashPassword(String password) throws NoSuchAlgorithmException{
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update(password.getBytes());
		byte[] digest = m.digest();
		String hashtext = DatatypeConverter.printHexBinary(digest).toLowerCase();
		
		return hashtext;
	}
}
