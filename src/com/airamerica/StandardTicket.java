package com.airamerica;


//this is a class to represent a standard ticket which is a type of product

public class StandardTicket extends Ticket {

	public StandardTicket(String productCode, Airport depAirport,
			Airport arrAirport, String depTime, String arrTime,
			String flightNo, String flightClass, String aircraftType) {
		super(productCode, depAirport, arrAirport, depTime, arrTime, flightNo,
				flightClass, aircraftType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double getFee() {
		return 0;
	}


	
	@Override
	public String getDetailedReport() {
		return String.format("%s	StandardTicket(%s) %s to %s (%f miles)(%d units @ $%.2f/unit)		$ %.2f 		$ %.2f 		$ %.2f\n", 
				this.getProductCode(), this.getFlightClass(), this.getDepAirport().getAirportCode(), 
				this.getArrAirport().getAirportCode(), this.getMiles(), this.getPassengers().size(), 
				this.getSubTotal()/this.getPassengers().size(), this.getSubTotal(), this.getTaxes(), this.getTotal());
	}
	
}