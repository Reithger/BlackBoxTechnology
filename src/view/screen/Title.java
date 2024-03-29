package view.screen;

import controller.Core;
import view.Visual;

public class Title extends Screen{
	
//---  Constructors   -------------------------------------------------------------------------
	
	public Title(int x, int y, int inWidth, int inHeight, Visual parent, String inName) {
		super(x, y, inWidth, inHeight, parent, inName);
	}
	
//---  Operations   ---------------------------------------------------------------------------

	@Override
	public void initialize() {
		center = new LocalPanel(0, 0, getWidth(), getHeight()) {
			@Override
			public void clickBehaviour(int event) {
				getVisual().triggerEvent(event);
			}

			@Override
			public void keyBehaviour(char event) {
				getVisual().triggerEvent(event);
			}
			
			@Override
			public void addBackground(String name, String path) {
				super.addBackground(name, path);
				removeElementPrefixed("money");
			}
		};

		center.addBackground("back", "/assets/background/back6.png");

		center.addBorderCustomBacking("bord", 1, getWidth()/2, getHeight() / 3, getWidth() * 3 / 4, getHeight() / 4, 2, true, false);
		center.addTextCustom("title", 10, getWidth() / 2, getHeight() / 3, "Black Box Technologies", 4);
		
		center.addButtonCustom("start", 10, getWidth() / 3, getHeight() * 3 / 4, getWidth() / 5, getHeight() / 8, "Continue", 2, Visual.TITLE_START_OLD);
		center.addButtonCustom("start_new", 10, getWidth() * 2 / 3, getHeight() * 3 / 4, getWidth() / 5, getHeight() / 8, "Start New", 2, Visual.TITLE_START_NEW);
		
		addPanel("Title", center);
	}

	public void update() {
		
	}
	
}
