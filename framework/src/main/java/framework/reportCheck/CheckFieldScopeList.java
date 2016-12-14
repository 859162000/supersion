package framework.reportCheck;

import java.util.ArrayList;
import java.util.List;

import framework.services.interfaces.MessageResult;

public class CheckFieldScopeList {
	private String discription;
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	private List<CheckFieldParamField> checkFieldParamFieldList;
	private List<CheckFieldScope> checkFieldScopeList;
	
	
	public CheckFieldScopeList(){
		this.checkFieldParamFieldList=new ArrayList<CheckFieldParamField>();
		this.checkFieldScopeList=new ArrayList<CheckFieldScope>();
		
	}
	public List<CheckFieldParamField> getCheckFieldParamFieldList() {
		return checkFieldParamFieldList;
	}
	public void setCheckFieldParamFieldList(
			List<CheckFieldParamField> checkFieldParamFieldList) {
		this.checkFieldParamFieldList = checkFieldParamFieldList;
	}
	public List<CheckFieldScope> getCheckFieldScopeList() {
		return checkFieldScopeList;
	}
	public void setCheckFieldScopeList(List<CheckFieldScope> checkFieldScopeList) {
		this.checkFieldScopeList = checkFieldScopeList;
	}
	public void Check(MessageResult messageResult) {
	
		
	}

}
