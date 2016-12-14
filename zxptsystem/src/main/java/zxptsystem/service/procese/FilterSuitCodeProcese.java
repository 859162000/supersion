package zxptsystem.service.procese;

import java.util.HashMap;
import java.util.Map;

import framework.services.interfaces.IProcese;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;

public class FilterSuitCodeProcese implements IProcese {

	@Override
	public Object Procese(Object serviceResult) throws Exception {
		ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;
		for(ShowFieldValue showFieldValue : showSaveOrUpdate.getShowFieldValueList()){
			if(showFieldValue.getShowField().getFieldName().equals("strSuit")){
				Map<String,String> oldMap = (Map<String,String>)showFieldValue.getTag();
				Map<String,String> newMap = new HashMap<String,String>();
				newMap.put("21", oldMap.get("21").toString());
				newMap.put("22", oldMap.get("22").toString());
				showFieldValue.setTag(newMap);
				break;
			}
		}
		return serviceResult;
	}

}
