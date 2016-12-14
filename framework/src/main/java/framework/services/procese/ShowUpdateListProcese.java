package framework.services.procese;

import java.lang.reflect.Field;
import java.util.List;

import framework.helper.ReflectOperation;
import framework.interfaces.ApplicationManager;
import framework.interfaces.RequestManager;
import framework.interfaces.TActionRule;
import framework.show.ShowField;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;

public class ShowUpdateListProcese extends ShowSaveProcese{

	@SuppressWarnings("unchecked")
	@Override
	public Object Procese(Object serviceResult) throws Exception {

		if(serviceResult == null){
			return super.Procese(serviceResult);
		}
		
		ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)super.Procese(null);
		
		Object tObject = RequestManager.getTOject();
		Class<?> type = tObject.getClass();

		List<ShowFieldValue> showFieldValueList = showSaveOrUpdate.getShowFieldValueList();
		
		for(ShowFieldValue showFieldValue : showFieldValueList){
			ShowField showField = showFieldValue.getShowField();
			Field field = ReflectOperation.getReflectField(type,showField.getFieldName());
			if(field != null){
				if(ReflectOperation.isPrimaryKeyField(field)){
					showFieldValueList.remove(showFieldValue);
					break;
				}
			}
		}
		
		for(ShowFieldValue showFieldValue : showFieldValueList){
			ShowField showField = showFieldValue.getShowField();
			Field field = ReflectOperation.getReflectField(type,showField.getFieldName());
			if(field != null){
				Object value = null;
				if(showField.getSingleTag().equals(ApplicationManager.getSingleTagMultipleSelect()) || showField.getSingleTag().equals(ApplicationManager.getSingleTagTreeField())){
					value = serviceResult;
				}
				else{
					value = ReflectOperation.getFieldValue(tObject, field.getName());
					showFieldValue.setSelectChange(true);
				}
				if(value != null){
					if(ReflectOperation.isBaseType(value)){
						showFieldValue.setFieldValue(value.toString());
					}
					else{
						if(showField.getSingleTag().equals(ApplicationManager.getSingleTagMultipleSelect()) || showField.getSingleTag().equals(ApplicationManager.getSingleTagTreeField())){
							
							List<Object> objectSet = (List<Object>)value;
							String[] values = new String[objectSet.size()];
							if(ReflectOperation.isBaseType(field.getType())){
								int i = 0;
								for(Object object : objectSet){
									Object value1 = ReflectOperation.getFieldValue(object,field.getName());
									values[i] = value1.toString();
									i++;
								}
							}
							else{
								Field PrimaryKeyField = ReflectOperation.getPrimaryKeyField(field.getType());
								int i = 0;
								for(Object object : objectSet){
									Object value1 = ReflectOperation.getFieldValue(object,field.getName());
									Object value2 = ReflectOperation.getFieldValue(value1,PrimaryKeyField.getName());
									values[i] = value2.toString();
									i++;
								}
							}
							
							showFieldValue.setFieldValue(values);
						}
						else{
							Object value1 = ReflectOperation.getFieldValue(value,showField.getFieldTargetPrimaryKey());
							if(value1 != null){
								showFieldValue.setFieldValue(value1.toString());
							}
						}
					}
				}
			}
		}

		showSaveOrUpdate.setShowUpdateListActionName(TActionRule.getCurrentLevelShowUpdateList(RequestManager.getTName()));
		
		return showSaveOrUpdate;
	}

}
