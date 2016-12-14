package zxptsystem.service.check;

import org.apache.commons.lang.StringUtils;
import zxptsystem.dto.condition.VIEW_GR_LOANDATA_Condition;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;
/**
 * 个人征信-贷款信息数据查询空校验
 * @author Transino
 *
 */
public class GR_LOANDATAEmptyCheck implements ICheck {

	public MessageResult Check() throws Exception {
		MessageResult messageResult = new MessageResult();
		VIEW_GR_LOANDATA_Condition vIEW_GR_LOANDATA_Condition=(VIEW_GR_LOANDATA_Condition)RequestManager.getTOject();
		if(StringUtils.isBlank(vIEW_GR_LOANDATA_Condition.getJRJGDM().toString())){
			messageResult.setSuccess(false);
			messageResult.getMessageSet().add("金融机构代码不能为空");   
			
		} if(StringUtils.isBlank(vIEW_GR_LOANDATA_Condition.getYWH())){
			messageResult.setSuccess(false);
			messageResult.getMessageSet().add("业务号不能为空");
		}
		 if(StringUtils.isBlank(vIEW_GR_LOANDATA_Condition.getXM())){
			messageResult.setSuccess(false);
			messageResult.getMessageSet().add("姓名不能为空");
		}
		 if(StringUtils.isBlank(vIEW_GR_LOANDATA_Condition.getZJHM())){
			messageResult.setSuccess(false);
			messageResult.getMessageSet().add("证件号码不能为空");
		}
		 if(StringUtils.isBlank(vIEW_GR_LOANDATA_Condition.getStartDate())){
			messageResult.setSuccess(false);
			messageResult.getMessageSet().add("起始结算、应还款日期不能为空");
		}
		 if(StringUtils.isBlank(vIEW_GR_LOANDATA_Condition.getEndDate())){
			messageResult.setSuccess(false);
			messageResult.getMessageSet().add("终止结算、应还款日期不能为空");
		}
		messageResult.AlertTranslate();
		return messageResult;
	}

}
