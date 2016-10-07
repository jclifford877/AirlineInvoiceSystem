package com.airamerica.utils;

import java.util.ArrayList;

import com.airamerica.Searchable;

public class Finder {
	//this class has one static method that is used to find an object in a list of the objects, the objects must implement
	//searchable to be found
	public static <T extends Searchable>  T findObject(ArrayList<T> a, String code){
		for(T  value : a){
			if(value.getCode().equals(code)){
				return value;
			}
		}
		return null;
	}
}
