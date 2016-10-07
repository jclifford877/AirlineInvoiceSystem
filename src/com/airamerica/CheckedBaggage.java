package com.airamerica;

public class CheckedBaggage extends Service {
	private Ticket ticket;
	private String ticketCode;
	private int additionalBaggage;

	public CheckedBaggage(String productCode, String ticketCode) {
		super(productCode);
		this.ticketCode = ticketCode;
	}


	public CheckedBaggage(String productCode) {
		super(productCode);
	}


	public Ticket getTicket() {
		return ticket;
	}


	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}


	public String getTicketCode() {
		return ticketCode;
	}


	public void setTicketCode(String ticketCode) {
		this.ticketCode = ticketCode;
	}


	@Override
	public void setInfo(String s) {
		// TODO Auto-generated method stub
		String[] tokens = s.split(":");
		this.additionalBaggage = Integer.parseInt(tokens[1]);
	}

	public int getAdditionalBaggage() {
		return additionalBaggage;
	}

	public void setAdditionalBaggage(int additionalBaggage) {
		this.additionalBaggage = additionalBaggage;
	}


	//the subtotal changes depending on what the type of the ticket is because you get either 0, 2 or 3 free bags
	//depending on the flight class
	@Override
	public double getSubTotal() {
		int subtotal;
		if(this.ticket == null){
			return 0;
		}
		if(this.ticket.getFlightClass().equals("EC")){
			 subtotal = 25;
			for(int i=0; i<this.additionalBaggage; i++){
				subtotal += 35;
			}
		}else if(this.ticket.getFlightClass().equals("EP")){
			 subtotal = 0;
			for(int i=1; i<this.additionalBaggage; i++){
				if(i==1){
					subtotal += 25;
				}else{
					subtotal += 35;
				}
			}
		}else{
			subtotal = 0;
			for(int i=2; i<this.additionalBaggage; i++){
				if(i==2){
					subtotal += 25;
				}else{
					subtotal += 35;
				}
			}
		}
		return subtotal;
	}


	@Override
	public String getDetailedReport() {
		return String.format("%s	Baggage (%d units)									$ %4.2f 		$ %4.2f 			$ %4.2f\n", 
				this.getProductCode(), this.additionalBaggage + 1, this.getSubTotal(), this.getTaxes(), this.getTotal());
	}


}
