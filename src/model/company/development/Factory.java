package model.company.development;

import java.util.ArrayList;

import model.company.Company;
import model.world.people.Person;

public class Factory {

	private Company owner;
	private ArrayList<Person> employees;
	private ArrayList<Equipment> equipment;	
	
	public Factory() {
		employees = new ArrayList<Person>();
	}
	
	public void iterate() {
		
	}
	
	public boolean buildEquipment(Equipment e) {
		if(owner.changeMoney(e.buildCost())) {
			equipment.add(e);
			return true;
		}
		else {
			return false;
		}
	}
	
	public void assignEmployee(String name, String target) {
		
	}
	
}
