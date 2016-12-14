package zxptsystem.service.imps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import report.helper.ReportInstUtils;

import extend.helper.HelpTool;
import zxptsystem.dto.GRZXDownload;
import zxptsystem.dto.condition.GRZXDownload_Condition;
import framework.interfaces.RequestManager;
import framework.services.imps.SingleObjectShowListService;

public class ShowDownLoadGRZXRportService extends SingleObjectShowListService{
	
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {
		
		super.initSuccessResult();
		
		List<GRZXDownload> gRZXDownloadList = (List<GRZXDownload>)this.getServiceResult();

		GRZXDownload_Condition gRZXDownload_Condition = (GRZXDownload_Condition)RequestManager.getTOject();
		
		if(gRZXDownload_Condition == null || StringUtils.isBlank(gRZXDownload_Condition.getStrGrReportType())){
			gRZXDownloadList = new ArrayList<GRZXDownload>();
		}
		
		GRZXDownload_Condition objectDestination = (GRZXDownload_Condition)((Map<String,Object>)ServletActionContext.getContext().get("request")).get("gRZXDownload_Condition");
		
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
