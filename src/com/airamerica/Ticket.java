package com.airamerica;

import java.util.ArrayList;

import com.airamerica.utils.Finder;
import com.airamerica.utils.Haversine;

import unl.cse.assignments.DataConverter;

public abstract class Ticket extends Product{
	private Airport depAirport;
	private Airport arrAirport;
	private String depTime;
	private String arrTime;
	private String flightNo;
	private String flightClass;
	private String aircraftType;
	private String travelDate;
	private String ticketNote;
	private ArrayList<Person> passengers;
public Ticket(String productCode, Airport depAirport, Airport arrAirport, String depTime, String arrTime,
						String flightNo, String flightClass, String aircraftType){
		super(productCode);
		this.depAirport = depAirport;
		this.arrAirport = arrAirport;
		this.depTime = depTime;
		this.arrTime = arrTime;
		this.flightNo = flightNo;
		this.flightClass = flightClass;
		this.aircraftType = aircraftType;
	}
public Airport getDepAirport() {
	return depAirport;
}
public void setDepAirport(Airport depAirport) {
	this.depAirport = depAirport;
}
public Airport getArrAirport() {
	return arrAirport;
}
public void setArrAirport(Airport arrAirport) {
	this.arrAirport = arrAirport;
}
public String getDepTime() {
	return depTime;
}
public void setDepTime(String depTime) {
	this.depTime = depTime;
}
public String getArrTime() {
	return arrTime;
}
public void setArrTime(String arrTime) {
	this.arrTime = arrTime;
}
public String getFlightNo() {
	return flightNo;
}
public void setFlightNo(String flightNo) {
	this.flightNo = flightNo;
}
public String getFlightClass() {
	return flightClass;
}
public void setFlightClass(String flightClass) {
	this.flightClass = flightClass;
}
public String getAircraftType() {
	return aircraftType;
}
public void setAircraftType(String aircraftType) {
	this.aircraftType = aircraftType;
}
public void setInfo(String s){
	String[] tokens = s.split(":");
	this.travelDate = tokens[1];
	int i=2;
	ArrayList<Person> passengers = new ArrayList<Person>();
	for(; i<Integer.parseInt(tokens[2])*5; i=i+5){
		Person p = Finder.findObject(DataConverter.getPeople(), tokens[i+2]);
		p.setSeat(tokens[i+1]);
		p.setID(tokens[i+3]);
		p.setAge(Integer.parseInt(tokens[i+4]));
		p.setNationality(tokens[i+5]);
		passengers.add(p);
	}
	if(tokens != null && i+1>= 0 && i+1<tokens.length && tokens[i+1] != null){
        this.ticketNote = tokens[i+1];
	}else{
		this.ticketNote = "";
	}
	this.passengers = passengers;
	
}
public String getTravelDate() {
	return travelDate;
}
public void setTravelDate(String travelDate) {
	this.travelDate = travelDate;
}
public String getTicketNote() {
	return ticketNote;
}
public void setTicketNote(String ticketNote) {
	this.ticketNote = ticketNote;
}
public ArrayList<Person> getPassengers() {
	return passengers;
}
public void setPassengers(ArrayList<Person> passengers) {
	this.passengers = passengers;
}
//uses the given haversine function to calculate the number of miles between the two airports
public double getMiles(){
	return Haversine.getMiles(this.depAirport.getLatDegs(), this.depAirport.getLatMins(), this.depAirport.getLonDegs(), 
			this.depAirport.getLonMins(), this.arrAirport.getLatDegs(), this.arrAirport.getLatMins(), this.arrAirport.getLonDegs(), 
			this.arrAirport.getLonMins());
}

	//the flight class determines the sub total
	@Override
	public double getSubTotal() {
		if(this.getFlightClass().equals("EC")){
			return 0.15 * this.getMiles() * this.passengers.size();
		}else if(this.getFlightClass().equals("BC")){
			return 0.5 * this.getMiles() * this.passengers.size();
		}else{
			return 0.2 * this.getMiles() * this.passengers.size();
		}
	}

	//the taxes are added for every passenger in the list of passengers
	@Override
	public double getTaxes() {
		// TODO Auto-generated method stub
		return (0.075 * this.getSubTotal()) + (this.passengers.size() * 4) + (this.passengers.size() * 5.6) + (this.arrAirport.getPassengerFacilityFee() * this.passengers.size());
	}
	
	@Override
	public String getFlightReport(){
		String flightReport = String.format("%s		%s		%s		%s/%-10s		%s/%-10s		%s\n"
										  + "Traveller		Age		SeatNo\n", 
				this.travelDate, this.flightNo, this.flightClass, this.depAirport.getAddress().getCity(), 
				this.depTime, this.arrAirport.getAddress().getCity(), this.arrTime, this.aircraftType);
		for(Person p : this.passengers){
			flightReport = flightReport + p.getFlightReport();
		}
		flightReport = flightReport+this.ticketNote+"\n";
		return flightReport;
	}
}

