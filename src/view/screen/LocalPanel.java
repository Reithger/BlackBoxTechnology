package view.screen;

import java.awt.Color;
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
	
//---  Constants   ----------------------------------------------------------------------------
	
	public final static int ANIMATION_RATE = 10;

//---  Instance Variables   -------------------------------------------------------------------
	
	private LinkedList<String> decayObject;
	private HashMap<String, Integer> decayTimer;
	private double money;
	
//---  Constructors   -------------------------------------------------------------------------
	
	public LocalPanel(int x, int y, int width, int height) {
		super(x, y, width, height);
		decayObject = new LinkedList<String>();
		decayTimer = new HashMap<String, Integer>();
	}
	
//---  Composite Methods   --------------------------------------------------------------------
	
	public void addBackground(String name) {
		String path = "/assets/background/back6.png";
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
							newImg.addImage(16 * j + k, 16 * i, "/assets/UI/border/thin_across_top.png", true);
						}
						else if(i + 1 == up) {
							newImg.addImage(16 * j + k, 16 * i + difY, "/assets/UI/border/thin_across_bottom.png", true);
						}
						else {
							newImg.addImage(16 * j + k, 16 * i, "/assets/UI/border/fill.png", true);
						}
					}
				}
				if(i == up / 2) {
					for(int k = 0; k < difY; k++) {
						if(j == 0) {
							newImg.addImage(16 * j, 16 * i + k, "/assets/UI/border/thin_up_left.png", true);
						}
						else if(j + 1 == across) {
							newImg.addImage(16 * j + difX, 16 * i + k, "/assets/UI/border/thin_up_right.png", true);
						}
						else {
							newImg.addImage(16 * j, 16 * i + k, "/assets/UI/border/fill.png", true);
						}
					}
				}
				
				int extX = (j >= across / 2) ? difX : 0;
				int extY = (i >= up / 2) ? difY : 0;
				
				if(i == 0 && j == 0) {
					newImg.addImage(16 * j + extX, 16 * i + extY, filledCorner ? "/assets/UI/border/topLeftFill.png" : "/assets/UI/border/topLeft.png", true);
				}
				else if(i + 1 == up && j + 1 == across) {
					newImg.addImage(16 * j + extX, 16 * i + extY,filledCorner ? "/assets/UI/border/bottomRightFill.png" : "/assets/UI/border/bottomRight.png", true);
				}
				else if(i == 0 && j + 1 == across) {
					newImg.addImage(16 * j + extX, 16 * i + extY,filledCorner ? "/assets/UI/border/topRightFill.png" : "/assets/UI/border/topRight.png", true);
				}
				else if(i + 1 == up && j == 0) {
					newImg.addImage(16 * j + extX, 16 * i + extY,filledCorner ? "/assets/UI/border/bottomLeftFill.png" : "/assets/UI/border/bottomLeft.png", true);
				}
				else if(i == 0) {
					newImg.addImage(16 * j + extX, 16 * i + extY,"/assets/UI/border/across_top.png", true);
				}
				else if(i + 1 == up) {
					newImg.addImage(16 * j + extX, 16 * i + extY,"/assets/UI/border/across_bottom.png", true);
				}
				else if(j == 0) {
					newImg.addImage(16 * j + extX, 16 * i + extY,"/assets/UI/border/vertical_left.png", true);
				}
				else if(j + 1 == across) {
					newImg.addImage(16 * j + extX, 16 * i + extY,"/assets/UI/border/vertical_right.png", true);
				}
				else {
					newImg.addImage(16 * j + extX, 16 * i + extY,"/assets/UI/border/fill.png", true);
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
	
	public void addBorderCustomBacking(String name, int priority, int x, int y, int width, int height, int size, boolean centered, boolean filledCorner) {
		width /= size;
		height /= size;
		String usedPath = "decal/b_b_" + size + "_" + width + "_" + height + ".png";
		CustomImage newImg = new CustomImage(width, height, BufferedImage.TYPE_INT_ARGB);
		if(newImg.retrieveImage(usedPath) != null) {
			this.addImage(name + "_bord", priority, x, y, centered, usedPath, size);
			return;
		}
		
		this.addBorderCustom(name, priority, x, y, width, height, 1, centered, filledCorner);
		
		String path = "decal/b_" + 1 + "_" + width + "_" + height + ".png";
		BufferedImage img = newImg.retrieveImage(path);
		BufferedImage betw = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		betw.createGraphics().drawImage(img, 0, 0, width, height, null);
		CustomImage out = new CustomImage(width, height, BufferedImage.TYPE_INT_ARGB);
		out.addImage(0, 0, path, false);
		out.addImage(0, 0, "/assets/UI/border/topLeftBack.png", true);
		out.addImage(width - 16, 0, "/assets/UI/border/topRightBack.png", true);
		out.addImage(0, height - 16, "/assets/UI/border/bottomLeftBack.png", true);
		out.addImage(width - 16, height - 16, "/assets/UI/border/bottomRightBack.png", true);
		try {
			ImageIO.write(out, "png", new File(usedPath));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		this.addImage(name + "_bord", priority, x, y, centered, out, size);
	}
	
	public void addButtonCustom(String name, int priority, int x, int y, int width, int height, String phrase, int scaleText, int code) {
		addBorderCustomBacking(name + "_bord", priority, x, y, width, height, 1, true, false);
		addTextCustom(name + "_tex", priority + 1, x, y, phrase, scaleText);
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
		decayObject.addLast(name + "_tex_" + System.currentTimeMillis());
		decayTimer.put(name + "_tex_" + System.currentTimeMillis(), time);
		addTextCustom(name + "_tex", priority, x, y, phrase, scale);
	}
	
	public void addFactoryDecal(String name, int priority, int x, int y, int width, int height, Data dat, int cycle) {
		if(dat == null) {
			return;
		}
		addBorderCustomBacking(name, priority, x,  y, width, height, 1, true, false);
		
		addTextCustom(name + "_nom", priority + 5, x, y - height / 4, dat.getString(Factory.TITLE), 2);
		addTextCustom(name + "_equi", priority + 5, x, y + height / 4, "Equipment: " + dat.getDatasetArray(Factory.EQUIPMENT).length, 2);
		addTextCustom(name + "_empl", priority + 5, x, y + height * 3 / 8, "Employees: " + dat.getDatasetArray(Factory.EMPLOYEES).length, 2);
		
		String imgPath = dat.getStringArray(Factory.IMAGE)[cycle % (ANIMATION_RATE * dat.getStringArray(Factory.IMAGE).length) / ANIMATION_RATE];
		
		addImage(name + "_fac", priority + 5, x, y, true, imgPath, 4);	
	}
	
	public void addEquipmentDecal(String name, int priority, int x, int y, int width, int height, Data dat, int code, int cycle) {
		addBorderCustomBacking(name, priority, x, y, width, height, 1, true, false);

		addTextCustom(name + "_nom", priority + 5, x, y + height / 4, dat.getString(Equipment.TYPE), 2);
		addTextCustom(name + "_lev", priority + 5, x, y + height * 3 / 8, "Level: " + dat.getInt(Equipment.LEVEL), 2);
		addButton(name + "_but", priority, x, y, width, height, code, true);
		String imgPath = dat.getStringArray(Equipment.IMAGE)[cycle % (ANIMATION_RATE * dat.getStringArray(Equipment.IMAGE).length) / ANIMATION_RATE];

		addImage(name + "_fac", priority + 5, x, y - height * 3 / 16, true, imgPath, 2);
	}
	
	public void addEquipmentInfo(String name, int priority, int x, int y, int width, int height, Data dat, int cycle, int code) {
		String imgPath = dat.getStringArray(Equipment.IMAGE)[cycle % (ANIMATION_RATE * dat.getStringArray(Equipment.IMAGE).length) / ANIMATION_RATE];
		addBorderCustomBacking(name, priority, x, y, width, height, 2, true, false);
		int level = dat.getInt(Equipment.LEVEL);
		addTextCustom(name + "_nom", priority + 1, x, y - height * 7 / 16, "Name: " + dat.getString(Equipment.NAME), 2);
		addTextCustom(name + "_typ", priority + 1, x, y - height * 6 / 16, dat.getString(Equipment.TYPE) + ": " + level, 2);
		addTextCustom(name + "_makes_title", priority + 1, x, y - height * 5 / 16, "Producing: ", 2);

		addTextCustom(name + "_makes", priority + 1, x, y - height * 17 / 64, dat.getString(Equipment.PRODUCT), 2);
		
		addImage(name + "_fac", priority + 1 + 5, x, y - height * 9 / 64, true, imgPath, 3);
		
		addTextCustom(name + "_produc", priority + 1, x, y, "Production: " + dat.getDoubleArray(Equipment.PRODUCTION)[level], 2);
		addTextCustom(name + "_mainten", priority + 1, x, y + height * 1 / 16, "Maintenance: " + dat.getDoubleArray(Equipment.MAINTENANCE)[level], 2);
		addTextCustom(name + "_person", priority + 1, x, y + height * 2 / 16, "Personnel: " + dat.getStringArray(Equipment.PERSONNEL).length, 2);

		if(level + 1 < dat.getDoubleArray(Equipment.COST).length) {
			addButtonCustom(name + "_upg", priority + 1, x, y + height * 4 / 16, width * 2 / 3, height * 2 / 16, "Upgrade", 2, code);
			addTextCustom(name + "_cost", priority + 1, x, y + height * 6 / 16, "Cost: " + dat.getDoubleArray(Equipment.COST)[level + 1], 2);
		}
	}

	public void addBuildableInfo(String name, int priority, int x, int y, int width, int height, Data dat, int code, int cycle) {
		String imgPath = dat.getStringArray(Equipment.IMAGE)[cycle % (ANIMATION_RATE * dat.getStringArray(Equipment.IMAGE).length) / ANIMATION_RATE];
		addBorderCustomBacking(name + "_bord", priority, x, y, width, height, 1, true, true);
		addTextCustom(name + "_nom", priority + 10, x, y - height / 4, "Type: " + dat.getString(Equipment.TYPE), 2);
		addImage(name + "_img", priority + 5, x, y + height / 8, true, imgPath, 2);
		addButton(name + "_but", 10, x, y, width, height, code, true);
	}
	
	public void addMoney(String name, int priority, int x, int y, int width, int height) {
		if(money != Screen.getMoney()) {
			removeElementPrefixed("money_tex");
			money = Screen.getMoney();
		}
		addButtonCustom(name, priority, x, y, width, height, "Money: " + money, 2, -1);
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
				decayTimer.remove(decayObject.pollFirst());
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
		
		public void addImage(int x, int y, String path, boolean transp) {
			BufferedImage img = retrieveImage(path);
			for(int i = 0; i < img.getWidth(); i++) {
				for(int j = 0; j < img.getHeight(); j++) {
					this.setRGB(x + i, y + j, (img.getRGB(i, j) >> 24 == 0x00 && !transp) ? new Color(255, 255, 255).getRGB() : img.getRGB(i, j));	
				}
			}
		}
		
	}
}
