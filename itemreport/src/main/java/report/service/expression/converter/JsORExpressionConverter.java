package report.service.expression.converter;

public class JsORExpressionConverter extends JsBaseExpressionConverter {
	private static final String TYPE_FLAG="symbol";
	private static final String FLAG_VALUE="or";

	@Override
	protected boolean convert(String expressionType, String expressionValue) {
		if(TYPE_FLAG.equalsIgnoreCase(expressionType)&& 
				FLAG_VALUE.equalsIgnoreCase(expressionValue))
		{
			this.result="||";
			return true;
			
			
		}
		return false;
	}


}