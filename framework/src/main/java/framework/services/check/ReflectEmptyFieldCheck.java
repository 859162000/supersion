package framework.services.check;

import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;

import framework.helper.ReflectOperation;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IColumn;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.ShowParamManager;
import framework.services.interfaces.MessageResult.ErrorField;
import framework.show.ShowField;
import framework.show.ShowInstance;

public class ReflectEmptyFieldCheck  extends BaseConstructor  implements ICheck , IParamObjectResultExecute{

	public ReflectEmptyFieldCheck(){
		super();
	}
	
	public ReflectEmptyFieldCheck(Object baseObject){
		super(baseObject);
	}
	
	
	public MessageResult Check() throws Exception {
		Object checkResult = paramObjectResultExecute(this.getBaseObject());
		return (MessageResult)checkResult;
	}


	public Object paramObjectResultExecute(Object tObject) throws Exception {
		
		ShowInstance showInstance =null;
		if(ServletActionContext.getContext() == null){
			showInstance = ReflectOperation.getDefaultShowInstance(tObject.getClass().getName());
		}
		else{
			showInstance = ReflectOperation.getShowInstance(tObject.getClass().getName(), ShowParamManager.getShowInstanceName());
		}

		MessageResult messageResult = new MessageResult();
		Field[] fields = ReflectOperation.getReflectFields(tObject.getClass());

		for(int i=0;i<fields.length;i++){
			boolean isCanNull = true;
			if(fields[i].isAnnotationPresent(Id.class) && !fields[i].isAnnotationPresent(GeneratedValue.class)){
				isCanNull = false;
			}
			
			if(isCanNull){
				if(fields[i].isAnnotationPresent(IColumn.class)){
					IColumn column = fields[i].getAnnotation(IColumn.class);
					if(!column.isNullable()){
						isCanNull = false;
					}
				}
			}
			
			if(isCanNull){
				if(fields[i].isAnnotationPresent(Column.class)){
					Column column = fields[i].getAnnotation(Column.class);
					if(!column.nullable()){
						isCanNull = false;
					}
				}
				else if(fields[i].isAnnotationPresent(JoinColumn.class)){
					JoinColumn joinColumn = fields[i].getAnnotation(JoinColumn.class);
					if(!joinColumn.nullable()){
						isCanNull = false;
					}
				}
			}

			/*
			if(fields[i].isAnnotationPresent(JoinColumn.class)){
				if(isCanNull){
					Object value = ReflectOperation.getFieldValue(tObject, fields[i].getName());
					if(value != null){
						Object primaryKeyValue = ReflectOperation.getPrimaryKeyValue(value);
						if(primaryKeyValue != null && primaryKeyValue.toString().equals(ApplicationManager.getEmptySelectValue())){
							ReflectOperation.setFieldNullValue(tObject, fields[i].getName(), value.getClass());
						}
					}
				}
			}
			*/
			
			if(!isCanNull){
				boolean emptyValue = false;
				Object value = ReflectOperation.getFieldValue(tObject, fields[i].getName());
				if(value == null){
					if(!ReflectOperation.isIdListTargetField(tObject.getClass(), fields[i])){
						emptyValue = true;
					}
				}
				else{
					if(fields[i].isAnnotationPresent(JoinColumn.class)){
						Object primaryKeyValue = ReflectOperation.getPrimaryKeyValue(value);
						if(primaryKeyValue.toString().equals(ApplicationManager.getEmptySelectValue())){
							emptyValue = true;
						}
					}
					else{
						if(StringUtils.isBlank(value.toString())|| value.equals(ApplicationManager.getEmptySelectValue())){
							emptyValue = true;
						}
					}
				}
				
				if(emptyValue){
					for(ShowField showField : showInstance.getShowFieldList()){
						if(showField.getFieldName().equals(fields[i].getName())){
							if(fields[i].isAnnotationPresent(Column.class) || fields[i].isAnnotationPresent(JoinColumn.class)){
								messageResult.getErrorFieldList().add(new ErrorField(showField.getFieldName(), MessageResult.COLORRED, showField.getFieldShowName() + "：不能为空。"));
							}
							break;
						}
					}
				}
			}
		}

		for(ErrorField errorField : messageResult.getErrorFieldList()){
			if(errorField.getColor().equals(MessageResult.COLORRED)){
				messageResult.setSuccess(false);
				break;
			}
		}
		
		return messageResult;
	}

}
