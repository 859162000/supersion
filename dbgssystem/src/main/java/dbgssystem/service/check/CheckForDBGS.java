package dbgssystem.service.check;

import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;

import framework.services.interfaces.ICheckWithParam;
import framework.services.interfaces.MessageResult;

/**
 * 债权人及主合同信息段:当债权人为放贷机构时,债权人证件号码填企业征信系统为其配发的11位代码
 * @author xiajieli
 *
 */
public class CheckForDBGS implements ICheckWithParam{

	public MessageResult Check(Map<String, Object> mapObject) throws Exception {
		
		MessageResult messageResult = new MessageResult();
		
		Object ZQRLX=mapObject.get("ZQRLX");
		Object ZQRZJLX=mapObject.get("ZQRZJLX");
		Object ZQRZJHM=mapObject.get("ZQRZJHM");
		
		if(ZQRLX!=null && !StringUtils.isBlank(ZQRLX.toString()) && ZQRZJHM!=null && !StringUtils.isBlank(ZQRZJHM.toString())){
			if(ZQRLX.equals("1") && ZQRZJLX.toString().equals("z") && ZQRZJHM.toString().length()!=11){
				messageResult.setSuccess(false);
				messageResult.getMessageSet().add("当债权人为放贷机构时,债权人证件号码填企业征信系统为其配发的11位代码"); 
			}
		}
		
		return messageResult;
	}
	


}