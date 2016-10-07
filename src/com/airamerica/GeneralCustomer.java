package com.airamerica;
//this is a class to represent a general customer which is a type of customer

public class GeneralCustomer extends Customer {

	public GeneralCustomer(String customerCode, Person primaryContact,
			String name, int airlineMiles) {
		super(customerCode, primaryContact, name, airlineMiles);
	}

	@Override
	public double getDiscount(double subTotal, double taxes) {
		return 0;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "General";
	}

}
