package view.screen;

import java.util.HashMap;

import controller.Data;
import view.Visual;
import visual.panel.Panel;

public abstract class Screen {
	
	private Visual Visual;
	private String name;
	private HashMap<String, Panel> panels;
	
	private int width;
	private int height;
	
//---  Constructors   -------------------------------------------------------------------------
	
	public Screen(int x, int y, int inWidth, int inHeight, Visual parent, String inName) {
		Visual = parent;
		name = inName;
		width = inWidth;
		height = inHeight;
		panels = new HashMap<String, Panel>();
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
	
	public Visual getVisual() {
		return Visual;
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
	
	public Data[] getStock() {
		return getPlayer().getDatasetArray("stock");
	}	

//---  Adder Methods   ------------------------------------------------------------------------
	
	public void addPanel(String panelName, Panel panel) {
		panels.put(panelName, panel);
	}
	
}
