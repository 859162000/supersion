package report.service.expression;

public class SqlExpUtil {
	private static char equal='=';
	private static char greate='>';
	private static char less='<';
	private static char comma = ',';
	
	/*
	 *从SQL语句中获取需要用?替换的参数， sql为SQL语句，paramFlagPos为参数的起始位置
	 * 参数在SQL语句中的存在形式举例如下：
	 * 1、以空格结束 RQ =@dtDate AND …………
	 * 2、以大于符号结束 @dtDate>'20160824' 或这种形式@dtDate>='20160824'
	 * 3、以小于符号结束 @dtDate<'20160824' 或这种形式@dtDate<='20160824'
	 * 4、以小于符号结束 @dtDate='20160824'
	 * 5、以逗号结束 substr(RQ,1,6) = substr(@dtDate,1,6),这种形式主要是在数据库函数中使用了参数
	 */
	public static String getParamName(int paramFlagPos,StringBuilder sql)
	{
		
		int next=paramFlagPos+1;
		int sqlLen=sql.length();
		int end=-1;
		char rightBrackets=ExpressionKey.SQL_RIGHT_BRACKETS.charAt(0);
		char c;
		
		while(next<sqlLen)
		{
			c=sql.charAt(next);
			
			//判断参数的结束位置
			if(Character.isWhitespace(c) ||
					rightBrackets==c||
					equal==c||
					greate==c||
					less==c||
					comma==c
				)
			{
				end=next;
				break;
			}
			next++;
		}
		if(end>0)
		{
			return sql.substring(paramFlagPos+1, end);
		}
		else
		{
			return  sql.substring(paramFlagPos+1, sqlLen);
		}
	}
}
