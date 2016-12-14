package szzxpt.service.add;

import java.util.Map;
import org.apache.struts2.ServletActionContext;
import szzxpt.dto.condition.WHZHDownload_Condition;
import zxptsystem.dto.condition.QYZXDownload_Condition;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IAdd;

public class ShowWHZHDownLoadRportAdd implements IAdd{

	@SuppressWarnings("unchecked")
	public void Add() throws Exception {
        
		WHZHDownload_Condition wHZHDownload_Condition = (WHZHDownload_Condition)RequestManager.getTOject();

		if(wHZHDownload_Condition != null){
			
			WHZHDownload_Condition objectDestination = new WHZHDownload_Condition();
			ReflectOperation.CopyFieldValue(wHZHDownload_Condition, objectDestination);
			((Map<String,Object>)ServletActionContext.getContext().get("request")).put("wHZHDownload_Condition", objectDestination);
			
			wHZHDownload_Condition.setStrJRJGCode(null);
		}
	}

}
