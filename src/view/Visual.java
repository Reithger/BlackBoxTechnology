package view;

import java.util.HashMap;

import controller.Core;
import controller.Data;
import model.Model;
import model.company.development.Equipment;
import model.company.development.Factory;
import view.screen.Development;
import view.screen.News;
import view.screen.Nexus;
import view.screen.Research;
import view.screen.Screen;
import view.screen.Title;
import visual.frame.WindowFrame;

public class Visual {
	
//---  Constants   ----------------------------------------------------------------------------
	
	public final static int FOCUS_SIZE = 100;
	
	public final static int FOCUS_FACTORY_INDEX = 0;
	public final static int FOCUS_EQUIPMENT_INDEX = 1;
	public final static int FOCUS_EQUIPMENT_PAGE_INDEX = 2;

	public final static int TITLE_START_OLD = 1;
	public final static int TITLE_START_NEW = 2;
	
	public final static int NEXUS_NAVIGATE_RESEARCH = 1;
	public final static int NEXUS_NAVIGATE_DEVELOPMENT = 2;
	public final static int NEXUS_NAVIGATE_NEWS = 3;
	public final static int NEXUS_INCREMENT_FACTORY = 4;
	public final static int NEXUS_DECREMENT_FACTORY = 5;
	
	public final static int RESEARCH_NAVIGATE_NEXUS = 1;
	
	public final static int DEVELOPMENT_NAVIGATE_NEXUS = 1;
	public final static int DEVELOPMENT_INCREMENT_EQUIPMENT = 2;
	public final static int DEVELOPMENT_DECREMENT_EQUIPMENT = 3;
	public final static int DEVELOPMENT_EMPTY_EQUIPMENT = 4;
	public final static int DEVELOPMENT_EQUIPMENT_UPGRADE = 5;
	public final static int DEVELOPMENT_EQUIPMENT_SELECT_START = 50;
	public final static int DEVELOPMENT_EQUIPMENT_BUILD_START = 100;
	
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
		addScreen(new Title(0, 0, width, height, this, "title"));
		addScreen(new Nexus(0, 0, width, height, this, "nexus"));
		addScreen(new News(0, 0, width, height, this, "news"));
		addScreen(new Research(0, 0, width, height, this, "research"));
		addScreen(new Development(0, 0, width, height, this, "development"));
		setActive("title");
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
	
//---  Setter Methods   -----------------------------------------------------------------------

	public void updateModel(Data dat) {
		model = dat;
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	public Data getModel() {
		return model;
	}
		
	public Data getReference(String ref) {
		return core.getReference(ref);
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
			case NEXUS_NAVIGATE_RESEARCH : 
				setActive("research"); 
				break;
			case NEXUS_NAVIGATE_DEVELOPMENT :
				setActive("development");
				Screen.updateFocusValue(FOCUS_EQUIPMENT_INDEX, -1);
				Screen.updateFocusValue(FOCUS_EQUIPMENT_PAGE_INDEX, 0);
				break;
			case NEXUS_NAVIGATE_NEWS :
				setActive("news");
				break;
			case NEXUS_INCREMENT_FACTORY :
				Screen.updateFocusValue(FOCUS_FACTORY_INDEX, (Screen.getFocusValue(FOCUS_FACTORY_INDEX) + 1) % Screen.getFactories().length);
				break;
			case NEXUS_DECREMENT_FACTORY :
				int ind = Screen.getFocusValue(FOCUS_FACTORY_INDEX) - 1;
				if(ind < 0) {
					ind = Screen.getFactories().length - 1;
				}
				Screen.updateFocusValue(FOCUS_FACTORY_INDEX, ind);
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
		Data factor = Screen.getCurrentFactory();
		String[] build = core.getReference(Model.PATH_EQUIPMENT).getDataset(factor.getString(Factory.TITLE)).getStringArray("names");
		for(int i = DEVELOPMENT_EQUIPMENT_BUILD_START; i <= DEVELOPMENT_EQUIPMENT_BUILD_START + build.length; i++) {
			if(event == i) {
				if(!core.buildEquipment(factor.getString(Factory.TITLE), build[i - DEVELOPMENT_EQUIPMENT_BUILD_START])) {
					Screen s = screens.get(current);
					s.printTemporaryMessage(s.getWidth() / 2, s.getHeight() * 9 / 10, "Insufficient Funds", 30);
				}
				else {
					core.updateVisualModel();
				}
			}
		}
		switch(event) {
		  case DEVELOPMENT_NAVIGATE_NEXUS : 
			  setActive("nexus");
			  break;
		  case DEVELOPMENT_INCREMENT_EQUIPMENT : 
			  Screen.updateFocusValue(FOCUS_EQUIPMENT_PAGE_INDEX, (Screen.getFocusValue(FOCUS_EQUIPMENT_PAGE_INDEX) + 1) % (int)((factor.getDatasetArray(Factory.EQUIPMENT).length / (Development.EQUIPMENT_COLUMNS * Development.EQUIPMENT_ROWS) + 1)));
			  break;
		  case DEVELOPMENT_DECREMENT_EQUIPMENT :
			  int ind = Screen.getFocusValue(FOCUS_EQUIPMENT_PAGE_INDEX) - 1;
			  if(ind >= 0) {
				  Screen.updateFocusValue(FOCUS_EQUIPMENT_PAGE_INDEX, ind);
			  }
			  break;
		  case DEVELOPMENT_EMPTY_EQUIPMENT :
			  Screen.updateFocusValue(FOCUS_EQUIPMENT_INDEX, -1);
			  break;
		  case DEVELOPMENT_EQUIPMENT_UPGRADE :
			  if(!core.upgradeEquipment(factor.getString(Factory.TITLE), factor.getDatasetArray(Factory.EQUIPMENT)[Screen.getFocusValue(FOCUS_EQUIPMENT_INDEX)].getString(Equipment.NAME))) {
				  Screen s = screens.get(current);
			      s.printTemporaryMessage(s.getWidth() / 2, s.getHeight() * 9 / 10, "Insufficient Funds", 30);
			  }
			  else {
				  core.updateVisualModel();
			  }
			  break;
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
