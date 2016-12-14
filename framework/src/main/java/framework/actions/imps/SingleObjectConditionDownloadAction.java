package framework.actions.imps;

import java.io.InputStream;

import framework.services.interfaces.DownloadResult;

public class SingleObjectConditionDownloadAction extends BaseSTModelDrivenConditionAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InputStream getInputStream(){ // 供action返回后，struts/tomcat传输文件给浏览器时调用
		return ((DownloadResult)this.getServiceResult()).getInputStream();
	}

}
