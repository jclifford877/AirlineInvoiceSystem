package com.airamerica;
//A class to represent an award ticket which is a type of product
public class AwardTicket extends Ticket {
	
	private int pointsPerMile;
	

	public AwardTicket(String productCode, Airport depAirport,
			Airport arrAirport, String depTime, String arrTime,
			String flightNo, String flightClass, String aircraftType,
			int pointsPerMile) {
		super(productCode, depAirport, arrAirport, depTime, arrTime, flightNo,
				flightClass, aircraftType);
		this.pointsPerMile = pointsPerMile;
	}

	public int getPointsPerMile() {
		return pointsPerMile;
	}

	public void setPointsPerMile(int pointsPerMile) {
		this.pointsPerMile = pointsPerMile;
	}

	@Override
	//an award ticket always has a flat fee of $30
	public double getFee() {
		// TODO Auto-generated method stub
		return 30;
	}

	@Override
	//an award ticket is always free in this project because points are used instead of dollars
	public double getSubTotal(){
		return 0;
	}
	
	@Override 
	//the subtotal has to be used in the calculation of the taxes so the tickets get subtotal method is called instead
	public double getTaxes(){
		return (0.075 * super.getSubTotal()) + (this.getPassengers().size() * 4) + (this.getPassengers().size() * 5.6) + (this.getArrAirport().getPassengerFacilityFee() * this.getPassengers().size());
	}

	@Override
	public String getDetailedReport() {
		return String.format("%s	AwardTicket(%s) %s to %s (%f miles)	(%d units @ $%.2f/unit)			$ %.2f 		$ %.2f 		$ %.2f\n", 
				this.getProductCode(), this.getFlightClass(), this.getDepAirport().getAirportCode(), 
				this.getArrAirport().getAirportCode(), this.getMiles(), this.getPassengers().size(), 
				this.getSubTotal()/this.getPassengers().size(), this.getSubTotal()+this.getFee(), this.getTaxes(), this.getTotal());
	}

	
}
