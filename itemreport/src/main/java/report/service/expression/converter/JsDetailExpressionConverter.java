package report.service.expression.converter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;


import report.service.expression.ExpressionKey;

public class JsDetailExpressionConverter extends JsBaseExpressionConverter {

	private static final String TYPE_FLAG="detail";
	private static final String TABLE_SPLITER=":";
	private static final String WHERE_SPLITER="[";
	private static final String SELECT=" SELECT ";
	private static final String FROM=" FROM ";
	private static final String WHERE=" WHERE ";
	private static final String NOFIELD="$-1#";
	private static final String JOINPATTERN="#(AND|OR|-1|\\(|\\))\\$";
	private static final String OPERATORPATTERN="#(=|<>|>|>=|<|<=|IN|LIKE|NOT LIKE|-1)#";
	private static final String FIELD_TYPE_SPLITER="\\|";
	private static final String CONDITION_SPLITER="$";
	private static final String CONDITION_SECTION_SPLITER="#";
	private static final String NO_FIELD_REPLACE="$#";
	
	
	private static Map<String,String> operatorMap;
	private static Map<String,String> joinMap;
	private static Set<String> defaultDBStrType;
	static
	{	operatorMap=new HashMap<String,String>();
		operatorMap.put("#=#", "=");
		operatorMap.put("#>=#", ">=");
		operatorMap.put("#>#", ">");
		operatorMap.put("#<=#", "<=");
		operatorMap.put("#<#", "<");
		operatorMap.put("#NOT LIKE#", " NOT LIKE ");
		operatorMap.put("#LIKE#", " LIKE ");
		operatorMap.put("#IN#", " IN ");
		operatorMap.put("#<>#", "<>");
		operatorMap.put("#-1#", " ");
		
		joinMap=new HashMap<String,String>();
		joinMap.put("#AND$", " AND ");
		joinMap.put("#OR$", " OR ");
		joinMap.put("#($", "(");
		joinMap.put("#)$", ")");
		joinMap.put("#-1$", "");
		
		defaultDBStrType=new HashSet<String>();
		defaultDBStrType.add("char");
		defaultDBStrType.add("varchar");
		defaultDBStrType.add("datetime");
		defaultDBStrType.add("clob");
		defaultDBStrType.add("timestamp");
		defaultDBStrType.add("varchar2");
		defaultDBStrType.add("date");
		defaultDBStrType.add("text");
		defaultDBStrType.add("character");
	   }

	

