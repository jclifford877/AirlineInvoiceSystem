package com.airamerica;

import unl.cse.assignments.DataConverter;

import com.airamerica.utils.Finder;

//this is a class to represent a special assistance package which is a type of product
public class SpecialAssistance extends Service {
	private String typeofService;
	private Person person;

	public SpecialAssistance(String productCode, String typeofService) {
		super(productCode);
		this.typeofService = typeofService;
		// TODO Auto-generated constructor stub
	}

	public String getTypeofService() {
		return typeofService;
	}

	public void setTypeofService(String typeofService) {
		this.typeofService = typeofService;
	}

	@Override
	public void setInfo(String s) {
		String[] tokens = s.split(":");
		this.person = Finder.findObject(DataConverter.getPeople(), tokens[1]);
		
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public double getSubTotal() {
		return 0;
	}

	@Override
	public String getDetailedReport() {
		if(this.person == null){
			return String.format("%s	Special Assistance			(%-15s)					$0.00			$0.00			$0.00\n", this.getProductCode(), this.typeofService);
		}
		return String.format("%s	Special assistance for [%s, %s] (%-15s)				$ 0.00 			$ 0.00 			$ 0.00\n", 
				this.getProductCode(), this.person.getLastName(), this.person.getFirstName(), this.typeofService);
	}

}
