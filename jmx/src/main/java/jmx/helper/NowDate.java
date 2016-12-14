package jmx.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NowDate {
	
	public static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	
	private static SimpleDateFormat sf_yyyy_MM_dd_HH_mm_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String date(){
		Date date = new Date();
		return sf.format(date);
	}
	
	public static String dateStr(){
		Date date = new Date();
		return sf_yyyy_MM_dd_HH_mm_SS.format(date);
	}
	
	
	public static void main(String[] args){
		System.out.println(dateStr().substring(0,dateStr().length()-1)+"0");
	}
	
}
