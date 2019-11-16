package model.world.people;

import controller.Data;

public class Person {
	
	public final static String NAME = "name";

	private String name;
	private int age;
	private String country;
	private String gender;
	private String sexuality;
	private String race;
	
	private double research;
	private double production;
	
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
	
	public double getResearch() {
		return research;
	}
	
	public double getProduction() {
		return production;
	}
	
	public Data generateData() {
		Data dat = new Data();
		dat.setTitle(name);
		dat.addInt(age, "age");
		dat.addString(gender, "gender");
		dat.addString(sexuality, "sexuality");
		dat.addString(race, "race");
		dat.addString(country, "country");
		dat.addDouble(research, "research");
		dat.addDouble(production, "production");
		return dat;
	}
	
	public double getWage() {
		return 1;
	}
	
	public String getName() {
		return name;
	}
	
	public Data exportData() {
		Data dat = new Data(name);
		dat.addString(name, NAME);
		return dat;
	}
	
}
