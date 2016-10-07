package com.airamerica.interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/* Assignment 5 - (Phase IV) */
/* NOTE: Donot change the package name or any of the method signatures.
 *  
 * There are 23 methods in total, all of which need to be completed as a 
 * bare minimum as part of the assignment.You can add additional methods 
 * for testing if you feel.
 * 
 * It is also recommended that you write a separate program to read
 * from the .dat files and test these methods to insert data into your 
 * database.
 * 
 * Donot forget to change your reports generation classes to read from 
 * your database instead of the .dat files.
 */

/**
 * This is a collection of utility methods that define a general API for
 * interacting with the database supporting this application.
 *
 */
public class InvoiceData {

	/**
	 * Method that removes every person record from the database
	 */
	public static void removeAllPersons() { 
		Connection conn = ConnectionBuilder.getConnection();
		PreparedStatement ps = null;
		try{
			String query = "DELETE FROM InvoiceProducts";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();

			query = "DELETE FROM Invoices";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			
			query = "DELETE FROM Customers";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			
			query = "DELETE FROM Emails";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();

			query = "DELETE FROM Persons";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * Method to add a person record to the database with the provided data. 
	 */
	public static void addPerson(String personCode, String firstName, String lastName, 
			String phoneNo, String street, String city, String state, 
			String zip, String country) {
		Connection conn = ConnectionBuilder.getConnection();
		PreparedStatement ps = null;
		try{
			String insertQuery = "INSERT INTO AddressStates (State) VALUES (?)";
			ps = conn.prepareStatement(insertQuery);
			ps.setString(1, state);
			ps.executeUpdate();
			
			String query = "SELECT AddressStateID FROM AddressStates WHERE State=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, state);
			ResultSet rs = ps.executeQuery();
			int addressStateID = 0;
			if(rs.next()){
				addressStateID = rs.getInt("AddressStateID");
			}
			
			insertQuery = "INSERT INTO Addresses (Street, City, Zip, Country, AddressStateID) VALUES (?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(insertQuery);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, zip);
			ps.setString(4, country);
			ps.setInt(5, addressStateID);
			ps.executeUpdate();
			
			query = "SELECT AddressID FROM Addresses WHERE Street=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, street);
			rs = ps.executeQuery();
			int addressID = 0;
			if(rs.next()){
				addressID = rs.getInt("AddressID");
			}
			
			insertQuery = "INSERT INTO Persons (PersonCode, FirstName, LastName, PhoneNumber, AddressID) VALUES (?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(insertQuery);
			ps.setString(1, personCode);
			ps.setString(2, firstName);
			ps.setString(3, lastName);
			ps.setString(4, phoneNo);
			ps.setInt(5, addressID);
			ps.executeUpdate();
			ps.close();
			conn.close();
			rs.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * Method that removes every airport record from the database
	 */
	public static void removeAllAirports() {
		Connection conn = ConnectionBuilder.getConnection();
		PreparedStatement ps = null;
		try{
			String query = "DELETE FROM InvoiceProducts WHERE TravelDate IS NOT NULL OR PersonID IS NOT NULL";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			
			query = "DELETE FROM Products WHERE DepAirportID IS NOT NULL OR ArrAirportID IS NOT NULL";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			
			query = "DELETE FROM Airports";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();
			conn.close();
			
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Method to add a airport record to the database with the provided data. 
	 */
	public static void addAirport(String airportCode, String name, String street, 
			String city, String state, String zip, String country, 
			int latdegs, int latmins, int londegs, int lonmins, 
			double passengerFacilityFee) { 
		Connection conn = ConnectionBuilder.getConnection();
		PreparedStatement ps = null;
		try{
			String insertQuery = "INSERT INTO AddressStates (State) VALUES (?)";
			ps = conn.prepareStatement(insertQuery);
			ps.setString(1, state);
			ps.executeUpdate();
			
			String query = "SELECT AddressStateID FROM AddressStates WHERE State=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, state);
			ResultSet rs = ps.executeQuery();
			int addressStateID = 0;
			if(rs.next()){
				addressStateID = rs.getInt("AddressStateID");
			}
			
			insertQuery = "INSERT INTO Addresses (Street, City, Zip, Country, AddressStateID) VALUES (?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(insertQuery);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setString(3, zip);
			ps.setString(4, country);
			ps.setInt(5, addressStateID);
			ps.executeUpdate();
			
			query = "SELECT AddressID FROM Addresses WHERE Street=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, street);
			rs = ps.executeQuery();
			int addressID = 0;
			if(rs.next()){
				addressID = rs.getInt("AddressID");
			}
			
			insertQuery = "INSERT INTO Airports (AirportCode, AirportName, AddressID, LatDegs, LatMins, LonDegs, LonMins, PassengerFacilityFee) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(insertQuery);
			ps.setString(1, airportCode);
			ps.setString(2, name);
			ps.setInt(3, addressID);
			ps.setInt(4, latdegs);
			ps.setInt(5, latmins);
			ps.setInt(6, londegs);
			ps.setInt(7, lonmins);
			ps.setDouble(8, passengerFacilityFee);
			ps.executeUpdate();
			ps.close();
			conn.close();
			rs.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 */
	public static void addEmail(String personCode, String email) { 
		Connection conn = ConnectionBuilder.getConnection();
		PreparedStatement ps = null;
		try{
			String query = "INSERT INTO Emails (PersonID, EmailAddress) VALUES ((SELECT PersonID FROM Persons WHERE PersonCode=?), ?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, personCode);
			ps.setString(2, email);
			ps.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Method that removes every customer record from the database
	 */
	public static void removeAllCustomers() {
		Connection conn = ConnectionBuilder.getConnection();
		PreparedStatement ps = null;
		try{
			String query = "DELETE FROM InvoiceProducts WHERE InvoiceID IS NOT NULL";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			
			query = "DELETE FROM Invoices";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			
			query = "DELETE FROM Customers";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			
			ps.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Method to add a customer record to the database with the provided data. 
	 */
	public static void addCustomer(String customerCode, String customerType, 
			String primaryContactPersonCode, String name, 
			int airlineMiles) {	
		Connection conn = ConnectionBuilder.getConnection();
		PreparedStatement ps = null;
		try{
			String query = "INSERT INTO Customers (CustomerCode, CustomerType, PersonID, CustomerName, AirlineMiles) VALUES (?, ?, (SELECT PersonID FROM Persons WHERE PersonCode=?), ?, ?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, customerCode);
			ps.setString(2, customerType);
			ps.setString(3, primaryContactPersonCode);
			ps.setString(4, name);
			ps.setInt(5, airlineMiles);
			ps.executeUpdate();
			

			ps.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Removes all product records from the database
	 */
	public static void removeAllProducts() {
		Connection conn = ConnectionBuilder.getConnection();
		PreparedStatement ps = null;
		try{
			String query = "DELETE FROM InvoiceProducts WHERE ProductID IS NOT NULL";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			
			query = "DELETE FROM Products";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds an standardTicket record to the database with the
	 * provided data.  
	 */
	public static void addStandardTicket(String productCode,String depAirportCode, 
			String arrAirportCode, String depTime, String arrTime, 
			String flightNo, String flightClass, String aircraftType) { 
		Connection conn = ConnectionBuilder.getConnection();
		PreparedStatement ps = null;
		try{
			String query = "INSERT INTO Products (ProductCode, ProductType, DepAirportID, ArrAirportID, DepTime, ArrTime, FlightNo, FlightClass, AircraftType) VALUES (?, 'TS', (SELECT AirportID FROM Airports WHERE AirportCode=?), (SELECT AirportID FROM Airports WHERE AirportCode=?), ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, productCode);
			ps.setString(2, depAirportCode);
			ps.setString(3, arrAirportCode);
			ps.setString(4, depTime);
			ps.setString(5, arrTime);
			ps.setString(6, flightNo);
			ps.setString(7, flightClass);
			ps.setString(8, aircraftType);
			ps.executeUpdate();
			
			ps.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
		
	
	 /** 
	 * Adds an offSeasonTicket record to the database with the
	 * provided data.  
	 */
	public static void addOffSeasonTicket(String productCode, String seasonStartDate, 
			String seasonEndDate, String depAirportCode, String arrAirportCode, 
			String depTime, String arrTime,	String flightNo, String flightClass, 
			String aircraftType, double rebate) {
		Connection conn = ConnectionBuilder.getConnection();
		PreparedStatement ps = null;
		try{
			String query = "INSERT INTO Products (ProductCode, ProductType, DepAirportID, ArrAirportID, DepTime, ArrTime, FlightNo, FlightClass, AircraftType, SeasonStartDate, SeasonEndDate, Rebate) VALUES (?, 'TO', (SELECT AirportID FROM Airports WHERE AirportCode=?), (SELECT AirportID FROM Airports WHERE AirportCode=?), ?, ?, ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, productCode);
			ps.setString(2, depAirportCode);
			ps.setString(3, arrAirportCode);
			ps.setString(4, depTime);
			ps.setString(5, arrTime);
			ps.setString(6, flightNo);
			ps.setString(7, flightClass);
			ps.setString(8, aircraftType);
			ps.setString(9, seasonStartDate);
			ps.setString(10, seasonEndDate);
			ps.setDouble(11, rebate);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	 
	 /** Adds an awardsTicket record to the database with the
	 * provided data.  
	 */
	public static void addAwardsTicket(String productCode,String depAirportCode, 
			String arrAirportCode, String depTime, String arrTime, 
			String flightNo, String flightClass, 
			String aircraftType, double pointsPerMile) { 
		Connection conn = ConnectionBuilder.getConnection();
		PreparedStatement ps = null;
		try{
			String query = "INSERT INTO Products (ProductCode, ProductType, DepAirportID, ArrAirportID, DepTime, ArrTime, FlightNo, FlightClass, AircraftType, PointsPerMile) VALUES (?, 'TA', (SELECT AirportID FROM Airports WHERE AirportCode=?), (SELECT AirportID FROM Airports WHERE AirportCode=?), ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, productCode);
			ps.setString(2, depAirportCode);
			ps.setString(3, arrAirportCode);
			ps.setString(4, depTime);
			ps.setString(5, arrTime);
			ps.setString(6, flightNo);
			ps.setString(7, flightClass);
			ps.setString(8, aircraftType);
			ps.setDouble(9, pointsPerMile);
			ps.executeUpdate();
			
			ps.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	} 
	
	/**
	 * Adds a CheckedBaggage record to the database with the
	 * provided data.  
	 */
	public static void addCheckedBaggage(String productCode, String ticketCode) { 
		
		Connection conn = ConnectionBuilder.getConnection();
		PreparedStatement ps = null;
		try{
			String query = "INSERT INTO Products (ProductCode, ProductType) VALUES (?, 'SC')";
			ps = conn.prepareStatement(query);
			ps.setString(1, productCode);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a Insurance record to the database with the
	 * provided data.  
	 */
	public static void addInsurance(String productCode, String productName, 
			String ageClass, double costPerMile) {	
		Connection conn = ConnectionBuilder.getConnection();
		PreparedStatement ps = null;
		try{
			String query = "INSERT INTO Products (ProductCode, ProductType, InsuranceName, AgeClass, InsuranceCostPerMile) VALUES (?, 'SI', ?, ?, ?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, productCode);
			ps.setString(2, productName);
			ps.setString(3, ageClass);
			ps.setDouble(4, costPerMile);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Adds a SpecialAssistance record to the database with the
	 * provided data.  
	 */
	public static void addSpecialAssistance(String productCode, String assistanceType) { 
		
		Connection conn = ConnectionBuilder.getConnection();
		PreparedStatement ps = null;
		try{
			String query = "INSERT INTO Products (ProductCode, ProductType, TypeOfService) VALUES (?, 'SS', ?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, productCode);
			ps.setString(2, assistanceType);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a refreshment record to the database with the
	 * provided data.  
	 */
	public static void addRefreshment(String productCode, String name, double cost) { 
		
		Connection conn = ConnectionBuilder.getConnection();
		PreparedStatement ps = null;
		try{
			
			String query = "INSERT INTO Products (ProductCode, ProductType, RefreshmentName, RefreshmentCost) VALUES (?, 'SR', ?, ?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, productCode);
			ps.setString(2, name);
			ps.setDouble(3, cost);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Removes all invoice records from the database
	 */
	public static void removeAllInvoices() { 
		
		Connection conn = ConnectionBuilder.getConnection();
		PreparedStatement ps = null;
		try{
			String query = "DELETE FROM InvoiceProducts WHERE InvoiceID IS NOT NULL";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			
			query = "DELETE FROM Invoices";
			ps = conn.prepareStatement(query);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Adds an invoice record to the database with the given data.  
	 */
	public static void addInvoice(String invoiceCode, String customerCode, 
			String salesPersonCode, String invoiceDate) { 
		
		Connection conn = ConnectionBuilder.getConnection();
		PreparedStatement ps = null;
		try{
			String query = "INSERT INTO Invoices (InvoiceCode, CustomerID, PersonID, InvoiceDate) VALUES (?, (SELECT CustomerID FROM Customers WHERE CustomerCode=?), (SELECT PersonID FROM Persons WHERE PersonCode=?), ?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, invoiceCode);
			ps.setString(2, customerCode);
			ps.setString(3, salesPersonCode);
			ps.setString(4, invoiceDate);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Adds a particular Ticket (corresponding to <code>productCode</code>) to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * additional details
	 */
	public static void addTicketToInvoice(String invoiceCode, String productCode, 
			String travelDate, String ticketNote) { 
		
		Connection conn = ConnectionBuilder.getConnection();
		PreparedStatement ps = null;
		try{
			String query = "INSERT INTO InvoiceProducts (ProductID, InvoiceID, TravelDate, TicketNote) VALUES ((SELECT ProductID FROM Products WHERE ProductCode=?), (SELECT InvoiceID FROM Invoices WHERE InvoiceCode=?), ?, ?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, productCode);
			ps.setString(2, invoiceCode);
			ps.setString(3, travelDate);
			ps.setString(4, ticketNote);
			
			ps.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Adds a Passenger information to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> 
	 */
	public static void addPassengerInformation(String invoiceCode, String productCode, 
			String personCode, 
			String identity, int age, String nationality, String seat){ 
		
		Connection conn = ConnectionBuilder.getConnection();
		PreparedStatement ps = null;
		try{
			String query = "UPDATE Persons SET FormalID=?, Age=?, Nationality=?, Seat=? WHERE PersonCode=?";
			ps = conn.prepareStatement(query);
			ps.setString(1, identity);
			ps.setInt(2, age);
			ps.setString(3, nationality);
			ps.setString(4, seat);
			ps.setString(5, personCode);
			ps.executeUpdate();
			
			query = "INSERT INTO InvoiceProducts (ProductID, InvoiceID, PersonID) VALUES ((SELECT ProductID FROM Products WHERE ProductCode=?), (SELECT InvoiceID FROM Invoices WHERE InvoiceCode=?), (SELECT PersonID FROM Persons WHERE PersonCode=?)) ";
			ps = conn.prepareStatement(query);
			ps.setString(1, productCode);
			ps.setString(2, invoiceCode);
			ps.setString(3, personCode);
			ps.executeUpdate();
			
			ps.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Adds an Insurance Service (corresponding to <code>productCode</code>) to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of quantity and associated ticket information
	 */
	public static void addInsuranceToInvoice(String invoiceCode, String productCode, 
			int quantity, String ticketCode) { 
		
		Connection conn = ConnectionBuilder.getConnection();
		PreparedStatement ps = null;
		try{
			String query = "INSERT INTO InvoiceProducts (InvoiceID, ProductID, InsuranceQuantity) VALUES ((SELECT InvoiceID FROM Invoices WHERE InvoiceCode=?), (SELECT ProductID FROM Products WHERE ProductCode=?), ?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, invoiceCode);
			ps.setString(2, productCode);
			ps.setInt(3, quantity);
			ps.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Adds a CheckedBaggage Service (corresponding to <code>productCode</code>) to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of quantity.
	 */
	public static void addCheckedBaggageToInvoice(String invoiceCode, String productCode, 
			int quantity) { 
		
		Connection conn = ConnectionBuilder.getConnection();
		PreparedStatement ps = null;
		try{
			String query = "INSERT INTO InvoiceProducts (InvoiceID, ProductID, AdditionalBaggage) VALUES ((SELECT InvoiceID FROM Invoices WHERE InvoiceCode=?), (SELECT ProductID FROM Products WHERE ProductCode=?), ?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, invoiceCode);
			ps.setString(2, productCode);
			ps.setInt(3, quantity);
			ps.executeUpdate();
			
			ps.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
		
	/**
	 * Adds a SpecialAssistance Service (corresponding to <code>productCode</code>) to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of quantity.
	 */
	public static void addSpecialAssistanceToInvoice(String invoiceCode, String productCode, 
			String personCode) { 
		
		Connection conn = ConnectionBuilder.getConnection();
		PreparedStatement ps = null;
		try{
			String query = "INSERT INTO InvoiceProducts (InvoiceID, ProductID, PersonID) VALUES ((SELECT InvoiceID FROM Invoices WHERE InvoiceCode=?), (SELECT ProductID FROM Products WHERE ProductCode=?), (SELECT PersonID FROM Persons WHERE PersonCode=?))";
			ps = conn.prepareStatement(query);
			ps.setString(1, invoiceCode);
			ps.setString(2, productCode);
			ps.setString(3, personCode);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Adds a Refreshment service (corresponding to <code>productCode</code>) to an 
	 * invoice corresponding to the provided <code>invoiceCode</code> with the given
	 * number of quantity.
	 */
	public static void addRefreshmentToInvoice(String invoiceCode, 
			String productCode, int quantity) { 
		
		Connection conn = ConnectionBuilder.getConnection();
		PreparedStatement ps = null;
		try{
			String query = "INSERT INTO InvoiceProducts (InvoiceID, ProductID, RefreshmentQuantity) VALUES ((SELECT InvoiceID FROM Invoices WHERE InvoiceCode=?), (SELECT ProductID FROM Products WHERE ProductCode=?), ?)";
			ps = conn.prepareStatement(query);
			ps.setString(1, invoiceCode);
			ps.setString(2, productCode);
			ps.setInt(3, quantity);
			ps.executeUpdate();
			ps.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}