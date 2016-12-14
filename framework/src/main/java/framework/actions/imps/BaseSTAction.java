package framework.actions.imps;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.ServletActionContext;
import framework.bean.context.ServiceContextList;
import framework.helper.FrameworkFactory;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.interfaces.TActionRule;

// 需要访问DTO的action,负责解析DTO并设置到request对象中
public class BaseSTAction extends BaseSAction{
	
	public BaseSTAction(){
		
	}
	
	private String windowId;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Object GetTObject() {
		return FrameworkFactory.CreateClass(getTName());
	}
	
	// 从actionName中取DTO的TName
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
	
	@Override
    protected void initAction(){
		TActionRule.initActionName();
	}

	@Override
	protected void initService() throws Exception{
		
		IObjectResultExecute service=null;
		String tName = getTName();
		String action = TActionRule.getAction(this.getActionName());
		String curServiceBeanId=TActionRule.getServiceBeanName(tName, action);
		service=(IObjectResultExecute)FrameworkFactory.CreateBean(curServiceBeanId);
		if(service==null)
		{
			curServiceBeanId=TActionRule.getDefualtBeanName(this.getDefaultServiceBeanId());	
			service=(IObjectResultExecute)FrameworkFactory.CreateBean(curServiceBeanId);
		}
		if(service==null)
		{
			curServiceBeanId=this.getDefaultServiceBeanId();
		}
		this.setBaseService(service);
		super.initService();
		
		merageServiceProperty(curServiceBeanId,tName,this.getBaseService());
	}
	private void merageServiceProperty(String beanId,String dtoName,Object service) throws Exception
	{
		ServiceContextList contextList=(ServiceContextList)FrameworkFactory.CreateBean("serviceBeanContextList");
		contextList.meragePropertys(beanId, dtoName, service);
	}
	
	protected void initWindowId(){
		if(StringUtils.isBlank(windowId)){
			windowId = SessionManager.getWindowId(getTName());
		}
		else{
			SessionManager.setWindowId(getTName(), windowId);
		}
		RequestManager.setWindowId(windowId);
		if(!StringUtils.isBlank(windowId))
		{
			HttpServletRequest request = ServletActionContext.getRequest();

			String actionName=getActionName()+"?"+request.getQueryString();
			SessionManager.pushWinwodIdAction(windowId, actionName);

		}
				
		
	}

	@Override
    public String execute() throws Exception {
		
		RequestManager.setTName(getTName());

		initWindowId();
		return super.execute();
	}

	public void setWindowId(String windowId) {
		this.windowId = windowId;
	}

	public String getWindowId() {
		return windowId;
	}

}
