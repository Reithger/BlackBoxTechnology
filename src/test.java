import java.io.File;

import controller.Data;

public class test {

	public static void main(String[] args) {
		try {
			Data dat = new Data(new File("dta/Equipment.dta"));
			dat.setTitle("Equipment");
			System.out.println(dat);
			
			int rows = 2;
			int columns = 3;
			for(double i = 0; i < 8; i++) {
				double across = ((double)i % columns - columns/2) / (columns - 1);
				double up = (double)((int)(i / columns) - rows / 2) / (rows - 1);
				System.out.println(across + " " + up);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
