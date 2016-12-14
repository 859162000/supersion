package report.actions.imps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.struts2.ServletActionContext;

import framework.actions.imps.BaseSTAction;
import framework.interfaces.RequestManager;
import framework.services.interfaces.MessageResult;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;




public class ShowFromSaveByAjaxAction extends BaseSTAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String calcData;
	private String result;
	private String vs;
	
	@SuppressWarnings("unchecked")
	public String execute() {
		Object[] jsonArray = JSONArray.fromObject( this.getCalcData()).toArray();
		ArrayList<HashMap<String, String>> oParam=new ArrayList<HashMap<String, String>>();
		for(Object o : jsonArray){
			JSONObject oo =(JSONObject)o;
			HashMap<String, String> hm=new HashMap<String, String>();
			for (Iterator keys = oo.keys(); keys.hasNext();){
				String key = keys.next().toString();
				hm.put(key, oo.get(key).toString());
			} 
			oParam.add(hm);
		}
		((Map<String,Object>)ServletActionContext.getContext().get("request")).put("vs", this.getVs());
		RequestManager.setTOject(oParam.toArray());
		try {
			super.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		MessageResult messageResult = (MessageResult)this.getServiceResult();
		java.util.List list = new ArrayList();
		list.add(messageResult.getMessage());
		JSONArray jo = JSONArray.fromObject(list); 
		this.setResult(jo.toString());  
		return SUCCESS;
	}

	public void setCalcData(String calcData) {
		this.calcData = calcData;
	}

	public String getCalcData() {
		return calcData;
	}

	public void setVs(String vs) {
		this.vs = vs;
	}

	public String getVs() {
		return vs;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

}
