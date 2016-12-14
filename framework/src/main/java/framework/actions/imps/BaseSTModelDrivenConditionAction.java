package framework.actions.imps;


import com.opensymphony.xwork2.ModelDriven;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.interfaces.TActionRule;

// 查询条件放到serviceParam,覆盖getModel接收struts提交的条件数据
public class BaseSTModelDrivenConditionAction extends BaseSTModelDrivenAction implements ModelDriven<Object>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private Object display_serviceParam;
	
	private String param;
    
	private int currentPage;
	
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}
	
	private Boolean firstShowData;
	
	@Override
	public Object getModel() {
		Object conditionObject = null;
		
		try {
			String tName = this.getTName();
			conditionObject = FrameworkFactory.CreateClass(TActionRule.getConditionClassName(tName));
			if(conditionObject == null){
				conditionObject = this.GetTObject();
			}
			
			if(SessionManager.getTCondition(this.getTName()) != null){
				ReflectOperation.CopyFieldValue(SessionManager.getTCondition(this.getTName()), conditionObject);
			}
			
			this.setServiceParam(conditionObject);
			this.setDisplay_serviceParam(conditionObject);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		} 
		
		return null;
	}
	
	@Override
    public String execute() throws Exception {
		
		RequestManager.setFirstShowData(firstShowData);

		if(this.currentPage > 0){
			RequestManager.setCurrentPage(this.currentPage);
		}
		else{
			if(SessionManager.getCurrentPage(this.getTName()) == null){
				RequestManager.setCurrentPage(1);
			}
			else{
				RequestManager.setCurrentPage(SessionManager.getCurrentPage(this.getTName()));
			}
		}


		if(param!=null && param.equals("resetCondition")){ 
			SessionManager.setTCondition(null, this.getTName());
			this.setServiceParam(null);
		}
		String result = super.execute();
        if(this.isSuccessResult()){
    		if(this.currentPage > 0){
            	SessionManager.setTCondition(this.getServiceParam(), this.getTName());
    			SessionManager.setCurrentPage(this.currentPage, this.getTName());
    		}
		}
        return result;
	}

	public void setFirstShowData(Boolean firstShowData) {
		this.firstShowData = firstShowData;
	}

	public Boolean getFirstShowData() {
		return firstShowData;
	}

	public Object getDisplay_serviceParam() {
		return display_serviceParam;
	}

	public void setDisplay_serviceParam(Object display_serviceParam) {
		this.display_serviceParam = display_serviceParam;
	}

	public void setParam(String param) {
		this.param = param;
	}
}
