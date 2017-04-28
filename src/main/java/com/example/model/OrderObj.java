package com.example.model;

import java.util.ArrayList;

public class OrderObj {
	private Product product;
	private long userId;
	private String description;
	private double price;
	private ArrayList<String> subproducts;
	private int quantity;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public ArrayList<String> getSubproducts() {
		return subproducts;
	}
	
	public void setSubproducts(ArrayList<String> subproducts) {
		this.subproducts = subproducts;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
