package framework.reportCheck;

import java.util.ArrayList;
import java.util.List;

public class CheckFieldScope {
	private String belongType;
	private List<CheckFieldScopeField>  leftFieldList;
	private List<CheckFieldScopeField>  rightFieldList;

	public List<CheckFieldScopeField> getLeftFieldList() {
		return leftFieldList;
	}

	public void setLeftFieldList(List<CheckFieldScopeField> leftFieldList) {
		this.leftFieldList = leftFieldList;
	}

	public List<CheckFieldScopeField> getRightFieldList() {
		return rightFieldList;
	}

	public void setRightFieldList(List<CheckFieldScopeField> rightFieldList) {
		this.rightFieldList = rightFieldList;
	}

	public String getBelongType() {
		return belongType;
	}

	public void setBelongType(String belongType) {
		this.belongType = belongType;
	}

	public CheckFieldScope(){
		this.leftFieldList=new ArrayList<CheckFieldScopeField>();
		this.rightFieldList=new ArrayList<CheckFieldScopeField>();
	}
	
}
