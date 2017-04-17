package com.example.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Order {

	private long orderId;
	private long userId;
	private LocalDateTime date;
	
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
	
	
}
