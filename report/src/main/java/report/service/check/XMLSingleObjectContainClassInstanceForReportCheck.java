package report.service.check;

import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.check.XMLSingleObjectContainClassInstanceCheck;
import framework.services.interfaces.MessageResult;

public class XMLSingleObjectContainClassInstanceForReportCheck extends XMLSingleObjectContainClassInstanceCheck {

	@SuppressWarnings("unchecked")
	@Override
	public MessageResult Check() throws Exception {
		
		Object tobject=RequestManager.getTOject();
		
		 MessageResult currentResult = super.Check();
		
		if(!currentResult.isSuccess() || !ReflectOperation.getFieldValue(tobject, "RPTCheckType").equals("1")) {
			ReflectOperation.setFieldValue(tobject, "RPTCheckType", "3");
		}
		else{
			ReflectOperation.setFieldValue(tobject, "RPTCheckType", "2");
		}
		currentResult.setSuccess(true);
		return currentResult;
	}
	

} 
