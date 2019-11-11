import java.io.File;
import java.util.Arrays;

public class test {

	public static void main(String[] args) {
		try {
			Data dat = new Data(new File("Equipment.dta"));
			dat.setTitle("Equipment");
			dat.save("Equipment2.dta");
			Data dat2 = new Data(new File("Equipment2.dta"));
			dat2.setTitle("Second");
			System.out.println(dat2);
			dat2.addData("copy", dat);
			dat2.save("Equipment3.dta");
			System.out.println(dat2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
