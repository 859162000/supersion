package framework.reportCheck;

import java.util.ArrayList;
import java.util.List;

public class CheckTableForeign {
	
	public CheckTableForeign(){
		setCheckFieldForeignList(new ArrayList<CheckFieldForeign>());
	}
	
	private String name;
	private List<CheckFieldForeign> checkFieldForeignList;
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setCheckFieldForeignList(List<CheckFieldForeign> checkFieldForeignList) {
		this.checkFieldForeignList = checkFieldForeignList;
	}

	public List<CheckFieldForeign> getCheckFieldForeignList() {
		return checkFieldForeignList;
	}
	
}
