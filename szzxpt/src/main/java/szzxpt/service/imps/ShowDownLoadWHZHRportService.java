package szzxpt.service.imps;

import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import report.helper.ReportInstUtils;
import szzxpt.dto.WHZHDownload;
import szzxpt.dto.condition.WHZHDownload_Condition;


import framework.interfaces.RequestManager;
import framework.services.imps.SingleObjectShowListService;

public class ShowDownLoadWHZHRportService extends SingleObjectShowListService {

	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {
		
		super.initSuccessResult();
		
		List<WHZHDownload> jGXXDownloadList = (List<WHZHDownload>)this.getServiceResult();
		
		WHZHDownload_Condition wHZHDownload_Condition = (WHZHDownload_Condition)RequestManager.getTOject();
	
		WHZHDownload_Condition objectDestination = (WHZHDownload_Condition)((Map<String,Object>)ServletActionContext.getContext().get("request")).get("wHZHDownload_Condition");
		
		if(objectDestination != null){
			
			if(wHZHDownload_Condition.getStrJRJGCode()==null){
				wHZHDownload_Condition.setStrJRJGCode(ReportInstUtils.getCurrentUserInJRJGCode("WHZH"));
			}
		}
		
		this.setServiceResult(jGXXDownloadList);
	}
}
