package report.actions.imps;

import java.io.InputStream;

import framework.actions.imps.BaseSTModelDrivenConditionAction;
import framework.interfaces.RequestManager;
import framework.services.interfaces.DownloadResult;

public class ExportReportsAction extends BaseSTModelDrivenConditionAction{

	private static final long serialVersionUID = 1L;

	private String[] idList;
	
	public void setIdList(String[] idList) {
		this.idList = idList;
	}
	
	public InputStream getInputStream(){ // 供action返回后，struts/tomcat传输文件给浏览器时调用
		return ((DownloadResult)this.getServiceResult()).getInputStream();
	}

	public String execute() throws Exception {
		RequestManager.setIdList(this.idList);
		
		return super.execute();
	}
}
