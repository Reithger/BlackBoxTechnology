import java.io.File;

import controller.Data;

public class test {

	public static void main(String[] args) {
		try {
			Data dat = new Data(new File("Equipment.dta"));
			dat.setTitle("Equipment");
			System.out.println(dat);
			dat.save("Equipment2.dta");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
