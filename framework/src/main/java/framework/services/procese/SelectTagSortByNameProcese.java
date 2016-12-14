package framework.services.procese;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.interfaces.TActionRule;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldCondition;
import framework.show.ShowFieldValue;
import framework.show.ShowList;
import framework.show.ShowSaveOrUpdate;

public class SelectTagSortByNameProcese implements IProcese{

	@SuppressWarnings("unchecked")
	public Object Procese(Object serviceResult) throws Exception {
		
		Class<?> type = Class.forName(RequestManager.getTName());
		
		if(serviceResult.getClass().equals(ShowList.class)){
			ShowList showList = (ShowList)serviceResult;
			
			for(ShowFieldCondition showFieldCondition :showList.getShowCondition()){
				if(showFieldCondition.getTag() != null){
					
					if(FrameworkFactory.CreateClass(TActionRule.getConditionClassName(type.getName())) == null){
						if(!ReflectOperation.isBaseType(ReflectOperation.getReflectField(type,showFieldCondition.getFieldName()).getType())){
							Map<String,String> tagMap = (Map<String,String>)showFieldCondition.getTag();
							Map<String,String> sortedMap = sortMapByValue(tagMap);
							showFieldCondition.setTag(sortedMap);
						}
					}
					else{
						if(!ReflectOperation.isBaseType(ReflectOperation.getReflectField(type,showFieldCondition.getTarget()).getType())){
							Map<String,String> tagMap = (Map<String,String>)showFieldCondition.getTag();
							Map<String,String> sortedMap = sortMapByValue(tagMap);
							showFieldCondition.setTag(sortedMap);
						}
					}
				}
			}
		}
		else if(serviceResult.getClass().equals(ShowSaveOrUpdate.class)){
			ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;
			
			for(ShowFieldValue showFieldValue : showSaveOrUpdate.getShowFieldValueList()){
				if(showFieldValue.getTag() != null){
					if(!ReflectOperation.isBaseType(ReflectOperation.getReflectField(type,showFieldValue.getShowField().getFieldName()).getType())){
						Map<String,String> tagMap = (Map<String,String>)showFieldValue.getTag();
						Map<String,String> sortedMap = sortMapByValue(tagMap);
						showFieldValue.setTag(sortedMap);
					}
				}
			}
		}
		return serviceResult;
	}
	
	public Map<String, String> sortMapByValue(Map<String, String> oriMap) {
		
		Map<String, String> sortedMap = new LinkedHashMap<String, String>();
		if (oriMap != null && !oriMap.isEmpty()) {
			List<Map.Entry<String, String>> entryList = new ArrayList<Map.Entry<String, String>>(oriMap.entrySet());
			Collections.sort(entryList,new Comparator<Map.Entry<String, String>>() {
			public int compare(Entry<String, String> entry1,Entry<String, String> entry2) {
				return entry1.getValue().toString().compareTo(entry2.getValue().toString());
				}
		});
			Iterator<Map.Entry<String, String>> iter = entryList.iterator();
			Map.Entry<String, String> tmpEntry = null;
			while (iter.hasNext()) {
				tmpEntry = iter.next();
				sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
			}
		}
		
		 
		return sortedMap;
	}
	
}