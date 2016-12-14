package framework.actions.imps;

import com.opensymphony.xwork2.ModelDriven;
import framework.interfaces.RequestManager;

// 需要页面传回数据的action,实现getModel提供DTO对象供struts设置表单数据
public class BaseSTModelDrivenAction extends BaseSTAction implements ModelDriven<Object>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private Object serviceParam;
	
	public void setServiceParam(Object serviceParam) {
		this.serviceParam = serviceParam;
	}

	public Object getServiceParam() {
		return serviceParam;
	}
	
    private String id; // 页面上传回的上级对象id
	
	public void setId(String id) {
		this.id = id;
	}
	
	private String type;

	public void setType(String type) {
		this.type = type;
	}

	public Object getModel() {// 设置一个对象供struts/JSP设置表单数据,页面表单的参数均设置到serviceParam的属性中
    	this.setServiceParam(this.GetTObject());
		return null;
	}

    @Override
    public String execute() throws Exception {
    	
		RequestManager.setTOject(this.getServiceParam());
		RequestManager.setLevelIdValue(this.id);
		RequestManager.setTypeName(type);
		
		String ret = super.execute();
		
		return ret;
	}
    
}
