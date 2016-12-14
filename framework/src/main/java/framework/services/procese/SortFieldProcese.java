package framework.services.procese;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import framework.helper.ReflectOperation;
import framework.interfaces.ApplicationManager;
import framework.interfaces.RequestManager;

import framework.services.interfaces.IProcese;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;
public class SortFieldProcese implements IProcese{

	public Object Procese(Object serviceResult) throws Exception {
		
		String tName = RequestManager.getTName();
		ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;

		//排序，把隐藏字段放后，方便UI操作
		List<ShowFieldValue> showFieldValueList = showSaveOrUpdate.getShowFieldValueList();
		List<ShowFieldValue> sortedShowFieldValueList = new ArrayList<ShowFieldValue>();
		List<ShowFieldValue> hiddenShowFieldValueList = new ArrayList<ShowFieldValue>();
		Field primaryKeyField = ReflectOperation.getPrimaryKeyField(tName);
		for(ShowFieldValue showFieldValue : showFieldValueList){
			if(showFieldValue.getShowField().getSingleTag().equals(ApplicationManager.getSingleTagHidden())){
				hiddenShowFieldValueList.add(showFieldValue);
			}
			else{
				sortedShowFieldValueList.add(showFieldValue);
			}
			//通过配置设置只读属性
			if(!primaryKeyField.getName().equals(showFieldValue.getShowField().getFieldName())){
				showFieldValue.setReadOnly(showFieldValue.getShowField().isUpdateReadOnly());
			}
		}
		for(ShowFieldValue showFieldValue : hiddenShowFieldValueList){
			sortedShowFieldValueList.add(showFieldValue);
		}
		showSaveOrUpdate.setShowFieldValueList(sortedShowFieldValueList);

		return showSaveOrUpdate;
	}

}
