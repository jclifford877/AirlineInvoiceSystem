package com.airamerica;

/* A partial implementation of address of a particular
 * Location */
public class Address {
	
	private String street;
	private String city;
	private String state;
	private String zip;
	private String country;
//TODO: Add more fields as needed

	/* Constructor - Generated using Eclipse Menu 
	 * (Source-> Generate Constructor using fields) */

	public Address(String street, String city, String state, String zip, String country) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
		}
	public Address(Address a){
		this.street = a.street;
		this.city = a.city;
		this.state = a.state;
		this.zip = a.zip;
		this.country = a.country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	/* Getters and Setters - Generated using Eclipse 
	 * Menu (Source-> Generate Getters and Setters) */	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	/* Additional methods as required */
}