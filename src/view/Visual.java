package view;

import view.screen.Screen;
import visual.frame.WindowFrame;

public class Visual {

	public final static int TITLE_NAVIGATE_NEXUS = 1;
	
	public final static int NEXUS_NAVIGATE_RESEARCH = 1;
	public final static int NEXUS_NAVIGATE_DEVELOPMENT = 2;
	public final static int NEXUS_NAVIGATE_NEWS = 3;
	
	public final static int RESEARCH_NAVIGATE_NEXUS = 1;
	
	public final static int DEVELOPMENT_NAVIGATE_NEXUS = 1;
	
	public final static int NEWS_NAVIGATE_NEXUS = 1;
	
	private WindowFrame display;
	private String current;
	
	public Visual(int width, int height) {
		display = new WindowFrame(width, height) {
			@Override
			public void repaint() {
				super.repaint();
			}
		};
		current = "title";
	}
	
	public void addScreen(Screen in) {
		for(String s : in.getPanels().keySet()) {
			display.reservePanel(in.getName(), s, in.getPanel(s));
		}
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
		  	case TITLE_NAVIGATE_NEXUS: setActive("nexus"); break;
		  	default : break;
		}
	}
	
	private void handleNexus(int event) {
		switch(event) {
			case NEXUS_NAVIGATE_RESEARCH : setActive("research"); break;
			case NEXUS_NAVIGATE_DEVELOPMENT : setActive("development"); break;
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
