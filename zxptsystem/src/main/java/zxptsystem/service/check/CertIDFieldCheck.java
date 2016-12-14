package zxptsystem.service.check;

import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.reportCheck.helper.CheckUtils;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;

public class CertIDFieldCheck implements ICheck {
	
	
	@Override
	public MessageResult Check() throws Exception {
		Object obj = RequestManager.getTOject();
		MessageResult messageResult = new MessageResult();
		String strCertType = ReflectOperation.getFieldValue(obj, "strCertType").toString();
		if(strCertType.equals("0") ||
				strCertType.equals("7")){
			String strCertNo=ReflectOperation.getFieldValue(obj, "strCertNo").toString();
			if(!CheckUtils.IDCardCheck(strCertNo).equals("")){
				messageResult.setSuccess(false);
				messageResult.getMessageSet().add(CheckUtils.IDCardCheck(strCertNo));
			}
			
		}
		return messageResult;
	}
}