	@Override
	protected boolean convert(String expressionType, String expressionValue)
			throws Exception {
		if(TYPE_FLAG.equalsIgnoreCase(expressionType))
		{
			String sql=BuilderSql(expressionValue);
			this.result=ExpressionKey.SQL+ExpressionKey.PARAM_LEFT_SPLITER+sql+ExpressionKey.PARAM_RIGHT_SPLITER;
			return true;
		}
		return false;
	}
	private void getFieldCondInfo(String cond,StringBuilder name,StringBuilder nameType,StringBuilder fieldType,StringBuilder val)
	{
		name.setLength(0);
		fieldType.setLength(0);
		val.setLength(0);
		nameType.setLength(0);
		String[] nameTypeVal=cond.split(OPERATORPATTERN);
		
		if(nameTypeVal.length>=1)
		{   
			
			nameType.append(nameTypeVal[0]);
			
			String[] aryNameType=nameTypeVal[0].split(FIELD_TYPE_SPLITER);
			name.append(aryNameType[0]);
			if(aryNameType.length==2)
			{
				fieldType.append(aryNameType[1]);
				
			}
		}
		if(nameTypeVal.length==2)
		{
			val.append(nameTypeVal[1]);
		}
			
	}
	private String BuilderSql(String expressionValue)
   {
		StringBuilder sqlBuilder=new StringBuilder();
		sqlBuilder.append(SELECT);
		int tableEndIndex=expressionValue.indexOf(TABLE_SPLITER);
		int whereStartIndex=expressionValue.indexOf(WHERE_SPLITER);
		
		sqlBuilder.append(expressionValue.substring(tableEndIndex+1,whereStartIndex));
		
		sqlBuilder.append(FROM);
		
		sqlBuilder.append(expressionValue.substring(1,tableEndIndex));
		
	
		
		int whereEndIndex=2;
		
		String where= expressionValue.substring(whereStartIndex+1,expressionValue.length()-whereEndIndex)+CONDITION_SPLITER;
		StringBuilder whereBuilder=new StringBuilder(where);
		
		
		String[] aryFieldCond=where.split(JOINPATTERN);
		
		
		StringBuilder sbname=new StringBuilder(),
						sbnameType=new StringBuilder(),
						sbfieldType =new StringBuilder(),
						sbval=new StringBuilder();
		String fieldType ,newVal,val;
		StringBuilder condBuilder=new StringBuilder();
		for(String cond:aryFieldCond)
		{   
			if(!cond.startsWith("-1"))
			{
				condBuilder.setLength(0);
				condBuilder.append(cond);
				getFieldCondInfo(cond,sbname,sbnameType,sbfieldType,sbval);
				fieldType=sbfieldType.toString().toLowerCase();
				val=sbval.toString();
				String sqlPara=val.trim().toUpperCase();
				if(sqlPara.startsWith(ExpressionKey.SQL_PARAM_FLAG)
						||sqlPara.startsWith(ExpressionKey.SQL_LEFT_BRACKETS))
				{
					newVal=val.trim();
				}
				else
				{
					if(!StringUtils.isBlank(fieldType))
					{
						if(defaultDBStrType.contains(fieldType))
						{
							newVal=" '"+StringEscapeUtils.escapeSql(val)+"'";
							
						}
						else
						{
							newVal=" "+val;
						}
							
					}
					else 
					{
						newVal=" '"+StringEscapeUtils.escapeSql(val)+"'";
					}
				}
				
				
				if(sbfieldType.length()>0)
				{
					ReplaceAll(condBuilder,sbnameType.append(CONDITION_SECTION_SPLITER).toString(),sbname.append(" ").toString(),false);
					ReplaceAll(condBuilder,CONDITION_SECTION_SPLITER+val,newVal,false);
				}
				else
				{
					if(sbval.length()==0)
					{
						condBuilder.append("''");
					}
					else
					{
						ReplaceAll(condBuilder,val,newVal.toString(),false);
					}
					
				}
				
				ReplaceAll(whereBuilder,cond,condBuilder.toString(),false);
			}
			
			
		}
		//where格式为字段|字段类型#操作符#值#连接符@
		if(whereBuilder.charAt(0)=='-')
		{
			whereBuilder.delete(0, 2);
		}
		
		ReplaceAll(whereBuilder,NOFIELD,NO_FIELD_REPLACE);
		for(String op:operatorMap.keySet())
		{
			ReplaceAll(whereBuilder,op,operatorMap.get(op));
		}
		for(String join:joinMap.keySet())
		{
			ReplaceAll(whereBuilder,join,joinMap.get(join));
		}
		
		String strWhere=whereBuilder.toString().trim();
		if(strWhere.length()>0)
		{
			sqlBuilder.append(WHERE);
			sqlBuilder.append(strWhere);
		}
		
		
		
		
		return sqlBuilder.toString();
				
	}

	private void ReplaceAll(StringBuilder sbBuilder,String oldStr,String newStr)
	{
		ReplaceAll(sbBuilder,oldStr,newStr,true);
	}
	
	private void ReplaceAll(StringBuilder sbBuilder,String oldStr,String newStr,boolean isReplaceAll)
	{
		
        int oldStrLen=oldStr.length();
        int newStrLen=newStr.length();
		int startindex=-1;
		startindex=sbBuilder.indexOf(oldStr);
		while(startindex>-1)
		{
		     
			sbBuilder.replace(startindex, startindex+oldStrLen,newStr);
			if(isReplaceAll)
			{
				startindex=sbBuilder.indexOf(oldStr,startindex+newStrLen);
				
			}
			else
			{
				break;
			}
			
		}
	}

	
}


