package com.airamerica;

import java.util.Arrays;

public class OffseasonTicket extends Ticket {

	private String seasonStartDate;
	private String seasonEndDate;
	private double rebate;
	public OffseasonTicket(String productCode, Airport depAirport,
			Airport arrAirport, String depTime, String arrTime,
			String flightNo, String flightClass, String aircraftType,
			String seasonStartDate, String seasonEndDate, double rebate) {
		super(productCode, depAirport, arrAirport, depTime, arrTime, flightNo,
				flightClass, aircraftType);
		this.seasonStartDate = seasonStartDate;
		this.seasonEndDate = seasonEndDate;
		this.rebate = rebate;
	}
	public String getSeasonStartDate() {
		return seasonStartDate;
	}
	public void setSeasonStartDate(String seasonStartDate) {
		this.seasonStartDate = seasonStartDate;
	}
	public String getSeasonEndDate() {
		return seasonEndDate;
	}
	public void setSeasonEndDate(String seasonEndDate) {
		this.seasonEndDate = seasonEndDate;
	}
	public double getRebate() {
		return rebate;
	}
	public void setRebate(double rebate) {
		this.rebate = rebate;
	}
	@Override
	public double getFee() {
		return 20;
	}
	
	//sorts the dates to determine if the travel date falls between the start and end date
	//if it is then apply the rebate
	public double getSubTotal(){
		double potentialRebate;
		String[] dates = new String[3];
		dates[0] = this.getSeasonStartDate();
		dates[1] = this.getSeasonEndDate();
		dates[2] = this.getTravelDate();
		//Arrays.sort(dates);
		if(dates[1].equals(this.getTravelDate())){
			potentialRebate = 1-this.rebate;
		}else{
			potentialRebate = 1;
		}
		if(this.getFlightClass().equals("EC")){
			return 0.15 * this.getMiles() * this.getPassengers().size() * potentialRebate;
		}else if(this.getFlightClass().equals("BC")){
			return 0.5 * this.getMiles() * this.getPassengers().size() * potentialRebate;
		}else{
			return 0.2 * this.getMiles() * this.getPassengers().size() * potentialRebate;
		}
	}
	
	
	public String getDetailedReport() {
		return String.format("%s	OffseasonTicket(%s) %s to %s (%f miles)	(%d units @ $%.2f/unit)	$ %.2f 		$ %.2f 		$ %.2f\n", 
				this.getProductCode(), this.getFlightClass(), this.getDepAirport().getAirportCode(), 
				this.getArrAirport().getAirportCode(), this.getMiles(), this.getPassengers().size(), 
				this.getSubTotal()/this.getPassengers().size(), this.getSubTotal() + this.getFee(), this.getTaxes(), this.getTotal());
	}
}