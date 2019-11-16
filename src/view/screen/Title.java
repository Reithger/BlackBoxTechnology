package view.screen;

import java.awt.Color;
import java.awt.Font;

import view.Visual;
import visual.panel.ElementPanel;

public class Title extends Screen{
		
	public Title(int x, int y, int inWidth, int inHeight, Visual parent, String inName) {
		super(x, y, inWidth, inHeight, parent, inName);
	}

	@Override
	public void initialize() {
		LocalPanel center = new LocalPanel(0, 0, getWidth(), getHeight()) {
			public void clickBehaviour(int event) {
				getVisual().triggerEvent(event);
			}
			
			public void keyBehaviour(char event) {
				getVisual().triggerEvent(event);
			}
		};
		
		center.addBackground("back", "/assets/background/back1.png");
		
		center.addRectangle("rect", 1, getWidth()/2, getHeight() / 3,  getWidth() * 3 / 4, getHeight() / 4, ElementPanel.CENTERED, new Color(84, 34, 200));
		center.addTextCustom("title", 10, getWidth() / 2, getHeight() / 3, "Black Box Technologies", 4);
		
		center.addButtonCustom("start", 10, getWidth() / 3, getHeight() * 3 / 4, getWidth() / 5, getHeight() / 6, "Continue", 2, 3, Visual.TITLE_START_OLD);
		center.addButtonCustom("start_new", 10, getWidth() * 2 / 3, getHeight() * 3 / 4, getWidth() / 5, getHeight() / 6, "Start New", 2, 3, Visual.TITLE_START_NEW);
		
		addPanel("Title", center);
	}

	public void update() {
		
	}
	
}
