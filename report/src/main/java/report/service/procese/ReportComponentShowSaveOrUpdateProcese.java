package report.service.procese;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import framework.helper.ReflectOperation;
import framework.interfaces.ApplicationManager;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.LogicParamManager;
import framework.show.ShowField;
import framework.show.ShowFieldValue;
import framework.show.ShowInstance;
import framework.show.ShowSaveOrUpdate;

public class ReportComponentShowSaveOrUpdateProcese  implements IProcese {

	public Object Procese(Object serviceResult) throws Exception {

		ShowSaveOrUpdate showSaveOrUpdate=(ShowSaveOrUpdate)serviceResult;
		for (ShowFieldValue showFieldValue : showSaveOrUpdate.getShowFieldValueList()) {
			String fieldName = showFieldValue.getShowField().getFieldName();
			if(fieldName.equals("RPTFeedbackType") || 
					fieldName.equals("RPTSubmitStatus") || 
					fieldName.equals("RPTVerifyType")|| 
					fieldName.equals("RPTSendType")|| 
					fieldName.equals("RPTCheckType") ||
					fieldName.equals("instInfo") ||
					fieldName.equals("operationUser") ||
					fieldName.equals("extend1") ||
					fieldName.equals("extend2") ||
					fieldName.equals("extend3") ||
					fieldName.equals("extend4") ||
					fieldName.equals("extend5") ||
					fieldName.equals("FOREIGNID") ||
					fieldName.equals("dtDate") ||
					fieldName.equals("lastUpdateDate")
			){
				showFieldValue.getShowField().setSingleTag(ApplicationManager.getSingleTagHidden());
			}
		}
		
		if(LogicParamManager.getDtoServicesBeanDefaultShowInstance() != null){
			if(LogicParamManager.getDtoServicesBeanMap()==null){
				List<Field> fieldList=ReflectOperation.getOneToManyField(RequestManager.getTName());
				for (Field field : fieldList) {
					Class<?> fieldType=ReflectOperation.getGenericClass(field.getGenericType());
					ShowInstance showInstance = ReflectOperation.getShowInstance(fieldType, LogicParamManager.getDtoServicesBeanDefaultShowInstance());
					for(ShowField ShowField : showInstance.getShowFieldList()){
						if(ShowField.getFieldName().equals("RPTFeedbackType") || 
							ShowField.getFieldName().equals("RPTSendType")|| 
							ShowField.getFieldName().equals("RPTCheckType") ||
							ShowField.getFieldName().equals("extend1") ||
							ShowField.getFieldName().equals("extend2") ||
							ShowField.getFieldName().equals("extend3") ||
							ShowField.getFieldName().equals("extend4") ||
							ShowField.getFieldName().equals("extend5") ||
							ShowField.getFieldName().equals("FOREIGNID") 
							){
							ShowField.setListVisible(false);
						}
					}
				}
			}
			else{
				Map<String,String> dtoServicesBeanMap=LogicParamManager.getDtoServicesBeanMap();
				List<Field> fieldList=ReflectOperation.getOneToManyField(RequestManager.getTName());
				for (Map.Entry<String, String> entry : dtoServicesBeanMap.entrySet()) {
					for (Field field : fieldList) {
						Class<?> fieldType=ReflectOperation.getGenericClass(field.getGenericType());
						if(entry.getKey().equals(fieldType.getName())){
							ShowInstance showInstance = ReflectOperation.getShowInstance(fieldType, LogicParamManager.getDtoServicesBeanDefaultShowInstance());
							for(ShowField ShowField : showInstance.getShowFieldList()){
								if(ShowField.getFieldName().equals("RPTFeedbackType") || 
									ShowField.getFieldName().equals("RPTSendType")|| 
									ShowField.getFieldName().equals("RPTCheckType") ||
									ShowField.getFieldName().equals("extend1") ||
									ShowField.getFieldName().equals("extend2") ||
									ShowField.getFieldName().equals("extend3") ||
									ShowField.getFieldName().equals("extend4") ||
									ShowField.getFieldName().equals("extend5") ||
									ShowField.getFieldName().equals("FOREIGNID") 
									){
									ShowField.setListVisible(false);
								}
							}
							
							break;
						}
					}
				}
			}
		}
		
		return showSaveOrUpdate;
	}

}
