package com.airamerica;
/*
/* A partial implementation representing a 
 * Person */
import java.util.ArrayList;
import java.util.List;

public class Person implements Searchable{
	

	private String personCode;
	
	private String firstName;
	private String lastName;
	/* Note how Address has been used (Composition Relationship) */ 
	private Address address;
	
	/* Note how email is used (a collection of variable size) */ 
	private List<String> emails;
	
	/*TODO: Add other fields as necessary (eg. firstName, lastName,
	phoneNo etc) */
	private String phoneNumber;
	private String seat;
	private String id;
	private int age;
	private String nationality;
	


	// TODO: Add appropriate constructor(s)
	public Person(String personCode, String firstName, String lastName, String phoneNumber, Address address) {
		this.personCode = personCode;
		this.address = address;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.emails = new ArrayList<String>();
	}
	
	public Person(String personCode, String firstName, String lastName,  Address address) {
		this.personCode = personCode;
		this.address = address;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = "N/A";
		this.emails = new ArrayList<String>();
	}

	public Person(String personCode, String firstName, String lastName,
			Address address, List<String> emails, String phoneNumber,
			String seat, String id, int age, String nationality) {
		super();
		this.personCode = personCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.emails = emails;
		this.phoneNumber = phoneNumber;
		this.seat = seat;
		this.id = id;
		this.age = age;
		this.nationality = nationality;
	}
	
	public Person(Person p){
		this.personCode = p.personCode;
		this.address = new Address(p.address);
		this.firstName = p.firstName;
		this.lastName = p.lastName;
		this.phoneNumber = p.phoneNumber;
		this.emails = p.emails;
	}
	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	// TODO: Add Getters and setters as appropriate
	public Address getAddress() {
		return this.address;
	}
	
	public void setEmails(List<String> emails)
	{
		this.emails = emails;
	}
		
	// TODO: Add additional methods here
	public void addEmail(String email) {
		this.emails.add(email);
	}

	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return this.personCode;
	}
	public String getSeat() {
		return seat;
	}

	public void setSeat(String seat) {
		this.seat = seat;
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getFlightReport() {
		return String.format("%s, %-10s 	%d		%s\n", this.lastName, this.firstName, this.age, this.seat);
	}
	
}
