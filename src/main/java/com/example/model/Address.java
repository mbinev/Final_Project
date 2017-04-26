package com.example.model;

public class Address {

	private long addressId;
	private String name; 
	private String street;
	private String addressNumber; 
	private String postcode;
	private String city;
	private String phone; 
	private String bell;
	private int floor;
	private int buildingNumber;
	private int apartmentNumber;
	private String entrance;
	private int userId;
	
	public Address(String name, String street, String addressNumber, String postcode, String phone) {
		this.name = name;
		this.street = street;
		this.addressNumber = addressNumber;
		this.postcode = postcode;
		this.phone = phone;
	}
	
	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}
	
	public void setNeighbourhood(String neighbourhood) {
		this.name = neighbourhood;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public void setFloor(int floor) {
		this.floor = floor;
	}
	
	public void setBell(String bell) {
		this.bell = bell;
	}
	
	public void setBuildingNumber(int buildingNumber) {
		this.buildingNumber = buildingNumber;
	}
	
	public void setApartmentNumber(int apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}
	
	public void setEntrance(String entrance) {
		this.entrance = entrance;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public long getAddressId() {
		return addressId;
	}
	
	public String getName() {
		return name;
	}
	public String getStreet() {
		return street;
	}
	public String getaddressNumber() {
		return addressNumber;
	}
	public String getPostcode() {
		return postcode;
	}
	public String getCity() {
		return city;
	}
	public String getPhone() {
		return phone;
	}
	public String getBell() {
		return bell;
	}
	public int getFloor() {
		return floor;
	}
	public int getBuildingNumber() {
		return buildingNumber;
	}
	public int getApartmentNumber() {
		return apartmentNumber;
	}
	public String getEntrance() {
		return entrance;
	}
	public int getUserId() {
		return userId;
	}
	
	
}
