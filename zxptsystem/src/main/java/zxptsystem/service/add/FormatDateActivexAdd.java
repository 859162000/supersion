package zxptsystem.service.add;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.struts2.ServletActionContext;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.interfaces.TActionRule;
import framework.services.interfaces.IAdd;

public class FormatDateActivexAdd implements IAdd {

	@Override
	public void Add() throws Exception {
		
		if(ServletActionContext.getContext().getSession().get("FormatDateActivexAdd") == null){
			
			Object tObject_Condition =SessionManager.getTCondition(RequestManager.getTName());
			Object rObject_Condition =RequestManager.getTOject();
			if(tObject_Condition == null){
				tObject_Condition = FrameworkFactory.CreateClass(TActionRule.getConditionClassName(RequestManager.getTName()));
			}
			if(tObject_Condition == null){
				tObject_Condition = Class.forName(RequestManager.getTName()).newInstance();
			}
			SessionManager.setTCondition(tObject_Condition, RequestManager.getTName());
			
			SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
			Calendar ca= Calendar.getInstance();
			int month= ca.get(ca.MONTH);
			Date strEndDateTo =ca.getTime(); 
			Date strBeginDateTo =null;
			ca.set(ca.MONTH, month);
			if(ca.get(ca.DAY_OF_MONTH)==1){
				ca.set(ca.MONTH, month-1);
				ca.set(ca.DAY_OF_MONTH, ca.getActualMaximum(ca.DAY_OF_MONTH));
				strEndDateTo =ca.getTime();
				
				ca.set(ca.MONTH, month-1);
				ca.set(ca.DAY_OF_MONTH, ca.getActualMinimum(ca.DAY_OF_MONTH));
				strBeginDateTo=ca.getTime();
			}else{
				ca.set(ca.MONTH, month);
				ca.set(ca.DAY_OF_MONTH, ca.getActualMinimum(ca.DAY_OF_MONTH));
				strBeginDateTo =ca.getTime();
			}
			
			if(ReflectOperation.getFieldByName(tObject_Condition.getClass().getName(), "startDate")!=null){
				ReflectOperation.setFieldValue(tObject_Condition, "startDate",df.format(strBeginDateTo));
				ReflectOperation.setFieldValue(rObject_Condition, "startDate",df.format(strBeginDateTo));
			}
			
			if(ReflectOperation.getFieldByName(tObject_Condition.getClass().getName(), "endDate")!=null){
				ReflectOperation.setFieldValue(tObject_Condition, "endDate",df.format(strEndDateTo));
				ReflectOperation.setFieldValue(rObject_Condition, "endDate",df.format(strEndDateTo));
			}
			
			ServletActionContext.getContext().getSession().put("FormatDateActivexAdd", true);
		}
		
	}

}
