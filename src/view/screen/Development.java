package view.screen;

import controller.Data;
import model.company.development.Factory;
import view.Visual;

public class Development extends Screen{
	
	public final static int EQUIPMENT_ROWS = 2;
	public final static int EQUIPMENT_COLUMNS = 4;
	
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
		
		center.addBorderCustom("menu", 10, getWidth() * 7 / 8, getHeight() / 2, getWidth() / 4, getHeight() - 6, 2, true, false);
		
		addPanel("Development", center);
	}
	
	public void update() {
		updateCycle(getCycle() + 1);
		if(getModel() != null && center != null && getCycle() % LocalPanel.ANIMATION_RATE == 0) {
			Data factory = getFactories()[Screen.getFocusValue(Visual.FOCUS_FACTORY_INDEX)];
			Data[] equipment = factory.getDatasetArray(Factory.EQUIPMENT);
			int page = 0;
			int limit = EQUIPMENT_COLUMNS * EQUIPMENT_ROWS;
			if(equipment.length >= EQUIPMENT_COLUMNS * EQUIPMENT_ROWS) {
				page = Screen.getFocusValue(Visual.FOCUS_EQUIPMENT_PAGE_INDEX);
				center.addImage("lef", 100, getWidth() / 3 - getWidth() * 7 / 24, getHeight() / 2, true, "/assets/UI/left_arrow.png", 4);
				center.addButton("lef_int", 100, getWidth() / 3 - getWidth() * 7 / 24, getHeight() / 2, 64, 64, Visual.DEVELOPMENT_INCREMENT_EQUIPMENT, true);
				center.addImage("rig", 100, getWidth() / 3 + getWidth() * 7 / 24, getHeight() / 2, true, "/assets/UI/right_arrow.png", 4);
				center.addButton("rig_int", 100, getWidth() / 3 + getWidth() * 7 / 24, getHeight() / 2, 64, 64, Visual.DEVELOPMENT_DECREMENT_EQUIPMENT, true);
			}
			for(int i = page * limit; i < (page + 1) * limit; i++) {
				int x = (int)((double)((int)(i % EQUIPMENT_COLUMNS) + 1) / (EQUIPMENT_COLUMNS + 1) * getWidth() * 3 / 4);
				int y = (int)((double)((int)(i / EQUIPMENT_COLUMNS) + 1) / (EQUIPMENT_ROWS + 1) * getHeight());
				
				int wid = getWidth() * 3 / 4 / (EQUIPMENT_COLUMNS + 1);
				int hei = getHeight() / (EQUIPMENT_ROWS + 1);
				
				if(i < equipment.length) {
					Data d = equipment[i];
					center.addEquipmentDecal(factory.getString(Factory.TITLE) + "_" + i, 10, x, y, wid, hei, d, Visual.DEVELOPMENT_EQUIPMENT_SELECT_START + i, getCycle());
				}
				else {
					center.addBorderCustom(factory.getString(Factory.TITLE) + "_" + i, 10, x, y, wid, hei, 1, true, false);
					center.addButton("new_equi_" + i, 10, x, y, wid, hei, Visual.DEVELOPMENT_EMPTY_EQUIPMENT, true);
				}
			}
			
			if(Screen.getFocusValue(Visual.FOCUS_EQUIPMENT_INDEX) >= 0) {
				center.removeElementPrefixed("build");
				Data d = getFactories()[Screen.getFocusValue(Visual.FOCUS_FACTORY_INDEX)].getDatasetArray(Factory.EQUIPMENT)[Screen.getFocusValue(Visual.FOCUS_EQUIPMENT_INDEX)];
				center.addEquipmentInfo("det", 10, getWidth() * 7 / 8, getHeight() / 2, getWidth() / 4, getHeight(), d, getCycle());
			}
			else {
				center.removeElementPrefixed("det");
				Data d = getCurrentFactory().getDataset(Factory.BUILDABLE);
				String[] names = d.getStringArray("names");
				for(int i = 0; i < names.length; i++) {
					center.addBuildableInfo("build_" + i, 100, getWidth() * 7 / 8, (int)(getHeight() * (i + 1.0) / (names.length + 1)), getWidth() / 4, getHeight() / 4, d.getDataset(names[i]), Visual.DEVELOPMENT_EQUIPMENT_BUILD_START + i, getCycle());
				}
			}
			if(getCycle() > LocalPanel.ANIMATION_RATE * getFactories()[Screen.getFocusValue(Visual.FOCUS_FACTORY_INDEX)].getStringArray(Factory.IMAGE).length) {
				updateCycle(0);
			}
		}
	}
	
	public void printTemporaryMessage(int x, int y, String phrase, int timer) {
		center.addFadingText("fade", 200, x, y, phrase, 3, timer);
	}
}
