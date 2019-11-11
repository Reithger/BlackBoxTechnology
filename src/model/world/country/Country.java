package model.world.country;

import java.util.ArrayList;

import model.world.market.Market;
import model.world.social.Social;

public class Country {

	private String name;
	private ArrayList<Market> marketVariables;
	private ArrayList<Social> socialVariables;
	private Population population;
	
	public String getName() {
		return name;
	}
	
}
