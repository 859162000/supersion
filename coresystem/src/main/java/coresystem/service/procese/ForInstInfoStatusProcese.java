package coresystem.service.procese;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import framework.services.interfaces.IProcese;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;

public class ForInstInfoStatusProcese implements IProcese {

	public Object Procese(Object serviceResult) throws Exception {
		ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;
		List<ShowFieldValue> showFieldValueList = showSaveOrUpdate.getShowFieldValueList();
		for (ShowFieldValue showFieldValue : showFieldValueList) {
			if(showFieldValue.getShowField().getFieldName().equals("strInstStatus")){
				LinkedHashMap<String,String> linkedHashMap=(LinkedHashMap<String, String>) showFieldValue.getTag();
				LinkedHashMap<String,String> temPTagMap = new LinkedHashMap<String,String>();
				for (Map.Entry<String, String> entry : linkedHashMap.entrySet()) {
					if(!entry.getKey().equals("4")){
						temPTagMap.put(entry.getKey(), entry.getValue());
					}
				}
				showFieldValue.setTag(temPTagMap);
			}
		}
		return showSaveOrUpdate;
	}
}
