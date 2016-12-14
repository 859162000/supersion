package zxptsystem.service.imps;

import org.apache.commons.lang.StringUtils;

import zxptsystem.dto.condition.PROC_PEndsData_Check_Condition;
import framework.interfaces.RequestManager;
import framework.services.imps.ExportForProcedureService;
import framework.services.interfaces.MessageResult;

/**
 * 通过选择条件作为存储过程参数查询出数据然后导出excle或者文本文件
 * @author xiajieli
 *
 */
public class GRZXEndsData_CheckForProcedureService extends ExportForProcedureService{

	@Override
	public void initSuccessResult() throws Exception {
		PROC_PEndsData_Check_Condition pROC_PEndsData_Check_Condition = (PROC_PEndsData_Check_Condition)RequestManager.getTOject();
		
		MessageResult messageResult = new MessageResult();
		//先校验是否选择条件参数
		if(StringUtils.isBlank(pROC_PEndsData_Check_Condition.getP_REPORTINST().getStrReportInstCode())){
			messageResult.getMessageSet().add("报送机构不能为空");
		}
		if(StringUtils.isBlank(pROC_PEndsData_Check_Condition.getP_ENDMONTH())){
			messageResult.getMessageSet().add("数据截止日期不能为空");
		}
		if (messageResult.getMessageSet().size() > 0) {
			messageResult.setSuccess(false);
			messageResult.AlertTranslate();
			this.setServiceResult(messageResult);
		}
		else{//导出excle数据
			String strFileName = pROC_PEndsData_Check_Condition.getP_REPORTINST().getStrReportInstCode() + "_" + pROC_PEndsData_Check_Condition.getP_ENDMONTH() + ".xls";
			this.setFileName(strFileName);
			super.initSuccessResult();
		}
	}
	
}
