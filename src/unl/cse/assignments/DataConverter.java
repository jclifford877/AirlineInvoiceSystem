package unl.cse.assignments;

/* Phase-I */
/*
 * James Clifford
 * CSE 156
 * This is a program to read in data from the database and populate array lists with the right classes.
 */
import com.airamerica.*;
import com.airamerica.interfaces.ConnectionBuilder;
import com.airamerica.utils.Finder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



import java.util.Comparator;
import java.util.Scanner;













// Include imports for XML/JSON libraries if needed
import com.thoughtworks.xstream.XStream;

public class DataConverter {
//this class contains methods to read in all of the data from the data files and makes lists of the objects of the file

		// TODO: Add your code to read data from .dat files, create objects
		//and export them as XML or JSON 
	public static ArrayList<Person> getPeople(){
		ArrayList<Person> people = new ArrayList<Person>();
		Connection conn = ConnectionBuilder.getConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;
		String query = "SELECT PersonID, Street, City, State, Zip, Country, PersonCode, FirstName, LastName, PhoneNumber, Seat, FormalID, Age, Nationality FROM Persons AS p JOIN Addresses AS a ON a.AddressID=p.AddressID JOIN AddressStates AS s ON s.AddressStateID=a.AddressStateID";
		try{
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()){
				ArrayList<String> emails = new ArrayList<String>();
				ResultSet emailResults = null;
				String emailQuery = "SELECT EmailAddress FROM Emails WHERE PersonID=?";
				ps = conn.prepareStatement(emailQuery);
				ps.setInt(1, rs.getInt("PersonID"));
				emailResults = ps.executeQuery();
				while(emailResults.next()){
					emails.add(emailResults.getString("EmailAddress"));
				}
				Address a = new Address(rs.getString("Street"), rs.getString("City"), rs.getString("State"), rs.getString("Zip"), rs.getString("Country"));
				Person p = new Person(rs.getString("PersonCode"), rs.getString("FirstName"), rs.getString("LastName"), a, emails, 
						rs.getString("PhoneNumber"), rs.getString("Seat"), rs.getString("FormalID"), rs.getInt("Age"), rs.getString("Nationality"));
				people.add(p);
				emailResults.close();
			}
			ps.close();
			rs.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return people;
		
		
		
	}
	
	public static ArrayList<Airport> getAirports(){
		ArrayList<Airport> airports = new ArrayList<Airport>();
		Connection conn = ConnectionBuilder.getConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;
		String query = "SELECT Street, City, State, Zip, Country, AirportCode, AirportName, LatDegs, LatMins, LonDegs, LonMins, PassengerFacilityFee FROM Airports AS a JOIN Addresses AS ad ON a.AddressID=ad.AddressID JOIN AddressStates AS s ON s.AddressStateID=ad.AddressStateID";
		try{
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()){
				Address a = new Address(rs.getString("Street"), rs.getString("City"), rs.getString("State"), rs.getString("Zip"), rs.getString("Country"));
				Airport ap = new Airport(rs.getString("AirportCode"), rs.getString("AirportName"), a, rs.getInt("LatDegs"), rs.getInt("LatMins"), rs.getInt("LonDegs"), rs.getInt("LonMins"), rs.getDouble("PassengerFacilityFee"));
				airports.add(ap);
			}
			ps.close();
			rs.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return airports;
	}

	public static ArrayList<Airport> getAirportsFromFile(){
		ArrayList<Airport> airports = new ArrayList<Airport>();
		File airportFile = new File("data/Airports.dat");
		try{
			Scanner s = new Scanner(airportFile);
			int numOfAirports = s.nextInt();
			s.nextLine();
			for(int i=0; i<numOfAirports; i++){
				String line = s.nextLine();
				String[] tokens = line.split(";");
				String airportCode = tokens[0];
				String name = tokens[1];
				String[] addressTokens = tokens[2].split(",");
				String street = addressTokens[0];
				String city = addressTokens[1];
				String state = addressTokens[2];
				String zip = addressTokens[3];
				String country = addressTokens[4];
				Address address = new Address(street, city, state, zip, country);
				String[] degtokens = tokens[3].split(",");
				int latDegs = Integer.parseInt(degtokens[0]);
				int latMins = Integer.parseInt(degtokens[1]);
				int lonDegs = Integer.parseInt(degtokens[2]);
				int lonMins = Integer.parseInt(degtokens[3]);
				double passengerFacilityFee = Double.parseDouble(tokens[4]);
				Airport airport = new Airport(airportCode, name, address, latDegs, latMins, lonDegs, lonMins, passengerFacilityFee);
				airports.add(airport);
				
			}
			s.close();
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}
        return airports;
	}
		
