package model.company;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import controller.Data;
import model.company.development.Factory;
import model.company.research.Research;
import model.world.World;
import model.world.market.Product;

public class Company {
	
	
	public final static String TITLE = "title";
	public final static String MONEY = "money";
	public final static String STOCK = "stock";
	public final static String RESEARCH = "research";
	public final static String FACTORY = "factory";
	
	public final static String PATH_FACTORY_LIST = "dta/Factories.dta";
	
//---  Instance Variables   -------------------------------------------------------------------

	private World world;
	private String title;
	private ArrayList<Factory> factory;
	private Research research;
	private HashMap<String, Double> stock;
	private double money;
	
	private Data factoryList;
	
//---  Constructors   -------------------------------------------------------------------------
	
	public Company(World w) {
		world = w;
		title = "company";
		factory = new ArrayList<Factory>();
		research = new Research();
		stock = new HashMap<String, Double>();
		money = 0;
		try {
			factoryList = new Data(new File(PATH_FACTORY_LIST));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		factory.add(new Factory(this, factoryList.getDataset("Smokestack"), factoryList.getDataset("Smokestack")));
		for(String s : world.getProducts().keySet()) {
			stock.put(s, 0.0);
		}
	}
	
	public Company(World w, Data dat) {
		try {
			factoryList = new Data(new File(PATH_FACTORY_LIST));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		title = dat.getString(TITLE);
		world = w;
		factory = new ArrayList<Factory>();
		for(Data d : dat.getDatasetArray(FACTORY)) {
			factory.add(new Factory(this, d, factoryList.getDataset(d.getString(Factory.TYPE))));
		}
		research = new Research();
		stock = new HashMap<String, Double>();
		money = 0;
		for(String s : world.getProducts().keySet()) {
			stock.put(s, 0.0);
		}
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	public void iterate() {
		for (Factory f : factory) {
			f.iterate();
		}
	}
		
	public boolean changeProduct(double change, String reference) {
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
		for(String s : stock.keySet()) {
			prod.addDouble(stock.get(s), s);
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
