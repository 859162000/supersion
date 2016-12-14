package report.service.expression.converter;

import report.service.expression.ExpressionContextKey;
import report.service.expression.JsExpressionCalculater;

public abstract class JsBaseExpressionConverter extends BaseExpressionConverter {

	
	public JsBaseExpressionConverter() {
		super();
	}

	@Override
	public boolean convert(String expression) throws Exception {
	   if(expression!=null)
	   {
		   String[] aryStr=expression.split(ExpressionContextKey.KEY_VALUE_SPLITER);
		   String expressionValue="";
		   if(aryStr.length>1)
			   expressionValue=aryStr[1];
		   return convert(aryStr[0],expressionValue);
		   
	   }
	   return false;
	
	}
	protected abstract boolean convert(String expressionType,String expressionValue) throws Exception;
	

}