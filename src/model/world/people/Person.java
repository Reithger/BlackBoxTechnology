package model.world.people;

import model.mechanics.Data;
import model.world.country.Country;

public class Person {

	private String name;
	private int age;
	private Gender gender;
	private Sexuality sexuality;
	private Race race;
	private Country country;
	
	private double research;
	private double production;
	
	public Person(Data dat) {
		
	}
	
	public double getResearch() {
		return research;
	}
	
	public double getProduction() {
		return production;
	}
	
	public Data generateData() {
		return null;
	}
	
}
