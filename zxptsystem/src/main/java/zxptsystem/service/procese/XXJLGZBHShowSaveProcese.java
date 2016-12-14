package zxptsystem.service.procese;

import framework.services.interfaces.IProcese;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;

public class XXJLGZBHShowSaveProcese implements IProcese{

	public Object Procese(Object serviceResult) throws Exception{
		ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;
		for(ShowFieldValue showFieldValue : showSaveOrUpdate.getShowFieldValueList()){
			if(showFieldValue.getShowField().getFieldName().equals("XXJLGZBH")){
				showFieldValue.setFieldValue("00000000000000000000");
				break;
			}
		}

		return serviceResult;
	}
}
