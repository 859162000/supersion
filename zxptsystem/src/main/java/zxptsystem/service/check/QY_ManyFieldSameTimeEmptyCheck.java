package zxptsystem.service.check;

import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;

import framework.services.interfaces.ICheckWithParam;
import framework.services.interfaces.MessageResult;
/**
 * 校验逻辑
 * 1、出资资本构成情况段中，当出资方为企业时，“出资方贷款卡编码”、“组织机构代码” 、“登记注册号”不能同时为空；
 *    当出资方为自然人时，“证件类型”、“证件号码”为必填
 * @author Administrator
 *
 */
public class QY_ManyFieldSameTimeEmptyCheck implements ICheckWithParam {
	
	@Override
	public MessageResult Check(Map<String, Object> mapObject) throws Exception {
		
		MessageResult messageResult = new MessageResult();
		String CZFDKKBM="";
		String ZZJGDM="";
		String DJZCH="";
		String ZJHM="";
		
		if(mapObject.get("CZFDKKBM")!=null){
			CZFDKKBM=mapObject.get("CZFDKKBM").toString();
		}
		if(mapObject.get("ZZJGDM")!=null){
			ZZJGDM=mapObject.get("ZZJGDM").toString();
		}
		if(mapObject.get("DJZCH")!=null){
			DJZCH=mapObject.get("DJZCH").toString();
		}
		if(mapObject.get("ZJHM")!=null){
			ZJHM=mapObject.get("ZJHM").toString();
		}
		
		if(StringUtils.isBlank(ZJHM)){
			if(StringUtils.isBlank(CZFDKKBM) && StringUtils.isBlank(ZZJGDM) && StringUtils.isBlank(DJZCH)){
				messageResult.setSuccess(false);
				messageResult.getMessageSet().add("当“证件号码”为空时，“出资方贷款卡编码”、“组织机构代码” 、“登记注册号”不能同时为空。");
			}
		}
		
		if(!StringUtils.isBlank(ZJHM)){
			if(!StringUtils.isBlank(CZFDKKBM) && !StringUtils.isBlank(ZZJGDM) && !StringUtils.isBlank(DJZCH)){
				messageResult.setSuccess(false);
				messageResult.getMessageSet().add("当“证件号码”不为空时，“出资方贷款卡编码”、“组织机构代码” 、“登记注册号”应为空。");
			}
		}
		
		if(!StringUtils.isBlank(CZFDKKBM) || !StringUtils.isBlank(ZZJGDM) || !StringUtils.isBlank(DJZCH)){
			if(!StringUtils.isBlank(ZJHM)){
				messageResult.setSuccess(false);
				messageResult.getMessageSet().add("当“出资方贷款卡编码”、“组织机构代码” 、“登记注册号”不同时为空时，“证件号码”应为空。");
			}
		}
		
		if(StringUtils.isBlank(CZFDKKBM) && StringUtils.isBlank(ZZJGDM) && StringUtils.isBlank(DJZCH)){
			if(StringUtils.isBlank(ZJHM)){
				messageResult.setSuccess(false);
				messageResult.getMessageSet().add("当“出资方贷款卡编码”、“组织机构代码” 、“登记注册号” 同时为空时，“证件号码”不能为空。");
			}
		}
				
		return messageResult;
		
	}


}
