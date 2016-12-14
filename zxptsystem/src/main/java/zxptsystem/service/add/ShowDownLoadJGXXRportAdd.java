package zxptsystem.service.add;

import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import zxptsystem.dto.condition.JGXXDownload_Condition;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IAdd;


public class ShowDownLoadJGXXRportAdd implements IAdd{

	@SuppressWarnings("unchecked")
	public void Add() throws Exception {
        
		JGXXDownload_Condition jGXXDownload_Condition = (JGXXDownload_Condition)RequestManager.getTOject();

		if(jGXXDownload_Condition != null){
			
			JGXXDownload_Condition objectDestination = new JGXXDownload_Condition();
			ReflectOperation.CopyFieldValue(jGXXDownload_Condition, objectDestination);
			((Map<String,Object>)ServletActionContext.getContext().get("request")).put("jGXXDownload_Condition", objectDestination);
			
			jGXXDownload_Condition.setStrJgJRJGCode(null);
			jGXXDownload_Condition.setStrJgReportCode(null);
			jGXXDownload_Condition.setXXGXRQ(null);
		}
		if(StringUtils.isBlank(jGXXDownload_Condition.getStrJgReportType())){
			jGXXDownload_Condition.setStrJgReportType("51");
		}
	}

}