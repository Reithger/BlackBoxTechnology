package view.screen;

import model.company.development.Factory;
import view.Visual;
import visual.panel.ElementPanel;

public class Nexus extends Screen{

	private LocalPanel center;
	
	public Nexus(int x, int y, int inWidth, int inHeight, Visual parent, String inName) {
		super(x, y, inWidth, inHeight, parent, inName);
	}

	@Override
	public void initialize() {
		center = new LocalPanel(0, 0, getWidth(), getHeight()) {
			public void clickBehaviour(int event) {
				getVisual().triggerEvent(event);
			}
			
			public void keyBehaviour(char event) {
				getVisual().triggerEvent(event);
				if(event == 'd') {
					System.out.println(getModel());
				}
			}
		};
		center.addBackground("back", "/assets/background/back2.png");
		
		center.addButton("but1", 0, getWidth() / 4, getHeight() / 2, getWidth() / 3, getHeight() * 3 / 5, Visual.NEXUS_NAVIGATE_RESEARCH, ElementPanel.CENTERED);
		center.addButton("but2", 0, getWidth() * 3 / 4, getHeight() / 2, getWidth() / 3, getHeight() * 3 / 5,  Visual.NEXUS_NAVIGATE_DEVELOPMENT, ElementPanel.CENTERED);

		center.addBorderCustom("dev", 1, getWidth() / 4 - getWidth() / 6, getHeight () / 2 - getHeight() * 3 / 10, getWidth() / 3, getHeight() * 3 / 5, 1, false);
		center.addBorderCustom("res", 1, getWidth() * 3 / 4 - getWidth() / 6,  getHeight() / 2 - getHeight() * 3 / 10, getWidth() / 3, getHeight() * 3 / 5, 1, false);
		
		center.addTextCustom("tex1", 10, getWidth() / 4, getHeight() / 4, "Research", 3);
		center.addTextCustom("tex2", 10, getWidth() * 3/ 4, getHeight() / 4, "Development", 3);
		
		center.addBorderCustom("dev_tit", 2, getWidth() / 4 - getWidth() / 6, getHeight () / 2 - getHeight() * 3 / 10, getWidth() / 3, getHeight() / 10, 1, false);
		center.addBorderCustom("res_tit", 2, getWidth() * 3 / 4 - getWidth() / 6,  getHeight() / 2 - getHeight() * 3 / 10, getWidth() / 3, getHeight() / 10, 1, false);

		addPanel("Nexus", center);
	}

	public void update() {
		updateCycle(getCycle() + 1);
		if(getModel() != null && center != null && getCycle() % LocalPanel.ANIMATION_RATE == 0) {
			center.addFactoryDecal("fac_1", 10, getWidth() * 3 / 4, getHeight() / 2, getWidth() / 3, getHeight() * 3 / 5, getCurrentFactory(), getCycle());
			if(getCycle() > LocalPanel.ANIMATION_RATE * getCurrentFactory().getStringArray(Factory.IMAGE).length) {
				Screen.updateCycle(0);
			}
		}
	}
	
}
