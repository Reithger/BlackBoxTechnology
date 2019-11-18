package model;

import controller.Data;
import model.company.Company;
import model.world.World;

public class Model {
	
	public final static String NAME = "name";
	public final static String WORLD = "world";
	public final static String PLAYER = "player";

	private String name;
	private World world;
	private Company player;
		
	public Model(String nom, Data dat) {
		name = dat.getString(NAME);
		world = new World(dat.getDataset(WORLD));
		player = new Company(world, dat.getDataset(PLAYER));
	}
	
	public Model(String nom) {
		name = nom;
		world = new World();
		player = new Company(world);
	}
	
	public Data exportData() {
		Data dat = new Data(name);
		dat.addString(name, NAME);
		dat.addData(WORLD, world.exportData());
		dat.addData(PLAYER, player.exportData(PLAYER));
		return dat;
	}
		
	public Company getPlayer() {
		return player;
	}
}
