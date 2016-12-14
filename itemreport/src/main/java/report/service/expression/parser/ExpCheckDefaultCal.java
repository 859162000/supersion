package report.service.expression.parser;

import java.util.HashSet;
import java.util.Set;

import report.service.expression.ExpressionKey;


public class ExpCheckDefaultCal extends BaseWordCal {

	private Set<String> keyWords;
	public  ExpCheckDefaultCal()
	{
		keyWords=new HashSet<String>();
		keyWords.add(ExpressionKey.ITEM);
		keyWords.add(ExpressionKey.SQL);
	}
	@Override
	protected void calWordVal(Word w) throws Exception {
		w.value="1.1";
		
	}

	@Override
	protected Set<String> getWordKey() {
		// TODO Auto-generated method stub
		return keyWords;
	}

	

}
