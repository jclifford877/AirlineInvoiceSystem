package com.airamerica;
//this is a class to represent a refreshment which is a type of product

public class Refreshments extends Service {
	private String name;
	private double cost;
	private int quantity;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public Refreshments(String productCode, String name, double cost) {
		super(productCode);
		this.name = name;
		this.cost = cost;
	}

	public Refreshments(String productCode) {
		super(productCode);
	}

	@Override
	public void setInfo(String s) {
		String[] tokens = s.split(":");
		this.quantity = Integer.parseInt(tokens[1]);
		
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	//the benefit of the doubt is given and we assume that if a refreshment is on an invoice there will be
	//a ticket associated with it so it always gets the 5% discount
	@Override
	public double getSubTotal() {
		return this.cost * this.quantity * 0.95;
	}

	@Override
	public String getDetailedReport() {
		return String.format("%s 	%s (%d units @ $ %.2f/unit with 5 percent discount) 			$ %5.2f 		$ %4.2f 			$ %4.2f\n", 
				this.getProductCode(), this.name, this.quantity, this.cost, this.getSubTotal(), this.getTaxes(), this.getTotal());
	}

}
