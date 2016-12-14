package szzxpt.service.check;

import java.lang.reflect.Field;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.MessageResult.ErrorField;

/*
 * 当账户性质代码（account_type） 模糊匹配 '2%' 且 不等于 '2105'时，外汇局批件号/备案表号/ 业务编号（file_number） 为必填项
 * 
 */
public class WHZHCheck implements ICheck, IParamObjectResultExecute{

	@SuppressWarnings("deprecation")
	public MessageResult Check() throws Exception {
		return (MessageResult) paramObjectResultExecute(RequestManager.getTOject());
	}

	public Object paramObjectResultExecute(Object param) throws Exception {
		MessageResult messageResult = new MessageResult();
		Object tObject= param;
		
		List<Field> fieldList=ReflectOperation.getColumnFieldList(tObject.getClass());
		Set<String> fieldNameSet=new LinkedHashSet<String>();
		for(Field field : fieldList){
			fieldNameSet.add(field.getName());
		}
		
		Object valueFile_number=null;
		if(fieldNameSet.contains("file_number")){
			valueFile_number=ReflectOperation.getFieldValue(tObject,"file_number");
		}
		
		Object valueAccount_type=null;
		if(fieldNameSet.contains("account_type")){
			valueAccount_type=ReflectOperation.getFieldValue(tObject,"account_type");
		}
		
		if(valueAccount_type!=null && valueAccount_type.toString().startsWith("2") && !valueAccount_type.toString().equals("2015")){
			if(valueFile_number==null || valueFile_number.equals("")){
				messageResult.setSuccess(false);
				messageResult.getErrorFieldList().add(new ErrorField("file_number", "red", "对除“境内划入保证金专用账户”以外的各类资本项目外汇账户的开立、变更、关户，为必填项"));
			}
		}

		return messageResult;
	}
}
