package report.service.add;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IAdd;


public class RptCopySaveDefultValueAdd  implements IAdd{
	
	
	
	public void Add() throws Exception {		
		Object tObjct = RequestManager.getTOject();
		String strRptPath = ReflectOperation.getFieldShowValue(tObjct, "strRptPath");
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("SourceTemplePath", strRptPath);
		ReflectOperation.setFieldValue(tObjct, "strRptCode","");
		String version = ReflectOperation.getFieldShowValue(tObjct,"strRptVersion");
		String strBusCode =ReflectOperation.getFieldShowValue(tObjct, "strBCode");
		ReflectOperation.setFieldValue(tObjct, "strRptPath",strBusCode+"_"+version);

	    }
	
	}

		