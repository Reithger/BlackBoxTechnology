package view.screen;

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
			
			public void keyBehaviour(int event) {
				getVisual().triggerEvent(event);
			}
		};
	}

}
