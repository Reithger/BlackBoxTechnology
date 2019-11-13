package model.company.development;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import model.company.Company;
import model.world.people.Person;

public class Factory {

	private Company owner;
	private HashMap<String, Person> employees;
	private HashMap<String, Equipment> equipment;
	
	public Factory() {
		equipment = new HashMap<String, Equipment>();
		employees = new HashMap<String, Person>();
	}
	
	public void iterate() {
		for(Equipment e : equipment.values()) {
			owner.changeProduct(e.produce(), e.getProduct());
			owner.changeMoney(e.maintenanceCost());
		}
		for(Person p : employees.values()) {
			owner.changeMoney(p.getWage());
		}
	}
	
	public boolean buildEquipment(Equipment e) {
		if(owner.changeMoney(e.buildCost())) {
			while(equipment.keySet().contains(e.getTitle()))
				e.setTitle(e.getTitle() + "_1");
			equipment.put(e.getTitle(), e);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean assignEmployee(String name, String target) {
		if(equipment.get(target) != null && employees.get(name) != null) {
			return equipment.get(target).assignEmployee(employees.get(name));
		}
		return false;
	}
	
	public Equipment getEquipment(String name) {
		return equipment.get(name);
	}
	
	public Person getEmployee(String name) {
		return employees.get(name);
	}
	
	public Collection<Person> getEmployees(){
		return employees.values();
	}
	
	public Collection<Equipment> getEquipment(){
		return equipment.values();
	}
	
}
