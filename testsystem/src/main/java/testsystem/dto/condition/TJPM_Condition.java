package testsystem.dto.condition;

import java.util.Date;

import coresystem.dto.InstInfo;

import framework.interfaces.ICondition;
import framework.interfaces.ICondition.Comparison;

public class TJPM_Condition {
/*	@ICondition(description = "开始时间",target = "extend2")
	private Date startDate;*/
	@ICondition(description = "业务截止日期",target = "extend2",comparison=Comparison.EQ)
	private Date endDate;
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	
	public void setInstInfo(InstInfo instInfo) {
		this.instInfo = instInfo;
	}
	public InstInfo getInstInfo() {
		return instInfo;
	}

	private InstInfo instInfo;
	
	/*public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getStartDate() {
		return startDate;
	}*/
}
