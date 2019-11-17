package model.world.market;

import controller.Data;

public class Product implements Market{

	private String name;
	private int price;
	
	public Product(Data dat) {
		
	}
	
	public String getName() {
		return name;
	}
	
}
