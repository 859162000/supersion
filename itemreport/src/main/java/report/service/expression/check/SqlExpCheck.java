package report.service.expression.check;

import java.io.StringReader;
import java.util.ArrayList;

import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;

import org.apache.commons.lang.StringUtils;

import report.service.expression.ExpressionKey;
import report.service.expression.SqlExpUtil;
import report.service.expression.interfaces.IWordCheck;
import report.service.expression.parser.Word;

public class SqlExpCheck implements IWordCheck {

	
	private StringBuilder msgBuilder;
	private char singleQuote='\'';
	
	public SqlExpCheck()
	{
		msgBuilder=new StringBuilder();
	}
	@Override
	public boolean check(Word w) throws Exception {
		msgBuilder.setLength(0);
		if(ExpressionKey.SQL.equals(w.wordType))
		{
			String expressionValue=(String)w.getExpParam().get(ExpressionKey.SQL);
			if(!StringUtils.isBlank(expressionValue))
			{
				try
				{
					StringBuilder sbSql=new StringBuilder(expressionValue);
					processSqlParam(sbSql);
					
					Statement stmt =new CCJSqlParserManager().parse(new StringReader(sbSql.toString()));
					  if(!(stmt instanceof Select))
					  {
						 msgBuilder.append("表达式"+w.word+"检验错误：sql表达式只支持查询语句。");
						 return false;
					  }
				}
				catch(Throwable ex)
				{
					String msg="表达式"+w.word+"检验错误：";
					if(StringUtils.isBlank(ex.getMessage())&& ex.getCause()!=null)
					{
						
						msg=msg+ex.getCause().getMessage();
						
					}
					else
					{
						msg=msg+ex.getMessage();
					}
					msgBuilder.append(msg);
					return false;
					
				}
				 
			}
			else
			{
				msgBuilder.append("表达式检验错误：缺少sql语句。");
				return false;
			}
			
		}
		return true;
	}
	
	private void processSqlParam(StringBuilder sbSql)
	{
		StringBuilder uppderSql= new StringBuilder(sbSql.toString().toUpperCase());
		int whereIndex=uppderSql.indexOf(" WHERE ");
		int curIndex=whereIndex+" WHERE ".length();
		int sqlLen=sbSql.length();
		boolean inQuote=false;
		String replaceStr="'@expcheck'";
		while(curIndex<sqlLen)
		{
			if(!inQuote&&sbSql.charAt(curIndex)==ExpressionKey.KEY_WORD_FLAG)
			{
				
				String paramName=SqlExpUtil.getParamName(curIndex,sbSql).toUpperCase();
				if(!"".equals(paramName))
				{
					
					
					sbSql.replace(curIndex,curIndex+paramName.length()+1, replaceStr);
					
					sqlLen=sbSql.length();
					curIndex=curIndex+replaceStr.length();
					continue;
					
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
	}
	@Override
	public String getCheckMsg() {
		return msgBuilder.append("\r\n").toString();
	}

}
