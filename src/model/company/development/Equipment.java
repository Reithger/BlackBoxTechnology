package model.company.development;

import java.util.ArrayList;

import controller.Data;
import model.world.market.Product;
import model.world.people.Person;

public class Equipment {
	
	private ArrayList<Person> assigned;
	private Product product;
	private String title;
	private double[] cost;
	private double[] production;
	private int[] personnel;
	private double[] maintenance;
	private int level;
	
//---  Constructors   -------------------------------------------------------------------------
	
	public Equipment(Data dat) {
		level = 0;
		assigned = new ArrayList<Person>();
		title = dat.getString(title);
		cost = dat.getDoubleArray("cost");
		production = dat.getDoubleArray("production");
		personnel = dat.getIntArray("personnel");
		maintenance = dat.getDoubleArray("maintenance");
	}

//---  Operations
	
	public double produce() {
		double base = production[level] * (double)assigned.size() / (double)personnel[level];
		for(Person p : assigned) {
			base *= p.getProduction();	//change to stepwise, probably
		}
		return base;
	}
	
	public boolean assignEmployee(Person empl) {
		if(assigned.size() < personnel[level]) {
			assigned.add(empl);
			return true;
		}
		return false;
	}
	
	public boolean removeEmployee(Person empl) {
		if(assigned.contains(empl)) {
			assigned.remove(empl);
			return true;
		}
		return false;
	}

	public boolean increaseLevel() {
		if(level + 1 < production.length) {
			level += 1;
			return true;
		}
		return false;
	}
	
//---  Setter Methods
	
	public void setProduced(Product p) {
		product = p;
	}
	
	public void setTitle(String newTit) {
		title = newTit;
	}

//---  Getter Methods
		
	public double maintenanceCost() {
		return maintenance[level];
	}
	
	public double buildCost() {
		return cost[0];
	}
	
	public double upgradeCost() {
		return cost[level];
	}

	public String getTitle() {
		return title;
	}
	
	public Product getProduct() {
		return product;
	}
	
}
