package zxptsystem.service.procese;

import java.util.List;

import framework.helper.ReflectOperation;
import framework.interfaces.ApplicationManager;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldCondition;
import framework.show.ShowList;

public class ShowDownLoadGRZXRportProcese implements IProcese {

	public Object Procese(Object serviceResult) throws Exception {
		Object tObject=RequestManager.getTOject();
		ShowList showList=(ShowList)serviceResult;

		List<ShowFieldCondition> showFieldValueList = showList.getShowCondition();
		for(ShowFieldCondition showFieldCondition :showFieldValueList){
			if(showFieldCondition.getFieldName().equals("strGRSJFSNY")){
				if(!ReflectOperation.getFieldValue(tObject, "StrGrReportType").equals("1")){
					showFieldCondition.setSingleTag(ApplicationManager.getSingleTagHidden());
				}else{
					showFieldCondition.setSingleTag("dateFieldMonth");
					showFieldCondition.setParamName(showFieldCondition.getFieldName()); // ？
				}
			}
		}
		return serviceResult;
	}

}
