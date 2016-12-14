package report.service.add;

import org.apache.struts2.ServletActionContext;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.interfaces.TActionRule;
import framework.services.interfaces.IAdd;

public class DefaultStatusValueMapAdd implements IAdd {

	@Override
	public void Add() throws Exception {
		
		if(ServletActionContext.getContext().getSession().get(RequestManager.getTName()+"DefaultStatusValueMapAdd") == null){
			
			Object tObject_Condition =SessionManager.getTCondition(RequestManager.getTName());
			Object rObject_Condition =RequestManager.getTOject();
			if(tObject_Condition == null){
				tObject_Condition = FrameworkFactory.CreateClass(TActionRule.getConditionClassName(RequestManager.getTName()));
			}
			if(tObject_Condition == null){
				tObject_Condition = Class.forName(RequestManager.getTName()).newInstance();
			}
			SessionManager.setTCondition(tObject_Condition, RequestManager.getTName());
			
			if(ReflectOperation.getFieldByName(tObject_Condition.getClass().getName(), "RPTSendType")!=null){
				ReflectOperation.setFieldValue(tObject_Condition, "RPTSendType","1,5");
				ReflectOperation.setFieldValue(rObject_Condition, "RPTSendType","1,5");
			}
			
			ServletActionContext.getContext().getSession().put(RequestManager.getTName()+"DefaultStatusValueMapAdd", true);
		}
	}

}
