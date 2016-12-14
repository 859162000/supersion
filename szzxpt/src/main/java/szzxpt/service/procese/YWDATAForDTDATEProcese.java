package szzxpt.service.procese;

import framework.interfaces.ApplicationManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldCondition;
import framework.show.ShowList;

public class YWDATAForDTDATEProcese implements IProcese {

	public Object Procese(Object serviceResult) throws Exception {
		
		ShowList showList=(ShowList)serviceResult;
		
		for(ShowFieldCondition showFieldCondition : showList.getShowCondition()){
			if(showFieldCondition.getFieldName().equals("startDate") || showFieldCondition.getFieldName().equals("endDate")){
				showFieldCondition.setSingleTag(ApplicationManager.getSingleTagDateNoSlash());
			}
		}
		
		return serviceResult;
	}

}
