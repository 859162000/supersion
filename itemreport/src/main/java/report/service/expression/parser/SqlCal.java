package report.service.expression.parser;

import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;

import org.apache.commons.lang.StringUtils;

import extend.dto.SystemParam;
import extend.helper.HelpTool;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.helper.SmallTools;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import report.dto.CalRoundMethod;
import report.dto.ItemDataType;
import report.helper.DoubleUtil;
import report.service.expression.ExpressionContextKey;
import report.service.expression.ExpressionKey;
import report.service.expression.SqlExpUtil;

/**
 * @author transino2
 *
 */
public class SqlCal extends BaseWordCal {

	 
	private Set<String> keyWords;
	private Map<String,List<Integer>> paramPosition=new HashMap<String,List<Integer>>();
	private Object[] params;
	private Map<String,Object> paramTable=null;
	private Map<String,String> sqlTypeParam=null;
	private char singleQuote='\'';

	
	public SqlCal()
	{
		keyWords=new HashSet<String>();
		keyWords.add(ExpressionKey.SQL);
	}
	
	@Override
	protected void calWordVal(Word w) throws Exception {
		String expressionValue=(String)w.getExpParam().get(ExpressionKey.SQL);
		
		
		String sqlUpper=processSqlParam(expressionValue);
		
		paramTable.put("ItemDataTraceParams", params);
		w.setRuntimeParam(paramTable);
		IParamObjectResultExecute paramObjectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("createSqlQueryResultSetDao");
		ResultSet resultSet = (ResultSet)paramObjectResultExecute.paramObjectResultExecute(new Object[]{sqlUpper,params,null});
		
		String result="";
		while (resultSet.next()){
			Object objResult=resultSet.getObject(1);
			if(objResult==null)
			{
				result="0";
			}
			else
			{
				result=objResult.toString();
			}
			break;
			
			
		}
		resultSet.close();
		
		/*IParamObjectResultExecute objectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("queryDataBaseEditionDao");
		String dataBaseType = (String) objectResultExecute.paramObjectResultExecute(null);
		if(dataBaseType!=null)
		{
			if(dataBaseType.equals("oracle"))
				resultSet.getStatement().close();	
		}*/
		
		SmallTools.closeCursor(resultSet);
		
		String strItemCode = null;
		String strItemDataType = null;
		String itemInfo = w.getExpParam().get("ItemDataType").toString();
		if(itemInfo != null)
		{
			String[] strr = itemInfo.split("#");
			strItemCode = strr[0];
			strItemDataType = strr[1];
		}
		
	    w.value=format(result,TypeParse.parseInt(strItemDataType));
	    
	    
	    if(expressionLog!=null)
		{
	    	Map<Object,Object> logParams=new HashMap<Object,Object>();
			logParams.put(ExpressionContextKey.DTDATE_PARAM_KEY, context.get(ExpressionContextKey.DTDATE_PARAM_KEY));
			logParams.put(ExpressionContextKey.INSTCODE_PARAM_KEY, context.get(ExpressionContextKey.INSTCODE_PARAM_KEY));
			logParams.put(ExpressionContextKey.DATA_PARAM_KEY,result);
			expressionLog.log("@sql("+expressionValue+")", logParams, "sqlExpressionFormatter");
		}
		
	}

	@Override
	protected Set<String> getWordKey() {
		return keyWords;
	}
	
	private void convertParamKeyToUpper() throws Exception
	{
		if(paramTable==null)
		{
			paramTable=new HashMap<String,Object>();
			sqlTypeParam=new HashMap<String,String>();
			if(context!=null)
			{
				for(Entry<String,Object> entry:context.entrySet())
				{
					Object val=entry.getValue();
					if(val!=null)
					{
						String strVal=val.toString().trim();
						if(strVal.toUpperCase().startsWith("SELECT"))
						{
							Statement stmt;
							try {
								stmt = new CCJSqlParserManager().parse(new StringReader(strVal.replace('@', ' ')));
								if((stmt instanceof Select))
								{
									sqlTypeParam.put(entry.getKey().toUpperCase(), strVal);
									continue;
								}
							} catch (JSQLParserException e) {
								ExceptionLog.CreateLog(e);
							}
							
						}
					}
					paramTable.put(entry.getKey().toUpperCase(),entry.getValue());
				}
			}
		}
		
		if(!sqlTypeParam.containsKey("InnerInstTable"))
		{			
			SystemParam systemParam = HelpTool.getSystemParam("InnerInstTable");
			
			if(systemParam!=null && systemParam.getStrEnable().equals("1"))
			{
				sqlTypeParam.put("InnerInstTable".toUpperCase(), systemParam.getStrParamValue());
			}
		}
		
	}
	 
	
	
