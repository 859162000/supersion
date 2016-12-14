package dbgssystem.service.imps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import report.helper.ReportInstUtils;

import dbgssystem.dto.DBGSDownload;
import dbgssystem.dto.condition.DBGSDownload_Condition;
import extend.helper.HelpTool;

import framework.interfaces.RequestManager;
import framework.services.imps.SingleObjectShowListService;
/**
 * 显示担保业务生成报文条件界面
 * @author xiajieli
 *
 */
public class ShowDownLoadDBGSRportService extends SingleObjectShowListService{
	
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {
		
		super.initSuccessResult();
		
		List<DBGSDownload> dBGSDownloadList = (List<DBGSDownload>)this.getServiceResult();

		DBGSDownload_Condition dBGSDownload_Condition = (DBGSDownload_Condition)RequestManager.getTOject();
		
		if(dBGSDownload_Condition == null || StringUtils.isBlank(dBGSDownload_Condition.getStrReportType())){
			dBGSDownloadList = new ArrayList<DBGSDownload>();
		}
		
		DBGSDownload_Condition objectDestination = (DBGSDownload_Condition)((Map<String,Object>)ServletActionContext.getContext().get("request")).get("dBGSDownload_Condition");
		
		if(objectDestination != null){
			dBGSDownload_Condition.setStrJRJGCode(objectDestination.getStrJRJGCode());
			dBGSDownload_Condition.setStrReportCode(objectDestination.getStrReportCode());
			dBGSDownload_Condition.setSJBGRQ(objectDestination.getSJBGRQ());
			
			if(dBGSDownload_Condition.getSJBGRQ()==null){
				dBGSDownload_Condition.setSJBGRQ(HelpTool.getBeforeDate("yyyy-MM-dd"));
			}
			
			if(dBGSDownload_Condition.getStrJRJGCode()==null){
				dBGSDownload_Condition.setStrJRJGCode(ReportInstUtils.getCurrentUserInJRJGCode("23"));
			}
		}
		
		this.setServiceResult(dBGSDownloadList);
	}
}
