package report.service.add;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import report.dto.RptInfo;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IAdd;





public class RptInfoAddDefultValueAdd implements IAdd{

public void Add() throws Exception {
	
	Object tObject = RequestManager.getTOject();
	if(tObject instanceof RptInfo)
	{
		RptInfo rptInfo=(RptInfo)tObject;
		String version =rptInfo.getStrRptVersion();// ReflectOperation.getFieldShowValue(tObjct,"strRptVersion");
		String strBCode =rptInfo.getStrBCode();// ReflectOperation.getFieldShowValue(tObjct, "strBCode");
		rptInfo.setStrRptPath(strBCode+"_"+version);
		//查询数据库获得保存之前的模板名称
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		if(!StringUtils.isBlank(rptInfo.getStrRptCode()))
		{
			RptInfo rptInfo2 = (RptInfo) singleObjectFindByIdDao.paramObjectResultExecute(new Object[] {RptInfo.class.getName(), rptInfo.getStrRptCode(), null });
			String oldRptPath = rptInfo2.getStrRptPath();
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("oldRptPath", oldRptPath);
		}
		
		//ReflectOperation.setFieldValue(tObjct, "strRptPath",strBCode+"_"+version);
	}
	

    }
}
