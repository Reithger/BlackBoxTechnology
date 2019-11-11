package model.world.errata;

public class Time {

	private int year;
	private int season;
	private int day;
	
	public Time() {
		year = 0;
		season = 0;
		day = 0;
	}
	
	public void setYear(int in) {
		year = in;
	}
	
	public void setSeason(int in) {
		season = in % 4;
		if(in > 4) {
			setYear(getYear() + 1);
		}
	}
	
	public void setDay(int in) {
		day = in % 30;
		if(in > 30) {
			setSeason(getSeason() + 1);
		}
	}
	
	public int getYear() {
		return year;
	}
	
	public int getSeason() {
		return season;
	}
	
	public int getDay() {
		return day;
	}
	
}
