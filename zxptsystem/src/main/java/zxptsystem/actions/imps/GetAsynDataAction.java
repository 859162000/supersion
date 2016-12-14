package zxptsystem.actions.imps;

import net.sf.json.JSONObject;
import framework.actions.imps.BaseSTAction;
public class GetAsynDataAction extends BaseSTAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String result;
	@Override
	public String execute() throws Exception {
		JSONObject jo = JSONObject.fromObject(this.getServiceResult());  
		this.setResult(jo.toString());  
		return super.execute();
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getResult() {
		return result;
	}	
}
