package view.screen;

import view.Core;
import visual.panel.ElementPanel;

public class News extends Screen{

	public News(int x, int y, int inWidth, int inHeight, Core parent, String inName) {
		super(x, y, inWidth, inHeight, parent, inName);
	}

	@Override
	public void initialize() {
		ElementPanel center = new ElementPanel(0, 0, getWidth(), getHeight()) {
			public void clickBehaviour(int event) {
				getCore().triggerEvent(event);
			}
			
			public void keyBehaviour(int event) {
				getCore().triggerEvent(event);
			}
		};
	}

}
