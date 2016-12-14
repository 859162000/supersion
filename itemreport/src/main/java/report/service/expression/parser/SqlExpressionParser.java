package report.service.expression.parser;


import java.util.Stack;
import report.service.expression.ExpressionKey;


public class SqlExpressionParser extends BaseExpressionParser{

	@Override
	public boolean parse(Word word) throws Exception {
		
		int keyEndIndex;
		
		
		if(word.word.indexOf(ExpressionKey.SQL)<0)
		{
			return false;
		}
		word.wordType=ExpressionKey.SQL;
		StringBuilder expression=new StringBuilder(word.word);
		String strParams="";
		int keyLen=ExpressionKey.SQL.length()+ExpressionKey.PARAM_LEFT_SPLITER.length();
		
		
		keyEndIndex=getEndIndex(keyLen-1,expression);
		if(keyEndIndex>-1)
		{
			strParams=expression.substring(keyLen, keyEndIndex);
		    word.getExpParam().put(ExpressionKey.SQL, strParams);

		}
		else
			throw new Exception("表达式语法错误，@sql缺少右结束符"+ExpressionKey.PARAM_RIGHT_SPLITER);
		
		return true;
	}
	
	private int getEndIndex(int startindex,StringBuilder expression)
	{
		Stack<Comparable> s=new Stack<Comparable>();
		int i=startindex+1;
		int len=expression.length();
		s.add(ExpressionKey.PARAM_LEFT_SPLITER);
		char curChar;
		for(;i<len;i++)
		{
    		curChar=expression.charAt(i);
			if(ExpressionKey.PARAM_LEFT_SPLITER.equals(String.valueOf(curChar)))
			{
				s.push(curChar);
			}
			else if(ExpressionKey.PARAM_RIGHT_SPLITER.equals(String.valueOf(curChar)))
			{
				s.pop();
				if(s.empty())
				{
					return i;
				}
			}
		}
		return -1;
	}



}
