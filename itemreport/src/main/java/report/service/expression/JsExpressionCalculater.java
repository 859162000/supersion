package report.service.expression;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang.xwork.StringUtils;

import framework.helper.ExceptionLog;
import framework.interfaces.ApplicationManager;
import report.service.expression.interfaces.IExpressionParser;
import report.service.expression.interfaces.IWordCal;
import report.service.expression.interfaces.IWordCheck;
import report.service.expression.parser.Word;
import report.service.expression.parser.WordSpliter;



public class JsExpressionCalculater extends BaseExpressionCalculater{

	private String commonFun;
	private StringBuilder expressionBuilder;
	private WordSpliter wordSpliter;
	private ScriptEngineManager factory;
	private ScriptEngine engine;
	
	public  JsExpressionCalculater()
	{
		expressionBuilder=new StringBuilder();
		wordSpliter=new WordSpliter();
		factory = new ScriptEngineManager();  
		engine = factory.getEngineByName("JavaScript");
		
		
		
	}
	public void setCommonFun(String commonFun)
	{
		this.commonFun=commonFun;
		if(engine!=null)
		{
			try {
				engine.eval(commonFun);
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ExceptionLog.CreateLog(e);
			}
		}
	}
	public String getCommonFun()
	{
		return this.commonFun;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Object calculate(String express) throws Exception  {
		
		if(!StringUtils.isBlank(express))
		{	
			String strItemCode = null;
			String strItemDataType = null;
			String exp;
			if(express.indexOf("#") > 0)
			{
				String[] strr = express.split("#");
				strItemCode = strr[0];
				strItemDataType = strr[1];
				exp = strr[2];
			}
			else
				exp = express;
			
			ApplicationManager.getActionExcuteLog().debug("原始表达式为"+exp);
			StringBuilder checkMsgBuilder=new StringBuilder();
			lstWords=wordSpliter.SplitWord(exp);
			expressionBuilder.setLength(0);
			boolean isCal=false;
			
			//校验exp表达式是否有错
			for(Word w:lstWords)
			{
				for(IExpressionParser convert:converters)
				{
				   convert.setExpressionLog(expressionLog);	
				   convert.setContext(context);
				   if(convert.parse(w))
				   {
     				   break;
				   }
					   
				}
				for(IWordCheck check:wordChecks)
				{
					
				   if(!check.check(w))
				   {
					   checkMsgBuilder.append(check.getCheckMsg());
     			   }
					   
				}
			}
			if(checkMsgBuilder.length()>0)
			{
				throw new Exception(checkMsgBuilder.toString());
			}
			
			//执行exp表达式
			for(Word w:lstWords)
			{
				if(strItemCode != null && strItemDataType != null)
				{
					Map<String,Object> itemMap = new HashMap<String,Object>();
					itemMap.put("ItemDataType",strItemCode+"#"+strItemDataType);
					w.setExpParam(itemMap);
				}
						
				isCal=false;
				for(IWordCal cal:wordCals)
				{
					cal.setExpressionLog(expressionLog);	
					cal.setContext(context);
				   if(cal.cal(w))
				   {
					   
					   isCal=true;
					   break;
				   }
					   
				}
				
				
				if(!isCal)
				{
					w.value=w.word;
					w.description=w.word;
					
				}
				expressionBuilder.append(w.value+" ");//加入空字符串防止常量之间不加操作符而导致两个常量被拼接为一个字符串
				
				if(expressionLog!=null)
				{
					expressionLog.log(w.description);
				}
			}
			
			if(expressionBuilder.length()>0)
			{
				try
			    {
					ApplicationManager.getActionExcuteLog().debug("表达式为"+expressionBuilder.toString());
					
					if(strItemDataType != null)
					{
						if(!strItemDataType.equals("1"))
							return engine.eval(expressionBuilder.toString());
						else
							return expressionBuilder.toString();
					}
					else
						return engine.eval(expressionBuilder.toString());
					
			    }
				catch(Exception ex)
				{
					ExceptionLog.CreateLog(ex);
					ApplicationManager.getActionExceptionLog().error("表达式为"+expressionBuilder.toString());
					throw ex;
				}
			}

		}

		 return "";
	}

}
