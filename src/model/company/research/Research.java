package model.company.research;

import controller.Data;

public class Research {
	
	public final static String TITLE = "research";

	
	public Data exportData() {
		Data dat = new Data(TITLE);
		
		return dat;
	}
	
}
