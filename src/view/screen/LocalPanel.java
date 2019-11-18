package view.screen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import controller.Data;
import model.company.development.Equipment;
import model.company.development.Factory;
import visual.panel.ElementPanel;

public class LocalPanel extends ElementPanel{
	
	public final static int ANIMATION_RATE = 10;

	private LinkedList<String> decayObject;
	private HashMap<String, Integer> decayTimer;
	
//---  Constructors   -------------------------------------------------------------------------
	
	public LocalPanel(int x, int y, int width, int height) {
		super(x, y, width, height);
		decayObject = new LinkedList<String>();
		decayTimer = new HashMap<String, Integer>();
	}
	
//---  Composite Methods   --------------------------------------------------------------------
	
	public void addBackground(String name, String path) {
		addImage(name, 0, 0, 0, ElementPanel.NON_CENTERED, path, 14);
		addBorderCustom("border", 100, 0, 0, getWidth(), getHeight(), 2, false, true);
	}
	
	public void addBorderCustom(String name, int priority, int x, int y, int width, int height, int size, boolean centered, boolean filledCorner) {
		if(new File("decal/").listFiles() == null) {
			(new File("decal/")).mkdir();
		}
		String path = "decal/b_" + size + "_" + width + "_" + height + ".png";
		width /= size;
		height /= size;
		CustomImage newImg = new CustomImage(width, height, BufferedImage.TYPE_INT_ARGB);
		if(newImg.retrieveImage(path) != null) {
			this.addImage(name + "_bord", priority, x, y, centered, path, size);
			return;
		}
		int across = width / 16;
		int up = height / 16;
		int difX = width - across * 16;
		int difY = height - up * 16;
		for(int i = 0; i < up; i++) {
			for(int j = 0; j < across; j++) {
				if(j == across / 2) {
					for(int k = 0; k < difX; k++) {
						if(i == 0) {
							newImg.addImage(16 * j + k, 16 * i, "/assets/UI/border/thin_across_top.png");
						}
						else if(i + 1 == up) {
							newImg.addImage(16 * j + k, 16 * i + difY, "/assets/UI/border/thin_across_bottom.png");
						}
						else {
							newImg.addImage(16 * j + k, 16 * i, "/assets/UI/border/fill.png");
						}
					}
				}
				if(i == up / 2) {
					for(int k = 0; k < difY; k++) {
						if(j == 0) {
							newImg.addImage(16 * j, 16 * i + k, "/assets/UI/border/thin_up_left.png");
						}
						else if(j + 1 == across) {
							newImg.addImage(16 * j + difX, 16 * i + k, "/assets/UI/border/thin_up_right.png");
						}
						else {
							newImg.addImage(16 * j, 16 * i + k, "/assets/UI/border/fill.png");
						}
					}
				}
				
				int extX = (j >= across / 2) ? difX : 0;
				int extY = (i >= up / 2) ? difY : 0;
				
				if(i == 0 && j == 0) {
					newImg.addImage(16 * j + extX, 16 * i + extY, filledCorner ? "/assets/UI/border/topLeftFill.png" : "/assets/UI/border/topLeft.png");
				}
				else if(i + 1 == up && j + 1 == across) {
					newImg.addImage(16 * j + extX, 16 * i + extY,filledCorner ? "/assets/UI/border/bottomRightFill.png" : "/assets/UI/border/bottomRight.png");
				}
				else if(i == 0 && j + 1 == across) {
					newImg.addImage(16 * j + extX, 16 * i + extY,filledCorner ? "/assets/UI/border/topRightFill.png" : "/assets/UI/border/topRight.png");
				}
				else if(i + 1 == up && j == 0) {
					newImg.addImage(16 * j + extX, 16 * i + extY,filledCorner ? "/assets/UI/border/bottomLeftFill.png" : "/assets/UI/border/bottomLeft.png");
				}
				else if(i == 0) {
					newImg.addImage(16 * j + extX, 16 * i + extY,"/assets/UI/border/across_top.png");
				}
				else if(i + 1 == up) {
					newImg.addImage(16 * j + extX, 16 * i + extY,"/assets/UI/border/across_bottom.png");
				}
				else if(j == 0) {
					newImg.addImage(16 * j + extX, 16 * i + extY,"/assets/UI/border/vertical_left.png");
				}
				else if(j + 1 == across) {
					newImg.addImage(16 * j + extX, 16 * i + extY,"/assets/UI/border/vertical_right.png");
				}
				else {
					newImg.addImage(16 * j + extX, 16 * i + extY,"/assets/UI/border/fill.png");
				}
			}
		}
		try {
			ImageIO.write(newImg, "png", new File(path));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		this.addImage(name + "_bord", priority, x, y, centered, newImg, size);
	}
	
	public void addButtonCustom(String name, int priority, int x, int y, int width, int height, String phrase, int scaleText, int code) {
		addBorderCustom(name + "_bord", priority, x, y, width, height, 1, true, false);
		addTextCustom(name + "_img", priority + 1, x, y, phrase, scaleText);
		addButton(name + "_butt", priority, x, y, width, height, code, ElementPanel.CENTERED);
	}

	public void addTextCustom(String name, int priority, int x, int y, String phrase, int scale){
		for(int i = 0; i < phrase.length(); i++){
			char c = phrase.toLowerCase().charAt(i);
			String address = "/assets/Letters/" + getImagePath(c);
			if(phrase.charAt(i) != ' ')
			  addImage(name + "_" + i, priority, x + (i - phrase.length()/2) * scale * 6 + (phrase.length()%2 == 0 ? scale * 3 : 0), y, true, address, scale);
		}
	}
	
	public void addFadingText(String name, int priority, int x, int y, String phrase, int scale, int time) {
		decayObject.add(name + "_tex");
		decayTimer.put(name + "_tex", time);
		addTextCustom(name + "_tex", priority, x, y, phrase, scale);
	}
	
	public void addFactoryDecal(String name, int priority, int x, int y, int width, int height, Data dat, int cycle) {
		if(dat == null) {
			return;
		}
		addBorderCustom(name, priority, x,  y, width, height, 1, true, false);
		
		addTextCustom(name + "_nom", priority + 5, x, y - height / 4, dat.getString(Factory.TITLE), 2);
		addTextCustom(name + "_equi", priority + 5, x, y + height / 4, "Equipment: " + dat.getDatasetArray(Factory.EQUIPMENT).length, 2);
		addTextCustom(name + "_empl", priority + 5, x, y + height * 3 / 8, "Employees: " + dat.getDatasetArray(Factory.EMPLOYEES).length, 2);
		
		String imgPath = dat.getStringArray(Factory.IMAGE)[cycle % (ANIMATION_RATE * dat.getStringArray(Factory.IMAGE).length) / ANIMATION_RATE];
		
		addImage(name + "_fac", priority + 5, x, y, true, imgPath, 4);	
	}
	
	public void addEquipmentDecal(String name, int priority, int x, int y, int width, int height, Data dat, int code, int cycle) {
		addBorderCustom(name, priority, x, y, width, height, 1, true, false);

		addTextCustom(name + "_nom", priority + 5, x, y + height / 4, dat.getString(Equipment.TYPE), 2);
		addTextCustom(name + "_lev", priority + 5, x, y + height * 3 / 8, "Level: " + dat.getInt(Equipment.LEVEL), 2);
		addButton(name + "_but", priority, x, y, width, height, code, true);
		
		String imgPath = dat.getStringArray(Equipment.IMAGE)[cycle % (ANIMATION_RATE * dat.getStringArray(Equipment.IMAGE).length) / ANIMATION_RATE];
		addImage(name + "_fac", priority + 5, x, y - height * 3 / 16, true, imgPath, 2);
	}
	
	public void addEquipmentInfo(String name, int priority, int x, int y, int width, int height, Data dat, int cycle) {
		String imgPath = dat.getStringArray(Equipment.IMAGE)[cycle % (ANIMATION_RATE * dat.getStringArray(Equipment.IMAGE).length) / ANIMATION_RATE];
		
		int level = dat.getInt(Equipment.LEVEL);
		addTextCustom(name + "_nom", priority, x, y - height * 7 / 16, "Name: " + dat.getString(Equipment.NAME), 2);
		addTextCustom(name + "_typ", priority, x, y - height * 6 / 16, dat.getString(Equipment.TYPE) + ": " + level, 2);
		addTextCustom(name + "_makes_title", priority, x, y - height * 5 / 16, "Producing: ", 2);

		addTextCustom(name + "_makes", priority, x, y - height * 17 / 64, dat.getString(Equipment.PRODUCT), 2);
		
		addImage(name + "_fac", priority + 5, x, y - height * 9 / 64, true, imgPath, 3);
		
		addTextCustom(name + "_cost", priority, x, y, "Cost: " + dat.getDoubleArray(Equipment.COST)[level], 2);
		addTextCustom(name + "_produc", priority, x, y + height * 1 / 16, "Production: " + dat.getDoubleArray(Equipment.PRODUCTION)[level], 2);
		addTextCustom(name + "_mainten", priority, x, y + height * 2 / 16, "Maintenance: " + dat.getDoubleArray(Equipment.MAINTENANCE)[level], 2);
		addTextCustom(name + "_person", priority, x, y + height * 3 / 16, "Personnel: " + dat.getStringArray(Equipment.PERSONNEL).length, 2);
	}

	public void addBuildableInfo(String name, int priority, int x, int y, int width, int height, Data dat, int code, int cycle) {
		String imgPath = dat.getStringArray(Equipment.IMAGE)[cycle % (ANIMATION_RATE * dat.getStringArray(Equipment.IMAGE).length) / ANIMATION_RATE];
		addBorderCustom(name + "_bord", priority, x, y, width, height, 1, true, true);
		addTextCustom(name + "_nom", priority, x, y - height / 4, "Type: " + dat.getString(Equipment.TYPE), 2);
		addImage(name + "_img", priority + 5, x, y + height / 8, true, imgPath, 2);
		addButton(name + "_but", 10, x, y, width, height, code, true);
	}
	
//---  Mechanics   ----------------------------------------------------------------------------
	
	public String getImagePath(char c) {
		if("0123456789".indexOf(c) != -1){
			return("Digits/" + c + ".png");
		}
		else if(c == '!'){
			return("SpecialCharacters/" + c + ".png");
		}
		else if(c == ':'){
			return("SpecialCharacters/colon.png");
		}
		else if(c == '?'){
			return("SpecialCharacters/question_mark.png");
		}
		else if(c == '.'){
			return("SpecialCharacters/period.png");
		}
		else if(c == '/'){
			return("SpecialCharacters/forward_slash.png");
		}
		else if(c == '-'){
			return("SpecialCharacters/dash.png");
		}
		else if(c == '_') {
			return("SpecialCharacters/underscore.png");
		}
		else if(c == '('){
			return("SpecialCharacters/open_paren.png");
		}
		else if(c == ')'){
			return("SpecialCharacters/close_paren.png");
		}
		else if(c == '+'){
			return("SpecialCharacters/plus.png");
		}
		else{
			return("Alphabet/" + (c+"").toLowerCase() + ".png");
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(decayObject.peekFirst() != null) {
			int time = decayTimer.get(decayObject.peekFirst());
			if(time > 0) {
				decayTimer.put(decayObject.peekFirst(), time - 1);
			}
			else {
				removeElementPrefixed(decayObject.peekFirst());
				decayTimer.remove(decayObject.pop());
			}
		}
	}
	
	public class CustomImage extends BufferedImage{
		private HashMap<String, BufferedImage> images;
		
		public CustomImage(int width, int height, int imageType) {
			super(width, height, imageType);
			images = new HashMap<String, BufferedImage>();
		}
		
		public BufferedImage retrieveImage(String pathIn) {
			String path = pathIn.replace("\\", "/");
			if(images.get(path) != null) {
				return images.get(path);
			}
			try {
				images.put(path, ImageIO.read(ElementPanel.class.getResource(path.substring(path.indexOf("/")))));
				return images.get(path);
			}
			catch(Exception e) {
				try {
					images.put(path, ImageIO.read(new File(path)));
					return images.get(path);
				}
				catch(Exception e1) {
					return null;
				}
			}
		}
		
		public void addImage(int x, int y, String path) {
			BufferedImage img = retrieveImage(path);
			for(int i = 0; i < img.getWidth(); i++) {
				for(int j = 0; j < img.getHeight(); j++) {
					this.setRGB(x + i, y + j, img.getRGB(i, j));
				}
			}
		}
	}
	
}
