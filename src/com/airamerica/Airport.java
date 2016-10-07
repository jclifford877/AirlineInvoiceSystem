package com.airamerica;
//this class represents a unique airport
public class Airport implements Searchable{

	private String airportCode;
	private String name;
	private Address address;
	private int latDegs;
	private int latMins;
	private int lonDegs;
	private int lonMins;
	private double passengerFacilityFee;
	
	public Airport(String airportCode, String name, Address address,
					int latDegs, int latMins, int lonDegs, int lonMins, double passengerFacilityFee){
		this.airportCode = airportCode;
		this.name = name;
		this.address = address;
		this.latDegs = latDegs;
		this.latMins = latMins;
		this.lonDegs = lonDegs;
		this.lonMins = lonMins;
		this.passengerFacilityFee = passengerFacilityFee;
	}

	public Airport(Airport a) {
		this.airportCode = a.airportCode;
		this.name = a.name;
		this.address = new Address(a.address);
		this.latDegs = a.latDegs;
		this.latMins = a.latMins;
		this.lonDegs = a.lonDegs;
		this.lonMins = a.lonMins;
		this.passengerFacilityFee = a.passengerFacilityFee;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getLatDegs() {
		return latDegs;
	}

	public void setLatDegs(int latDegs) {
		this.latDegs = latDegs;
	}

	public int getLatMins() {
		return latMins;
	}

	public void setLatMins(int latMins) {
		this.latMins = latMins;
	}

	public int getLonDegs() {
		return lonDegs;
	}

	public void setLonDegs(int lonDegs) {
		this.lonDegs = lonDegs;
	}

	public int getLonMins() {
		return lonMins;
	}

	public void setLonMins(int lonMins) {
		this.lonMins = lonMins;
	}

	public double getPassengerFacilityFee() {
		return passengerFacilityFee;
	}

	public void setPassengerFacilityFee(double passengerFacilityFee) {
		this.passengerFacilityFee = passengerFacilityFee;
	}

	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return this.airportCode;
	}
}
