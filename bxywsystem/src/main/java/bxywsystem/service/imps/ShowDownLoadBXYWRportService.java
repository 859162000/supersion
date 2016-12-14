package bxywsystem.service.imps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import report.helper.ReportInstUtils;


import bxywsystem.dto.BXYWDownload;
import bxywsystem.dto.condition.BXYWDownload_Condition;
import extend.helper.HelpTool;
import framework.interfaces.RequestManager;
import framework.services.imps.SingleObjectShowListService;

public class ShowDownLoadBXYWRportService extends SingleObjectShowListService{
	
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {
		
		super.initSuccessResult();
		
		List<BXYWDownload> bXYWDownloadList = (List<BXYWDownload>)this.getServiceResult();

		BXYWDownload_Condition bXYWDownload_Condition = (BXYWDownload_Condition)RequestManager.getTOject();
		
		if(bXYWDownload_Condition == null || StringUtils.isBlank(bXYWDownload_Condition.getStrReportType())){
			bXYWDownloadList = new ArrayList<BXYWDownload>();
		}
		
		BXYWDownload_Condition objectDestination = (BXYWDownload_Condition)((Map<String,Object>)ServletActionContext.getContext().get("request")).get("bXYWDownload_Condition");
		
		if(objectDestination != null){
			bXYWDownload_Condition.setStrJRJGCode(objectDestination.getStrJRJGCode());
			bXYWDownload_Condition.setStrReportCode(objectDestination.getStrReportCode());
			
			if(bXYWDownload_Condition.getSJBGRQ()==null){
				bXYWDownload_Condition.setSJBGRQ(HelpTool.getBeforeDate("yyyy-MM-dd"));
			}
			
			if(bXYWDownload_Condition.getStrJRJGCode()==null){
				bXYWDownload_Condition.setStrJRJGCode(ReportInstUtils.getCurrentUserInJRJGCode("25"));
			}
			
			if(bXYWDownload_Condition.getBBZRLX()==null){
				bXYWDownload_Condition.setBBZRLX("1");
			}
		}
		
		this.setServiceResult(bXYWDownloadList);
	}
}
