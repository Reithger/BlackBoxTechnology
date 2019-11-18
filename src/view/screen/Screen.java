package view.screen;

import java.util.HashMap;

import controller.Data;
import view.Visual;
import visual.panel.Panel;

public abstract class Screen {
		
	private Visual visual;
	private String name;
	private HashMap<String, Panel> panels;
	
	private int width;
	private int height;
	private static int cycle;
	private static int[] focus;
	private boolean update;
	
//---  Constructors   -------------------------------------------------------------------------
	
 	public Screen(int x, int y, int inWidth, int inHeight, Visual parent, String inName) {
		visual = parent;
		name = inName;
		width = inWidth;
		height = inHeight;
		cycle = 0;
		focus = new int[1];
		panels = new HashMap<String, Panel>();
		update = true;
		initialize();
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	public abstract void initialize();
	
	public abstract void update();
	
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
	
	public boolean getUpdateStatus() {
		return update;
	}
	
	public Visual getVisual() {
		return visual;
	}
	
	public Data getModel() {
		return getVisual().getModel();
	}

	public Data getWorld() {
		return getModel().getDataset("world");
	}
	
	public Data getPlayer() {
		return getModel().getDataset("player");
	}
	
	public Data[] getFactories() {
		return getPlayer().getDatasetArray("factory");
	}
	
	public Data getCurrentFactory() {
		return getFactories()[getFocusValue(Visual.FOCUS_FACTORY_INDEX)];
	}
	
	public Data[] getStock() {
		return getPlayer().getDatasetArray("stock");
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
	
	public void flagUpdate() {
		update = !update;
	}
	
//---  Adder Methods   ------------------------------------------------------------------------
	
 	public void addPanel(String panelName, Panel panel) {
		panels.put(panelName, panel);
	}
	
}
