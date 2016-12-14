package coresystem.service.translate;

import java.util.Date;

import coresystem.dto.UserInfo;
import framework.helper.AESSecretKey;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ITranslate;

public class UserInfoSaveOrUpdateTranslate  implements ITranslate{

	public void Translate() throws Exception {
		UserInfo userInfo = (UserInfo)RequestManager.getTOject();
		String encryPassword  = AESSecretKey.EncryString(userInfo.getStrPassword(), null);
		userInfo.setStrPassword(encryPassword);
		
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		UserInfo dataBaseUser = (UserInfo)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{UserInfo.class.getName(),userInfo.getStrUserCode(),null});
		
		if(dataBaseUser == null || !userInfo.getStrPassword().equals(dataBaseUser.getStrPassword())){
			userInfo.setUpdatePasswordTime(new Date());
		}
	}

}
