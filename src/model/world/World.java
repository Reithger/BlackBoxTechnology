package model.world;

import java.util.ArrayList;
import java.util.HashMap;
import controller.Data;
import model.Model;
import model.world.country.Country;
import model.world.errata.Time;
import model.world.market.Product;

public class World {
	
	public final static String TITLE = "world";
	public final static String PRODUCTS = "products";
	public final static String PATH_PRODUCTS = Model.PATH_PRODUCTS;

	private Time time;
	private ArrayList<Country> countries;
	private HashMap<String, Product> products;
	
	public World(Data dat) {
		products = new HashMap<String, Product>();
		time = new Time();
		countries = new ArrayList<Country>();
		Data d = null;
		try {
			d = new Data(PATH_PRODUCTS);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		for(String s : d.getStringArray(PRODUCTS)) {
			products.put(s, new Product(d.getDataset(s)));	//these are default values, update with save file if relevant
		}
	}
	
	public World() {
		products = new HashMap<String, Product>();
		time = new Time();
		countries = new ArrayList<Country>();
		Data d = null;
		try {
			d = new Data(PATH_PRODUCTS);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		for(String s : d.getStringArray(PRODUCTS)) {
			products.put(s, new Product(d.getDataset(s)));
		}
	}
	
	public void iterate() {
		
	}
	
	public void affect() {
		
	}
	
	public HashMap<String, Product> getProducts(){
		return products;
	}
	
	public Product getProduct(String nom){
		return products.get(nom);
	}
	
	public Data exportData() {
		Data dat = new Data();
		dat.setTitle(TITLE);
		
		return dat;
	}
	
}
