package model.company;

import java.util.ArrayList;
import java.util.HashMap;

import controller.Data;
import model.company.development.Factory;
import model.company.research.Research;
import model.world.market.Product;

public class Company {

	private ArrayList<Factory> factory;
	private Research research;
	private HashMap<Product, Double> stock;
	private double money;
	
	public Company(Data dat) {
		factory = new ArrayList<Factory>();
		research = new Research();
		stock = new HashMap<Product, Double>();
		money = 0;
	}
	
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
	
	public double getStock(Product reference) {
		if(stock.containsKey(reference)) {
			return stock.get(reference);
		}
		return 0;
	}
	
	public double getMoney() {
		return money;
	}
	
	public boolean changeMoney(double change) {
		if(money + change < 0) {
			return false;
		}
		money += change;
		return true;
	}
	
	
	
}
