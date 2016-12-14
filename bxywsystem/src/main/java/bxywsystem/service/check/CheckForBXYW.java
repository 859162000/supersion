package bxywsystem.service.check;

import java.lang.reflect.Field;

import framework.helper.ReflectOperation;
import framework.interfaces.IColumn;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;

public class CheckForBXYW implements ICheck {

	@Override
	public MessageResult Check() throws Exception {
		
		MessageResult messageResult = new MessageResult();
		Object tObject = RequestManager.getTOject();
		
		String ZJLX = "";
		String ZJLXDIS = "";
		String ZJHM = "";
		String ZJHMDIS = "";
		if(tObject.getClass().getName().toString().trim().contains("AutoDTO_BX_BXYW_JC")){
			ZJLX = ReflectOperation.getFieldValue(tObject, "BBZRZJLX").toString();
			ZJLXDIS = ((Field) ReflectOperation.getFieldByName(tObject.getClass(), "BBZRZJLX")).getAnnotation(IColumn.class).description().toString();
			ZJHM = ReflectOperation.getFieldValue(tObject, "BBZRZJHM").toString();
			ZJHMDIS = ((Field) ReflectOperation.getFieldByName(tObject.getClass(), "BBZRZJHM")).getAnnotation(IColumn.class).description().toString();
			
		}else if(tObject.getClass().getName().toString().trim().contains("AutoDTO_BX_ZQRJZHTXX")){
			ZJLX = ReflectOperation.getFieldValue(tObject, "ZQRZJLX").toString();
			ZJLXDIS = ((Field) ReflectOperation.getFieldByName(tObject.getClass(), "ZQRZJLX")).getAnnotation(IColumn.class).description().toString();
			ZJHM = ReflectOperation.getFieldValue(tObject, "ZQRZJHM").toString();
			ZJHMDIS = ((Field) ReflectOperation.getFieldByName(tObject.getClass(), "ZQRZJHM")).getAnnotation(IColumn.class).description().toString();
		}
			
		if(ZJLX.trim().equals("0")){
			if( !String.valueOf(ZJHM.trim().length()).equals("15") &&!String.valueOf(ZJHM.trim().length()).equals("18") ){
				messageResult.setSuccess(false);
				messageResult.getMessageSet().add("当"+ZJLXDIS+"为身份证时，"+ZJHMDIS+"长度应为15或者18位，现在为"+String.valueOf(ZJHM.trim().length())+"位");
				
			}
			else if(String.valueOf(ZJHM.trim().length()).equals("15")){
				String regex ="^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
				if(!ZJHM.matches(regex)){
					messageResult.setSuccess(false);
					messageResult.getMessageSet().add("当"+ZJLXDIS+"为'身份证'且"+ZJHMDIS+"为15位时，"+ZJHMDIS+"的每一位必须都是数字,且必须为合法的身份证号码"); 
				}
			}
			else if(String.valueOf(ZJHM.trim().length()).equals("18")){
				String regex = "[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)";
				if(!ZJHM.matches(regex)){
					messageResult.setSuccess(false);
					messageResult.getMessageSet().add("当"+ZJLXDIS+"为'身份证'且"+ZJHMDIS+"为18位时，"+ZJHMDIS+"的前17位必须都是数字和最后一位必须都是数字或大写的X,,且必须为合法的身份证号码"); 
				}
			}
		}
		else if(ZJLX.trim().equals("a")){
			String regex = "([A-Za-z0-9]{8}-[0-9a-zA-Z]{1})";
			if(!ZJHM.matches(regex)){
				messageResult.setSuccess(false);
				messageResult.getMessageSet().add("当"+ZJLXDIS+"为“组织机构代码证”时,"+ZJHMDIS+"必须为10位的有效组织机构代码，前8位为有效数字和字符，第9位为'－'，第10位为校验位");
			}
		}
		
		return messageResult;
		
	}

}
