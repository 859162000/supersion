package report.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

import extend.dto.Suit;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.show.ShowContext;

@Entity
@Table(name = "TaskFlow")
public class TaskFlow implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = -1088899999866522014L;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@IColumn(description="任务名称", isKeyName=true, isNullable = false, isSpecialCharCheck=true)
	@Column(name = "strTaskName", length = 50, nullable = false)
	private String strTaskName;

	@Temporal(TemporalType.DATE)
	@Column(name = "dtTaskDate", nullable = false)
	@IColumn(description="数据时间", isNullable = false)
	private Date dtTaskDate;
	
	@Column(name = "strFreq", nullable=false)
	@IColumn(tagMethodName="getCheckResultTag", description="报表频度", isNullable = false)
	private String strFreq;
	
	public static Map<String,String> getCheckResultTag(){
		return ShowContext.getInstance().getShowEntityMap().get("reportFreq");
	}
	
	@Column(name = "strTime", length = 50)
	@IColumn(description="批次",isNullable = true,tagMethodName="getLotTag")
	private String strTime;
	
	public void setStrTime(String strTime)
	{
		this.strTime=strTime;
	}
	
	public String getStrTime()
	{
		return this.strTime;
	}
	public static Map<String,String> getLotTag(){
		return ShowContext.getInstance().getShowEntityMap().get("lot");
	}
	
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "taskFlow")
	private Set<TaskRptInst> taskRptInsts = new HashSet<TaskRptInst>(0);
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "taskFlow")
	private Set<TaskModelInst> taskModelInsts = new HashSet<TaskModelInst>(0);
	
	@Column(name = "strTaskState", nullable=false)
	@IColumn(tagMethodName="getstrTaskStateTag", description="任务状态", isNullable = false)
	private String strTaskState;
	
	public static Map<String,String> getstrTaskStateTag(){
		return ShowContext.getInstance().getShowEntityMap().get("strTaskState");
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "taskStartData", nullable = false)
	@IColumn(description="任务开始时间", isNullable = false)
	private Date taskStartData;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "taskEndData", nullable = false)
	@IColumn(description="任务结束时间", isNullable = false)
	private Date taskEndData;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strReportSuit")
	@IColumn(description="所属主题", isNullable = false)
	private Suit suit;

	public String getStrTaskState() {
		return strTaskState;
	}

	public void setStrTaskState(String strTaskState) {
		this.strTaskState = strTaskState;
	}


	//public void setDtTaskDate(Date dtTaskDate) {
	//	this.dtTaskDate = dtTaskDate;
	//}

	public void setStrTaskName(String strTaskName) {
		this.strTaskName = strTaskName;
	}

	public String getStrTaskName() {
		return strTaskName;
	}

	public void setDtTaskDate(String dtTaskDate) {
		this.dtTaskDate = TypeParse.parseDate(dtTaskDate);
	}
	@TypeConversion(converter = "framework.struts.ext.DateConverter")
	public Date getDtTaskDate() {
		return dtTaskDate;
	}
	//public String getDtTaskDate() {
	//	return TypeParse.parseString(dtTaskDate, "yyyy-MM-dd");
	//}

	public void setTaskRptInsts(Set<TaskRptInst> taskRptInsts) {
		this.taskRptInsts = taskRptInsts;
	}

	public Set<TaskRptInst> getTaskRptInsts() {
		return taskRptInsts;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setStrFreq(String strFreq) {
		this.strFreq = strFreq;
	}

	public String getStrFreq() {
		return strFreq;
	}

	public void setTaskStartData(String taskStartData) {
		this.taskStartData = TypeParse.parseDate(taskStartData);
	}

	public Date getTaskStartData() {
		return taskStartData;
	}

	public void setTaskEndData(String taskEndData) {
		this.taskEndData = TypeParse.parseDate(taskEndData);
	}

	public Date getTaskEndData() {
		return taskEndData;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setTaskModelInsts(Set<TaskModelInst> taskModelInsts) {
		this.taskModelInsts = taskModelInsts;
	}

	public Set<TaskModelInst> getTaskModelInsts() {
		return taskModelInsts;
	}
}

