package coresystem.service.imps;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import framework.helper.TypeParse;
import framework.services.imps.SingleObjectUpdateFieldService;

public class UserInfoLockUpdateFieldService extends SingleObjectUpdateFieldService{

	@Override
    public void initSuccessResult() throws Exception {
		
		 Map<String,String> userInfoValueMap = new HashMap<String,String>();
		 userInfoValueMap.put("updatePasswordTime", TypeParse.parseString(new Date(), "yyyy-MM-dd"));
		 userInfoValueMap.put("strLockState", "3");
		 userInfoValueMap.put("errorCount", "0");
		 userInfoValueMap.put("lastLoginTime", TypeParse.parseString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		 this.setUpdateValueMap(userInfoValueMap);
		
		super.initSuccessResult();
	}
}
