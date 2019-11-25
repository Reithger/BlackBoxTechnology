package controller;

import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import model.Model;
import view.Visual;
import view.screen.LocalPanel;
import visual.frame.WindowFrame;

public class Core {

//---  Constants   ----------------------------------------------------------------------------
	
	private final static int width = 840;
	private final static int height = 560;
	
	private final static int SELECT_WIDTH = 400;
	private final static int SELECT_HEIGHT = 500;
	
//---  Instance Variables   -------------------------------------------------------------------
	
	private Visual visual;
	private Model model;

//---  Constructors   -------------------------------------------------------------------------
	
	public Core() {
		visual = new Visual(width, height, this);		
		model = null;
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	public boolean startGame(String name) {
		try {
			if(name.substring(name.length() - 4, name.length()).equals(".dta")) {
				name = name.substring(0, name.length() - 4);
			}
			Data saveFile = new Data("saves/" + name + ".dta");
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
		if(new File("saves/").listFiles() == null) {
			(new File("saves/")).mkdir();
		}
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
			
			@Override
			public void addBackground(String name, String path) {
				super.addBackground(name, path);
				removeElementPrefixed("money");
			}
		};
		lP.addBackground("back", "/assets/background/back6.png");
		for(int i = 0; i < fS.length; i++) {
			lP.addButtonCustom("file_" + i, 10, SELECT_WIDTH/2, SELECT_HEIGHT * 2 / 10 + i * SELECT_HEIGHT / 7, SELECT_WIDTH / 2, SELECT_HEIGHT/10, fS[i].getName(), 2, i);
		}
		lP.addButtonCustom("back", 10, SELECT_WIDTH / 8, SELECT_HEIGHT * 9 / 10, SELECT_WIDTH / 5, SELECT_HEIGHT / 10, "Back", 2, -1);
		disp.addPanelToScreen(lP);
	}
	
	public void updateVisualModel() {
		visual.updateModel(model.exportData());
	}
	
	public boolean buildEquipment(String factory, String title) {
		return model.getPlayer().getFactories().get(factory).buildEquipment(title);
	}
	
	public boolean upgradeEquipment(String factory, String title) {
		return model.getPlayer().getFactories().get(factory).upgradeEquipment(title);
	}
	
	public void iterate() {
		model.iterate();
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	public Data getReference(String ref) {
		return model.getReference(ref);
	}
	
//---  Mechanics   ----------------------------------------------------------------------------
	
	public static void errorReport(String in) {
		WindowFrame wf = new WindowFrame(300, 300);
		LocalPanel lp = new LocalPanel(0, 0, 300, 300);
		if(in == null)
			in = "";
		lp.addText("te", 0, 0, 0, 300, 300, in, new Font("Serif", Font.BOLD, 8), false, false, true);
		
		wf.addPanelToScreen(lp);
	}
	
}
