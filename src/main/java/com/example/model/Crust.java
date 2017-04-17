package com.example.model;

public class Crust {

	private int crustId;
	private String name;
	
	public Crust(String name) {
		this.name = name;
	}
	
	public int getCrustId() {
		return crustId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setCrustId(int crustId) {
		this.crustId = crustId;
	}
	
}
