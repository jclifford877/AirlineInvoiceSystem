package com.airamerica;
//this is a class to represent an abstract customer because there will never be just a customer they will always be typed
public abstract class Customer implements Searchable{
	
	
	private String customerCode;
	
	/* Note how Person has been used as 
	 * primary contact(Composition Relationship) */ 
	private Person primaryContact;
	private String name;
	private int airlineMiles;

	/*TODO: Add other fields as necessary (eg. firstName, lastName,
	phoneNo etc) */
	
	// TODO: Add constructor(s)
	public Customer(String customerCode, Person primaryContact, String name, int airlineMiles){
		this.customerCode = customerCode;
		this.primaryContact = primaryContact;
		this.name = name;
		this.airlineMiles = airlineMiles;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAirlineMiles() {
		return airlineMiles;
	}
	public void setAirlineMiles(int airlineMiles) {
		this.airlineMiles = airlineMiles;
	}
	public void setPrimaryContact(Person primaryContact) {
		this.primaryContact = primaryContact;
	}
	/*TODO: Add Getters and setters */
	public Person getPrimaryContact() {
		return primaryContact;
	}
	
	@Override
	public String getCode(){
		return this.customerCode;
	}
	
	//returns the discount depending on the type of customers
	public abstract double getDiscount(double subTotal, double taxes);

	//the fee is normally 0 so it's defined here and overridden where necessary
	public double getFee(){
		return 0;
	}
	
	//returns the type of customer as a string
	public abstract String getType();
	//TODO: Add additional methods if needed */
}
