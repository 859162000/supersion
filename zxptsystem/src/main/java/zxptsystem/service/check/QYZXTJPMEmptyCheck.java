package zxptsystem.service.check;

import org.apache.commons.lang.StringUtils;

import zxptsystem.dto.condition.PROC_QYZXTJPM_Condition;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;
/**
 * 企业征信统计排名空校验
 * @author Transino
 *
 */
public class QYZXTJPMEmptyCheck implements ICheck {

	public MessageResult Check() throws Exception {
		MessageResult messageResult = new MessageResult();
		PROC_QYZXTJPM_Condition pROC_QYZXTJPM_Condition=(PROC_QYZXTJPM_Condition)RequestManager.getTOject();
		if(StringUtils.isBlank(pROC_QYZXTJPM_Condition.getBEGINDATE())){
			messageResult.setSuccess(false);
			messageResult.getMessageSet().add("开始时间不能为空");   
			
		} if(StringUtils.isBlank(pROC_QYZXTJPM_Condition.getENDDATE())){
			messageResult.setSuccess(false);
			messageResult.getMessageSet().add("结束时间不能为空");
		}
		 if(StringUtils.isBlank(pROC_QYZXTJPM_Condition.getJRJGDM().getStrReportInstCode().toString())){
			messageResult.setSuccess(false);
			messageResult.getMessageSet().add("报送机构不能为空");
		}
	     messageResult.AlertTranslate();
		return messageResult;
	}

}
