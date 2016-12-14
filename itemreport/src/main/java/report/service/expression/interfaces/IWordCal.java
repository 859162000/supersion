package report.service.expression.interfaces;

import java.util.Map;

import report.service.expression.parser.Word;

public interface IWordCal {
	 boolean cal(Word w) throws Exception;
	void setContext(Map<String,Object> context);
	void setExpressionLog(IExpressionLog log);
	void setExpressionFormatter(IExpressionLogFormatter formatter); 
}
