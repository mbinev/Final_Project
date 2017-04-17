package com.example.model;

public class Pizza extends Product{

	private int crustId;
	private int sizeId;
	
	public Pizza(String name, double price, int crustId, int sizeId) {
		super(name, price, name);
		this.crustId = crustId;
		this.sizeId = sizeId;
	} 

	public int getCrustId() {
		return crustId;
	}

	public int getSizeId() {
		return sizeId;
	}
	
}
