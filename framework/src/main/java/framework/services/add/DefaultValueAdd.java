package framework.services.add;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import framework.helper.ReflectOperation;
import framework.interfaces.ApplicationManager;
import framework.security.SecurityContext;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.IAdd;
import framework.services.interfaces.LogicParamManager;

public class DefaultValueAdd extends BaseConstructor implements IAdd{

	public DefaultValueAdd(){
		super();
	}
	
	public DefaultValueAdd(Object baseObject){
		super(baseObject);
	}
	
	private Map<String,String> defaultValueMap;

	public void Add() throws Exception {
		
		this.defaultValueMap = LogicParamManager.getDefaultValueMap();
		
		List<Field> filedList = ReflectOperation.getJoinColumnFieldList(this.getBaseObject().getClass().getName());
		for(Field field : filedList){
			Object value = ReflectOperation.getFieldValue(this.getBaseObject(), field.getName());
			if(value != null){
				Object primaryKeyValue = ReflectOperation.getPrimaryKeyValue(value);
				if(primaryKeyValue != null && primaryKeyValue.toString().equals(ApplicationManager.getEmptySelectValue())){
					ReflectOperation.setFieldNullValue(this.getBaseObject(), field.getName(), value.getClass());
				}
			}
		}
		
		if(this.defaultValueMap != null){
			Map<String,String> defaultValueMap = this.defaultValueMap;
			for(Map.Entry<String,String> defaultValue : defaultValueMap.entrySet()){
				if(defaultValue.getValue().endsWith("SystemDate")){
					Field field = ReflectOperation.getReflectField(this.getBaseObject().getClass(), defaultValue.getKey());
					if(field.getType().equals(java.util.Date.class) || field.getType().equals(java.sql.Date.class)){
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
						ReflectOperation.setFieldValue(this.getBaseObject(),defaultValue.getKey(),simpleDateFormat.format(new Date()));
					}
					else if(field.getType().equals(Timestamp.class)){
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						ReflectOperation.setFieldValue(this.getBaseObject(),defaultValue.getKey(),simpleDateFormat.format(new Date()));
					}
				}
				else if(defaultValue.getValue().endsWith("LoginUserInfo")){
					if(!SecurityContext.getInstance().getLoginInfo().isAdministrator()){
						ReflectOperation.setFieldValue(this.getBaseObject(),defaultValue.getKey(),SecurityContext.getInstance().getLoginInfo().getTag());
					}
				}
				else if(defaultValue.getValue().endsWith("ObjectKeyEmptyToNull")){
					Object object = ReflectOperation.getFieldValue(this.getBaseObject(),defaultValue.getKey());
					if(object != null){
						Object objectPrimaryKey = ReflectOperation.getPrimaryKeyValue(object);
						if(objectPrimaryKey == null || objectPrimaryKey.toString().equals("")){
							ReflectOperation.setFieldNullValue(this.getBaseObject(), defaultValue.getKey(), object.getClass());
						}
					}
				}
				else{
					ReflectOperation.setFieldValue(this.getBaseObject(),defaultValue.getKey(),defaultValue.getValue());
				}
			}
		}
	}

	public void setDefaultValueMap(Map<String,String> defaultValueMap) {
		this.defaultValueMap = defaultValueMap;
	}

	public Map<String,String> getDefaultValueMap() {
		return defaultValueMap;
	}
}
