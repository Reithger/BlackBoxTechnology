package view.screen;

import java.util.HashMap;
import view.Core;
import visual.panel.Panel;

public abstract class Screen {
	
	private Core core;
	private String name;
	private HashMap<String, Panel> panels;
	
	private int width;
	private int height;
	
	public Screen(int x, int y, int inWidth, int inHeight, Core parent, String inName) {
		core = parent;
		name = inName;
		width = inWidth;
		height = inHeight;
		panels = new HashMap<String, Panel>();
		initialize();
	}
	
	public abstract void initialize();
	
	public String getName() {
		return name;
	}
	
	public void addPanel(String panelName, Panel panel) {
		panels.put(panelName, panel);
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
	
	public Core getCore() {
		return core;
	}
	
}
