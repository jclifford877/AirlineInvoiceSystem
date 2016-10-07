package com.airamerica;

public interface Searchable {
	//this is an interface that everything that can be searched will implement
	
	//this method returns the code which is the only needed information to search for an object in this project
	public String getCode();
}
