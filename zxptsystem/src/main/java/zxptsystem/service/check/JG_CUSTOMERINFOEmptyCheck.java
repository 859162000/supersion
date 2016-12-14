package zxptsystem.service.check;

import org.apache.commons.lang.StringUtils;
import zxptsystem.dto.condition.VIEW_JG_CUSTOMERINFO_Condition;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;
/**
 * 机构客户信息数据查询空校验
 * @author Transino
 *
 */
public class JG_CUSTOMERINFOEmptyCheck implements ICheck {

	public MessageResult Check() throws Exception {
		MessageResult messageResult = new MessageResult();
		VIEW_JG_CUSTOMERINFO_Condition vIEW_JG_CUSTOMERINFO_Condition=(VIEW_JG_CUSTOMERINFO_Condition)RequestManager.getTOject();
		if(StringUtils.isBlank(vIEW_JG_CUSTOMERINFO_Condition.getJGDM())){
			messageResult.setSuccess(false);
			messageResult.getMessageSet().add("机构代码不能为空");   	
		}
		 if(StringUtils.isBlank(vIEW_JG_CUSTOMERINFO_Condition.getKHH())){
			messageResult.setSuccess(false);
			messageResult.getMessageSet().add("客户号不能为空");
		}
		 if(StringUtils.isBlank(vIEW_JG_CUSTOMERINFO_Condition.getJGZWMC())){
			messageResult.setSuccess(false);
			messageResult.getMessageSet().add("机构中文名称不能为空");
		}
		 if(StringUtils.isBlank(vIEW_JG_CUSTOMERINFO_Condition.getKHLX())){
			messageResult.setSuccess(false);
			messageResult.getMessageSet().add("客户类型不能为空");
		}
		 if(vIEW_JG_CUSTOMERINFO_Condition.getXXGXRQ()==null){
			messageResult.setSuccess(false);
			messageResult.getMessageSet().add("信息更新日期不能为空");
		}
		 messageResult.AlertTranslate();
		return messageResult;
	}

}
