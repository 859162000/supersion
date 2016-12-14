package report.actions.imps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import framework.actions.imps.BaseSAction;
import framework.interfaces.SessionManager;

public class AjaxRequestAction extends BaseSAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		String itemCode="";
		String suitCode="";
		if(this.getItemCode()!=null){
			Object[] jsonArray = JSONArray.fromObject(this.getItemCode()).toArray();
			
			for(Object o : jsonArray){
				itemCode =o.toString();
			}		
		}
		if(this.getSuit()!=null){
			Object[] jsonArray = JSONArray.fromObject(this.getSuit()).toArray();
			
			for(Object o : jsonArray){
				suitCode =o.toString();
			}		
		}
		ServletActionContext.getContext().getSession().put("ItemCode", itemCode);	
		ServletActionContext.getContext().getSession().put("SuitCode", suitCode);	
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

	public void setItemCode(Object itemCode) {
		this.itemCode = itemCode;
	}

	public Object getItemCode() {
		return itemCode;
	}

	public void setSuit(Object suit) {
		this.suit = suit;
	}

	public Object getSuit() {
		return suit;
	}

	private Object itemCode;
	private Object suit;
	private String result;
}
