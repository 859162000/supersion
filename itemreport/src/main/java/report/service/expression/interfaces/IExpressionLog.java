package report.service.expression.interfaces;

import java.util.Map;

public interface IExpressionLog {
	void log(Object obj);
	void log(Object obj,Map<Object,Object> params);
	void log(Object obj,Map<Object,Object> params,IExpressionLogFormatter formatter);
	void log(Object obj,Object[] params,IExpressionLogFormatter formatter) throws Exception;
	void log(Object obj,Map<Object,Object> params,String formatterName);
	void log(Object obj,Object[] params,String formatterName) throws Exception;
	void insertLog(int index,Object obj,Map<Object,Object> params,String formatterName);
	void insertLog(int index,Object obj,Object[] params,String formatterName) throws Exception;
	void appendLog(int index,Object obj,Map<Object,Object> params,String formatterName);
	void appendLog(int index,Object obj,Object[] params,String formatterName) throws Exception;
	
	void clear();
    String logToString();
    
    
}

