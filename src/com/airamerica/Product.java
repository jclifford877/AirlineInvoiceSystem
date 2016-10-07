package com.airamerica;
//this class represents an abstract product because all products are of a specific type
public abstract class Product implements Searchable{
	private String productCode;
	public Product(String productCode){
		this.productCode = productCode;
	}
	public String getProductCode() {
		return productCode;
	}
	
	//this method takes in a string from the invoices data file related to a product and sets the additional information
	//that was in the invoices data file
	public abstract void setInfo(String s);
	
	public abstract double getTaxes();

	public abstract double getSubTotal();

	public  double getTotal(){
		return this.getTaxes() + this.getSubTotal() + this.getFee();
	}
	
	public abstract double getFee();
	

	public String getCode(){
		return this.productCode;
	}
	
	//the flight report is empty for all non-tickets so it is defined here
	public String getFlightReport() {
		return "";
	}

	public abstract String getDetailedReport(); 
}
