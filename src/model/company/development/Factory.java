package model.company.development;

import java.util.Collection;
import java.util.HashMap;
import controller.Data;
import model.company.Company;
import model.world.people.Person;

public class Factory {
	
//---  Constants   ----------------------------------------------------------------------------
	
	public final static String TITLE = "title";
	public final static String OWNER = "owner";
	public final static String EQUIPMENT = "equipment";
	public final static String EMPLOYEES = "employees";

//---  Instance Variables   -------------------------------------------------------------------
	
	private Company owner;
	private String name;
	private HashMap<String, Person> employees;
	private HashMap<String, Equipment> equipment;
	
//---  Constructors   -------------------------------------------------------------------------
	
	public Factory(Company comp, String nom) {
		owner = comp;
		name = nom;
		equipment = new HashMap<String, Equipment>();
		employees = new HashMap<String, Person>();
	}
	
	public Factory(Company comp, Data dat) {
		owner = comp;
		name = dat.getString(TITLE);
		equipment = new HashMap<String, Equipment>();
		for(Data d : dat.getDatasetArray(EQUIPMENT)) {
			equipment.put(d.getString(Equipment.NAME), new Equipment(d));
		}
		employees = new HashMap<String, Person>();
		for(Data d : dat.getDatasetArray(EMPLOYEES)) {
			employees.put(d.getString(Equipment.NAME), new Person(d));
		}
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
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
	
//---  Getter Methods   -----------------------------------------------------------------------
	
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
	
//---  Mechanics   ----------------------------------------------------------------------------
	
	public Data exportData() {
		Data dat = new Data(name);
		dat.addString(name, TITLE);
		dat.addString(owner.getName(), OWNER);
		Data[] equi = new Data[equipment.size()];
		int i = 0;
		for(Equipment q : equipment.values()) {
			equi[i++] = q.exportData();
		}
		dat.addDataArray(EQUIPMENT, equi);
		Data[] pers = new Data[employees.size()];
		i = 0;
		for(Person p : employees.values()) {
			pers[i++] = p.exportData();
		}
		dat.addDataArray(EMPLOYEES, pers);
		return dat;
	}
	
}
