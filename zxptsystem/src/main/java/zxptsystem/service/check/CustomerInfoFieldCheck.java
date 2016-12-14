package zxptsystem.service.check;

import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.reportCheck.helper.CheckUtils;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;

public class CustomerInfoFieldCheck implements ICheck {

	@Override
	public MessageResult Check() throws Exception {
		Object obj = RequestManager.getTOject();
		String strCUSCreditInstitutionsCode=ReflectOperation.getFieldValue(obj, "strCUSCreditInstitutionsCode").toString();
		String strInCode=ReflectOperation.getFieldValue(obj, "strInCode").toString();
		String strOrganizationCode=ReflectOperation.getFieldValue(obj, "strOrganizationCode").toString();
		String strTaxpayerIdentLandNo=ReflectOperation.getFieldValue(obj, "strTaxpayerIdentLandNo").toString();
		String strTaxpayerIdentStateNo=ReflectOperation.getFieldValue(obj, "strTaxpayerIdentStateNo").toString();
		
		String strRegistrationType=ReflectOperation.getFieldValue(obj, "strRegistrationType").toString();
		String strRegistrationNo=ReflectOperation.getFieldValue(obj, "strRegistrationNo").toString();
		
		MessageResult messageResult=new MessageResult();
		
		boolean isNullRegistrationinfo=true;
		if(!strRegistrationType.equals("") || !strRegistrationNo.equals("")){
			isNullRegistrationinfo=false;
			if(null == strRegistrationType || null ==strRegistrationNo || strRegistrationNo.equals("")){
				messageResult.setSuccess(false);
				messageResult.getMessageSet().add("登记注册号与登记注册类型必须同时为空，或者同时不为空!");
				return messageResult;
			}
		}
		
		if(CheckUtils.IsNullOrEmpty(strCUSCreditInstitutionsCode) && CheckUtils.IsNullOrEmpty(strInCode) 
				&& CheckUtils.IsNullOrEmpty(strOrganizationCode) && CheckUtils.IsNullOrEmpty(strTaxpayerIdentLandNo)
				&& CheckUtils.IsNullOrEmpty(strTaxpayerIdentStateNo) && isNullRegistrationinfo){
			messageResult.setSuccess(false);
			messageResult.getMessageSet().add("客户机构信用代码、中征码、组织机构代码、纳税人识别号（地税）、纳税人识别号（国税）、登记注册号与登记注册类型必须有一项不为空。!");
			return messageResult;
		}
		if(!CheckUtils.OrganizationCodeCheck(strOrganizationCode).equals("")){
			messageResult.setSuccess(false);
			messageResult.getMessageSet().add(CheckUtils.OrganizationCodeCheck(strOrganizationCode));
		}
		return messageResult;
	}	
}
