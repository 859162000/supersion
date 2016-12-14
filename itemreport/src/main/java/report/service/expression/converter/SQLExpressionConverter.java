package report.service.expression.converter;

import report.service.expression.ExpressionKey;

public class SQLExpressionConverter extends JsBaseExpressionConverter {
	
	private static final String TYPE_FLAG="sql";

	@Override
	protected boolean convert(String expressionType, String expressionValue)
			throws Exception {
		if(TYPE_FLAG.equalsIgnoreCase(expressionType))
		{

			//expressionValue=StringEscapeUtils.escapeSql(expressionValue);
			this.result=ExpressionKey.SQL+ExpressionKey.PARAM_LEFT_SPLITER+expressionValue+ExpressionKey.PARAM_RIGHT_SPLITER;
			return true;
		}
		return false;
	}
	

	
	

}
