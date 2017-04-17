package com.example.model;

public class PizzaSize {

	private int sizeId;
	private String name;
	private int sliceNumber;
	private int weight;
	
	public PizzaSize(String name, int sliceNumber, int weight) {
		this.name = name;
		this.sliceNumber = sliceNumber;
		this.weight = weight;
	}

	public int getSizeId() {
		return sizeId;
	}

	public String getName() {
		return name;
	}

	public int getSliceNumber() {
		return sliceNumber;
	}

	public int getWeight() {
		return weight;
	}
	
	public void setSizeId(int sizeId) {
		this.sizeId = sizeId;
	}
}
