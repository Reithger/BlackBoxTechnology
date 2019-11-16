package view.screen;

import view.Visual;
import visual.panel.ElementPanel;

public class Development extends Screen{

	public Development(int x, int y, int inWidth, int inHeight, Visual parent, String inName) {
		super(x, y, inWidth, inHeight, parent, inName);
	}

	@Override
	public void initialize() {
		LocalPanel center = new LocalPanel(0, 0, getWidth(), getHeight()) {
			public void clickBehaviour(int event) {
				getVisual().triggerEvent(event);
			}
			
			public void keyBehaviour(int event) {
				getVisual().triggerEvent(event);
			}
		};

		center.addBackground("back", "/assets/background/back3.png");
		
		center.addButtonCustom("back", 10, getWidth() / 6, getHeight() * 7 / 8, getWidth() / 8, getHeight() / 10, "Back", 1, 2, Visual.DEVELOPMENT_NAVIGATE_NEXUS);
		
		addPanel("Development", center);
	}
	
	public void update() {
		
	}

}
