package coresystem.service.procese;

import java.util.List;

import coresystem.dto.UserInfo;

import framework.helper.AESSecretKey;
import framework.helper.ReflectOperation;
import framework.services.interfaces.IProcese;

public class UserInfoShowPasswordProcese implements IProcese{

	@SuppressWarnings("unchecked")
	public Object Procese(Object serviceResult) throws Exception {
		
		if(serviceResult.getClass().equals(UserInfo.class)){
			UserInfo userInfo = (UserInfo)serviceResult;
			String decryPassword  = AESSecretKey.DecryString(userInfo.getStrPassword(), null);
			
		    UserInfo copyUserInfo = new UserInfo();
			ReflectOperation.CopyFieldValue(userInfo, copyUserInfo);
			copyUserInfo.setStrPassword(decryPassword);
			copyUserInfo.setUserRoles(userInfo.getUserRoles());
			return copyUserInfo;
			
			/*userInfo.setStrPassword(decryPassword);
			return userInfo;*/
		}
		else{
			List<UserInfo> userInfoList = (List<UserInfo>)serviceResult;
			for(UserInfo userInfo : userInfoList){
				String decryPassword  = AESSecretKey.DecryString(userInfo.getStrPassword(), null);
				userInfo.setStrPassword(decryPassword);
			}
			
			return userInfoList;
		}
	}

}
