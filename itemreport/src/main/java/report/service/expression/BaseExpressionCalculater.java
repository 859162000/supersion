package report.service.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import report.service.expression.interfaces.IExpressionCalculater;

import report.service.expression.interfaces.IExpressionLog;
import report.service.expression.interfaces.IExpressionParser;
import report.service.expression.interfaces.IWordCal;
import report.service.expression.interfaces.IWordCheck;
import report.service.expression.parser.Word;


public abstract class BaseExpressionCalculater implements IExpressionCalculater {

	protected Map<String,Object> context=null;
	protected List<IExpressionParser> converters=null;
	protected List<IWordCal> wordCals;
	protected List<IWordCheck> wordChecks;
	protected IExpressionLog expressionLog;
	protected List<Word> lstWords;
	public BaseExpressionCalculater()
	{
	   this.expressionLog=null;
       this.context=new HashMap<String,Object>();
       this.converters=new ArrayList<IExpressionParser>();
       this.wordCals=new ArrayList<IWordCal>();
       this.wordChecks=new ArrayList<IWordCheck>();
      	
		
	}
	public List<Word> getWords()
    {
    	return lstWords;
    }
	@Override
	public void setExpressionLog(IExpressionLog log) {
		
		this.expressionLog=log;
	}
	@Override
	public abstract Object calculate(String exp) throws Exception;

	@Override
	public void setContext(Map<String, Object> context) {
           if(context!=null)
           {
        	   this.context=context;
        	   
           }
          
		
	}
	@Override
	public void setConverters(List<IExpressionParser> converts) {
		if(converts!=null)
		{
		   this.converters=converts;		
			
		}
		
	}
	public void setWordCals(List<IWordCal> wordCals)
	{
		this.wordCals=wordCals;
	}
	
	public void setWordChecks(List<IWordCheck> wordChecks)
	{
		this.wordChecks=wordChecks;
	}

}
