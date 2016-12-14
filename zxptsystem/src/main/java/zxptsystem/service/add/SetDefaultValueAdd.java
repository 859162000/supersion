package zxptsystem.service.add;

import coresystem.dto.UserInfo;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.security.SecurityContext;
import framework.services.interfaces.IAdd;

public class SetDefaultValueAdd implements IAdd {

	@Override
	public void Add() throws Exception {
		if(!SecurityContext.getInstance().getLoginInfo().isAdministrator()){
			UserInfo loginUser=(UserInfo)SecurityContext.getInstance().getLoginInfo().getTag();
			ReflectOperation.setFieldValue(RequestManager.getTOject(),"strUserID",loginUser);
			if(null!=loginUser.getInstInfo()){
				ReflectOperation.setFieldValue(RequestManager.getTOject(),"strInnerInstCode",loginUser.getInstInfo());
			}	
		}			
	}
}
