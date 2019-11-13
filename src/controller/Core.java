package controller;

import model.Model;
import view.Visual;

public class Core {

	private Visual visual;
	private Model model;
	
	public Core() {
		visual = new Visual(600, 400);
	}
	
}
