package view.screen;

import java.awt.Color;
import java.awt.Font;

import view.Core;
import visual.panel.ElementPanel;

public class Nexus extends Screen{

	private static final Font titleFont = new Font("Times New Roman", Font.BOLD, 30);
		
	public Nexus(int x, int y, int inWidth, int inHeight, Core parent, String inName) {
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
		
		center.addButton("but1", 0, getWidth() / 4, getHeight() / 2, getWidth() / 3, getHeight() * 3 / 4, new Color(200, 200, 200), Core.NEXUS_NAVIGATE_RESEARCH, ElementPanel.CENTERED);
		center.addText("tex1", 1, getWidth() / 4, getHeight() / 2, getWidth() / 3, getHeight() * 3 / 4, "Research", titleFont, ElementPanel.CENTERED, ElementPanel.CENTERED, ElementPanel.CENTERED);

		center.addButton("but2", 0, getWidth() * 3/ 4, getHeight() / 2, getWidth() / 3, getHeight() * 3 / 4, new Color(200, 200, 200), Core.NEXUS_NAVIGATE_DEVELOPMENT, ElementPanel.CENTERED);
		center.addText("tex2", 1, getWidth() * 3 / 4, getHeight() / 2, getWidth() / 3, getHeight() * 3 / 4, "Factory", titleFont, ElementPanel.CENTERED, ElementPanel.CENTERED, ElementPanel.CENTERED);
		
		addPanel("center", center);
	}

}
