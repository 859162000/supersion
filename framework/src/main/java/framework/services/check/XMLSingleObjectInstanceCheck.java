package framework.services.check;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IColumn;
import framework.interfaces.IParamObjectResultExecute;
import framework.reportCheck.CheckContext;
import framework.reportCheck.CheckFieldBasic;
import framework.reportCheck.CheckFieldCaseWhen;
import framework.reportCheck.CheckFieldClass;
import framework.reportCheck.CheckFieldEffective;
import framework.reportCheck.CheckFieldLine;
import framework.reportCheck.CheckFieldOr;
import framework.reportCheck.CheckInstance;
import framework.reportCheck.CheckTable;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.MessageResult.ErrorField;

public class XMLSingleObjectInstanceCheck  extends BaseConstructor  implements ICheck{
	
	public XMLSingleObjectInstanceCheck(){
		super();
	}
	
	public XMLSingleObjectInstanceCheck(Object baseObject){
		super(baseObject);
	}

	@SuppressWarnings("unchecked")
	public MessageResult Check() throws Exception {
		
		if(LogicParamManager.getDefaultCheckInstance() != null){
			
			MessageResult messageResult = new MessageResult();
			
			CheckInstance checkInstance = CheckContext.getInstance().getCheckInstanceMap().get(LogicParamManager.getDefaultCheckInstance());
			if(checkInstance == null){
				IParamObjectResultExecute paramObjectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("checkInstanceCopyService");
				checkInstance = (CheckInstance)paramObjectResultExecute.paramObjectResultExecute(LogicParamManager.getDefaultCheckInstance());
				
				if(checkInstance == null){
					messageResult.setSuccess(false);
					messageResult.getMessageSet().add("校验实例不存在");
					return messageResult;
				}
			}

			String tName = this.getBaseObject().getClass().getName();
			Map<String,String> fieldNameMap = new HashMap<String,String>();
			
			Map<String,Map<String,String>> valueMap = new HashMap<String,Map<String,String>>();
			for(CheckTable checkTable : checkInstance.getCheckTableList()){

				Object checkObject = this.getBaseObject();
				List<Field> columnFiled = ReflectOperation.getColumnFieldList(checkObject.getClass());
				Map<String,Object> translateObject = new HashMap<String,Object>();
				for(Field field : columnFiled){
					
					Object value = ReflectOperation.getFieldValue(checkObject, field.getName());
					String aliasName = field.getName();
					if(field.isAnnotationPresent(IColumn.class)){
						IColumn iColumn = field.getAnnotation(IColumn.class);
						if(!StringUtils.isBlank(iColumn.aliasName())){
							aliasName = iColumn.aliasName();
						}
					}
					
					fieldNameMap.put(aliasName,  field.getName());

					if(value != null){
						
						if(value.equals(ApplicationManager.getEmptySelectValue()) || value.equals("")){
							translateObject.put(aliasName.toUpperCase(), null);
						}
						else{
							if(field.isAnnotationPresent(IColumn.class)){
								IColumn iColumn = field.getAnnotation(IColumn.class);
							    if(!StringUtils.isBlank(iColumn.tagMethodName())){
							    	translateObject.put(aliasName.toUpperCase(), value);
							    	Method method = ReflectOperation.getReflectMethod(Class.forName(tName),iColumn.tagMethodName());
									valueMap.put(aliasName, (Map<String,String>)method.invoke(Class.forName(tName)));
							    	continue;
							    }
							}
							if(ReflectOperation.isBaseType(field.getType())){
								if(value.getClass().equals(Date.class)){
									translateObject.put(aliasName.toUpperCase(), TypeParse.parseString((Date)value, "yyyy-MM-dd"));
								}
								else{
									translateObject.put(aliasName.toUpperCase(), value.toString());
								}
							}
							else{
								
								if(this.getBaseObject().getClass().getName().indexOf("AutoDTO_") == -1){
									IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
									DetachedCriteria detachedCriteria = DetachedCriteria.forClass(field.getType());
									List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					
									Map<String,String> fieldValueMap = new HashMap<String,String>();
									Field keyNameField = ReflectOperation.getKeyNameField(field.getType().getName());
									for(Object object : objectList){
										String keyNameValue = ReflectOperation.getFieldValue(object, keyNameField.getName()).toString();
										fieldValueMap.put(ReflectOperation.getPrimaryKeyValue(object).toString(), keyNameValue);
									}
									valueMap.put(aliasName, fieldValueMap);
								}

								if(ReflectOperation.getPrimaryKeyValue(value).toString().equals(ApplicationManager.getEmptySelectValue())){
									translateObject.put(aliasName.toUpperCase(), null);
								}
								else{
									translateObject.put(aliasName.toUpperCase(), ReflectOperation.getPrimaryKeyValue(value).toString());
								}
								
							}
						}
					}
					else{
						translateObject.put(aliasName.toUpperCase(), null);
					}
				}

				List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				list.add(translateObject);
					
				for(Object object : list){
					for(CheckFieldBasic checkFieldBasic : checkTable.getCheckFieldBasicList()){
						Map<String,Object> mapObject = (Map<String,Object>)object;
						List<String> messageList = checkFieldBasic.Check(mapObject,"");
						
						for(String message : messageList){
							messageResult.getErrorFieldList().add(new ErrorField(fieldNameMap.get(checkFieldBasic.getName()), MessageResult.COLORRED, message));
						}
					}
				}
				
				for(Object object : list){
					for(CheckFieldEffective checkFieldEffective : checkTable.getCheckFieldEffectiveList()){
						Map<String,Object> mapObject = (Map<String,Object>)object;
						List<String> messageList = checkFieldEffective.Check(mapObject,"",new HashMap<String,String>());
						for(String message : messageList){
							messageResult.getErrorFieldList().add(new ErrorField(fieldNameMap.get(checkFieldEffective.getName()), MessageResult.COLORRED, message));
						}
					}
				}
				
				for(Object object : list){
					for(CheckFieldCaseWhen checkFieldCaseWhen : checkTable.getCheckFieldCaseWhenList()){
						Map<String,Object> mapObject = (Map<String,Object>)object;
						List<String> messageList = checkFieldCaseWhen.Check(mapObject,"",valueMap);
						for(String message : messageList){
							messageResult.getErrorFieldList().add(new ErrorField(fieldNameMap.get(checkFieldCaseWhen.getName()), MessageResult.COLORRED, message));
						}
					}
				}

				for(Object object : list){
					for(CheckFieldOr checkFieldOr : checkTable.getCheckFieldOrList()){
						Map<String,Object> mapObject = (Map<String,Object>)object;
						List<String> messageList = checkFieldOr.Check(mapObject,"");

						for(String message : messageList){
							messageResult.getMessageSet().add(message);
						}

					}
				}
				
				for(CheckFieldLine checkFieldLine : checkTable.getCheckFieldLineList()){					
					for(Object object : list){
						Map<String,Object> mapObject = (Map<String,Object>)object;
						List<String> messageList = checkFieldLine.Check(mapObject,"");
						for(String message : messageList){
							messageResult.getMessageSet().add(message);
						}
					}
				}
				
				for(Object object : list){
					for(CheckFieldClass checkFieldClass : checkTable.getCheckFieldClassList()){//XML类校验
						Map<String,Object> mapObject = (Map<String,Object>)object;
						List<String> messageList = checkFieldClass.Check(mapObject);
						
						for(String message : messageList){
							messageResult.getMessageSet().add(message);
						}
					}
				}
			}
			
			if(messageResult.getMessageSet().size() > 0 || messageResult.getErrorFieldList().size() > 0){
				messageResult.setSuccess(false);
			}

			return messageResult;
		}
		
		return new MessageResult();
	}

}

