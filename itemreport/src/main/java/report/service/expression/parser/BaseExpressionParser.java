package report.service.expression.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import report.service.expression.interfaces.IExpressionLog;
import report.service.expression.interfaces.IExpressionLogFormatter;
import report.service.expression.interfaces.IExpressionParser;

public abstract class BaseExpressionParser implements IExpressionParser {

	protected Map<String,Object> context;
	protected IExpressionLog expressionLog;
	protected String result;
	
	
    protected IExpressionLogFormatter expressionFormatter;
    
    public void setExpressionFormatter(IExpressionLogFormatter formatter)
    {
    	this.expressionFormatter=formatter;
    }
   
    
	public String getResult()
	{
		return this.result;
	}
	public BaseExpressionParser()
	{
		this.context=new HashMap<String,Object>();
	}
	
	public void setExpressionLog(IExpressionLog log)
	{
		this.expressionLog=log;
	}
	@Override
	public abstract boolean parse(Word word) throws Exception ;
	@Override
	public void setContext(Map<String, Object> context) {
		if(context!=null)
        {
       	 this.context=context;
  
        }


	}

}
