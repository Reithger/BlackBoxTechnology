package model;

import java.util.HashMap;
import controller.Data;
import model.company.Company;
import model.world.World;

public class Model {
	
//---  Constants   ----------------------------------------------------------------------------
	
	public final static String NAME = "name";
	public final static String WORLD = "world";
	public final static String PLAYER = "player";
	
	public final static String PATH_EQUIPMENT = "/assets/dta/Equipment.dta";
	public final static String PATH_FACTORIES = "/assets/dta/Factories.dta";
	public final static String PATH_PRODUCTS = "/assets/dta/Products.dta";
	public final static String PATH_WORLD = "/assets/dta/World.dta";
	public final static String PATH_RESEARCH = "/assets/dta/Research.dta";
	public final static String PATH_MECHANICS = "/assets/dta/Mechanics.dta";
	
//---  Instance Variables   -------------------------------------------------------------------
	
	private HashMap<String, Data> reference;
	private String name;
	private World world;
	private Company player;
		
//---  Constructors   -------------------------------------------------------------------------
	
	public Model(String nom, Data dat) {
		name = dat.getString(NAME);
		world = new World(dat.getDataset(WORLD));
		player = new Company(world, dat.getDataset(PLAYER));
		reference = new HashMap<String, Data>();
	}
	
	public Model(String nom) {
		name = nom;
		world = new World();
		player = new Company(world);
		reference = new HashMap<String, Data>();
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	public void iterate() {
		player.iterate();
		world.iterate();
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	public Company getPlayer() {
		return player;
	}

	public Data getReference(String ref) {
		if(!reference.containsKey(ref)) {
			try {
				reference.put(ref, new Data(ref));
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		return reference.get(ref);
	}

//---  Mechanics   ----------------------------------------------------------------------------
	
	public Data exportData() {
		Data dat = new Data();
		dat.setTitle(name);
		dat.addString(name, NAME);
		dat.addData(WORLD, world.exportData());
		dat.addData(PLAYER, player.exportData(PLAYER));
		return dat;
	}
		
}
