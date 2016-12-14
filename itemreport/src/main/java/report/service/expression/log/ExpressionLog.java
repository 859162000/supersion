package report.service.expression.log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import framework.helper.FrameworkFactory;

import report.service.expression.interfaces.IExpressionLog;
import report.service.expression.interfaces.IExpressionLogFormatter;

public class ExpressionLog implements IExpressionLog {

	private static final String NEWLINE="\r\n";
	private List<StringBuilder> logBuilderList;
	private StringBuilder defaultLogBuilder;
	private boolean isDefaultLog;
	private int curLogIndex;
	
	public ExpressionLog()
	{
		logBuilderList=new ArrayList<StringBuilder>();
		 defaultLogBuilder=new  StringBuilder();
		logBuilderList.add(defaultLogBuilder);
		isDefaultLog=true;
		curLogIndex=0;
	}
	
	
	

	@Override
	public String logToString() {
	
		StringBuilder stringBuilder=new StringBuilder();
		for(StringBuilder sb:logBuilderList)
		{
			stringBuilder.append(sb);
		}
		return stringBuilder.toString();
		
		
	}

	@Override
	public void log(Object obj) {
	      log(obj,null);
	}

	@Override
	public void log(Object obj, Map<Object, Object> params) {
		if(obj!=null)
		{
			String s=obj.toString();
			if(params!=null)
			{
				for(Object key:params.keySet())
				{
					s.replaceAll(key.toString(),params.get(key)!=null?params.get(key).toString():"");
				}
			}
			StringBuilder logBuilder=getLogBuilder();
			logBuilder.append(s);
			logBuilder.append(NEWLINE);
			
		}
		
	}
 
	@Override
	public void log(Object obj, Map<Object, Object> params,
			IExpressionLogFormatter formatter) {
		 if(formatter!=null)
		 {
			 StringBuilder logBuilder=getLogBuilder();
			 logBuilder.append(formatter.format(obj, params));
			 logBuilder.append(NEWLINE);
		 }
		 else
		 {
			 log(obj,null);
		 }
		
	}
	
	private StringBuilder getLogBuilder()
	{
		if(!isDefaultLog)
		 {
			if(curLogIndex>=0&&curLogIndex<logBuilderList.size())
			 {
				 return logBuilderList.get(curLogIndex);
			 }
		 }
		 
		return  defaultLogBuilder;
	}
	@Override
	public void log(Object obj, Object[] params,
			IExpressionLogFormatter formatter) throws Exception {
		 if(formatter!=null)
		 {
			 StringBuilder logBuilder=getLogBuilder();
			 
			 logBuilder.append(formatter.format(obj, params));
			 logBuilder.append(NEWLINE);
		 }
		 else
		 {
			 log(obj,null);
		 }
		
	}
   
	@Override
	public void log(Object obj, Map<Object, Object> params, String formatterName) {
		IExpressionLogFormatter formatter=(IExpressionLogFormatter)FrameworkFactory.CreateBean(formatterName);
		log(obj,params,formatter);
		
	}

	@Override
	public void log(Object obj, Object[] params, String formatterName) throws Exception {
		IExpressionLogFormatter formatter=(IExpressionLogFormatter)FrameworkFactory.CreateBean(formatterName);
		log(obj,params,formatter);
		
	}

	@Override
	public void clear() {
		defaultLogBuilder.setLength(0);
		logBuilderList.clear();
		logBuilderList.add(defaultLogBuilder);
		isDefaultLog=true;
		curLogIndex=0;
		
	}

	@Override
	public void insertLog(int index, Object obj, Map<Object, Object> params,
			String formatterName) {
		logBuilderList.add(index, new StringBuilder());
		appendLog(index,obj,params,formatterName);
		
		
	}




	@Override
	public void appendLog(int index, Object obj, Map<Object, Object> params,
			String formatterName) {
		if(index>=0 && index<logBuilderList.size())
		{
			curLogIndex=index;
			isDefaultLog=false;
			try
			{
				log(obj,params,formatterName);
			}
			finally
			{
				isDefaultLog=true;
			}
		}
		
		
	}




	@Override
	public void appendLog(int index, Object obj, Object[] params,
			String formatterName) throws Exception {
		if(index>=0 && index<logBuilderList.size())
		{
			curLogIndex=index;
			isDefaultLog=false;
			try
			{
				log(obj,params,formatterName);
			}
			finally
			{
				isDefaultLog=true;
			}
		}
		
	}




	@Override
	public void insertLog(int index, Object obj, Object[] params,
			String formatterName) throws Exception {
		logBuilderList.add(index, new StringBuilder());
		appendLog(index,obj,params,formatterName);
		
	}

}
