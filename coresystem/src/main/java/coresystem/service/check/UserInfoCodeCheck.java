package coresystem.service.check;

import coresystem.dto.UserInfo;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;

/**
 * 校验UserInfo表不能创建admin用户
 * @author xiajieli
 *
 */
public class UserInfoCodeCheck implements ICheck {

	@Override
	public MessageResult Check() throws Exception {
		
		MessageResult messageResult = new MessageResult();
		
		Object tObject=RequestManager.getTOject();
		if(tObject!=null && tObject.getClass().getName().equals("coresystem.dto.UserInfo")){
			
			UserInfo userInfo=(UserInfo) tObject;
			if(userInfo.getStrUserCode().trim().equals("admin")){
				messageResult.setSuccess(false);
    			messageResult.getMessageSet().add("不可创建用户代码为admin的用户");
			}
			
		}
		
		return messageResult;
	}

}
