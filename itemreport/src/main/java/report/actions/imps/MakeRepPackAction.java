package report.actions.imps;

import java.io.InputStream;

import framework.actions.imps.BaseSAction;
import framework.services.interfaces.DownloadResult;

public class MakeRepPackAction extends BaseSAction{
	
	public InputStream getInputStream(){ // 供action返回后，struts/tomcat传输文件给浏览器时调用
		return ((DownloadResult)this.getServiceResult()).getInputStream();
	}
	
}
