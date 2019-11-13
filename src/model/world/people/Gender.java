package model.world.people;

import controller.Data;

public class Gender {
	
	private String title;
	private String subject;
	private String pronoun;
	private String possessive;
	
	public Gender(Data dat) {
		title = dat.getString("title");
		subject = dat.getString("subject");
		pronoun = dat.getString("pronoun");
		possessive = dat.getString("possessive");
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getPronoun() {
		return pronoun;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getPossessive() {
		return possessive;
	}
	
}
