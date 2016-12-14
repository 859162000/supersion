package zxptsystem.actions.imps;

import java.util.Collection;

import com.opensymphony.xwork2.ActionContext;

import net.sf.json.JSONObject;
import framework.actions.imps.BaseSTNameAndIdListAction;
import framework.helper.FrameworkFactory;

public class AjaxForOverrideDownLoadRequestAction extends BaseSTNameAndIdListAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		
		super.execute();		
		JSONObject jo = JSONObject.fromObject(this.getServiceResult());  
		this.setResult(jo.toString());  
		Collection<Object> values = ActionContext.getContext().getSession().values();
		return SUCCESS;
	}
	
	
	public void setResult(String result) {
		this.result = result;
	}
	public String getResult() {
		return result;
	}

	private String result;
}
