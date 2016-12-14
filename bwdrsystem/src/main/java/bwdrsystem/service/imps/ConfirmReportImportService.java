package bwdrsystem.service.imps;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import bwdrsystem.dto.ReportFileImportLog;

import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseDTService;
import framework.services.interfaces.MessageResult;

/**
 * 
 * @description <p>确认是否导入报文文件数据service</P>
 * @createTime 2016-8-15 下午06:22:34
 * @updateTime 2016-8-15 下午06:22:34
 * @author Liutao
 * @updater Liutao
 */
public class ConfirmReportImportService extends BaseDTService {

	@Override
	public void initSuccessResult() throws Exception {
		MessageResult messageResult = new MessageResult();
		try {
			super.initDao();
			DetachedCriteria dc = DetachedCriteria.forClass(ReportFileImportLog.class);
			dc.add(Restrictions.eq("result", "1"));
			dc.add(Restrictions.eq("reportFileName", RequestManager.getReportCheckParam().get("uploadFileFileName")));
			List<ReportFileImportLog> logList = (List<ReportFileImportLog>) ((IParamObjectResultExecute) this.getBaseDaoObject()).paramObjectResultExecute(new Object[] {dc, null});
			if(logList != null && !logList.isEmpty()) {
				messageResult.setSuccess(false);
				messageResult.setMessage("系统中已经存在导入成功的数据，是否继续导入");
			} else {
				messageResult.setSuccess(true);
			}
		} catch (Exception e) {
			messageResult.setSuccess(false);
			messageResult.setMessage("系统异常");
		}
		
		this.setServiceResult(messageResult);
	}
	
}
