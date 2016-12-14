package framework.struts.ext;

import java.util.Date;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import framework.helper.TypeParse;

public class DateConverter extends StrutsTypeConverter {

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		// TODO Auto-generated method stub
		return this.performFallbackConversion(context, values, toClass);
	}

	@Override
	public String convertToString(Map context, Object o) {
		if(o!=null)
		{
			if(o instanceof String[])
			{
				return ((String[])o)[0];
				//return TypeParse.parseString(()o, "yyyy-MM-dd");
			}
			else if(Date.class.isInstance(o))
			{
				return TypeParse.parseString((Date)o, "yyyy-MM-dd");
			}
			else 
			{
				return o.toString();
			}
			
				
				
			
		}
		return "";
	}

}
