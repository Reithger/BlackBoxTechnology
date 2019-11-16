package controller;

import java.io.File;
import java.util.ArrayList;

import model.Model;
import view.Visual;
import view.screen.LocalPanel;
import visual.frame.WindowFrame;

public class Core {

	private Visual visual;
	private Model model;
	
	private final static int width = 840;
	private final static int height = 560;
	
	private final static int SELECT_WIDTH = 300;
	private final static int SELECT_HEIGHT = 500;

	public Core() {
		visual = new Visual(width, height, this);		
		model = null;
	}
	
	public boolean startGame(String name) {
		try {
			if(name.substring(name.length() - 4, name.length()).equals(".dta")) {
				name = name.substring(0, name.length() - 4);
			}
			Data saveFile = new Data(new File("saves/" + name + ".dta"));
			model = new Model(name, saveFile.getDataset(name));
			updateVisualModel();
			visual.setActive("nexus");
			return true;
		}
		catch(Exception e) {
			System.out.println("Failed to generate Model for save file: " + name);
			e.printStackTrace();
			return false;
		}
	}
	
	public void startNewGame() {
		File f = new File("saves/");
		if(f.listFiles() == null) {
			f.mkdir();
		}
		String saveName = "save";
		File[] fs = f.listFiles();
		ArrayList<String> aL = new ArrayList<String>();
		for(File cf : fs) {
			aL.add(cf.getName());
		}
		int counter = 0;
		while(aL.contains(saveName + (counter == 0 ? "" : "_" + counter) + ".dta")) {
			counter++;
		}
		saveName = saveName + (counter == 0 ? "" : ("_" + counter));
		model = new Model(saveName);
		model.exportData().save("saves/" + saveName);
		startGame(saveName);
	}
	
	public void loadGame() {
		WindowFrame disp = new WindowFrame(SELECT_WIDTH, SELECT_HEIGHT);
		File[] fS = new File("saves/").listFiles();
		LocalPanel lP = new LocalPanel(0, 0, SELECT_WIDTH, SELECT_HEIGHT) {
			@Override
			public void clickBehaviour(int event) {
				for(int i = 0; i < fS.length; i++) {
					if(event == i) {
						if(startGame(fS[i].getName())) {
							disp.disposeFrame();
						}
					}
				}
				if(event == -1) {
					disp.disposeFrame();
				}
			}
			
			@Override
			public void keyBehaviour(char event) {
				
			}
		};
		lP.addBackground("back", "/assets/background/back3.png");
		for(int i = 0; i < fS.length; i++) {
			lP.addButtonCustom("file_" + i, 10, disp.getWidth()/2, disp.getHeight() * 2 / 10 + i * disp.getHeight() / 7, disp.getWidth() / 4, disp.getHeight()/10, fS[i].getName(), 2, 2, i);
		}
		lP.addButtonCustom("back", 10, disp.getWidth()/4, disp.getHeight() * 9 / 10, disp.getWidth() / 5, disp.getHeight() / 10, "Back", 1, 1, -1);
		disp.addPanelToScreen(lP);
	}
	
	public void updateVisualModel() {
		visual.updateModel(model.exportData());
	}
	
}
