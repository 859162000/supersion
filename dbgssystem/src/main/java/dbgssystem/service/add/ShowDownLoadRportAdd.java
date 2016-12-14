package dbgssystem.service.add;

import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import dbgssystem.dto.condition.DBGSDownload_Condition;


import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IAdd;
/**
 * 显示担保业务生成报文条件添加
 * @author xiajieli
 *
 */
public class ShowDownLoadRportAdd implements IAdd{

	@SuppressWarnings("unchecked")
	public void Add() throws Exception {
        
		DBGSDownload_Condition dBGSDownload_Condition = (DBGSDownload_Condition)RequestManager.getTOject();

		if(dBGSDownload_Condition != null){
			
			DBGSDownload_Condition objectDestination = new DBGSDownload_Condition();
			ReflectOperation.CopyFieldValue(dBGSDownload_Condition, objectDestination);
			((Map<String,Object>)ServletActionContext.getContext().get("request")).put("dBGSDownload_Condition", objectDestination);
			
			dBGSDownload_Condition.setStrJRJGCode(null);
			dBGSDownload_Condition.setStrReportCode(null);
			dBGSDownload_Condition.setSJBGRQ(null);
			
			if(StringUtils.isBlank(dBGSDownload_Condition.getStrReportType())){
				dBGSDownload_Condition.setStrReportType("15");
			}
		}
	}

}
