package zxptsystem.service.procese;

import framework.interfaces.ApplicationManager;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;

public class XXJLGZBHShowSaveOrUpdateProcese implements IProcese{

	public Object Procese(Object serviceResult) throws Exception{
		if(RequestManager.getActionName().contains("AutoDTO_QY_")){
			ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;
			for(ShowFieldValue showFieldValue : showSaveOrUpdate.getShowFieldValueList()){
				if(showFieldValue.getShowField().getFieldName().equals("XXJLGZBH")){
					showFieldValue.getShowField().setSingleTag(ApplicationManager.getSingleTagHidden());
					break;
				}
			}
		}
		
		return serviceResult;
	}
}
