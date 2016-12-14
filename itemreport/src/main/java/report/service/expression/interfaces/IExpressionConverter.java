package report.service.expression.interfaces;

import java.util.Map;

public interface IExpressionConverter {
	boolean convert(String expression) throws Exception;
	void setContext(Map<String,Object> context);
	String getResult();
}
