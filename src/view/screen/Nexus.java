package view.screen;

import model.company.development.Factory;
import view.Visual;

public class Nexus extends Screen{

//---  Constructors   -------------------------------------------------------------------------
	
	public Nexus(int x, int y, int inWidth, int inHeight, Visual parent, String inName) {
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
				if(event == 'd') {
					System.out.println(getModel());
				}
			}
		};
		center.addBackground("back");
		
		center.addButtonCustom("but1", 1, getWidth() / 4, getHeight() / 2, getWidth() / 3, getHeight() * 3 / 5, "", 2, Visual.NEXUS_NAVIGATE_RESEARCH);
		center.addButtonCustom("but2", 0, getWidth() * 3 / 4, getHeight() / 2, getWidth() / 3, getHeight() * 3 / 5, "", 2, Visual.NEXUS_NAVIGATE_DEVELOPMENT);

		center.addBorderCustomBacking("dev", 1, getWidth() / 4 - getWidth() / 6, getHeight () / 2 - getHeight() * 3 / 10, getWidth() / 3, getHeight() * 3 / 5, 1, false, false);
		center.addBorderCustomBacking("res", 1, getWidth() * 3 / 4 - getWidth() / 6,  getHeight() / 2 - getHeight() * 3 / 10, getWidth() / 3, getHeight() * 3 / 5, 1, false, false);
		
		center.addTextCustom("tex1", 10, getWidth() / 4, getHeight() / 4, "Research", 3);
		center.addTextCustom("tex2", 10, getWidth() * 3/ 4, getHeight() / 4, "Development", 3);
		
		center.addBorderCustom("dev_tit", 13, getWidth() / 4 - getWidth() / 6, getHeight () / 2 - getHeight() * 3 / 10, getWidth() / 3, getHeight() / 10, 1, false, false);
		center.addBorderCustom("res_tit", 13, getWidth() * 3 / 4 - getWidth() / 6,  getHeight() / 2 - getHeight() * 3 / 10, getWidth() / 3, getHeight() / 10, 1, false, false);

		center.addButtonCustom("but3", 1, getWidth() / 2, getHeight() * 9 / 10, getWidth() / 5, getHeight() / 10, "News", 2, Visual.NEXUS_NAVIGATE_NEWS);
		
		addPanel("Nexus", center);
	}

	public void update() {
		updateCycle(getCycle() + 1);
		if(getModel() != null && center != null && getCycle() % LocalPanel.ANIMATION_RATE == 0) {
			center.addMoney("money", 2, getWidth() / 2, getHeight() / 10, getWidth() / 5, getHeight() / 10);
			center.addFactoryDecal("fac_1", 10, getWidth() * 3 / 4, getHeight() / 2, getWidth() / 3, getHeight() * 3 / 5, getCurrentFactory(), getCycle());
			if(Screen.getFactories().length > 1) {
				center.addImage("lef", 100, getWidth() * 3 / 4 - getWidth() * 3 / 24, getHeight() / 2, true, "/assets/UI/left_arrow.png", 4);
				center.addButton("lef_int", 100, getWidth() * 3 / 4 - getWidth() * 3 / 24, getHeight() / 2, 64, 64, Visual.NEXUS_INCREMENT_FACTORY, true);
				center.addImage("rig", 100, getWidth() * 3 / 4 + getWidth() * 3 / 24, getHeight() / 2, true, "/assets/UI/right_arrow.png", 4);
				center.addButton("rig_int", 100, getWidth() * 3 / 4 + getWidth() * 3 / 24, getHeight() / 2, 64, 64, Visual.NEXUS_DECREMENT_FACTORY, true);
			}
			if(getCycle() > LocalPanel.ANIMATION_RATE * getCurrentFactory().getStringArray(Factory.IMAGE).length) {
				Screen.updateCycle(0);
			}
		}
	}
	
}
