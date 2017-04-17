package com.example.model;

public class Topping {

	private int toppingId;
	private String name;
	private double price; 
	private int extra;
	private double extraPrice;
	
	public Topping(String name, double price) {
		this.name = name;
		this.price = price;
	}

	public int getToppingId() {
		return toppingId;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public int getExtra() {
		return extra;
	}
	
	public double getExtraPrice() {
		return extraPrice;
	}
	
	public void setToppingId(int toppingId) {
		this.toppingId = toppingId;
	}
	
	public void setExtra(int extra) {
		this.extra = extra;
	}
	
	public void setExtraPrice(double extraPrice) {
		this.extraPrice = extraPrice;
	}
}
