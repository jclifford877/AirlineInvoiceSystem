package com.airamerica;
//A class to represent a corporate customer which is a type of customer

public class CorporateCustomer extends Customer {

	public CorporateCustomer(String customerCode, Person primaryContact,
			String name, int airlineMiles) {
		super(customerCode, primaryContact, name, airlineMiles);
	}

	

	@Override
	public double getFee(){
		return 40;
	}

	@Override
	public double getDiscount(double subTotal, double taxes) {
		return subTotal*0.12;
	}


	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "Corporate";
	}

}
