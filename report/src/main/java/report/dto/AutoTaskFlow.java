package report.dto;

import java.util.Date;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import extend.dto.Suit;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.show.ShowContext;

@Entity
@Table(name = "AutoTaskFlow")
public class AutoTaskFlow implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = 5956098063951116925L;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@IColumn(description="任务名称", isKeyName=true, isNullable = false)
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
	
	
	@Column(name = "strTaskState", nullable=false)
	@IColumn(tagMethodName="getstrTaskStateTag", description="任务状态", isNullable = false)
	private String strTaskState;
	
	public static Map<String,String> getstrTaskStateTag(){
		return ShowContext.getInstance().getShowEntityMap().get("strTaskState");
	}
	
	@Column(name = "taskStartDate", nullable = false)
	@IColumn(description="任务开始时间（天）", isNullable = false)
	private String taskStartDate;
	
	@Column(name = "taskEndDate", nullable = false)
	@IColumn(description="任务结束时间（天）", isNullable = false)
	private String taskEndDate;
	
	@Column(name = "autoPubTaskTime", nullable = false)
	@IColumn(description="自动下发时间（小时）", isNullable = false)
	private String autoPubTaskTime;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strReportSuit")
	@IColumn(description="所属主题")
	private Suit suit;
	
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

	public String getStrTaskState() {
		return strTaskState;
	}

	public void setStrTaskState(String strTaskState) {
		this.strTaskState = strTaskState;
	}



	public void setStrTaskName(String strTaskName) {
		this.strTaskName = strTaskName;
	}

	public String getStrTaskName() {
		return strTaskName;
	}


	public void setDtTaskDate(String dtTaskDate) {
		this.dtTaskDate = TypeParse.parseDate(dtTaskDate);
	}

	public Date getDtTaskDate() {
		return dtTaskDate;
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
	
	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setAutoPubTaskTime(String autoPubTaskTime) {
		this.autoPubTaskTime = autoPubTaskTime;
	}

	public String getAutoPubTaskTime() {
		return autoPubTaskTime;
	}

	public void setTaskEndDate(String taskEndDate) {
		this.taskEndDate = taskEndDate;
	}

	public String getTaskEndDate() {
		return taskEndDate;
	}

	public void setTaskStartDate(String taskStartDate) {
		this.taskStartDate = taskStartDate;
	}

	public String getTaskStartDate() {
		return taskStartDate;
	}
}

