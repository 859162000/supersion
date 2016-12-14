package coresystem.dao.imps;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import coresystem.dto.ActionExcute;
import coresystem.dto.ActionUpdateExcute;
import coresystem.dto.UserInfo;
import framework.dao.imps.SingleObjectUpdateDao;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.RequestManager;
import framework.security.SecurityContext;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowContext;
import framework.show.ShowInstance;


public class ActionUpdateExcuteDao extends SingleObjectUpdateDao{

	@SuppressWarnings("unchecked")
	@Override
	public void voidResultExecute() throws Exception { 

		String initActionName = RequestManager.getActionName();
		String actionName = initActionName;
		String tName = null;
		if(actionName.indexOf("-") > -1){
			actionName = initActionName.substring(0,initActionName.indexOf("-"));
			tName = initActionName.substring(initActionName.indexOf("-") + 1);
		}
		if(actionName.indexOf("Level") > -1){
			actionName = actionName.substring(0,actionName.indexOf("Level"));
		}
		
		if(tName != null){
			for(Map.Entry<String, String> entry : ShowContext.getInstance().getShowEntityMap().get("actionUpdateExcute").entrySet()){
				if(actionName.equals(entry.getKey())){

					Date date = new Date();
					
					Object tObject = RequestManager.getTOject();
					RequestManager.setId(ReflectOperation.getPrimaryKeyValue(tObject));
					Object dataBaseObject = this.getHibernateTemplate().get(Class.forName(RequestManager.getTName()), (Serializable)RequestManager.getId());

					List<Field> fieldList = ReflectOperation.getColumnFieldList(tObject.getClass());
					List<ActionUpdateExcute> actionUpdateExcuteList = new ArrayList<ActionUpdateExcute>();
					for(Field field : fieldList){
						if(!ReflectOperation.isPrimaryKeyField(field) && !field.getType().equals(byte[].class)){
							Object tOjectFieldValue = ReflectOperation.getFieldValue(tObject,field.getName());
							Object dataBaseFieldValue = ReflectOperation.getFieldValue(dataBaseObject,field.getName());
							boolean isEqual = true;
							if((tOjectFieldValue != null && !StringUtils.isBlank(tOjectFieldValue.toString()) && dataBaseFieldValue == null) || (tOjectFieldValue == null && dataBaseFieldValue != null && !StringUtils.isBlank(dataBaseFieldValue.toString()))){
								isEqual = false;
							}
							else if(tOjectFieldValue != null && dataBaseFieldValue != null){
								if(ReflectOperation.isBaseType(tOjectFieldValue.getClass())){
									if(tOjectFieldValue.getClass().equals(java.util.Date.class) || tOjectFieldValue.getClass().equals(java.sql.Date.class)){
										if(((Date)tOjectFieldValue).compareTo((Date)dataBaseFieldValue) != 0){
											isEqual = false;
										}
									}
									else{
										if(!tOjectFieldValue.toString().equals(dataBaseFieldValue.toString())){
											isEqual = false;
										}
									}
								}
								else{
									if(!ReflectOperation.getPrimaryKeyValue(tOjectFieldValue).equals(ReflectOperation.getPrimaryKeyValue(dataBaseFieldValue))){
										isEqual = false;
									}
								}
							}
							if(!isEqual){
								ActionUpdateExcute actionUpdateExcute = new ActionUpdateExcute();
								
								if(tOjectFieldValue != null && !ReflectOperation.isBaseType(tOjectFieldValue.getClass())){
									Field keyNameField = ReflectOperation.getKeyNameField(tOjectFieldValue.getClass());
									Object keyNameObject = this.getHibernateTemplate().get(tOjectFieldValue.getClass(), (Serializable)ReflectOperation.getPrimaryKeyValue(tOjectFieldValue));
									if(keyNameObject == null) tOjectFieldValue = null;
									else tOjectFieldValue = ReflectOperation.getFieldValue(keyNameObject, keyNameField.getName()).toString();
								}
								if(dataBaseFieldValue != null && !ReflectOperation.isBaseType(dataBaseFieldValue.getClass())){
									Field keyNameField = ReflectOperation.getKeyNameField(dataBaseFieldValue.getClass());
									dataBaseFieldValue = ReflectOperation.getFieldValue(dataBaseFieldValue, keyNameField.getName()).toString();
								}

								if(field.isAnnotationPresent(IColumn.class)){
									IColumn iColumn = field.getAnnotation(IColumn.class);
									if(!StringUtils.isBlank(iColumn.tagMethodName())){
										Method method = ReflectOperation.getReflectMethod(Class.forName(tName), iColumn.tagMethodName());
										Map<String,String> mapValue = (Map<String,String>)method.invoke(Class.forName(tName));
										if(tOjectFieldValue != null && mapValue!=null){
											tOjectFieldValue = mapValue.get(tOjectFieldValue);
										}
										if(dataBaseFieldValue != null && mapValue!=null){
											dataBaseFieldValue = mapValue.get(dataBaseFieldValue);
										}
									}
									if(!StringUtils.isBlank(iColumn.description())){
										actionUpdateExcute.setUpdateFieldShowName(iColumn.description());
									}
								}

								if(!SecurityContext.getInstance().getLoginInfo().isAdministrator()){
									actionUpdateExcute.setUserInfo((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag());
								}
								SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								actionUpdateExcute.setActionTime(simpleDateFormat.format(date));
								if(dataBaseFieldValue == null){
									actionUpdateExcute.setStrPreviousValue(null);
								}
								else{
									actionUpdateExcute.setStrPreviousValue(dataBaseFieldValue.toString());
								}
								if(tOjectFieldValue == null){
									actionUpdateExcute.setStrUpdateValue(null);
								}
								else{
									if(tOjectFieldValue.getClass().equals(java.util.Date.class) || tOjectFieldValue.getClass().equals(java.sql.Date.class)){
										actionUpdateExcute.setStrUpdateValue(TypeParse.parseString((Date)tOjectFieldValue, "yyyy-MM-dd"));
									}
									else{
										actionUpdateExcute.setStrUpdateValue(tOjectFieldValue.toString());
									}
								}

								actionUpdateExcute.setUpdateId(ReflectOperation.getPrimaryKeyValue(dataBaseObject).toString());
								actionUpdateExcute.setUpdateTName(tName);
								ShowInstance showInstance = ReflectOperation.getShowInstance(tName, ShowParamManager.getShowInstanceName());
								actionUpdateExcute.setUpdateTShowName(showInstance.getShowEntityName());
								actionUpdateExcute.setUpdateFieldName(field.getName());
								if(StringUtils.isBlank(actionUpdateExcute.getUpdateFieldShowName())){
									actionUpdateExcute.setUpdateFieldShowName(field.getName());
								}
								
								Object obj = ((Map<String,Object>)ServletActionContext.getContext().get("request")).get("actionExcuteObject");
								if(obj != null){
									actionUpdateExcute.setActionExcute((ActionExcute)obj);
								}
								
								actionUpdateExcuteList.add(actionUpdateExcute);
							}
						}
					}
					
					this.getHibernateTemplate().saveOrUpdateAll(actionUpdateExcuteList);
				
					break;	
				}
			}
		}

		this.getHibernateTemplate().flush();
		this.getHibernateTemplate().clear();
		super.voidResultExecute();
	}
}


