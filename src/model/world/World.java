package model.world;

import java.util.ArrayList;

import controller.Data;
import model.world.country.Country;
import model.world.errata.Time;

public class World {
	
	public final static String TITLE = "world";

	private Time time;
	private ArrayList<Country> countries;
	
	public World(Data dat) {
		
	}
	
	public World() {
		time = new Time();
	}
	
	public void iterate() {
		
	}
	
	public void affect() {
		
	}
	
	public Data exportData() {
		Data dat = new Data(TITLE);
		
		return dat;
	}
	
}
