package com.example.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {

	private long orderId;
	private long userId;
	private LocalDateTime date;
	private long addressId;
	private ArrayList<OrderObject> products;
	
	public Order(long userId, LocalDateTime date) {
		this.userId = userId;
		this.date = date;
	}
	
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

	public long getOrderId() {
		return orderId;
	}

	public long getUserId() {
		return userId;
	}

	public LocalDateTime getDate() {
		return date;
	}
	
	public long getAddressId() {
		return addressId;
	}
	
	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}
	
	public ArrayList<OrderObject> getProducts() {
		return products;
	}
	
	public void setProducts(ArrayList<OrderObject> products) {
		this.products = products;
	}
	
}
