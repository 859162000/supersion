package report.service.expression.converter;

import java.util.HashMap;
import java.util.Map;

import report.service.expression.interfaces.IExpressionConverter;


public abstract class BaseExpressionConverter implements IExpressionConverter {

	
	protected Map<String,Object> context;
	protected String result;
	
	public BaseExpressionConverter()
	{
		context=new HashMap<String,Object>();
		result=null;
		
		
	}
	
	
	public abstract boolean convert(String expression) throws Exception ;

	public  String getResult()
	{
		return result;
	}

	public void setContext(Map<String, Object> context) {
         if(context!=null)
         {
        	 this.context=context;
   
         }

	}

}
