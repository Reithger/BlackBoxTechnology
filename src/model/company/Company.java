package model.company;

import java.util.ArrayList;

import model.company.development.Factory;
import model.company.research.Research;

public class Company {

	private ArrayList<Factory> factory;
	private Research research;
	private double money;
	
	public Company() {
		
	}
	
	public void iterate() {
		
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
