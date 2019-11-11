package model.world;

import java.util.ArrayList;

import model.world.country.Country;
import model.world.errata.Time;

public class World {

	private Time time;
	private ArrayList<Country> countries;
	
	public World() {
		time = new Time();
	}
	
	public void iterate() {
		
	}
	
	public void affect() {
		
	}
	
}
