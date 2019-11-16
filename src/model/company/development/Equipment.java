package model.company.development;

import java.util.ArrayList;

import controller.Data;
import model.world.market.Product;
import model.world.people.Person;

public class Equipment {
	
	public final static String NAME = "name";
	
	private ArrayList<Person> assigned;
	private Product product;
	private String name;
	private int level;
	private String type;
	
	private double[] cost;
	private double[] production;
	private int[] personnel;
	private double[] maintenance;
	
	
//---  Constructors   -------------------------------------------------------------------------
	
	public Equipment(Data dat) {
		assigned = new ArrayList<Person>();
		level = dat.getInt("level");
		type = dat.getString("type");
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
	
	public void setType(String newTit) {
		type = newTit;
	}
	
	public void setTitle(String title) {
		name = title;
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
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public Product getProduct() {
		return product;
	}
	
//---  Mechanics   ----------------------------------------------------------------------------
	
	public Data exportData() {
		Data dat = new Data(name);
		String[] names = new String[assigned.size()];
		for(int i = 0; i < assigned.size(); i++) {
			names[i] = assigned.get(i).getName();
		}
		dat.addInt(level, "level");
		dat.addString(product.getName(), "product");
		dat.addString(type, "type");
		dat.addString(name, NAME);
		dat.addStringArray(names, "assigned");
		return dat;
	}
	
}
