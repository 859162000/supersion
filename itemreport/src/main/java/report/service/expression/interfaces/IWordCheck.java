package report.service.expression.interfaces;

import report.service.expression.parser.Word;

public interface IWordCheck {
	
	   boolean check(Word w) throws Exception;
	   String getCheckMsg();


}
