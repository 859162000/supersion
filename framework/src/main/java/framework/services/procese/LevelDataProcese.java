package framework.services.procese;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IColumn;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowField;
import framework.show.ShowFieldCondition;
import framework.show.ShowFieldValue;
import framework.show.ShowInstance;
import framework.show.ShowList;
import framework.show.ShowSaveOrUpdate;

public class LevelDataProcese implements IProcese{

	private Object previousLevelObject  = null;
	
	public Object Procese(Object serviceResult) throws Exception {

		ShowSaveOrUpdate showSaveOrUpdate = null;
		ShowList showList = null;
		if(serviceResult.getClass().equals(ShowSaveOrUpdate.class)){
			showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;
		}
		else if(serviceResult.getClass().equals(ShowList.class)){
			showList = (ShowList)serviceResult;
		}

		List<ShowFieldValue> showLevelFieldValueList = new ArrayList<ShowFieldValue>();
		if(SessionManager.getCurrentLevel() != null){
			String currentLevelLevel = SessionManager.getCurrentLevel();
			if(SessionManager.getLevelTName(currentLevelLevel) != null && SessionManager.getLevelIdValue(currentLevelLevel) != null){
				String previousLevelLevelTName  = SessionManager.getPreviousLevelTName();
				
				if(ShowParamManager.getIsShowLevelObject()){
					if(previousLevelObject == null){
						
						IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
						previousLevelObject = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{previousLevelLevelTName,SessionManager.getLevelIdValue(currentLevelLevel),null});
						
						String showInstanceName = ShowParamManager.getShowInstanceName();
						ShowInstance showInstance = ReflectOperation.getShowInstance(previousLevelObject.getClass(), showInstanceName);

						for(Field levelField : ReflectOperation.getReflectFields(previousLevelObject.getClass())){
							if(levelField.isAnnotationPresent(IColumn.class)){
								IColumn iColumn = levelField.getAnnotation(IColumn.class);
								if(iColumn.isLevelShow()){
									ShowFieldValue showFieldValue = new ShowFieldValue();
									for(ShowField showField : showInstance.getShowFieldList()){
										if(showField.getFieldName().equals(levelField.getName())){
											showFieldValue.setShowField(showField);
											break;
										}
									}
									String value = ReflectOperation.getFieldShowValue(previousLevelObject, levelField.getName());
									showFieldValue.setFieldValue(value);
									
									showLevelFieldValueList.add(showFieldValue);
								}
							}
						}
					}
				}
				
				Field[] fields = ReflectOperation.getReflectFields(Class.forName(RequestManager.getTName()));
				for(int j=0;j<fields.length;j++){
					// 与上一级类型相同的字段不显示；LevelFieldMap中配置的字段不显示(程序自动设置值)
					if(fields[j].getType().equals(Class.forName(previousLevelLevelTName)) || isExsit(fields[j].getName())){
						if(showSaveOrUpdate != null){
							for(ShowFieldValue showFieldValue : showSaveOrUpdate.getShowFieldValueList()){
								if(showFieldValue.getShowField().getFieldName().equals(fields[j].getName())){
									if(ShowParamManager.getLevelFieldMap() == null){
										showFieldValue.setFieldValue(SessionManager.getLevelIdValue(currentLevelLevel));
									}
									else{
										
										if(previousLevelObject == null){
											IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
											previousLevelObject = singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{previousLevelLevelTName,SessionManager.getLevelIdValue(currentLevelLevel),null});
										}
										
										Field[] previousLevelFields = ReflectOperation.getReflectFields(previousLevelObject.getClass());
										for(Map.Entry<String, String> entry : ShowParamManager.getLevelFieldMap().entrySet()){
											if(entry.getKey().indexOf(".") > -1){
												String fieldName1 = entry.getKey().substring(0,entry.getKey().indexOf("."));
												for(int i=0;i<previousLevelFields.length;i++){
													if(previousLevelFields[i].getName().equals(fieldName1) && entry.getValue().equals(showFieldValue.getShowField().getFieldName())){
														Object value = ReflectOperation.getFieldLevelValue(previousLevelObject, entry.getKey(), false);
														if(value.getClass().equals(Date.class)){
															showFieldValue.setFieldValue(TypeParse.parseString((Date)value, "yyyy-MM-dd"));
														}
														else{
															showFieldValue.setFieldValue(value.toString());
														}
														
														break;
													}
												}
											}
											else{
												for(int i=0;i<previousLevelFields.length;i++){
													if(previousLevelFields[i].getName().equals(entry.getKey()) && entry.getValue().equals(showFieldValue.getShowField().getFieldName())){
														Object value = ReflectOperation.getFieldValue(previousLevelObject, entry.getKey());
														showFieldValue.setFieldValue(ReflectOperation.getPrimaryKeyValue(value));
														break;
													}
												}
											}
										}
									}
									showFieldValue.getShowField().setSingleTag(ApplicationManager.getSingleTagHidden());
									break;
								}
							}
						}
						else if(showList != null){
							for(ShowFieldCondition showFieldCondition : showList.getShowCondition()){
								if(showFieldCondition.getFieldName().equals(fields[j].getName())){
									showList.getShowCondition().remove(showFieldCondition);
									break;
								}
							}
						}
					}
				}
			}
		}
		
		if(showSaveOrUpdate != null){
			showSaveOrUpdate.setShowLevelFieldValueList(showLevelFieldValueList);
			return showSaveOrUpdate;
		}
		else if(showList != null){
			showList.setShowLevelFieldValueList(showLevelFieldValueList);
			return showList;
		}
		else{
			return serviceResult;
		}
	}
	
	private boolean isExsit(String fieldName){
		Map<String,String> levelFieldMap = ShowParamManager.getLevelFieldMap();
		if(levelFieldMap != null){
			for(Map.Entry<String, String> entry : levelFieldMap.entrySet()){
				if(entry.getValue().equals(fieldName)){
					return true;
				}
			}
		}

		return false;
	}
}
