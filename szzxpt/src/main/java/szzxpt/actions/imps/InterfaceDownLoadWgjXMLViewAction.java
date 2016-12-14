package szzxpt.actions.imps;

import java.io.InputStream;

import framework.actions.imps.BaseSTNameAndIdListAction;
import framework.services.interfaces.DownloadResult;

@SuppressWarnings("serial")
public class InterfaceDownLoadWgjXMLViewAction extends BaseSTNameAndIdListAction{

	public String execute() throws Exception {
		
		return super.execute();
	}


	public InputStream getInputStream(){ // 供action返回后，struts/tomcat传输文件给浏览器时调用
		return ((DownloadResult)this.getServiceResult()).getInputStream();
	}
}
