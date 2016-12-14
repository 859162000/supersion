package zxptsystem.service.add;

import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import zxptsystem.dto.condition.GRZXDownload_Condition;
import zxptsystem.dto.condition.OverRideGRZXDownload_Condition;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IAdd;

public class OverRideShowDownLoadGRZXRportAdd implements IAdd{

	@SuppressWarnings("unchecked")
	public void Add() throws Exception {
		OverRideGRZXDownload_Condition gRZXDownload_Condition = (OverRideGRZXDownload_Condition)RequestManager.getTOject();

		if(gRZXDownload_Condition != null){
			
			OverRideGRZXDownload_Condition objectDestination = new OverRideGRZXDownload_Condition();
			ReflectOperation.CopyFieldValue(gRZXDownload_Condition, objectDestination);
			((Map<String,Object>)ServletActionContext.getContext().get("request")).put("overRideGRZXDownload_Condition", objectDestination);
			
			gRZXDownload_Condition.setStrGrJRJGCode(null);
			gRZXDownload_Condition.setStrGrReportCode(null);
			gRZXDownload_Condition.setStrGRSJFSNY(null);
			
			if(StringUtils.isBlank(gRZXDownload_Condition.getStrGrReportType())){
				gRZXDownload_Condition.setStrGrReportType("1");
			}
		}
	}

}
