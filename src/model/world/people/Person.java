package model.world.people;

import controller.Data;

public class Person {
	
//---  Constants   ----------------------------------------------------------------------------
	
	public final static String NAME = "name";

//---  Instance Variables   -------------------------------------------------------------------
	
	private String name;
	private int age;
	private String country;
	private String gender;
	private String sexuality;
	private String race;
	
	private double research;
	private double production;
	
//---  Constructors   -------------------------------------------------------------------------
	
	public Person(Data dat) {
		name = dat.getString(NAME);
		age = dat.getInt("age");
		research = dat.getDouble("research");
		production = dat.getDouble("production");
		sexuality = dat.getString("sexuality");
		race = dat.getString("race");
		gender = dat.getString("gender");
		country = dat.getString("country");
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	public double getResearch() {
		return research;
	}
	
	public double getProduction() {
		return production;
	}
	
	public double getWage() {
		return 1;
	}
	
	public String getName() {
		return name;
	}
	
//---  Mechanics   ----------------------------------------------------------------------------

	public Data exportData() {
		Data dat = new Data();
		dat.setTitle(name);
		dat.addString(name, NAME);
		dat.addInt(age, "age");
		dat.addString(gender, "gender");
		dat.addString(sexuality, "sexuality");
		dat.addString(race, "race");
		dat.addString(country, "country");
		dat.addDouble(research, "research");
		dat.addDouble(production, "production");
		return dat;
	}
	
}
