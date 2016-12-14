package zxptsystem.service.procese;

import framework.services.interfaces.IProcese;
import framework.show.ShowFieldCondition;
import framework.show.ShowList;

public class FormatDateActivexProcese implements IProcese{

	@Override
	public Object Procese(Object serviceResult) throws Exception {
		
		ShowList showlist = (ShowList)serviceResult;
		for(ShowFieldCondition showFieldCondition : showlist.getShowCondition()){
			if(showFieldCondition.getFieldName().equals("startDate")){
				showFieldCondition.setSingleTag("dateFieldNoSlash");
				
			}else if(showFieldCondition.getFieldName().equals("endDate")){
				showFieldCondition.setSingleTag("dateFieldNoSlash");
			}
		}
		
		return serviceResult;
	}

}
