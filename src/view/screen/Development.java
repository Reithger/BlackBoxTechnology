package view.screen;

import controller.Data;
import model.company.development.Factory;
import view.Visual;
import visual.panel.ElementPanel;

public class Development extends Screen{
	
	public final static int EQUIPMENT_ROWS = 2;
	public final static int EQUIPMENT_COLUMNS = 4;
	
	LocalPanel center;

	public Development(int x, int y, int inWidth, int inHeight, Visual parent, String inName) {
		super(x, y, inWidth, inHeight, parent, inName);
	}

	@Override
	public void initialize() {
		center = new LocalPanel(0, 0, getWidth(), getHeight()) {
			public void clickBehaviour(int event) {
				System.out.println(event);
				getVisual().triggerEvent(event);
			}
			
			public void keyBehaviour(char event) {
				getVisual().triggerEvent(event);
			}
		};

		center.addBackground("back", "/assets/background/back3.png");
		
		center.addButtonCustom("back", 10, getWidth() / 10, getHeight() * 9 / 10, getWidth() / 10, getHeight() / 10, "Back", 1, Visual.DEVELOPMENT_NAVIGATE_NEXUS);
		
		center.addBorderCustom("menu", 10, getWidth() * 7 / 8, getHeight() / 2 + 16, getWidth() / 4, getHeight() - 32, 2, true);
		
		addPanel("Development", center);
	}

	public void update() {
		updateCycle(getCycle() + 1);
		if(getModel() != null && center != null && getCycle() % LocalPanel.ANIMATION_RATE == 0) {
			Data factory = getFactories()[Screen.getFocusValue(Visual.FOCUS_FACTORY_INDEX)];
			Data[] equipment = factory.getDatasetArray(Factory.EQUIPMENT); 
			for(int i = 0; i < EQUIPMENT_COLUMNS * EQUIPMENT_ROWS; i++) {
				int x = (int)((double)((int)(i % EQUIPMENT_COLUMNS) + 1) / (EQUIPMENT_COLUMNS + 1) * getWidth() * 3 / 4);
				int y = (int)((double)((int)(i / EQUIPMENT_COLUMNS) + 1) / (EQUIPMENT_ROWS + 1) * getHeight());
				
				int wid = getWidth() * 3 / 4 / (EQUIPMENT_COLUMNS + 1);
				int hei = getHeight() / (EQUIPMENT_ROWS + 1);
				
				if(i < equipment.length) {
					Data d = equipment[i];
					center.addEquipmentDecal(factory.getString(Factory.TITLE) + "_" + i, 10, x, y, wid, hei, d, Visual.DEVELOPMENT_EQUIPMENT_SELECT_START + i, getCycle());
				}
				else {
					center.addBorderCustom(factory.getString(Factory.TITLE) + "_" + i, 10, x, y, wid, hei, 1, true);
				}
			}
			
			if(Screen.getFocusValue(Visual.FOCUS_EQUIPMENT_INDEX) >= 0) {
				Data d = getFactories()[Screen.getFocusValue(Visual.FOCUS_FACTORY_INDEX)].getDatasetArray(Factory.EQUIPMENT)[Screen.getFocusValue(Visual.FOCUS_EQUIPMENT_INDEX)];
				center.addEquipmentInfo("det", 10, getWidth() * 7 / 8, getHeight() / 2, getWidth() / 4, getHeight(), d, getCycle());
			}
			if(getCycle() > LocalPanel.ANIMATION_RATE * getFactories()[Screen.getFocusValue(Visual.FOCUS_FACTORY_INDEX)].getStringArray(Factory.IMAGE).length) {
				updateCycle(0);
			}
		}
	}

}
