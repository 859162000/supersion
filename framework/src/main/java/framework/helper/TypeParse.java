package framework.helper;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.math.BigInteger;
import java.sql.Time;

import org.apache.commons.lang.xwork.StringUtils;

public class TypeParse {
	
	private static String[] inputDateFormatList = new String[]{"yyyy-MM-dd","yyyy/MM/dd","yyyyMMdd","EEE MMM dd HH:mm:ss 'UTC' yyyy"};
	private static String[] inputTimestampFormatList = new String[]{"yyyy-MM-dd HH:mm:ss,SSS","yyyy/MM/dd HH:mm:ss,SSS","yyyy-MM-dd HH:mm:ss","yyyy/MM/dd HH:mm:ss","yyyy-MM-dd","yyyy/MM/dd"};
	
	public static String parseString(Date date,String format){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(date);
	}
	
	public static String parseString(String s){
		if(StringUtils.isBlank(s)){
			return null;
		}
		else{
			return s.trim();
		}
	}
	
	public static Integer parseInt(String s){
		try{
			if(StringUtils.isBlank(s)){
				return null;
			}
			else{
				return Integer.parseInt(s);
			}
		}
		catch(Exception ex){
			return Integer.MAX_VALUE;
		}
	}
	
	public static Long parseLong(String s){
		try{
			if(StringUtils.isBlank(s)){
				return null;
			}
			else{
				return Long.parseLong(s);
			}
		}
		catch(Exception ex){
			return Long.MAX_VALUE;
		}
	}
	
	public static Double parseDouble(String s){
		try{
			if(StringUtils.isBlank(s)){
				return null;
			}
			else{
				return Double.parseDouble(s);
			}
		}
		catch(Exception ex){
			return Double.MAX_VALUE;
		}
	}
	
	public static Time parseTime(String s){
		try{
			if(StringUtils.isBlank(s)){
				return null;
			}
			else{
				return Time.valueOf(s);
			}
		}
		catch(Exception ex){
			return new Time(0);
		}
	}
	
	public static Float parseFloat(String s){
		try{
			if(StringUtils.isBlank(s)){
				return null;
			}
			else{
				return Float.valueOf(s);
			}
		}
		catch(Exception ex){
			return Float.MIN_VALUE;
		}
	}
	
	public static BigInteger parseBigInteger(String s){
		try{
			if(StringUtils.isBlank(s)){
				return new BigInteger("0",10);
			}
			else{
				return new BigInteger(s,10);
			}
		}
		catch(Exception ex){
			return new BigInteger("0",10);
		}
	}
	
	public static BigDecimal parseBigDecimal(String s){
		try{
			if(StringUtils.isBlank(s)){
				return null;
			}
			else{
				return new BigDecimal(s);
			}
		}
		catch(Exception ex){
			return new BigDecimal(Integer.MAX_VALUE);
		}
	}
	
	public static Date parseDate(String s){
		if(StringUtils.isBlank(s)){
			return null;
		}
		try{
			DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.US); 
			return format.parse(s);
		}
		catch(Exception ex){
			for(int i=0;i<inputDateFormatList.length;i++){
				String inputDateFormat = inputDateFormatList[i];
				try{
					DateFormat format = new SimpleDateFormat(inputDateFormat); 
					return format.parse(s);
				}
				catch(Exception e){
				}
			}
		}
		
		return maxDate();
	}
	
	public static Date maxDate(){
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
			return format.parse("9999-12-31");
		} catch (Exception e) {
		}
		return null;
	}
	
	public static Timestamp parseTimestamp(String s){
		if(StringUtils.isBlank(s)){
			return null;
		}
		for(int i=0;i<inputTimestampFormatList.length;i++){
			String inputTimestampFormat = inputTimestampFormatList[i];
			try{
				DateFormat format = new SimpleDateFormat(inputTimestampFormat); 
				return new Timestamp(format.parse(s).getTime());
			}
			catch(Exception e){
			}
		}
		return maxTimestamp();
	}
	
	public static Timestamp maxTimestamp(){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS"); 
		try {
			return new Timestamp(format.parse("9999-12-31 23:59:59,999").getTime());
		} 
		catch (Exception e) {
		}
		return null;
	}
	
	public static byte[] getByteData(byte[] byteData, File byteFile){
		FileInputStream fileInputStream = null;
		try{
			if(byteData != null || byteFile == null){
				return byteData;
			}
			else{
				 fileInputStream = new FileInputStream (byteFile);
				 int len=fileInputStream.available();
		         byteData = new byte[len];
		         fileInputStream.read(byteData);
		         fileInputStream.close();
		         return byteData;
			}
		}
		catch(Exception ex){
			if(fileInputStream != null){
				try {
					fileInputStream.close();
				} 
				catch (Exception ex1) {
					ExceptionLog.CreateLog(ex1);
				}
			}
			return null;
		}
	}
	
	public static String getFileName(String strFileName, File byteFile){
		if(!StringUtils.isBlank(strFileName) || byteFile == null){
			return strFileName;
		}
		else{
	         return byteFile.getName();
		}
	}
}
