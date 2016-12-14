package zxptsystem.service.procese;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldCondition;
import framework.show.ShowList;

public class ShowConditionResetProcese implements IProcese{

	@Override
	public Object Procese(Object serviceResult) throws Exception {
		// TODO Auto-generated method stub
		ShowList showlist = (ShowList)serviceResult;
		Object tObject_Condition =RequestManager.getTOject();
		
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
		
		for(ShowFieldCondition showFieldCondition : showlist.getShowCondition()){
			
			if(showFieldCondition.getFieldName().equals("startDate")){
				showFieldCondition.setSingleTag("dateFieldNoSlash");
				ReflectOperation.setFieldValue(tObject_Condition, "startDate",df.format(strBeginDateTo));
				
			}else if(showFieldCondition.getFieldName().equals("endDate")){
				showFieldCondition.setSingleTag("dateFieldNoSlash");
				ReflectOperation.setFieldValue(tObject_Condition, "endDate",df.format(strEndDateTo));
			}else{
				ReflectOperation.setFieldValue(tObject_Condition, showFieldCondition.getFieldName(),null);
			}
		}
		return serviceResult;
	}

}
