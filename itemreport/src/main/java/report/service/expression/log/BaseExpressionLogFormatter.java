package report.service.expression.log;

import java.util.Map;

import report.service.expression.interfaces.IExpressionLogFormatter;

public abstract class BaseExpressionLogFormatter implements IExpressionLogFormatter {

    protected StringBuilder formatterBuilder=new StringBuilder();
	@Override
	public abstract String format(Object obj, Map<Object, Object> params);
	public abstract String format(Object obj, Object[] params) throws Exception;
	
}
