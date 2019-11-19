package view.screen;

import java.util.HashMap;
import controller.Data;
import view.Visual;
import visual.panel.Panel;

public abstract class Screen {
		
	private static Visual visual;
	private String name;
	private HashMap<String, Panel> panels;
	protected LocalPanel center;
	
	private int width;
	private int height;
	private static int cycle;
	private static int[] focus;
	
//---  Constructors   -------------------------------------------------------------------------
	
 	public Screen(int x, int y, int inWidth, int inHeight, Visual parent, String inName) {
		visual = parent;
		name = inName;
		width = inWidth;
		height = inHeight;
		cycle = 0;
		focus = new int[1];
		panels = new HashMap<String, Panel>();
		initialize();
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	public abstract void initialize();
	
	public abstract void update();
	
	public void printTemporaryMessage(int x, int y, String phrase, int timer) {
		center.addFadingText("fade", 200, x, y, phrase, 3, timer);
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	public String getName() {
		return name;
	}

	public HashMap<String, Panel> getPanels() {
		return panels;
	}
	
	public Panel getPanel(String panelName) {
		return panels.get(panelName);
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public static int getCycle() {
		return cycle;
	}
	
	public static int getFocusValue(int index) {
		if(index < focus.length && index >= 0) {
			return focus[index];
		}
		return -1;
	}
	
	public static Visual getVisual() {
		return visual;
	}
	
	public static Data getModel() {
		return getVisual().getModel();
	}

	public static Data getWorld() {
		return getModel().getDataset("world");
	}
	
	public static Data getPlayer() {
		return getModel().getDataset("player");
	}
	
	public static Data[] getFactories() {
		return getPlayer().getDatasetArray("factory");
	}
	
	public static Data getCurrentFactory() {
		return getFactories()[getFocusValue(Visual.FOCUS_FACTORY_INDEX)];
	}
	
	public static Data[] getStock() {
		return getPlayer().getDatasetArray("stock");
	}	

	public static Data getReference(String ref) {
		return visual.getReference(ref);
	}
	
//---  Setter Methods   -----------------------------------------------------------------------
	
	public static void updateCycle(int in) {
		cycle = in;
	}
	
	public static void updateFocus(int size) {
		int[] newF = new int[size];
		for(int i = 0; i < (size < focus.length ? size : focus.length); i++) {
			newF[i] = focus[i];
		}
		focus = newF;
	}
	
	public static void updateFocusValue(int index, int value) {
		focus[index] = value;
	}
	
//---  Adder Methods   ------------------------------------------------------------------------
	
 	public void addPanel(String panelName, Panel panel) {
		panels.put(panelName, panel);
	}
	
//---  Mechanics   ----------------------------------------------------------------------------
 	

 	
}
