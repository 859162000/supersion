package framework.services.procese;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IColumn;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.TActionRule;
import framework.security.DataSecurity;
import framework.security.SecurityContext;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowField;
import framework.show.ShowFieldValue;
import framework.show.ShowInstance;
import framework.show.ShowInstanceValue;
import framework.show.ShowOperation;
import framework.show.ShowSaveOrUpdate;

public class ShowSaveProcese implements IProcese{

	public Object Procese(Object serviceResult) throws Exception {
		
		String tName = RequestManager.getTName();
		String showInstanceName = ShowParamManager.getShowInstanceName();
		
		ShowInstance showInstance = ReflectOperation.getShowInstance(tName, showInstanceName);
		ShowInstanceValue showInstanceValue = new ShowInstanceValue(showInstance);
		
		// 处理关联列
		List<Field> joinColumnFieldList = ReflectOperation.getJoinColumnFieldList(tName);
		for(Field joinField : joinColumnFieldList){
			
			for(ShowFieldValue showFieldValue : showInstanceValue.getShowFieldValueList()){
				ShowField showField = showFieldValue.getShowField();
				if(showField.getSingleTag().equals("selectField")){//如果是下拉框
					if(joinField.getName().equals(showField.getFieldName())){
						Map<String,String> simpleMap = getSimpleMap(joinField.getName(),joinField.getType());
						showFieldValue.setTag(simpleMap);
						break;
					}
				}
			}
		}

		// 带列表的列
		List<Field> idListTargetFieldList = ReflectOperation.getIdListTargetFieldList(tName);
		for(Field idListTargetField : idListTargetFieldList){
			if(ReflectOperation.isBaseType(idListTargetField.getType())){
				continue;
			}
			Field idListField = ReflectOperation.getIdListField(tName, idListTargetField);
			IColumn idListFieldColumn = idListField.getAnnotation(IColumn.class);
			Field targetField = null;
			if(ReflectOperation.isCollectionType(idListTargetField.getType().getName())){
				Class<?> type = ReflectOperation.getGenericClass(idListTargetField.getGenericType());
				targetField = ReflectOperation.getFieldByName(type,idListFieldColumn.mappedBy());
			}
			else{
				targetField = ReflectOperation.getFieldByName(tName,idListFieldColumn.target());
			}

			Map<String,String> simpleMap = getSimpleMap(idListTargetField.getName(),targetField.getType());
			for(ShowFieldValue showFieldValue : showInstanceValue.getShowFieldValueList()){
				ShowField showField = showFieldValue.getShowField();
				if(idListField.getName().equals(showField.getFieldTargetPrimaryKey())){
					showFieldValue.setTag(simpleMap);
					break;
				}
			}
		}
		
		// 带处理函数的列
		List<Field> haveMethodNameList = ReflectOperation.getHaveMethodNameList(tName);
		for(Field haveMethodName : haveMethodNameList){
			IColumn iColumn = haveMethodName.getAnnotation(IColumn.class);
			String methodName = iColumn.tagMethodName();
			for(ShowFieldValue showFieldValue : showInstanceValue.getShowFieldValueList()){
				ShowField showField = showFieldValue.getShowField();
				if(haveMethodName.getName().equals(showField.getFieldName())){
					Method method = ReflectOperation.getReflectMethod(Class.forName(tName),methodName);
					showFieldValue.setTag(method.invoke(Class.forName(tName)));
					break;
				}
			}
		}
		
		RequestManager.setTName(tName);
		
		ShowSaveOrUpdate showSaveOrUpdate = new ShowSaveOrUpdate();
		
		showSaveOrUpdate.setNavigationName(showInstance.getNavigationName());
		showSaveOrUpdate.setShowFieldValueList(showInstanceValue.getShowFieldValueList());
		showSaveOrUpdate.setNamespace(RequestManager.getNamespace());
		showSaveOrUpdate.setTName(tName);

		if(ShowParamManager.getOperationMap() != null){
			List<ShowOperation> operationList = new ArrayList<ShowOperation>();
			for(Map.Entry<String,String> operation : ShowParamManager.getOperationMap().entrySet()){
				ShowOperation showOperation = new ShowOperation();
				showOperation.setOperationName(operation.getKey());
				String[] operationValues = operation.getValue().split(",");
				String operationValue = operationValues[0]; // 配置的操作如：Get-ShowUpdate
				showOperation.setOperationNamespace(TActionRule.getNamespace(operationValue));
				showOperation.setOperationAction(TActionRule.getCurrentLevelOperationActionName(operationValue, RequestManager.getTName()));
				showOperation.setOperationType(TActionRule.getOperationType(operationValue));

				if(operationValues.length > 1){
					showOperation.setWidth(operationValues[1]);
				}
				if(operationValues.length > 2){
					showOperation.setImageUrl(operationValues[2]);
				}
				if(operationValues.length > 3){
					String strColor = "";
					String[] condtions = operationValues[3].split("\\|");
				
					List<Field> fieldList = ReflectOperation.getOneToManyField(Class.forName(tName));
					Field actionFiled = null;
					for(Field field : fieldList){
						if(showOperation.getOperationAction().contains(ReflectOperation.getGenericClass(field.getGenericType()).getName())){
							actionFiled = field;
							break;
						}
					}
					Collection<?> objectValue = (Collection<?>)ReflectOperation.getFieldValue(RequestManager.getTOject(), actionFiled.getName());
					if(objectValue.size() != 0){
						for(Object o : objectValue){
							boolean isMarth = true;
							for(int j=0;j<condtions.length;j++){
								isMarth = true; // red*RPTCheckType:1%2@RPTSendType:2  不匹配，isMarth = false，blue*RPTCheckType:2@RPTSendType:2 匹配上后，isMarth的值不变，还是false
								String[] str=condtions[j].split("\\*");
								String strLogic = str[1];
								String[] strLogicFieldValues = strLogic.split("@");
								for(String strLogicFieldValue : strLogicFieldValues){
									String strLogicField = strLogicFieldValue.split(":")[0];
									String[] strLogicValues = strLogicFieldValue.split(":")[1].split("%");
			
									String configValue = ReflectOperation.getFieldValue(o, strLogicField).toString();
									boolean isFieldMarch = false;
									for(String strLogicValue:strLogicValues){
										if(configValue.equals(strLogicValue)){
											isFieldMarch = true;
											break;
										}
									}
									if(!isFieldMarch){
										isMarth = false;
									}

									if(!isMarth){
										break;
									}
								}
								if(isMarth){
									strColor = str[0];
									break;
								}
							}
							if(isMarth){
								break;
							}
						}
					}

					if(strColor.startsWith("image")){
						showOperation.setImageUrl(strColor.substring(6));
					}
					else{
						showOperation.setColor(strColor);
					}
				}
				operationList.add(showOperation);
			}
			showSaveOrUpdate.setOperationList(operationList);
		}

		return showSaveOrUpdate;
	}

