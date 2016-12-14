package report.dto.condition;

import report.dto.RptInfo;
import report.dto.TaskFlow;
import coresystem.dto.InstInfo;
import framework.interfaces.ICondition;

public class TaskRptInstHistory_Condition implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	  @ICondition(target = "rptInfo" ,description="报表代码", targetField = "strBCode" , isASC = true,order=2)
	  private RptInfo rptInfo1;
	
	  @ICondition(target = "taskFlow" ,description="数据时间", targetField = "dtTaskDate" , isASC = true,order=3)
	  private TaskFlow taskFlow1;
	  
	  
	  /*@ICondition(target = "Suit" , targetField = "Suit"  ,order=4)
	  private Suit suit;*/
	  
	  @ICondition(target = "instInfo",description="机构",targetField = "strInstCode" ,isASC = true,order=4)
	  private InstInfo instInfo;

	  @ICondition(target = "taskFlow" ,description="主题", targetField = "suit" , isASC = true,order=5)
	  private TaskFlow taskFlow2;
	  
	  
	public TaskFlow getTaskFlow1() {
		return taskFlow1;
	}
	
	public void setTaskFlow1(TaskFlow taskFlow1) {
		this.taskFlow1 = taskFlow1;
	}
	
	public InstInfo getInstInfo() {
		return instInfo;
	}
	public void setInstInfo(InstInfo instInfo) {
		this.instInfo = instInfo;
	}
	public void setRptInfo1(RptInfo rptInfo1) {
		this.rptInfo1 = rptInfo1;
	}
	public RptInfo getRptInfo1() {
		return rptInfo1;
	}

	public void setTaskFlow2(TaskFlow taskFlow2) {
		this.taskFlow2 = taskFlow2;
	}

	public TaskFlow getTaskFlow2() {
		return taskFlow2;
	}
	
   
}
