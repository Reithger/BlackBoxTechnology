package model.company.development;

import java.util.ArrayList;

import controller.Data;
import model.world.market.Product;
import model.world.people.Person;

public class Equipment {
	
	public final static String NAME = "name";
	public final static String TYPE = "type";
	public final static String IMAGE = "image";
	public final static String COST = "cost";
	public final static String PRODUCTION = "production";
	public final static String PRODUCT = "product";
	public final static String LEVEL = "level";
	public final static String PERSONNEL = "personnel";
	public final static String MAINTENANCE = "maintenance";
	
	private ArrayList<Person> assigned;
	private String product;
	private String name;
	private int level;
	private String type;
	
	private double[] cost;
	private double[] production;
	private int[] personnel;
	private double[] maintenance;
	private String[] images;
	
	
//---  Constructors   -------------------------------------------------------------------------
	
	public Equipment(Data dat, Data ref) {
		assigned = new ArrayList<Person>();
		product = dat.getString(PRODUCT);
		name = dat.getString(NAME);
		level = dat.getInt(LEVEL);
		type = dat.getString(TYPE);
		
		cost = ref.getDoubleArray(COST);
		production = ref.getDoubleArray(PRODUCTION);
		personnel = ref.getIntArray(PERSONNEL);
		maintenance = ref.getDoubleArray(MAINTENANCE);
		images = ref.getStringArray(IMAGE);
	}
	
	public Equipment(Data ref) {
		assigned = new ArrayList<Person>();
		product = ref.getString(PRODUCT);
		name = ref.getString(NAME);
		level = 0;
		type = ref.getString(TYPE);
		
		cost = ref.getDoubleArray(COST);
		production = ref.getDoubleArray(PRODUCTION);
		personnel = ref.getIntArray(PERSONNEL);
		maintenance = ref.getDoubleArray(MAINTENANCE);
		images = ref.getStringArray(IMAGE);
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
	
	public void setProduced(String p) {
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
	
	public String getProduct() {
		return product;
	}
	
//---  Mechanics   ----------------------------------------------------------------------------
	
	public Data exportData() {
		Data dat = new Data(name);
		String[] names = new String[assigned.size()];
		for(int i = 0; i < assigned.size(); i++) {
			names[i] = assigned.get(i).getName();
		}
		dat.addInt(level, LEVEL);
		dat.addString(product, PRODUCT);
		dat.addString(type, TYPE);
		dat.addString(name, NAME);
		dat.addStringArray(names, PERSONNEL);
		dat.addStringArray(images, IMAGE);
		dat.addDoubleArray(cost, COST);
		dat.addDoubleArray(production, PRODUCTION);
		dat.addDoubleArray(maintenance, MAINTENANCE);
		return dat;
	}
	
}
