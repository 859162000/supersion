package zxptsystem.service.imps;

import java.util.Map;

import org.apache.struts2.ServletActionContext;

import framework.interfaces.RequestManager;
import framework.services.imps.ShowViewStatisticalService;


public class AccordingInstInfoShowViewService extends ShowViewStatisticalService {
	
	@Override
	public String getFixedFieldConditionSql(){
		
		Object instInfoCode = ((Map<String, Object>)ServletActionContext.getContext().getSession()).get(RequestManager.getActionName()+"instinfo");
		String strCondition = " INSTINFO = '"+instInfoCode+"'";
		
		return strCondition;
	}
}
