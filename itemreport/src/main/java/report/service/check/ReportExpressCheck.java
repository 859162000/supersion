package report.service.check;

import report.dto.CheckRule;
import report.service.expression.interfaces.IExpressionCalculater;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;

public class ReportExpressCheck  implements ICheck{

	@Override
	public MessageResult Check() throws Exception {
		MessageResult messageResult = new MessageResult();
		IExpressionCalculater calculater=(IExpressionCalculater)FrameworkFactory.CreateBean("jsExpCheckCal");
		
		Object tObjct = RequestManager.getTOject();
		String strExpression = (String) ReflectOperation.getFieldValue(tObjct, "strExpression");
		
		try
		{	
			calculater.calculate(strExpression);
			messageResult.setMessage("表达式校验成功");
			
		}
		catch(Exception ex)
		{   
			 messageResult.setSuccess(false);			
			 String msg =ex.getMessage();
			 msg=msg.replace("\r\n","\\r\\n");
			 
			 
			 messageResult.getMessageSet().add("表达式校验失败，错误如下:" + msg );
			
			
					}

		return messageResult;
	}

}
