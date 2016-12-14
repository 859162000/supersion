package report.service.expression.interfaces;


import java.util.List;
import java.util.Map;

import report.service.expression.parser.Word;


public interface IExpressionCalculater {
	Object calculate(String exp) throws Exception;
	void setContext(Map<String,Object> context);
	void setConverters(List<IExpressionParser> converts);
	void setWordCals(List<IWordCal> wordCals);
	void setWordChecks(List<IWordCheck> wordChecks);
	void setExpressionLog(IExpressionLog log);
	List<Word> getWords();

}
