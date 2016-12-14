package zxptsystem.service.check;

import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;

public class ZXCXAttachmentNullCheck implements ICheck {

	@Override
	public MessageResult Check() throws Exception {
		MessageResult messageResult = new MessageResult();
		
		Object tObject=RequestManager.getTOject();
		String typeName = "授权资料附件";
		if(tObject instanceof zxptsystem.dto.EIS_CertificateInfo){
			typeName="证照资料附件";
		}
		byte[] byteData = (byte[])ReflectOperation.getFieldValue(tObject, "byteData");
		if(null == byteData || byteData.length==0){
			messageResult.setSuccess(false);
			messageResult.getMessageSet().add(typeName+":不能为空!");
			return messageResult;
		}
		java.lang.reflect.Field field = ReflectOperation.getFieldByName(tObject.getClass(), "byteData");
		javax.persistence.Column column = field.getAnnotation(javax.persistence.Column.class);
		if(byteData.length>column.length()){
			messageResult.setSuccess(false);
			messageResult.getMessageSet().add(typeName+"最大为"+column.length()/1024/1024+"M!");
			return messageResult;
		}
		String strFileName = ReflectOperation.getFieldValue(tObject, "strFileName").toString();
		String[] strFileNames = strFileName.split("\\.");
		String prefix = strFileNames[strFileNames.length-1];
		//bmp,gif,jpg,jpeg,png
		if(prefix.toLowerCase().equals("bmp") || prefix.toLowerCase().equals("gif") || prefix.toLowerCase().equals("jpg") || prefix.toLowerCase().equals("png")){
			
		}else{
			messageResult.setSuccess(false);
			messageResult.getMessageSet().add(typeName+"不是有效的图片格式!");
		}
		return messageResult;
	}
}
