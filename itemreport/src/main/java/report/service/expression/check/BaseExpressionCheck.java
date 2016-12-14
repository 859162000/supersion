package report.service.expression.check;

import report.service.expression.interfaces.IExpressionCheck;

public abstract class BaseExpressionCheck implements IExpressionCheck {

	protected StringBuilder checkMsgBuilder;
	public BaseExpressionCheck()
	{
		checkMsgBuilder=new StringBuilder();
	}
	
	
	
	
	@Override
	public abstract boolean check(String exp) throws Exception;
	

	@Override
	public String getCheckMsg() {
		return checkMsgBuilder.toString();
	}

}
