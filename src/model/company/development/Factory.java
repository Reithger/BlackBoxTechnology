package model.company.development;

import java.util.Collection;
import java.util.HashMap;
import controller.Data;
import model.Model;
import model.company.Company;
import model.world.people.Person;

public class Factory {
	
//---  Constants   ----------------------------------------------------------------------------
	
	public final static String TITLE = "title";
	public final static String OWNER = "owner";
	public final static String EQUIPMENT = "equipment";
	public final static String EMPLOYEES = "employees";
	public final static String IMAGE = "image";
	public final static String TYPE = "type";

//---  Instance Variables   -------------------------------------------------------------------
	
	private Company owner;
	private String name;
	private String type;
	private HashMap<String, Person> employees;
	private HashMap<String, Equipment> equipment;
	private String[] images;
	
	private Data equipmentList;
	
//---  Constructors   -------------------------------------------------------------------------
	
	public Factory(Company comp, Data dat, Data ref) {
		try {
			equipmentList = new Data(Model.PATH_EQUIPMENT).getDataset(dat.getString(Equipment.TYPE));
			//add something here to limit the list based on requirements
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		owner = comp;
		type = dat.getString(TYPE);
		name = dat.getString(TITLE);
		equipment = new HashMap<String, Equipment>();
		for(Data d : dat.getDatasetArray(EQUIPMENT)) {
			equipment.put(d.getString(Equipment.NAME), new Equipment(d, equipmentList.getDataset(d.getString(Equipment.TYPE))));
		}
		employees = new HashMap<String, Person>();
		for(Data d : dat.getDatasetArray(EMPLOYEES)) {
			employees.put(d.getString(Person.NAME), new Person(d));
		}
		images = ref.getStringArray(IMAGE);

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
			int count = 1;
			String name = e.getTitle();
			while(equipment.keySet().contains(name + "_" + count))
				count++;
			e.setTitle(name + "_" + count);
			equipment.put(e.getTitle(), e);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean buildEquipment(String nom) {
		Data d = equipmentList.getDataset(nom);
		Equipment e = new Equipment(d);
		return buildEquipment(e);
	}
	
	public boolean upgradeEquipment(String nom) {
		Equipment e = equipment.get(nom);
		return owner.changeMoney(e.upgradeCost()) && e.increaseLevel();
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
	
	public Data getEquipmentList() {
		return equipmentList;
	}
	
//---  Mechanics   ----------------------------------------------------------------------------
	
	public Data exportData() {
		Data dat = new Data();
		dat.setTitle(name);
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
		dat.addString(type, TYPE);
		dat.addStringArray(images, IMAGE);
		return dat;
	}
	
}