	@SuppressWarnings("unchecked")
	protected Map<String,String> getSimpleMap(String fieldName, Class<?> type)  throws Exception{
        
	    Map<String,String> simpleMap = new LinkedHashMap<String,String>();
	    
	   // if(RequestManager.getTName().indexOf("AutoDTO_") > -1){
	    if(type.getName().indexOf("AutoDTO_") > -1){
	    	return simpleMap;
	    }
	    
	    
	    Field primaryKeyField = ReflectOperation.getPrimaryKeyField(type);
		Field keyNameField = ReflectOperation.getKeyNameField(type);
		String defaultLogicDaoBeanId = LogicParamManager.getDefaultLogicDaoBeanId();
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(type);

		//默认值过滤
	    if(LogicParamManager.getFieldValueMap() != null && LogicParamManager.getFieldValueMap().containsKey(fieldName)){
	    	String fieldValue = LogicParamManager.getFieldValueMap().get(fieldName);
	    	String[] fieldValues = fieldValue.split("-");
	    	for(int i=0;i<fieldValues.length;i++){
	    		String[] foreignfield = fieldValues[i].split("_");
	    		String[] foreignfieldValues = foreignfield[1].split(",");
	    		detachedCriteria.add(Restrictions.in(foreignfield[0], foreignfieldValues));
	    	}
	    }
	    
	    //数据权限过滤
	    Map<String, Map<String,String>> dataSecurityMap = SecurityContext.getInstance().getDataSecurityMap();
	    if(dataSecurityMap != null){
	    	for(Map.Entry<String, Map<String,String>> entry : dataSecurityMap.entrySet()){
		    	if(entry.getValue().containsKey(RequestManager.getTName())){
		    		String dataSecurityFieldName = entry.getValue().get(RequestManager.getTName());
		    		if(fieldName.equals(dataSecurityFieldName)){
		    			Set<DataSecurity> dataSecuritySet = SecurityContext.getInstance().getLoginInfo().getDataSecuritySet();
			    		Set<String> currentDataSecurity = new HashSet<String>();
			    		for(DataSecurity dataSecurity : dataSecuritySet){
			    			if(dataSecurity.getClassName().equals(RequestManager.getTName()) && dataSecurity.getFieldName().equals(dataSecurityFieldName)){
			    				currentDataSecurity = dataSecurity.getDataSet();
			    				break;
			    			}
			    		}
			    		if(currentDataSecurity.size() == 0){
			    			detachedCriteria.add(Restrictions.eq(primaryKeyField.getName(), null));
			    		}
			    		else{
			    			detachedCriteria.add(Restrictions.in(primaryKeyField.getName(), currentDataSecurity));
			    		}
		    		}
	    		}
	    	}
	    }

    	LogicParamManager.setDetachedCriteria(detachedCriteria);
    	IParamObjectResultExecute defaultLogicDaoDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean(defaultLogicDaoBeanId);
    	List<Object> objectList = (List<Object>)defaultLogicDaoDao.paramObjectResultExecute(null);
		
		if(keyNameField != null){
			for(Object object : objectList){
				Object objFieldVal = ReflectOperation.getFieldValue(object, keyNameField.getName());
				if(objFieldVal != null)
					simpleMap.put(ReflectOperation.getFieldValue(object, primaryKeyField.getName()).toString(), objFieldVal.toString());
			}
		}
		else{
			for(Object object : objectList){
				if(object != null)
					simpleMap.put(ReflectOperation.getFieldValue(object, primaryKeyField.getName()).toString(),
					ReflectOperation.getFieldValue(object, primaryKeyField.getName()).toString());
			}
		}
		
		return simpleMap;

	}
}