	//SQL语句加prams的封装,带@的参数全部替换为?，值放在parms数组中
	private String processSqlParam(String sql) throws Exception
	{
		paramPosition.clear();
		StringBuilder uppderSql= new StringBuilder(sql.toUpperCase());
		StringBuilder sbSql=new StringBuilder(sql);
		int whereIndex=uppderSql.indexOf(" WHERE ");
		int curIndex=whereIndex+" WHERE ".length();
		int sqlLen=sbSql.length();
		boolean inQuote=false;
		
		int i=1;
		
		convertParamKeyToUpper();
		
		while(curIndex<sqlLen)
		{
			if(!inQuote&&sbSql.charAt(curIndex)==ExpressionKey.KEY_WORD_FLAG)
			{
				String paramName=SqlExpUtil.getParamName(curIndex,sbSql).toUpperCase();
				if(!"".equals(paramName))
				{
					String newStr="?";
					
					if(sqlTypeParam.containsKey(paramName))
					{
						newStr=sqlTypeParam.get(paramName);
					}
					else
					{
						if(!paramPosition.containsKey(paramName))
						{
							paramPosition.put(paramName,new ArrayList<Integer>());
						}
						paramPosition.get(paramName).add(new Integer(i));
						i++;
					}
					sbSql.replace(curIndex,curIndex+paramName.length()+1, newStr);
					
					sqlLen=sbSql.length();
					
				}
				
			}
			else if(!inQuote&&sbSql.charAt(curIndex)==singleQuote)
			{
				inQuote=true;
				
			}
			else if(inQuote&&sbSql.charAt(curIndex)==singleQuote)
			{
				inQuote=false;
		
			}
			curIndex++;
		}
		
		if(i>1)
		{
			params=new Object[i-1];
			for(Entry<String,List<Integer>> entry:paramPosition.entrySet())
			{
				List<Integer> lstParaPos=entry.getValue();
				Object val=paramTable.get(entry.getKey());
				for(Integer pos:lstParaPos)
				{
					params[pos.intValue()-1]=val;
				}
			}
		}
		else
		{
			params=new Object[0];
		}
		
		paramTable.put("ItemDataTrace",sbSql);
		return sbSql.toString();
	}
	
	/*
	 * @param val:计算出的指标值
	 * @param itemDataType：指标类型(1:文本类型,2:金额类型,3:数值类型,4:日期类型,5百分比类型)
	 */
	private String format(String val, int itemDataType) throws  Exception
	{
		String result = null;
		try{
			switch(itemDataType)
			{
				case 2:
				case 5:
					if(StringUtils.isBlank(val))
					{
						val="0";
					}
					Double dblVal=TypeParse.parseDouble(val);
					if(Double.compare(Double.MAX_VALUE,dblVal)>0)
					{
						//SQL语句计算不需要判断四舍五入的先后顺序，因为SQL语句计算出的就是原始值，不存在四舍五入的问题
//						if(CalRoundMethod.FirstRound.toString().equals(context.get(ExpressionContextKey.CALROUNDMETHOD_PARAM_KEY)))
//						{
							Object objPrecise=context.get(ExpressionContextKey.PRECISE_PARAM_KEY);
							Integer intPrecise=2;
							if(objPrecise!=null)
							{
								intPrecise=(Integer)objPrecise;
							}
							
							Object objRptUnit=context.get(ExpressionContextKey.RptUnit);
							Integer intRptUnit=1;	//默认报表单位为元
							if(objRptUnit!=null)
							{
								intRptUnit=(Integer)objRptUnit;
							}
														
							
							if(itemDataType == 2)
							{
								BigDecimal b = new BigDecimal(val);
								b = b.divide(new BigDecimal(intRptUnit));
								
								result=DoubleUtil.format(TypeParse.parseDouble(b.toString()), intPrecise);
								
								b=new BigDecimal(result);
								result= b.multiply(new BigDecimal(intRptUnit)).toString();
							}
							else
							{								
								BigDecimal b1 = new BigDecimal(val);
								result = b1.multiply(new BigDecimal(100)).toString();	//先放大100倍再进行精度截取，然后除以100恢复回去
								result = DoubleUtil.format(Double.parseDouble(result), intPrecise);
								
								b1 = new BigDecimal(result);
								result = b1.divide(new BigDecimal(100)).toString();
							}
							
							if(result.indexOf("E")>0 || result.indexOf("e")>0)	//判断是否为科学计数法			
								result = (new BigDecimal(result)).toPlainString();
							
/*						}
						else
							result = val;*/
						
					}
					else
					{
						result="0";
					}
					break;

					
				default:
					result = val;
			}
		}
		catch(Exception ex)
		{
			if(val.equals("NaN"))
				result = "0";
			else
				result = val;
		}
				
		return result;
	}
	

}