	public static ArrayList<Customer> getCustomers(){
		ArrayList<Customer> customers = new ArrayList<Customer>();
		Connection conn = ConnectionBuilder.getConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;
		String query = "SELECT CustomerType, CustomerCode, PersonCode, CustomerName, AirlineMiles FROM Customers AS c JOIN Persons AS p ON c.PersonID=p.PersonID";
		try{
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			Customer c;
			while(rs.next()){
				if(rs.getString("CustomerType").equals("V")){
					c = new GovernmentCustomer(rs.getString("CustomerCode"), Finder.findObject(getPeople(), rs.getString("PersonCode")), rs.getString("CustomerName"), rs.getInt("AirlineMiles"));
				}else if(rs.getString("CustomerType").equals("C")){
					c = new CorporateCustomer(rs.getString("CustomerCode"), Finder.findObject(getPeople(), rs.getString("PersonCode")), rs.getString("CustomerName"), rs.getInt("AirlineMiles"));
				}else{
					c = new GeneralCustomer(rs.getString("CustomerCode"), Finder.findObject(getPeople(), rs.getString("PersonCode")), rs.getString("CustomerName"), rs.getInt("AirlineMiles"));
				}
				customers.add(c);
			}
			ps.close();
			rs.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return customers;
	}
	
	public static ArrayList<Customer> getCustomersFromFile(){
		ArrayList<Customer> customers = new ArrayList<Customer>();
		File customerFile = new File("data/Customers.dat");
		try{
			Scanner s = new Scanner(customerFile);
			int numOfCustomers = s.nextInt();
			s.nextLine();
			for(int i=0; i<numOfCustomers; i++){
				String line = s.nextLine();
				String[] tokens = line.split(";");
				String customerCode = tokens[0];
				Person primaryContact = Finder.findObject(getPeopleFromFile(), tokens[2]);
				String name = tokens[3];
				int airlineMiles;
				if(tokens.length==4){
					airlineMiles=0;
				}else{
					
					airlineMiles = Integer.parseInt(tokens[4]);
				}
				Customer customer;
				if(tokens[1].equals("G")){
					customer = new GeneralCustomer(customerCode, primaryContact, name, airlineMiles);
				}else if(tokens[1].equals("C")){
					customer = new CorporateCustomer(customerCode, primaryContact, name, airlineMiles);
				}else{
					customer = new GovernmentCustomer(customerCode, primaryContact, name, airlineMiles);
				}
				customers.add(customer);
				
			}
			s.close();
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}
        return customers;
	}	
	
	public static ArrayList<Product> getProducts(){
		ArrayList<Product> products = new ArrayList<Product>();
		Connection conn = ConnectionBuilder.getConnection();
		ResultSet rs = null;
		PreparedStatement ps = null;
		String query = "SELECT ProductID, ProductType, ProductCode, DepTime, ArrTime, FlightNo, FlightClass, AircraftType, SeasonStartDate, SeasonEndDate, Rebate, PointsPerMile, InsuranceName, AgeClass, InsuranceCostPerMile, TypeOfService, RefreshmentName, RefreshmentCost FROM Products";
		try{
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()){
				Product p;
				if(rs.getString("ProductType").equals("TS")){
					Airport depAirport = null;
					String depAirportQuery = "SELECT AirportCode FROM Products AS p JOIN Airports AS a ON p.DepAirportID=a.AirportID WHERE ProductID=?";
					ResultSet airportResults = null;
					ps = conn.prepareStatement(depAirportQuery);
					ps.setString(1, rs.getString("ProductID"));
					airportResults = ps.executeQuery();
					if(airportResults.next()){
						depAirport = Finder.findObject(getAirports(), airportResults.getString("AirportCode"));
					}
					Airport arrAirport = null;
					String arrAirportQuery = "SELECT AirportCode FROM Products AS p JOIN Airports AS a ON p.ArrAirportID=a.AirportID WHERE ProductID=?";
					ps = conn.prepareStatement(arrAirportQuery);
					ps.setString(1, rs.getString("ProductID"));
					airportResults = ps.executeQuery();
					if(airportResults.next()){
						arrAirport = Finder.findObject(getAirports(), airportResults.getString("AirportCode"));
					}

					p = new StandardTicket(rs.getString("ProductCode"), depAirport, arrAirport, rs.getString("DepTime"), rs.getString("ArrTime"), rs.getString("FlightNo"), rs.getString("FlightClass"), rs.getString("AircraftType"));
					airportResults.close();
				}else if(rs.getString("ProductType").equals("TO")){
					Airport depAirport = null;
					String depAirportQuery = "SELECT AirportCode FROM Products AS p JOIN Airports AS a ON p.DepAirportID=a.AirportID WHERE ProductID=?";
					ResultSet airportResults = null;
					ps = conn.prepareStatement(depAirportQuery);
					ps.setString(1, rs.getString("ProductID"));
					airportResults = ps.executeQuery();
					if(airportResults.next()){
						depAirport = Finder.findObject(getAirports(), airportResults.getString("AirportCode"));
					}
					Airport arrAirport = null;
					String arrAirportQuery = "SELECT AirportCode FROM Products AS p JOIN Airports AS a ON p.ArrAirportID=a.AirportID WHERE ProductID=?";
					ps = conn.prepareStatement(arrAirportQuery);
					ps.setString(1, rs.getString("ProductID"));
					airportResults = ps.executeQuery();
					if(airportResults.next()){
						arrAirport = Finder.findObject(getAirports(), airportResults.getString("AirportCode"));
					}

					p = new OffseasonTicket(rs.getString("ProductCode"), depAirport, arrAirport, rs.getString("DepTime"), rs.getString("ArrTime"), rs.getString("FlightNo"), rs.getString("FlightClass"), rs.getString("AircraftType") , rs.getString("SeasonStartDate"), rs.getString("SeasonEndDate"), rs.getDouble("Rebate"));
					airportResults.close();
					
				}else if(rs.getString("ProductType").equals("TA")){
					Airport depAirport = null;
					String depAirportQuery = "SELECT AirportCode FROM Products AS p JOIN Airports AS a ON p.DepAirportID=a.AirportID WHERE ProductID=?";
					ResultSet airportResults = null;
					ps = conn.prepareStatement(depAirportQuery);
					ps.setString(1, rs.getString("ProductID"));
					airportResults = ps.executeQuery();
					if(airportResults.next()){
						depAirport = Finder.findObject(getAirports(), airportResults.getString("AirportCode"));
					}
					Airport arrAirport = null;
					String arrAirportQuery = "SELECT AirportCode FROM Products AS p JOIN Airports AS a ON p.ArrAirportID=a.AirportID WHERE ProductID=?";
					ps = conn.prepareStatement(arrAirportQuery);
					ps.setString(1, rs.getString("ProductID"));
					airportResults = ps.executeQuery();
					if(airportResults.next()){
						arrAirport = Finder.findObject(getAirports(), airportResults.getString("AirportCode"));
					}

					p = new AwardTicket(rs.getString("ProductCode"), depAirport, arrAirport, rs.getString("DepTime"), rs.getString("ArrTime"), rs.getString("FlightNo"), rs.getString("FlightClass"), rs.getString("AircraftType"), rs.getInt("PointsPerMile"));
					airportResults.close();

				}else if(rs.getString("ProductType").equals("SC")){
					p = new CheckedBaggage(rs.getString("ProductCode"));
				}else if(rs.getString("ProductType").equals("SI")){
					p = new Insurance(rs.getString("ProductCode"), rs.getString("InsuranceName"), rs.getString("AgeClass"), rs.getInt("InsuranceCostPerMile"));
				}else if(rs.getString("ProductType").equals("SS")){
					p = new SpecialAssistance(rs.getString("ProductCode"), rs.getString("TypeOfService"));
				}else{
					p = new Refreshments(rs.getString("ProductCode"), rs.getString("RefreshmentName"), rs.getDouble("RefreshmentCost"));
				}
				products.add(p);
			}
			ps.close();
			rs.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return products;
	}
	
	public static ArrayList<Product> getProductsFromFile(){
		ArrayList<Product> products = new ArrayList<Product>();
		File productFile = new File("data/Products.dat");
		try{
			Scanner s = new Scanner(productFile);
			int numOfProducts = s.nextInt();
			s.nextLine();
			for(int i=0; i<numOfProducts; i++){
				String line = s.nextLine();
				String[] tokens = line.split(";");
				String productCode = tokens[0];
				Product product;
				if(tokens[1].equals("TS")){
					Airport depAirport = Finder.findObject(getAirportsFromFile(), tokens[2]);
					Airport arrAirport = Finder.findObject(getAirportsFromFile(), tokens[3]);
					String depTime = tokens[4];
					String arrTime = tokens[5];
					String flightNo = tokens[6];
					String flightClass = tokens[7];
					String aircraftType = tokens[8];
					product = new StandardTicket(productCode, depAirport, arrAirport, depTime, 
													arrTime, flightNo, flightClass, aircraftType);
				}else if(tokens[1].equals("TO")){
					String seasonStartDate = tokens[2];
					String seasonEndDate = tokens[3];
					Airport depAirport = Finder.findObject(getAirportsFromFile(), tokens[4]);
					Airport arrAirport = Finder.findObject(getAirportsFromFile(), tokens[5]);
					String depTime = tokens[6];
					String arrTime = tokens[7];
					String flightNo = tokens[8];
					String flightClass = tokens[9];
					String aircraftType = tokens[10];
					double rebate = Double.parseDouble(tokens[11]);
					product = new OffseasonTicket(productCode, 
													depAirport, arrAirport, depTime, arrTime,
													flightNo, flightClass, aircraftType, seasonStartDate, seasonEndDate, rebate);
				}else if(tokens[1].equals("TA")){
					Airport depAirport = Finder.findObject(getAirportsFromFile(), tokens[2]);
					Airport arrAirport = Finder.findObject(getAirportsFromFile(), tokens[3]);
					String depTime = tokens[4];
					String arrTime = tokens[5];
					String flightNo = tokens[6];
					String flightClass = tokens[7];
					String aircraftType = tokens[8];
					int pointsPerMile = Integer.parseInt(tokens[9]);
					product = new AwardTicket(productCode, depAirport, arrAirport, depTime, arrTime,
											flightNo, flightClass, aircraftType, pointsPerMile);
					
					
				}else if(tokens[1].equals("SC")){
					String ticketCode = tokens[2];
					product = new CheckedBaggage(productCode, ticketCode);
				}else if(tokens[1].equals("SI")){
					String name = tokens[2];
					String ageClass = tokens[3];
					double costPerMile = Double.parseDouble(tokens[4]);
					product = new Insurance(productCode, name, ageClass, costPerMile);
				}else if(tokens[1].equals("SS")){
					String typeOfService = tokens[2];
					product = new SpecialAssistance(productCode, typeOfService);
				}else{
					String name = tokens[2];
					double cost = Double.parseDouble(tokens[3]);
					product = new Refreshments(productCode, name, cost);
				}
				products.add(product);
			}
			s.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
		for(Product p: products){
			if(p instanceof CheckedBaggage){
				((CheckedBaggage) p).setTicket((Ticket)Finder.findObject(products, ((CheckedBaggage) p).getTicketCode()));
			}
		}
		return products;
	}	
	
	public static SortedList<Invoice> getInvoices(Comparator<Invoice> c){
		SortedList<Invoice> invoices = new SortedList<Invoice>(c);
		Connection conn = ConnectionBuilder.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String query = "SELECT CustomerCode, InvoiceCode, InvoiceDate, InvoiceID, i.PersonID FROM Invoices AS i JOIN Customers AS c ON i.CustomerID=c.CustomerID";
		try{
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()){
				String personsQuery = "SELECT PersonCode FROM Persons WHERE PersonID=?";
				ps = conn.prepareStatement(personsQuery);
				ps.setInt(1, rs.getInt("PersonID"));
				ResultSet personResults = ps.executeQuery();
				Person p;
				if(personResults.next()){
					p = Finder.findObject(getPeople(), personResults.getString("PersonCode"));
				}else{
					p = new Person("", "", "ONLINE", new Address("", "", "", "", ""));
				}
				personResults.close();
				Customer cus = Finder.findObject(getCustomers(), rs.getString("CustomerCode"));
				Invoice i = new Invoice(rs.getString("InvoiceCode"), cus, p, rs.getString("InvoiceDate"));
				String productQuery = "SELECT ProductCode, TravelDate, TicketNote, p.ProductID, InsuranceQuantity, AdditionalBaggage, RefreshmentQuantity FROM Invoices AS i JOIN InvoiceProducts AS ip ON i.InvoiceID=ip.InvoiceID JOIN Products AS p ON p.ProductID=ip.ProductID WHERE i.InvoiceID=?";
				ps = conn.prepareStatement(productQuery);
				ps.setInt(1, rs.getInt("InvoiceID"));
				ResultSet productResults = null;
				productResults = ps.executeQuery();
				ArrayList<Product> products = new ArrayList<Product>();
				Product pr = null;
				while(productResults.next()){
					pr = Finder.findObject(getProducts(), productResults.getString("ProductCode"));
					if(pr instanceof Ticket){
						((Ticket) pr).setTravelDate(productResults.getString("TravelDate"));
						((Ticket) pr).setTicketNote(productResults.getString("TicketNote"));
						String passengerQuery = "SELECT PersonCode FROM Persons AS p JOIN InvoiceProducts AS ip ON p.PersonID=ip.PersonID WHERE ip.ProductID=?";
						ResultSet passengerResults = null;
						ps = conn.prepareStatement(passengerQuery);
						ps.setString(1, productResults.getString("ProductID"));
						passengerResults = ps.executeQuery();
						ArrayList<Person> passengers = new ArrayList<Person>();
						while(passengerResults.next()){
							passengers.add(Finder.findObject(getPeople(), passengerResults.getString("PersonCode")));
						}
						((Ticket) pr).setPassengers(passengers);
					}else if(pr instanceof Insurance){
						((Insurance) pr).setQuantity(productResults.getString("InsuranceQuantity"));
					}else if(pr instanceof CheckedBaggage){
						((CheckedBaggage) pr).setAdditionalBaggage(productResults.getInt("AdditionalBaggage"));
					}else if(pr instanceof Refreshments){
						((Refreshments) pr).setQuantity(productResults.getInt("RefreshmentQuantity"));
					}
					products.add(pr);
				}
				productResults.close();
				i.setProductList(products);
				invoices.add(i);
			}
			ps.close();
			rs.close();
			conn.close();
		}catch (SQLException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return invoices;
	}
	
	public static SortedList<Invoice> getInvoicesFromFile(Comparator<Invoice> c){
		SortedList<Invoice> invoices = new SortedList<Invoice>(c);
		File invoiceFile = new File("data/Invoices.dat");
		try{
			Scanner s = new Scanner(invoiceFile);
			int numOfInvoices = s.nextInt();
			s.nextLine();
			for(int i=0; i<numOfInvoices; i++){
				String line = s.nextLine();
				String tokens[] = line.split(";");
				String invoiceCode = tokens[0];
				Customer customer = Finder.findObject(getCustomersFromFile(), tokens[1]);
				Person p;
				if(tokens[2].equalsIgnoreCase("ONLINE")){
					p = new Person("", "", "ONLINE", new Address("", "", "", "", ""));
				}else{
					p = Finder.findObject(getPeopleFromFile(), tokens[2]);
				}
				String invoiceDate = tokens[3];
				String[] products = tokens[4].split(",");
				ArrayList<Product> listOfProducts = new ArrayList<Product>();
				for(int j=0; j<products.length; j++){
					String[] productInfo = products[j].split(":");
					Product product = Finder.findObject(getProductsFromFile(), productInfo[0]);
					product.setInfo(products[j]);
					listOfProducts.add(product);
				}
				Invoice invoice = new Invoice(invoiceCode, customer, p, invoiceDate, listOfProducts);
				if(!invoices.add(invoice)){
					System.out.println("NOT ADDED");
				}
			}
			s.close();

		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
			return invoices;
	}
	public static void printToFiles(){
		XStream peopleXStream = new XStream();
		peopleXStream.alias("person", Person.class);
		peopleXStream.alias("email", String.class);
		String peopleXML = peopleXStream.toXML(getPeople());
		try {
			PrintWriter peopleOutXML = new PrintWriter("data/Persons.xml");
			peopleOutXML.println(peopleXML);
			peopleOutXML.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

        Gson peopleGson = new GsonBuilder().setPrettyPrinting().create();
		String peopleJson = peopleGson.toJson(getPeople());
		try {
			PrintWriter peopleOutJson = new PrintWriter("data/Persons.json");
			peopleOutJson.println(peopleJson);
			peopleOutJson.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		

		XStream airportsXStream = new XStream();
		airportsXStream.alias("airport", Airport.class);
		String airportsXML = airportsXStream.toXML(getAirports());
		try {
			PrintWriter airportsOutXML = new PrintWriter("data/Airports.xml");
			airportsOutXML.println(airportsXML);
			airportsOutXML.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
        Gson airportsGson = new GsonBuilder().setPrettyPrinting().create();
		String airportsJson = airportsGson.toJson(getAirports());
		try {
			PrintWriter airportsOutJson = new PrintWriter("data/Airports.json");
			airportsOutJson.println(airportsJson);
			airportsOutJson.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}


		XStream customerXStream = new XStream();
		customerXStream.alias("general customer", GeneralCustomer.class);
		customerXStream.alias("corporate customer", CorporateCustomer.class);
		customerXStream.alias("government customer", GovernmentCustomer.class);
		String customerXML = customerXStream.toXML(getCustomers());
		try {
			PrintWriter customerOutXML = new PrintWriter("data/Customers.xml");
			customerOutXML.println(customerXML);
			customerOutXML.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
        Gson customerGson = new GsonBuilder().setPrettyPrinting().create();
		String customerJson = customerGson.toJson(getCustomers());
		try {
			PrintWriter customerOutJson = new PrintWriter("data/Customer.json");
			customerOutJson.println(customerJson);
			customerOutJson.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		XStream productXStream = new XStream();
		productXStream.alias("standardticket", StandardTicket.class);
		productXStream.alias("offseasonticket", OffseasonTicket.class);
		productXStream.alias("awardticket", AwardTicket.class);
		productXStream.alias("checkedbaggage", CheckedBaggage.class);
		productXStream.alias("insurance", Insurance.class);
		productXStream.alias("refreshment", Refreshments.class);
		productXStream.alias("specialassistance", SpecialAssistance.class);
		String productXML = productXStream.toXML(getProducts());
		System.out.println(productXML);
		try {
			PrintWriter productOutXML = new PrintWriter("data/Product.xml");
			productOutXML.println(productXML);
			productOutXML.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
        Gson productGson = new GsonBuilder().setPrettyPrinting().create();
		String productJson = productGson.toJson(getProducts());
		System.out.println(productJson);
		try {
			PrintWriter productOutJson = new PrintWriter("data/Product.json");
			productOutJson.println(productJson);
			productOutJson.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}


public static ArrayList<Person> getPeopleFromFile(){
		ArrayList<Person> people = new ArrayList<Person>();
		File persons = new File("data/Persons.dat");
		try{
                Scanner s = new Scanner(persons);
                int numOfPeople = s.nextInt();
                s.nextLine();
                for(int i=0; i<numOfPeople; i++){
                	String line = s.nextLine();
                	String[] tokens = line.split(";");
                	String personCode = tokens[0];
                	String lastName = tokens[1].split(",")[0];
                	String firstName = tokens[1].split(",")[1];
                	String[] addressTokens = tokens[2].split(",");
                	String street = addressTokens[0];
                	String city = addressTokens[1];
                	String state = addressTokens[2];
                	String zip = addressTokens[3];
                	String country = addressTokens[4];
                	Address address = new Address(street, city, state, zip, country);
                	Person person;
                	if(!tokens[3].isEmpty()){
                		String phoneNumber = tokens[3];
                		person = new Person(personCode, firstName, lastName, phoneNumber, address);
                	}else{
                		person = new Person(personCode, firstName, lastName, address);
                	}
                	if(tokens.length == 5){
                		String[] emailTokens = tokens[4].split(",");
                		for(String email: emailTokens){
                			person.addEmail(email);
                		}
                	}
                	people.add(person);
                }
                s.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
        return people;
	
}

}
