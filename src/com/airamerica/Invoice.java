package com.airamerica;

import java.util.ArrayList;
import com.airamerica.utils.StandardUtils;

public class Invoice {

	//this class represents an invoice
	
	private String invoiceCode;
	private Customer customer;
	private Person salesPerson;
	private String invoiceDate;
	private ArrayList<Product> productList;
	private String pnr;
	public Invoice(String invoiceCode, Customer customer, Person salesPerson,
			String invoiceDate) {
		super();
		this.invoiceCode = invoiceCode;
		this.customer = customer;
		this.salesPerson = salesPerson;
		this.invoiceDate = invoiceDate;
		this.productList = new ArrayList<Product>();
		this.pnr = StandardUtils.generatePNR();
	}
	public Invoice(String invoiceCode, Customer customer, Person salesPerson,
			String invoiceDate, ArrayList<Product> productList) {
		super();
		this.invoiceCode = invoiceCode;
		this.customer = customer;
		this.salesPerson = salesPerson;
		this.invoiceDate = invoiceDate;
		this.productList = productList;
		this.pnr = StandardUtils.generatePNR();
	}
	public String getInvoiceCode() {
		return invoiceCode;
	}
	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Person getSalesPerson() {
		return salesPerson;
	}
	public void setSalesPerson(Person salesPerson) {
		this.salesPerson = salesPerson;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public ArrayList<Product> getProductList() {
		return productList;
	}
	public void setProductList(ArrayList<Product> productList) {
		this.productList = productList;
	}
	
	public void addProduct(Product p){
		this.productList.add(p);
	}
	
	//returns the subtotal of all of the products in this invoice's list of products
	public double getSubTotal(){
		double subtotal = 0;
		for(Product p : this.productList){
				subtotal += p.getSubTotal() + p.getFee();
		}
		return subtotal;
	}
	
	
	//returns the sum of all of the taxes of all of the products in this invoice's list of products
	public double getTaxes(){
		double taxes = 0;
		for(Product p : this.productList){
			taxes += p.getTaxes();
		}
		return taxes;
	}
	
	//returns the discount of this invoice's customer
	public double getDiscount(){
		return this.customer.getDiscount(this.getSubTotal(), this.getTaxes());
	}
	
	//returns the fee of this invoice'scustomer
	public double getFees(){
		return  this.customer.getFee();
	}

	//returns a summary of this invoice
	public String generateSummary(){
		String summary = String.format("InvoiceCode	CustomerName	CustomerType		SalesPerson		Subtotal	Fees		Taxes		Discount		Total\n"
				+ "%s %-30s [%-10s] %-10s, %-15s $ %.2f 	$ %.2f 		$ %.2f 	-$ %.2f 		$ %.2f\n", 
				this.invoiceCode, this.customer.getName(), this.customer.getType(), 
				this.salesPerson.getLastName(), 
				this.salesPerson.getFirstName(), this.getSubTotal(), this.getFees(), this.getTaxes(), 
				this.getDiscount(), this.getSubTotal() + this.getFees() + this.getTaxes() - this.getDiscount());
		return summary;
	}
	
	//returns a detailed report of this invoice by looping through this invoice's list of products and 
	//generating their summaries
	public String generateDetailedReport(){
		String detailedReport = String.format("Invoice %15s \n"
											+ "-----------------------------------\n"
											+ "Air America\n"
											+ "Issue Date: %15s  PNR: %s\n"
											+ "-----------------------------------\n"
											+ "%s\n"
											+ "Customer Information\n"
											+ "%s (%s)\n"
											+ "[%s]\n"
											+ "%s, %s\n"
											+ "%s\n"
											+ "%s %s %s %s\n"
											+ "Salesperson: %s, %s\n"
											+ "------------------------------------\n"
											+ "Fares and Services\n"
											+ "Code	Item											Subtotal		Tax			Total\n", 
											this.invoiceCode, this.invoiceDate, this.pnr, this.generateFlightReport(), 
											this.customer.getName(),this.customer.getCustomerCode(), this.customer.getType(), this.customer.getPrimaryContact().getLastName(), 
											this.customer.getPrimaryContact().getFirstName(), this.customer.getPrimaryContact().getAddress().getStreet(),
											this.customer.getPrimaryContact().getAddress().getCity(), this.customer.getPrimaryContact().getAddress().getState(),
											this.customer.getPrimaryContact().getAddress().getZip(), this.customer.getPrimaryContact().getAddress().getCountry(),
											this.salesPerson.getLastName(), this.salesPerson.getFirstName());
		for(Product p : this.productList){
			detailedReport = detailedReport + p.getDetailedReport();
		}
		detailedReport = detailedReport + String.format("-------------------------------\n"
										+ "Sub-totals 											$ %.2f 		$ %.2f 		$ %.2f\n"
									    + "Discount										  						       -$ %.2f\n"
									    + "Additional Fee								  									$ %.2f\n"
									    + "Total										  								$ %.2f\n",	
									    this.getSubTotal(), this.getTaxes(), this.getSubTotal()+this.getTaxes(), 
									    this.getDiscount(), this.getFees(), this.getSubTotal()+this.getTaxes()+this.getFees()-this.getDiscount());
		detailedReport = detailedReport + "----------------------------------\n";
		return detailedReport;
	}
	
	//returns a flight report of this invoice by looping through all of the products and 
	//returning their flight reports
	public String generateFlightReport(){
		String flightReport = String.format(  "Flight Information\n"
											+ "Date			Flight		Class		Departure City/Time		Arrival City/Time		Aircraft\n");
		
		for(Product p : this.productList){
			flightReport = flightReport + p.getFlightReport();
		}
		flightReport = flightReport + "-------------------------------------------------";
		return flightReport;
	}

}
