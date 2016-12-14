package report.service.check;

import report.dto.ReportInstInfo;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;

public class JRJGDMLengthCheck implements ICheck {

	public MessageResult Check() throws Exception {
		
		MessageResult messageResult=new MessageResult();
		Object tObject=RequestManager.getTOject();
		if(tObject.getClass().equals(ReportInstInfo.class)){
			String strReportCode=ReflectOperation.getFieldValue(tObject, "strReportInstCode").toString();
			
		    Object obj= ReflectOperation.getFieldValue(tObject, "suit");
		    if(obj!=null){
		    	String strSuitCode= ReflectOperation.getFieldValue(obj,"strSuitCode").toString();
		    	
		    	if(strSuitCode.equals("WHZH") && strReportCode.toString().length()!=11){//外汇账户
					messageResult.setSuccess(false);
					messageResult.getMessageSet().add("金融机构代码长度必须为11位");
				}
				else if(strSuitCode.equals("21") && strReportCode.toString().length()!=11){//企业征信
					messageResult.setSuccess(false);
					messageResult.getMessageSet().add("金融机构代码长度必须为11位");
				}
				else if(strSuitCode.equals("22") && strReportCode.toString().length()!=14){//个人征信
					messageResult.setSuccess(false);
					messageResult.getMessageSet().add("金融机构代码长度必须为14位");
				}
				else if(strSuitCode.equals("24") && strReportCode.toString().length()!=11){//机构信息
					messageResult.setSuccess(false);
					messageResult.getMessageSet().add("金融机构代码长度必须为11位");
				}
				else if(strSuitCode.equals("25") && strReportCode.toString().length()!=11 && strReportCode.toString().length()!=14){//保险业务
					messageResult.setSuccess(false);
					messageResult.getMessageSet().add("金融机构代码长度必须为11位或者14位");
				}
		    }
			
		}
		
		return messageResult;
	}

}