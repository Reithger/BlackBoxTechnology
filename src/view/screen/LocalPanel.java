package view.screen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;

import controller.Data;
import model.company.development.Factory;
import visual.panel.ElementPanel;

public class LocalPanel extends ElementPanel{

//---  Constructors   -------------------------------------------------------------------------
	
	public LocalPanel(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
//---  Composite Methods   --------------------------------------------------------------------
	
	public void addBackground(String name, String path) {
		addImage(name, 0, 0, 0, ElementPanel.NON_CENTERED, path, 14);
	}
	
	public void addBorderCustom(String name, int priority, int x, int y, int width, int height, boolean centered) {
		String path = "saves/assets/b_" + width + "_" + height + ".png";
		CustomImage newImg = new CustomImage(width, height, BufferedImage.TYPE_INT_ARGB);
		if(newImg.retrieveImage(path) != null) {
			this.addImage(name + "_bord", priority, x, y, centered, path);
			return;
		}
		int across = width / 16;
		int up = height / 16;
		int difX = width - across * 16;
		int difY = height - up * 16;
		if(centered) {
			x -= width / 2;
			y -= height / 2;
		}
		for(int i = 0; i < up; i++) {
			for(int j = 0; j < across; j++) {
				if(j == across / 2) {
					for(int k = 0; k < difX; k++) {
						if(i == 0) {
							newImg.addImage(16 * j + k, 16 * i, "/assets/UI/border/thin_across_top.png");
						}
						else if(i + 1 == up) {
							newImg.addImage(16 * j + k, 16 * i, "/assets/UI/border/thin_across_bottom.png");
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
							newImg.addImage(16 * j, 16 * i + k, "/assets/UI/border/thin_up_right.png");
						}
						else {
							newImg.addImage(16 * j, 16 * i + k, "/assets/UI/border/fill.png");
						}
					}
				}
				
				
				int extX = (j >= across / 2) ? difX : 0;
				int extY = (i >= up / 2) ? difY : 0;
				
				if(i == 0 && j + extX == 0) {
					newImg.addImage(16 * j + extX, 16 * i + extY,"/assets/UI/border/topLeft.png");
				}
				else if(i + 1 == up && j + 1 == across) {
					newImg.addImage(16 * j + extX, 16 * i + extY,"/assets/UI/border/bottomRight.png");
				}
				else if(i == 0 && j + 1 == across) {
					newImg.addImage(16 * j + extX, 16 * i + extY,"/assets/UI/border/topRight.png");
				}
				else if(i + 1 == up && j == 0) {
					newImg.addImage(16 * j + extX, 16 * i + extY,"/assets/UI/border/bottomLeft.png");
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
		this.addImage(name + "_bord", priority, x, y, centered, newImg);
	}
	
	public void addButtonCustom(String name, int priority, int x, int y, int width, int height, String phrase, int scaleButton, int scaleText, int code) {
		addImage(name + "_img", priority, x, y, ElementPanel.CENTERED, "/assets/UI/border.png", scaleButton);
		addTextCustom(name + "_img", priority + 1, x, y, phrase, scaleText);
		addButton(name + "_butt", priority, x, y, width, height, code, ElementPanel.CENTERED);
	}

	public void addTextCustom(String name, int priority, int x, int y, String phrase, int scale){
		for(int i = 0; i < phrase.length(); i++){
			char c = phrase.charAt(i);
			String address = "/assets/Letters/" + getImagePath(c);
			if(phrase.charAt(i) != ' ')
			  addImage(name + "_" + i, priority, x + (i - phrase.length()/2) * scale * 6 + (phrase.length()%2 == 0 ? scale * 3 : 0), y, true, address, scale);
		}
	}
	
	public void addFactoryDecal(String name, int priority, int x, int y, Data dat) {
		if(dat == null) {
			return;
		}
		this.addBorderCustom(name, priority, x,  y, getWidth() / 3, getHeight() * 3 / 5, true);
		addTextCustom(name + "_tex", priority + 5, x, y, "Name: " + dat.getString(Factory.TITLE), 1);
	}
	
	public void removeFactoryDecale(String name) {
		this.removeElement(name + "_tex");
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
