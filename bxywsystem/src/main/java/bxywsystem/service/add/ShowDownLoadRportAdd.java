package bxywsystem.service.add;

import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import bxywsystem.dto.condition.BXYWDownload_Condition;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IAdd;

public class ShowDownLoadRportAdd implements IAdd{

	@SuppressWarnings("unchecked")
	public void Add() throws Exception {
        
		BXYWDownload_Condition bXYWDownload_Condition = (BXYWDownload_Condition)RequestManager.getTOject();

		if(bXYWDownload_Condition != null){
			
			BXYWDownload_Condition objectDestination = new BXYWDownload_Condition();
			ReflectOperation.CopyFieldValue(bXYWDownload_Condition, objectDestination);
			((Map<String,Object>)ServletActionContext.getContext().get("request")).put("bXYWDownload_Condition", objectDestination);
			
			bXYWDownload_Condition.setStrJRJGCode(null);
			bXYWDownload_Condition.setStrReportCode(null);
			bXYWDownload_Condition.setSJBGRQ(null);
			bXYWDownload_Condition.setBBZRLX(null);
			
			if(StringUtils.isBlank(bXYWDownload_Condition.getStrReportType())){
				bXYWDownload_Condition.setStrReportType("15");
			}
		}
	}

}
