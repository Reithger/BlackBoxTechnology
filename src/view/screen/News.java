package view.screen;

import view.Visual;

public class News extends Screen{

//---  Constructors   -------------------------------------------------------------------------
	
	public News(int x, int y, int inWidth, int inHeight, Visual parent, String inName) {
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
		center.addBackground("background");
		
		center.addButtonCustom("back", 10, getWidth() / 10, getHeight() * 9 / 10, getWidth() / 10, getHeight() / 10, "Back", 2, Visual.DEVELOPMENT_NAVIGATE_NEXUS);
		
		addPanel("center", center);
	}

	public void update() {
		
	}
	
}
