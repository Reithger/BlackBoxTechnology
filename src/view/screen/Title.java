package view.screen;

import java.awt.Color;
import java.awt.Font;

import view.Visual;
import visual.panel.ElementPanel;

public class Title extends Screen{

	private static final Font titleFont = new Font("Times New Roman", Font.BOLD, 30);
		
	public Title(int x, int y, int inWidth, int inHeight, Visual parent, String inName) {
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
		
		center.addRectangle("rect", 0, getWidth()/2, getHeight() / 3,  getWidth() * 2 / 3, getHeight() / 4, ElementPanel.CENTERED, new Color(84, 34, 200));
		center.addText("text", 0, getWidth() / 2, getHeight() / 3, getWidth() * 2 / 3, getHeight() / 4, "Black Box Technologies", titleFont, ElementPanel.CENTERED, ElementPanel.NON_CENTERED, ElementPanel.CENTERED);
		
		center.addButton("butt", 0, getWidth() / 2, getHeight() * 2 / 3, getWidth() / 5, getHeight() / 6, new Color(84, 34, 200), Visual.TITLE_NAVIGATE_NEXUS, ElementPanel.CENTERED);
		addPanel("center", center);
	}

}
