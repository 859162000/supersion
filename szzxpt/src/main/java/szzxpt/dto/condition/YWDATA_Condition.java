package szzxpt.dto.condition;

import framework.interfaces.ICondition;
import framework.interfaces.ICondition.Comparison;

public class YWDATA_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ICondition(target="DATTIME",description="数据起始日期",comparison=Comparison.GE)
	private String startDate;
	
	@ICondition(target="DATTIME",description="数据截止日期",comparison=Comparison.LE)
	private String endDate;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
