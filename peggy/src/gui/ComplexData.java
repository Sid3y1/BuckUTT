package gui;

import java.io.UnsupportedEncodingException;

public class ComplexData {

	public static String[] getLines(String CSV)
	{
		CSV = CSV.replaceAll("\n", "");
		try {
			CSV = new String(CSV.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String[] result = CSV.split("\";\"");
		return result;
	}
	
	public static String[] getData(String CSV)
	{
		try {
			CSV = new String(CSV.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//si juste une ligne, il faut gicler le ; de la fin
		CSV = CSV.replaceAll("\n", "");
		CSV = CSV.replaceAll("^\"", "");
		CSV = CSV.replaceAll("\";$", "");
		
		String[] result = CSV.split("\",\"");
		
		return result;
	}
}
