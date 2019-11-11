package model.mechanics;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class Data {
	
	private final static String INT = "i";
	private final static String INT_ARR = "i*";
	private final static String DBL = "d";
	private final static String DBL_ARR = "d*";
	private final static String STR = "s";
	private final static String STR_ARR = "s*";
	private final static String DAT = "D";
	
	private final static String SEPARATOR = " : ";
	private final static char DAT_END = ';';
	
	
//---  Instance Variables   -------------------------------------------------------------------

	private String title;
	
	private HashMap<String, Object> data;
	
	private HashMap<String, String> types;
	
//---  Constructors   -------------------------------------------------------------------------
	
	public Data(File f) throws Exception{
		data = new HashMap<String, Object>();
		types = new HashMap<String, String>();
		RandomAccessFile raf = new RandomAccessFile(f, "r");
		String line = raf.readLine();
		LinkedList<Data> next = new LinkedList<Data>();
		while(line != null) {
		  String[] details = line.split(SEPARATOR);
		  String name = cleanString(details[0].trim());
		  if(details.length <= 1) {
			  if(name.charAt(name.length()-1) == DAT_END && next != null) {
				  Data out = next.pollLast();
				  if(next.size() == 0) {
					  this.addData(out);
				  }
				  else {
					  next.peekLast().addData(out);
				  }
			  }
			  line = raf.readLine();
			  continue;
		  }
		  switch(details[1]) {
			  case INT:
				  if(next.size() == 0) {
					  this.addInt(Integer.parseInt(details[2]), name);
				  }
				  else {
					  next.peekLast().addInt(Integer.parseInt(details[2]), name);
				  }
				  break;
			  case INT_ARR:
				  String[] workInt = details[2].replaceAll("[^\\d,]", "").split(",");
				  int[] outInt = new int[workInt.length];
				  for(int i = 0; i < workInt.length; i++) {
					  outInt[i] = Integer.parseInt(workInt[i]);
				  }
				  if(next.size() == 0) {
					  this.addIntArray(outInt, name);
				  }
				  else {
					  next.peekLast().addIntArray(outInt, name);
				  }
				  break;
			  case DBL:
				  if(next.size() == 0) {
					  this.addDouble(Double.parseDouble(details[2]), name);
				  }
				  else {
					  next.peekLast().addDouble(Double.parseDouble(details[2]), name);
				  }
				  break;
			  case DBL_ARR:
				  String[] workDbl = details[2].replaceAll("[^\\d,]", "").split(",");
				  double[] outDbl = new double[workDbl.length];
				  for(int i = 0; i < workDbl.length; i++) {
					  outDbl[i] = Double.parseDouble(workDbl[i]);
				  }
				  if(next.size() == 0) {
					  this.addDoubleArray(outDbl, name);
				  }
				  else {
					  next.peekLast().addDoubleArray(outDbl, name);
				  }
				  break;
			  case STR:
				  String use = cleanString(details[2]);
				  if(next.size() == 0) {
					  this.addString(use, name);
				  }
				  else {
					  next.peekLast().addString(use, name);
				  }
				  break;
			  case STR_ARR:
				  String[] workStr = cleanString(details[2].replaceAll("[\\[\\]]", "")).split(" ");
				  if(next.size() == 0) {
					  this.addStringArray(workStr, name);
				  }
				  else {
					  next.peekLast().addStringArray(workStr, name);
				  }
				  break;
			  case DAT:
				  next.add(new Data());
				  next.peekLast().setTitle(name);
				  break;
			  default:
				  System.out.println("Unrecognized data type: " + details[1]);
				  break;
		  }
		  line = raf.readLine();
		}
		raf.close();
	}
	
	public Data(String name) {
		data = new HashMap<String, Object>();
		types = new HashMap<String, String>();
		setTitle(name);
	}
	
	public Data() {
		data = new HashMap<String, Object>();
		types = new HashMap<String, String>();
	}
	
//---  Operations   ---------------------------------------------------------------------------
	
	
	
	public void save(String name) {
		try {
			if(!(name.substring(name.length() - 4)).equals(".dta")) {
				name += ".dta";
			}
			File f = new File(name);
			f.delete();
			RandomAccessFile raf = new RandomAccessFile(f, "rw");
			this.save(raf, 1);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void save(RandomAccessFile raf, int depth) throws Exception{
		String fbuffer = "";
		for(int i = 0; i < depth - 1; i++) {
			fbuffer += "\t";
		}
		raf.writeBytes(fbuffer + getTitle() + SEPARATOR + DAT + SEPARATOR + "{\n");
		String buffer = fbuffer + "\t";
		for(String s : data.keySet()) {
			switch(types.get(s)) {
				case DAT :
					this.getDataset(s).save(raf, depth + 1);
					break;
				case INT : 
					raf.writeBytes(buffer + "\"" + s + "\"" +  SEPARATOR + INT + SEPARATOR + this.getInt(s) + ",\n");
					break;
				case INT_ARR :
					raf.writeBytes(buffer + "\"" + s + "\"" +  SEPARATOR + INT_ARR + SEPARATOR + Arrays.toString(this.getIntArray(s)) + ",\n");
					break;
				case DBL :
					raf.writeBytes(buffer + "\"" + s + "\"" +  SEPARATOR + DBL + SEPARATOR + this.getDouble(s) + ",\n");
					break;
				case DBL_ARR :
					raf.writeBytes(buffer + "\"" + s + "\"" +  SEPARATOR + DBL_ARR + SEPARATOR + Arrays.toString(this.getDoubleArray(s)) + ",\n");
					break;
				case STR :
					raf.writeBytes(buffer + "\"" + s + "\"" +  SEPARATOR + STR + SEPARATOR + "\"" + this.getString(s).replaceAll("\"",  "\\\\\"") + "\",\n");
					break;
				case STR_ARR :
					String[] out = this.getStringArray(s);
					for(int i = 0; i < out.length; i++) {
						out[i] = "\"" + out[i].replaceAll("\"", "\\\\\"") + "\"";
					}
					raf.writeBytes(buffer + "\"" + s + "\"" +  SEPARATOR + STR_ARR + SEPARATOR + Arrays.toString(out) + ",\n");
					break;
				default :
					break;
			}
		}
		if(depth == 0) {
			raf.writeBytes(buffer + "};\n");
			raf.close();
		}
		else {
			raf.writeBytes(fbuffer + "};\n");
		}
	}

//---  Setter Methods   -----------------------------------------------------------------------
	
	public void setTitle(String name) {
		title = name;
	}
	
//---  Adder Methods   ------------------------------------------------------------------------
	
	public void addData(Data dat) {
		data.put(dat.getTitle(), dat);
		types.put(dat.getTitle(), DAT);
	}
	
	public void addData(String name, Data dat) {
		data.put(name, dat);
		types.put(name, DAT);
	}
	
	public void addString(String dat, String name) {
		data.put(name, dat);
		types.put(name, STR);
	}
	
	public void addStringArray(String[] dat, String name) {
		data.put(name, dat);
		types.put(name, STR_ARR);
	}
	
	public void addInt(int dat, String name) {
		data.put(name, dat);
		types.put(name, INT);
	}
	
	public void addIntArray(int[] dat, String name) {
		data.put(name, dat);
		types.put(name, INT_ARR);
	}
	
	public void addDouble(double dat, String name) {
		data.put(name, dat);
		types.put(name, DBL);
	}
	
	public void addDoubleArray(double[] dat, String name) {
		data.put(name, dat);
		types.put(name, DBL_ARR);
	}
	
//---  Remover Methods   ----------------------------------------------------------------------
	
	public void removeData(String name) {
		data.remove(name);
	}
	
//---  Getter Methods   -----------------------------------------------------------------------
	
	public String getTitle() {
		return title;
	}
	
	public HashMap<String, Object> retrieve(){
		return data;
	}
	
	public Data getDataset(String name) {
		return (Data)data.get(name);
	}
	
	public String[] getStringArray(String name) {
		return (String[])data.get(name);
	}
	
	public String getString(String name) {
		return (String)data.get(name);
	}
	
	public int getInt(String name) {
		return (int)data.get(name);
	}
	
	public int[] getIntArray(String name) {
		return(int[])data.get(name);
	}
	
	public double getDouble(String name) {
		return (double)data.get(name);
	}
	
	public double[] getDoubleArray(String name) {
		return (double[])data.get(name);
	}
	
//---  Mechanics   ----------------------------------------------------------------------------
	
	@Override
	public String toString() {
		return toString(0);
	}
	
	private String toString(int depth){
		StringBuilder out = new StringBuilder();
		String fbuffer = "";
		for(int i = 0; i < depth ; i++) {
			fbuffer += "\t";
		}
		out.append(fbuffer + getTitle() + SEPARATOR + DAT + SEPARATOR + "{\n");
		String buffer = fbuffer + "\t";
		for(String s : data.keySet()) {
			switch(types.get(s)) {
				case DAT :
					out.append(this.getDataset(s).toString(depth + 1));
					break;
				case INT : 
					out.append(buffer + "\"" + s + "\"" +  SEPARATOR + INT + SEPARATOR + this.getInt(s) + ",\n");
					break;
				case INT_ARR :
					out.append(buffer + "\"" + s + "\"" +  SEPARATOR + INT_ARR + SEPARATOR + Arrays.toString(this.getIntArray(s)) + ",\n");
					break;
				case DBL :
					out.append(buffer + "\"" + s + "\"" +  SEPARATOR + DBL + SEPARATOR + this.getDouble(s) + ",\n");
					break;
				case DBL_ARR :
					out.append(buffer + "\"" + s + "\"" +  SEPARATOR + DBL_ARR + SEPARATOR + Arrays.toString(this.getDoubleArray(s)) + ",\n");
					break;
				case STR :
					out.append(buffer + "\"" + s + "\"" +  SEPARATOR + STR + SEPARATOR + "\"" + this.getString(s).replaceAll("\"",  "\\\\\"") + "\",\n");
					break;
				case STR_ARR :
					String[] arr = this.getStringArray(s);
					for(int i = 0; i < arr.length; i++) {
						arr[i] = "\"" + arr[i].replaceAll("\"", "\\\\\"") + "\"";
					}
					out.append(buffer + "\"" + s + "\"" +  SEPARATOR + STR_ARR + SEPARATOR + Arrays.toString(arr) + ",\n");
					break;
				default :
					break;
			}
		}
		out.append(fbuffer + "};\n");
		return out.toString();
	}
	
	public String cleanString(String in) {
		return in.replaceAll("(?<![\\\\])\",", "").replaceAll("(?<![\\\\])\"", "").replaceAll("\\\\\"", "\"");
	}

}
