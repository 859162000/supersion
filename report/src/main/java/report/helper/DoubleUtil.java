package report.helper;

import java.text.DecimalFormat;
import java.text.ParseException;


public class DoubleUtil {
 
	
	public static final String format(Double d,int precise)
	{
		return format(d,precise,false);
	}
	public static final String format(Double d,int precise,boolean isThousandGroup)
	{
		String format = "0";
		int digital = 1;
		if(precise >= 0){
			digital = precise;
		}
		for(int i=0;i<digital;i++){
			if(i == 0){
				format += ".";
			}
			format += "0";
		}
		DecimalFormat decimalFormat = new DecimalFormat(format);
		if(isThousandGroup)
		{
			decimalFormat.setGroupingUsed(true);
			decimalFormat.setGroupingSize(3);
		}
		
		
				
		return decimalFormat.format(d);
	}
	public static final Double parse(String str) 
	{
		try
		{
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setGroupingUsed(true);
			decimalFormat.setGroupingSize(3);
			
					
			return decimalFormat.parse(str).doubleValue();
		}
		catch(Exception ex)
		{
			return Double.MAX_VALUE;
		}
		
	}
	public static void main(String[] args) throws ParseException {
		double i=356866.26566;
		System.out.println(format(i,2));
		System.out.println(parse("3665663.56336"));
	}
}
