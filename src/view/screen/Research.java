package view.screen;

import view.Visual;

public class Research extends Screen{

//---  Constructors   -------------------------------------------------------------------------
	
	public Research(int x, int y, int inWidth, int inHeight, Visual parent, String inName) {
		super(x, y, inWidth, inHeight, parent, inName);
	}
	
//---  Operations   ---------------------------------------------------------------------------

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
		center.addBackground("background", "/assets/background/back6.png");
		
		center.addButtonCustom("back", 10, getWidth() / 10, getHeight() * 9 / 10, getWidth() / 10, getHeight() / 10, "Back", 2, Visual.DEVELOPMENT_NAVIGATE_NEXUS);
		
		addPanel("Research", center);
	}
	
	public void update() {
		center.addMoney("money", 2, getWidth() / 2, getHeight() / 10, getWidth() / 5, getHeight() / 10);
	}

}
