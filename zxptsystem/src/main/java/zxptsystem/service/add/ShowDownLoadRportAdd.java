package zxptsystem.service.add;

import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import zxptsystem.dto.condition.QYZXDownload_Condition;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IAdd;

public class ShowDownLoadRportAdd implements IAdd{

	@SuppressWarnings("unchecked")
	public void Add() throws Exception {
        
		QYZXDownload_Condition qYZXDownload_Condition = (QYZXDownload_Condition)RequestManager.getTOject();

		if(qYZXDownload_Condition != null){
			
			QYZXDownload_Condition objectDestination = new QYZXDownload_Condition();
			ReflectOperation.CopyFieldValue(qYZXDownload_Condition, objectDestination);
			((Map<String,Object>)ServletActionContext.getContext().get("request")).put("qYZXDownload_Condition", objectDestination);
			
			qYZXDownload_Condition.setYWFSRQ(null);
			qYZXDownload_Condition.setStrJRJGCode(null);
			qYZXDownload_Condition.setStrReportCode(null);
			
			if(StringUtils.isBlank(qYZXDownload_Condition.getStrReportType())){
				qYZXDownload_Condition.setStrReportType("11");
			}
		}
	}

}
