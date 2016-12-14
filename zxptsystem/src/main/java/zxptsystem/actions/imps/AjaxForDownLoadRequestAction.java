package zxptsystem.actions.imps;

import net.sf.json.JSONObject;
import framework.actions.imps.BaseSTNameAndIdListAction;

public class AjaxForDownLoadRequestAction extends BaseSTNameAndIdListAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		
		super.execute();		
		JSONObject jo = JSONObject.fromObject(this.getServiceResult());  
		this.setResult(jo.toString());  
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
