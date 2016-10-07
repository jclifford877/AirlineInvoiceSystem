package com.airamerica;

import unl.cse.assignments.DataConverter;

import com.airamerica.utils.Finder;
//this is a class to represent an insurance package which is a type of product

public class Insurance extends Service {
	private String name;
	private String ageClass;
	private double costPerMile;
	private String quantity;
	private Ticket ticket;
	
	public Insurance(String productCode, String name, String ageClass, double costPerMile){
		super(productCode);
		this.name = name;
		this.ageClass = ageClass;
		this.costPerMile = costPerMile;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAgeClass() {
		return ageClass;
	}

	public void setAgeClass(String ageClass) {
		this.ageClass = ageClass;
	}

	public double getCostPerMile() {
		return costPerMile;
	}

	public void setCostPerMile(double costPerMile) {
		this.costPerMile = costPerMile;
	}

	public Insurance(String productCode) {
		super(productCode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setInfo(String s) {
		// TODO Auto-generated method stub
		String[] tokens = s.split(":");
		this.quantity = tokens[1];
		this.ticket = (Ticket) Finder.findObject(DataConverter.getProducts(), tokens[2]);
		
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public Product getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	@Override
	public double getSubTotal() {
		// TODO Auto-generated method stub
		return this.costPerMile * this.ticket.getMiles();
	}

	@Override
	public String getDetailedReport() {
		
		return String.format("%s	Insurance %-20s(%s)(%.2f per mile x %.2f miles)			$ %.2f 		$ %.2f 			$ %.2f\n",
				this.getProductCode(), this.name, this.ageClass, this.costPerMile, 
				this.ticket.getMiles(), this.getSubTotal(), this.getTaxes(), this.getTotal());
				
	}

}
