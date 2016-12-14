package report.actions.imps;

import java.io.InputStream;

import framework.actions.imps.BaseSAction;
import framework.actions.imps.BaseSTNameAndIdListAction;
import framework.services.interfaces.DownloadResult;

public class DownloadByPathAction extends BaseSAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InputStream getInputStream(){ // 供action返回后，struts/tomcat传输文件给浏览器时调用
		return ((DownloadResult)this.getServiceResult()).getInputStream();
	}

}
