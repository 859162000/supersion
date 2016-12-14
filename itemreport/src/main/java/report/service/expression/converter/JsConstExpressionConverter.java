package report.service.expression.converter;

public class JsConstExpressionConverter extends JsBaseExpressionConverter {

	private static final String TYPE_FLAG="const";

	@Override
	protected boolean convert(String expressionType, String expressionValue) {
		if(TYPE_FLAG.equalsIgnoreCase(expressionType))
		   {
			   this.result=expressionValue+" ";//加入空字符串防止常量之间不加操作符而导致两个常量被拼接为一个字符串
			   return true;
		   }
		return false;
	}
	

	

}
