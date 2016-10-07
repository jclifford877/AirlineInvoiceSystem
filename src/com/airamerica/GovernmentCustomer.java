package com.airamerica;
//this class represents a government customer which is a type of customer
public class GovernmentCustomer extends Customer {

	public GovernmentCustomer(String customerCode, Person primaryContact,
			String name, int airlineMiles) {
		super(customerCode, primaryContact, name, airlineMiles);
	}

	@Override
	public double getDiscount(double subTotal, double taxes) {
		return taxes;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "Government";
	}

}
