package testsystem.dto.condition;

import framework.interfaces.ICondition;
import framework.interfaces.ICondition.Comparison;

public class Test3_Condition {
	
	@ICondition(target="dtDate",description="开始时间",comparison=Comparison.GE)
	private String startDate;
	
	@ICondition(target="dtDate",description="结束时间",comparison=Comparison.LE)
	private String endDate;

	@ICondition(order=1,isASC=false,isVisible=false)
	private String dtDate;

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public String getDtDate() {
		return dtDate;
	}
}
