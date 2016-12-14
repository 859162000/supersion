package report.service.expression.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import report.service.expression.interfaces.IExpressionLog;
import report.service.expression.interfaces.IExpressionLogFormatter;
import report.service.expression.interfaces.IWordCal;

public abstract  class BaseWordCal implements IWordCal {
    
	protected abstract Set<String> getWordKey();
	
	@Override
	public boolean cal(Word w) throws Exception {
		Set<String> keyWords=getWordKey();
		
		if(keyWords!=null && keyWords.contains(w.wordType))
		{
			calWordVal(w);
			return true;
		}
		else
			return false;
		
	}
	
	protected abstract void calWordVal(Word w) throws Exception;

	protected Map<String,Object> context;
	protected IExpressionLog expressionLog;
	protected String result;
	
	
    protected IExpressionLogFormatter expressionFormatter;
    
    public void setExpressionFormatter(IExpressionLogFormatter formatter)
    {
    	this.expressionFormatter=formatter;
    }
   
	
	public BaseWordCal()
	{
		this.context=new HashMap<String,Object>();
	}
	
	public void setExpressionLog(IExpressionLog log)
	{
		this.expressionLog=log;
	}
	
	@Override
	public void setContext(Map<String, Object> context) {
		if(context!=null)
        {
       	 this.context=context;
  
        }


	}
	
}
