package framework.actions.imps;

import java.io.InputStream;

import framework.interfaces.RequestManager;
import framework.services.interfaces.DownloadResult;

public class SingleObjectDownloadAction extends BaseSTNameAndIdAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private String id; // 页面上传回的上级对象id
	
	public void setId(String id) {
		this.id = id;
	}
	
	public InputStream getInputStream(){ // 供action返回后，struts/tomcat传输文件给浏览器时调用
		return ((DownloadResult)this.getServiceResult()).getInputStream();
	}
	@Override
	public String execute() throws Exception {
		RequestManager.setLevelIdValue(this.id);
		
		return super.execute();
	}
}
