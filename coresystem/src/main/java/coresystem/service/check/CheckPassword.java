package coresystem.service.check;

import org.apache.commons.lang.xwork.StringUtils;

import coresystem.dto.ShowUpdatePassword;
import coresystem.dto.UserInfo;
import extend.dto.SystemParam;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.MessageResult.ErrorField;

public class CheckPassword implements ICheck{

	public MessageResult Check() throws Exception {
		
		String password = null;
		if(RequestManager.getTOject().getClass().equals(UserInfo.class)){
			UserInfo userInfo = (UserInfo)RequestManager.getTOject();
			password = userInfo.getStrPassword();
		}
		else{
			ShowUpdatePassword showUpdatePassword = (ShowUpdatePassword)RequestManager.getTOject();
			password = showUpdatePassword.getNewPassword1();
		}
		
		String regex = "^(?![a-zA-Z0-9]+$)(?![^a-zA-Z/D]+$)(?![^0-9/D]+$).{8,50}$";
		boolean result = true;
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		Object object = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{SystemParam.class.getName(), "6" , null});
		if(object != null){
			SystemParam systemParam = (SystemParam)object;
			if(systemParam.getStrEnable() != null && systemParam.getStrEnable().equals("1")){
				if(!StringUtils.isBlank(systemParam.getStrParamValue())){
					regex = systemParam.getStrParamValue();
				}
				result = password.matches(regex);
			}
		}

		//boolean result= password.matches(".*?[^a-zA-Z\\d]+.*?")&&password.matches(".*?[a-zA-Z]+.*?")&&password.matches(".*?[\\d]+.*?");

		MessageResult messageResult = new MessageResult();
		if(result){
			 return messageResult;
		}
		else{ 
			 //String message = "密码应包含:字母、数字、特殊字符    如：aaa111@@@";
			 String message = "密码规则不匹配";
			 messageResult.getErrorFieldList().add(new ErrorField("strPassword", MessageResult.COLORRED, message));
			 for(ErrorField errorField : messageResult.getErrorFieldList()){
				 if(errorField.getColor().equals(MessageResult.COLORRED)){
					 messageResult.setSuccess(false);
					 break;
				 }
			 }
			 return messageResult;
		}
	}
}