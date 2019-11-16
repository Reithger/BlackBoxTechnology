package model.company;

import java.util.ArrayList;
import java.util.HashMap;

import controller.Data;
import model.company.development.Factory;
import model.company.research.Research;
import model.world.market.Product;

public class Company {
	
	
	public final static String TITLE = "title";
	public final static String MONEY = "money";
	public final static String STOCK = "stock";
	public final static String RESEARCH = "research";
	public final static String FACTORY = "factory";
	
//---  Instance Variables   -------------------------------------------------------------------

	private String title;
	private ArrayList<Factory> factory;
	private Research research;
	private HashMap<Product, Double> stock;
	private double money;
	
//---  Constructors   -------------------------------------------------------------------------
	
	public Company() {
		title = "company";
		factory = new ArrayList<Factory>();
		factory.add(new Factory(this, "Factory"));
		research = new Research();
		stock = new HashMap<Product, Double>();
		money = 0;
	}
	
	public Company(Data dat) {
		title = dat.getString(TITLE);
		factory = new ArrayList<Factory>();
		for(Data d : dat.getDatasetArray(FACTORY)) {
			factory.add(new Factory(this, d));
		}
		research = new Research();
		stock = new HashMap<Product, Double>();
		money = 0;
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	public void iterate() {
		for (Factory f : factory) {
			f.iterate();
		}
	}
		
	public boolean changeProduct(double change, Product reference) {
		if(stock.get(reference) + change < 0) {
			return false;
		}
		stock.put(reference, stock.get(reference) + change);
		return true;
	}

	public boolean changeMoney(double change) {
		if(money + change < 0) {
			return false;
		}
		money += change;
		return true;
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	public double getStock(Product reference) {
		if(stock.containsKey(reference)) {
			return stock.get(reference);
		}
		return 0;
	}
	
	public double getMoney() {
		return money;
	}
		
	public String getName() {
		return title;
	}
	
	public ArrayList<Factory> getFactories(){
		return factory;
	}
	
	public Data exportData() {
		Data dat = new Data(title);
		dat.addString(title, TITLE);
		dat.addDouble(money, MONEY);
		Data[] factori = new Data[factory.size()];
		for(int i = 0; i < factory.size(); i++) {
			factori[i] = factory.get(i).exportData();
		}
		dat.addDataArray(FACTORY, factori);
		Data prod = new Data(STOCK);
		for(Product s : stock.keySet()) {
			prod.addDouble(stock.get(s), s.getName());
		}
		dat.addData(STOCK, prod);
		dat.addData(RESEARCH, research.exportData());
		return dat;
	}
	
	public Data exportData(String newNom) {
		Data dat = exportData();
		dat.setTitle(newNom);
		return dat;
	}
	
}
