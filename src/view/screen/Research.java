package view.screen;

import java.awt.Color;

import view.Visual;
import visual.panel.ElementPanel;

public class Research extends Screen{

	public Research(int x, int y, int inWidth, int inHeight, Visual parent, String inName) {
		super(x, y, inWidth, inHeight, parent, inName);
	}

	@Override
	public void initialize() {
		ElementPanel center = new ElementPanel(0, 0, getWidth(), getHeight()) {
			public void clickBehaviour(int event) {
				getVisual().triggerEvent(event);
			}
			
			public void keyBehaviour(char event) {
				getVisual().triggerEvent(event);
			}
		};
		center.addRectangle("back", 0, 0, 0, getWidth(), getHeight(), ElementPanel.NON_CENTERED, new Color(255,255, 255));
		
		addPanel("Research", center);
	}
	
	public void update() {
		
	}

}
