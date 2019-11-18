package view;

import java.util.HashMap;

import controller.Core;
import controller.Data;
import view.screen.Development;
import view.screen.News;
import view.screen.Nexus;
import view.screen.Research;
import view.screen.Screen;
import view.screen.Title;
import visual.frame.WindowFrame;

public class Visual {
	
	public final static int FOCUS_SIZE = 100;
	
	public final static int FOCUS_FACTORY_INDEX = 0;
	public final static int FOCUS_EQUIPMENT_INDEX = 1;

	public final static int TITLE_START_OLD = 1;
	public final static int TITLE_START_NEW = 2;
	
	public final static int NEXUS_NAVIGATE_RESEARCH = 1;
	public final static int NEXUS_NAVIGATE_DEVELOPMENT = 2;
	public final static int NEXUS_NAVIGATE_NEWS = 3;
	
	public final static int RESEARCH_NAVIGATE_NEXUS = 1;
	
	public final static int DEVELOPMENT_NAVIGATE_NEXUS = 1;
	public final static int DEVELOPMENT_EQUIPMENT_SELECT_START = 50;
	
	public final static int NEWS_NAVIGATE_NEXUS = 1;
	
//---  Instance Variables   -------------------------------------------------------------------
	
	private Core core;
	private WindowFrame display;
	private HashMap<String, Screen> screens;
	private String current;
	private Data model; 
	
//---  Constructors   -------------------------------------------------------------------------
	
	public Visual(int width, int height, Core c) {
		model = null;
		core = c;
		current = "title";
		screens = new HashMap<String, Screen>();
		display = new WindowFrame(width, height) {
			@Override
			public void repaint() {
				super.repaint();
				if(screens.get(current) != null)
					screens.get(current).update();
			}
		};
		Title titlePanel = new Title(0, 0, width, height, this, "title");
		Nexus nexus = new Nexus(0, 0, width, height, this, "nexus");
		News news = new News(0, 0, width, height, this, "news");
		Research research = new Research(0, 0, width, height, this, "research");
		Development development = new Development(0, 0, width, height, this, "development");
		addScreen(titlePanel);
		addScreen(nexus);
		addScreen(news);
		addScreen(research);
		addScreen(development);
		setActive(titlePanel);
		Screen.updateFocus(FOCUS_SIZE);
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	public void addScreen(Screen in) {
		for(String s : in.getPanels().keySet()) {
			display.reservePanel(in.getName(), s, in.getPanel(s));
		}
		screens.put(in.getName(), in);
	}
	
	public void setActive(Screen screen) {
		display.hidePanels();
		display.setActiveWindow(screen.getName());
		current = screen.getName();
	}
	
	public void setActive(String screen) {
		display.hidePanels();
		display.setActiveWindow(screen);
		current = screen;
	}
	
	public void updateModel(Data dat) {
		model = dat;
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	public Data getModel() {
		return model;
	}
		
//---  Interaction Handling   -----------------------------------------------------------------
	
	public void triggerEvent(int event) {
		switch(current) {
		  case "title" 	: 	handleTitle(event); break;
		  case "nexus" 	: 	handleNexus(event); break;
		  case "research" : handleResearch(event); break;
		  case "development" : handleDevelopment(event); break;
		  case "news" 	: 	handleNews(event); break;
		  default : break;
		}
	}
	
	private void handleTitle(int event) {
		switch(event) {
		  	case TITLE_START_OLD: 
		  		core.loadGame();
		  		break;
		  	case TITLE_START_NEW:
		  		core.startNewGame();
		  		break;
		  	default : break;
		}
	}
	
	private void handleNexus(int event) {
		switch(event) {
			case NEXUS_NAVIGATE_RESEARCH : setActive("research"); break;
			case NEXUS_NAVIGATE_DEVELOPMENT : 
				setActive("development");
				Screen.updateFocusValue(1, -1);
				break;
			default : break;
		}
	}
	
	private void handleResearch(int event) {
		switch(event) {
		  case RESEARCH_NAVIGATE_NEXUS : setActive("nexus");
		  default : break;
		}
	}
	
	private void handleDevelopment(int event) {
		for(int i = DEVELOPMENT_EQUIPMENT_SELECT_START; i <= DEVELOPMENT_EQUIPMENT_SELECT_START + Development.EQUIPMENT_COLUMNS * Development.EQUIPMENT_ROWS; i++) {
			if(event == i) {
				Screen.updateFocusValue(FOCUS_EQUIPMENT_INDEX, i - DEVELOPMENT_EQUIPMENT_SELECT_START);
			}
		}
		switch(event) {
		  case DEVELOPMENT_NAVIGATE_NEXUS : setActive("nexus");
		  default : break;
		}
	}
	
	private void handleNews(int event) {
		switch(event) {
		  case NEWS_NAVIGATE_NEXUS : setActive("nexus");
		  default : break;
		}
	}
		
}
