package report.dto.condition;

import java.text.SimpleDateFormat;
import java.util.Date;

import report.dto.RptInfo;
import report.dto.TaskFlow;
import coresystem.dto.InstInfo;
import framework.helper.TypeParse;
import framework.interfaces.ICondition;
import framework.interfaces.ICondition.Comparison;

public class TaskRptInstStatisical_Condition implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7929246959554779401L;

	@ICondition(isVisible=true)
	private String FieldListName;
	
	@ICondition(isVisible=true,target="dtTaskDate",comparison=Comparison.GT,description="开始时间")
	private Date startDate;
	
	@ICondition(isVisible=true,target="dtTaskDate",comparison=Comparison.LT,description="结束时间")
	private Date endDate;
	

	
	@ICondition(isVisible=true)
	private TaskFlow taskFlow;
	
	@ICondition(isVisible=true)
	private RptInfo rptInfo;
	
	@ICondition(isVisible=true)
	private InstInfo instInfo;
	
	
	public String getFieldListName() {
		return FieldListName;
	}
	public void setFieldListName(String fieldListName) {
		FieldListName = fieldListName;
	}
	public String getStartDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return startDate==null?null:format.format(startDate);
	}
	public void setStartDate(String startDate) {
		this.startDate =TypeParse.parseDate(startDate);
	}
	public String getEndDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return endDate==null?null:format.format(endDate);
	}
	public void setEndDate(String endDate) {
		this.endDate =TypeParse.parseDate( endDate);
	}
	public TaskFlow getTaskFlow() {
		return taskFlow;
	}
	public void setTaskFlow(TaskFlow taskFlow) {
		this.taskFlow = taskFlow;
	}
	public RptInfo getRptInfo() {
		return rptInfo;
	}
	public void setRptInfo(RptInfo rptInfo) {
		this.rptInfo = rptInfo;
	}
	public InstInfo getInstInfo() {
		return instInfo;
	}
	public void setInstInfo(InstInfo instInfo) {
		this.instInfo = instInfo;
	}
	
	
}
