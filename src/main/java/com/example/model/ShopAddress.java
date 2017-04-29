package com.example.model;

public class ShopAddress extends Address{

	private double lat;
	private double lng;
	private String info;
	
	public ShopAddress(String name, String street, String addressNumber, String postcode, String phone) {
		super(name, street, addressNumber, postcode, phone);
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}


}
