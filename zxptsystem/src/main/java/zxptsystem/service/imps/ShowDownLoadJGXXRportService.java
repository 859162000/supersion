package zxptsystem.service.imps;

import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import report.helper.ReportInstUtils;

import extend.helper.HelpTool;
import zxptsystem.dto.JGXXDownload;
import zxptsystem.dto.condition.JGXXDownload_Condition;
import framework.interfaces.RequestManager;
import framework.services.imps.SingleObjectShowListService;


public class ShowDownLoadJGXXRportService extends SingleObjectShowListService{
	
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {
		
		super.initSuccessResult();
		
		List<JGXXDownload> jGXXDownloadList = (List<JGXXDownload>)this.getServiceResult();

		JGXXDownload_Condition jGXXDownload_Condition = (JGXXDownload_Condition)RequestManager.getTOject();
	
		
		JGXXDownload_Condition objectDestination = (JGXXDownload_Condition)((Map<String,Object>)ServletActionContext.getContext().get("request")).get("jGXXDownload_Condition");
		
		if(objectDestination != null){
			jGXXDownload_Condition.setStrJgJRJGCode(objectDestination.getStrJgJRJGCode());
			jGXXDownload_Condition.setStrJgReportCode(objectDestination.getStrJgReportCode());
			
			if(jGXXDownload_Condition.getXXGXRQ()==null){
				jGXXDownload_Condition.setXXGXRQ(HelpTool.getBeforeDate("yyyyMMdd"));
			}
			
			if(jGXXDownload_Condition.getStrJgJRJGCode()==null){
				jGXXDownload_Condition.setStrJgJRJGCode(ReportInstUtils.getCurrentUserInJRJGCode("21,24"));
			}
		}
		
		this.setServiceResult(jGXXDownloadList);
	}
}