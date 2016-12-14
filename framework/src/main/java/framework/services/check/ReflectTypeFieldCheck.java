package framework.services.check;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import javax.persistence.Column;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;

import framework.interfaces.ApplicationManager;
import framework.interfaces.RequestManager;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.ShowParamManager;
import framework.services.interfaces.MessageResult.ErrorField;
import framework.show.ShowField;
import framework.show.ShowInstance;

public class ReflectTypeFieldCheck  extends BaseConstructor  implements ICheck{
	
	public ReflectTypeFieldCheck(){
		super();
	}
	
	public ReflectTypeFieldCheck(Object baseObject){
		super(baseObject);
	}
	
	public MessageResult Check() throws Exception {
		
		Object tObject = this.getBaseObject();
		
		ShowInstance showInstance = ReflectOperation.getShowInstance(tObject.getClass().getName(), ShowParamManager.getShowInstanceName());
		
		MessageResult messageResult = new MessageResult();
		Field[] fields = ReflectOperation.getReflectFields(tObject.getClass());

		for(int i=0;i<fields.length;i++){
			if(ReflectOperation.isSerialVersionUIDField(fields[i].getName())){
				continue;
			}
			Object value = ReflectOperation.getFieldValue(tObject, fields[i].getName());
			if(value == null || value.toString().equals(ApplicationManager.getEmptySelectValue())){
				continue;
			}
			ShowField showField = null;
			for(ShowField tempShowField : showInstance.getShowFieldList()){
				if(tempShowField.getFieldName().equals(fields[i].getName())){
					showField = tempShowField;
					break;
				}
			}
			if(fields[i].getType().equals(String.class)){
				if(fields[i].isAnnotationPresent(Column.class)){
					Column column = fields[i].getAnnotation(Column.class);
					if(column.length() < value.toString().length()){
						messageResult.getErrorFieldList().add(new ErrorField(showField.getFieldName(), MessageResult.COLORRED, showField.getFieldShowName() + "长度应小于等于" + column.length()));
					}
				}
			}
			else if(fields[i].getType().equals(java.util.Date.class) || fields[i].getType().equals(java.sql.Date.class)){
				if(value.equals(TypeParse.maxDate())){
					messageResult.getErrorFieldList().add(new ErrorField(showField.getFieldName(), MessageResult.COLORRED, showField.getFieldShowName() + "应为日期类型"));
				}
			}
			else if(fields[i].getType().equals(java.sql.Timestamp.class)){
				if(value.equals(TypeParse.maxTimestamp())){
					messageResult.getErrorFieldList().add(new ErrorField(showField.getFieldName(), MessageResult.COLORRED, showField.getFieldShowName() + "应为时间类型"));
				}
			}
			else if(fields[i].getType().equals(Integer.class) || fields[i].getType().equals(int.class)){
				if(value.equals(Integer.MAX_VALUE)){
					messageResult.getErrorFieldList().add(new ErrorField(showField.getFieldName(), MessageResult.COLORRED, showField.getFieldShowName() + "应为整数类型"));
				}
			}
			else if(fields[i].getType().equals(Long.class) || fields[i].getType().equals(long.class)){
				if(value.equals(Long.MAX_VALUE)){
					messageResult.getErrorFieldList().add(new ErrorField(showField.getFieldName(), MessageResult.COLORRED, showField.getFieldShowName() + "应为整数类型"));
				}
			} 
			else if(fields[i].getType().equals(Double.class) || fields[i].getType().equals(double.class) ){
				if(value.equals(Double.MAX_VALUE)){
					messageResult.getErrorFieldList().add(new ErrorField(showField.getFieldName(), MessageResult.COLORRED, showField.getFieldShowName() + "应为浮点数类型"));
				}
			}
			else if(fields[i].getType().equals(BigDecimal.class)){
				if(value.equals(new BigDecimal(Integer.MAX_VALUE))){
					messageResult.getErrorFieldList().add(new ErrorField(showField.getFieldName(), MessageResult.COLORRED, showField.getFieldShowName() + "应为货币类型"));
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
