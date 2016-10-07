package unl.cse.assignments;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.airamerica.Invoice;
import com.airamerica.SortedList;
import com.airamerica.SortedListNode;

/* Assignment 3,5 and 6 (Project Phase-II,IV and V) */
/*
 * James Clifford
 * CSCE 156
 * This phase generates a list of invoices based on the data file, sorts the list, then prints a summary of the invoices,
 * then prints a detailed report of all of the invoices.
 */
public class InvoiceReport {
	
	private String generateSummaryReport(Comparator<Invoice> c) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Executive Summary Report\n");
		sb.append("=========================\n");
		
		SortedList<Invoice> invoices = DataConverter.getInvoices(c);
		for(Invoice i : invoices){
			sb.append(i.generateSummary());
		}
		
		sb.append("Detailed Reports\n");
		sb.append("==================\n");
		
		
		for(Invoice i : invoices){
			//System.out.println("A LINE");
			sb.append(i.generateDetailedReport());
		}
		
		//TODO: Add code for generating summary of all Invoices
		
		return sb.toString();
	}
	

	private String getTravelSummary() {
		StringBuilder sb = new StringBuilder();
		sb.append("FLIGHT INFORMATION");
		sb.append("==================================================\n");

		//TODO: Add code for generating Travel Information of an Invoice
		
		return sb.toString();
		
	}
	
	private String getCostSummary() {
		StringBuilder sb = new StringBuilder();
		sb.append("FARES AND SERVICES");
		sb.append("==================================================\n");

		//TODO: Add code for generating Cost Summary of all 
		//products and services in an Invoice
		
		return sb.toString();
		
	}

	public String generateDetailReport() {
	StringBuilder sb = new StringBuilder();		
	sb.append("Individual Invoice Detail Reports\n");
	sb.append("==================================================\n");
	
	/* TODO: Loop through all invoices and call the getTravelSummary() and 
	getCostSummary() for each invoice*/
	
	
	
	return sb.toString();
	}
	
//comparator that checks the order of the last names first then the first names of the invoice's customer's primary contact
public static final  Comparator<Invoice> byName = new Comparator<Invoice>() {
		public int compare(Invoice arg0, Invoice arg1){
			if(arg0.getCustomer().getPrimaryContact().getLastName().equals(arg1.getCustomer().getPrimaryContact().getLastName())){
				return arg0.getCustomer().getPrimaryContact().getFirstName().compareTo(arg1.getCustomer().getPrimaryContact().getFirstName());
			}else{
				return arg0.getCustomer().getPrimaryContact().getLastName().compareTo(arg1.getCustomer().getPrimaryContact().getLastName());
			}
		}
	};
	
public static final Comparator<Invoice> byTotal = new Comparator<Invoice>(){
	public int compare(Invoice arg0, Invoice arg1){
		return (int) (arg0.getSubTotal() - arg1.getSubTotal());
	}
};


public static final Comparator<Invoice> byType = new Comparator<Invoice>(){
	public int compare(Invoice arg0, Invoice arg1){
		if(arg0.getCustomer().getType().equals(arg1.getCustomer().getType())){
			if(arg0.getSalesPerson().getLastName().equals(arg1.getSalesPerson().getLastName())){
				return arg0.getSalesPerson().getFirstName().compareTo(arg1.getSalesPerson().getFirstName());
			}else{
				return arg0.getSalesPerson().getLastName().compareTo(arg1.getSalesPerson().getLastName());
			}
		}else{
			return arg1.getCustomer().getType().compareTo(arg0.getCustomer().getType());
		}
	}
};

	
	public static void main(String args[]) {
		
		InvoiceReport ir = new InvoiceReport();
		String summary = ir.generateSummaryReport(byName);
				
		System.out.println(summary);
		System.out.println("\n\n");
		
		System.out.println("======================================================================================================================");
		System.out.println("\n\n");
		
		summary = ir.generateSummaryReport(byTotal);
				
		System.out.println(summary);
		System.out.println("\n\n");
		
		System.out.println("======================================================================================================================");
		System.out.println("\n\n");

		summary = ir.generateSummaryReport(byType);
				
		System.out.println(summary);
		System.out.println("\n\n");
		
		System.out.println("======================================================================================================================");
		System.out.println("\n\n");
	}
}
