import controller.Data;

public class test {

	public static void main(String[] args) {
		try {
			Data dat = new Data("/assets/dta/Equipment.dta");
			dat.setTitle("Equipment");
			System.out.println(dat);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
