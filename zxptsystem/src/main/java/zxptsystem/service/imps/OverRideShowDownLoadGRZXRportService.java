package zxptsystem.service.imps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import report.helper.ReportInstUtils;

import extend.helper.HelpTool;
import zxptsystem.dto.GRZXDownload;
import zxptsystem.dto.OverRideGRZXDownload;
import zxptsystem.dto.condition.GRZXDownload_Condition;
import zxptsystem.dto.condition.OverRideGRZXDownload_Condition;
import framework.interfaces.RequestManager;
import framework.services.imps.SingleObjectShowListService;

public class OverRideShowDownLoadGRZXRportService extends SingleObjectShowListService{
	
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {
		
		super.initSuccessResult();
		
		List<OverRideGRZXDownload> gRZXDownloadList = (List<OverRideGRZXDownload>)this.getServiceResult();

		OverRideGRZXDownload_Condition gRZXDownload_Condition = (OverRideGRZXDownload_Condition)RequestManager.getTOject();
		
		if(gRZXDownload_Condition == null || StringUtils.isBlank(gRZXDownload_Condition.getStrGrReportType())){
			gRZXDownloadList = new ArrayList<OverRideGRZXDownload>();
		}
		
		OverRideGRZXDownload_Condition objectDestination = (OverRideGRZXDownload_Condition)((Map<String,Object>)ServletActionContext.getContext().get("request")).get("overRideGRZXDownload_Condition");
		
		if(objectDestination != null){
			gRZXDownload_Condition.setStrGrJRJGCode(objectDestination.getStrGrJRJGCode());
			gRZXDownload_Condition.setStrGrReportCode(objectDestination.getStrGrReportCode());
			
			if(gRZXDownload_Condition.getStrGRSJFSNY()==null){
				gRZXDownload_Condition.setStrGRSJFSNY((HelpTool.getBeforeDate("yyyyMM")));
			}
			
			if(gRZXDownload_Condition.getStrGrJRJGCode()==null){
				gRZXDownload_Condition.setStrGrJRJGCode(ReportInstUtils.getCurrentUserInJRJGCode("22"));
			}
		}
		
		this.setServiceResult(gRZXDownloadList);
	}
}
