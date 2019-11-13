package model;

import model.company.Company;
import model.world.World;

public class Model {

	private World world;
	private Company player;
	
	/*
	 * Save file; what data is stored to reconstruct objects?
	 * World:
	 *  - Countries and their conditions
	 *  - Research performed (names)
	 *  - People (all data)
	 *  - Consequences
	 *  
	 * Company:
	 *  - Factories
	 *   - Equipment
	 *    - Level
	 *    - Assigned Employees (names)
	 *   - Employees (names)
	 *  - Research
	 *   - In progress
	 * 
	 */
	
	public Model() {
		
	}
	
}
