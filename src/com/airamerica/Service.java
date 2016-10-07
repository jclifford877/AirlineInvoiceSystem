package com.airamerica;

public abstract class Service extends Product {
	//this class represents an abstract service

	public Service(String productCode) {
		super(productCode);
		// TODO Auto-generated constructor stub
	}

	//service taxes and fees are all the same so it is defined here
	@Override
	public double getTaxes() {
		return this.getSubTotal() * 0.04;
	}


	public double getFee(){
		return 0;
	}


}
