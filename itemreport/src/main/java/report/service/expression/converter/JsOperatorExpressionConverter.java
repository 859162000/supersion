package report.service.expression.converter;

public class JsOperatorExpressionConverter extends JsBaseExpressionConverter {

	private static final String TYPE_FLAG="symbol";

	@Override
	protected boolean convert(String expressionType, String expressionValue) {
		if(TYPE_FLAG.equalsIgnoreCase(expressionType))
		{
			this.result=expressionValue;
			return true;
			
			
		}
		return false;
	}



}
