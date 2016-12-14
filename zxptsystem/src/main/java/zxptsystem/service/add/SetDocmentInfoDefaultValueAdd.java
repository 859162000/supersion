package zxptsystem.service.add;

import java.util.Calendar;
import java.util.Date;

import org.apache.struts2.ServletActionContext;

import oracle.sql.DATE;
import zxptsystem.dto.EIS_AuthorizationDocumentInfo;
import zxptsystem.dto.EIS_CertificateInfo;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.interfaces.IAdd;

public class SetDocmentInfoDefaultValueAdd implements IAdd {
	static String DownLine="_";
	@Override
	public void Add() throws Exception {
		String currentLevelLevel = SessionManager.getCurrentLevel();
		String id = SessionManager.getLevelIdValue(currentLevelLevel);
		String profileType="";
		String className=SessionManager.getPreviousLevelTName();
		if(Class.forName(className).newInstance() instanceof zxptsystem.dto.EIS_ENTCustomernBasicInfo){
			profileType="1";
		}else if(Class.forName(className).newInstance() instanceof zxptsystem.dto.EIS_PERCustomernBasicInfo){
			profileType="2";
		}
		
		Object obj = RequestManager.getTOject();
		Date date =Calendar.getInstance().getTime();
		
		if(obj instanceof EIS_AuthorizationDocumentInfo){
			ReflectOperation.setFieldValue(obj, "profileType", profileType);
			String attachmentNo="SQ";
			//attachmentNo+=DownLine+profileType+DownLine+id+DownLine+TypeParse.parseString(date, "yyyyMMdd");
			
			//ReflectOperation.setFieldValue(obj, "attachmentNo", attachmentNo);
			ReflectOperation.setFieldValue(obj, "strCustomerID", id);
		}else if(obj instanceof EIS_CertificateInfo){
			String attachmentNo="ZJ";
			//attachmentNo+=DownLine+profileType+DownLine+id+DownLine+TypeParse.parseString(date, "yyyyMMdd");
			
			//ReflectOperation.setFieldValue(obj, "attachmentNo", attachmentNo);
			ReflectOperation.setFieldValue(obj, "recordTime", date);
			ReflectOperation.setFieldValue(obj, "strCustomerID", id);
		}
	}

}
