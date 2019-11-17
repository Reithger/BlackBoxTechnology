package view.screen;

import model.company.development.Factory;
import view.Visual;
import visual.panel.ElementPanel;

public class Development extends Screen{
	
	LocalPanel center;
	int cycle;

	public Development(int x, int y, int inWidth, int inHeight, Visual parent, String inName) {
		super(x, y, inWidth, inHeight, parent, inName);
		cycle = 0;
	}

	@Override
	public void initialize() {
		center = new LocalPanel(0, 0, getWidth(), getHeight()) {
			public void clickBehaviour(int event) {
				getVisual().triggerEvent(event);
			}
			
			public void keyBehaviour(char event) {
				getVisual().triggerEvent(event);
			}
		};

		center.addBackground("back", "/assets/background/back3.png");
		
		center.addButtonCustom("back", 10, getWidth() / 6, getHeight() * 7 / 8, getWidth() / 8, getHeight() / 10, "Back", 1, Visual.DEVELOPMENT_NAVIGATE_NEXUS);
		
		addPanel("Development", center);
	}
	
	public void update() {
		if(getModel() != null && center != null) {
			center.addFactoryDecal("fac_1", 10, getWidth() * 3 / 4, getHeight() / 2, getWidth() / 3, getHeight() * 3 / 5, getFactories()[factoryIndex], cycle++);
			if(cycle > LocalPanel.ANIMATION_RATE * getFactories()[factoryIndex].getStringArray(Factory.IMAGE).length) {
				cycle = 0;
			}
		}
	}

}
