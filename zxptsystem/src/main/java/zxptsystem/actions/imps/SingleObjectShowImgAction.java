package zxptsystem.actions.imps;

import java.io.InputStream;

import framework.actions.imps.BaseSAction;
import framework.interfaces.RequestManager;
import framework.interfaces.TActionRule;
import framework.services.interfaces.DownloadResult;

public class SingleObjectShowImgAction extends BaseSAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id; // 页面上传回的上级对象id
	
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	
	public InputStream getInputStream(){ // 供action返回后，struts/tomcat传输文件给浏览器时调用
		return ((DownloadResult)this.getServiceResult()).getInputStream();
	}
	@Override
	public String execute() throws Exception {
		RequestManager.setLevelIdValue(this.id);
		RequestManager.setTName(getTName());
		return super.execute();
	}
	protected String getTName(){
		String actionName = this.getActionName();
		String tName = null;
		try{
			tName = TActionRule.getTName(actionName);
		}
		catch(Exception ex){
		}
		return tName;
	}
}
