package com.example.model;

import java.util.ArrayList;

public class Product {

	private long productId;
	private String name;
	private double price;
	private long ownerId;
	private String category;
	private ArrayList<String> subproducts;
	private String sub;
	private String path;
	
	public Product(String name, double price, String category) {
		this.name = name;
		this.price = price;
		this.category = category;
		subproducts = new ArrayList<>();
		sub = "Pizza";
	}

	public long getProductId() {
		return productId;
	}
	
	public long getOwnerId() {
		return ownerId;
	}
	
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public ArrayList<String> getSubproducts() {
		return subproducts;
	}
	
	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	@Override
	public String toString() {
		return name;
	}

	public String getSub() {
		StringBuilder sb = new StringBuilder();
		for (String string : subproducts) {
			sb.append(string+"  ");
		}
		sub = sb.toString();
		return sub;
	}
}

