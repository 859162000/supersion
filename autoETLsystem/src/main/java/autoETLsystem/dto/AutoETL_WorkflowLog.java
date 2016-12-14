package autoETLsystem.dto;

import java.sql.Timestamp;
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
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "AutoETL_WorkflowLog")
@IEntity(navigationName="工作流日志",description="工作流日志")
public class AutoETL_WorkflowLog implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	
	@Column(name="strUserName",length = 50)
	@IColumn(description="用户名")
	private String strUserName;
	
	@Column(name="dtDate")
	@IColumn(description="执行时间")
	private Timestamp dtDate;
	
	@IColumn(tagMethodName="getOperationTypeTag",description="启动类型", isNullable = false)
	@Column(name = "strOperationType", nullable = false)
	private String strOperationType;
	
	public static Map<String,String> getOperationTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("operationType");
	}
	
	@IColumn(tagMethodName="getResultTypeTag",description="结果", isNullable = false)
	@Column(name = "strResultType", nullable = false)
	private String strResultType;
	
	public static Map<String,String> getResultTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("resultType");
	}
	
	@Column(name="strParam",length = 2000)
	@IColumn(description="参数")
	private String strParam;
	
	@Column(name="strContent",length = 200,nullable=false)
	@IColumn(description="内容", isNullable = false)
	private String strContent;
	
	@Column(name="strDiscription",length = 2000)
	@IColumn(description="描述")
	private String strDiscription;

	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "AutoWorkflowID", nullable = false)
	private AutoETL_Workflow autoETL_Workflow;
	
	@Id
	@Column(name = "autoWorkflowLogID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoWorkflowLogID;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_WorkflowLog")
	private Set<AutoETL_ActivityNodeLog> autoETL_ActivityNodeLogs = new HashSet<AutoETL_ActivityNodeLog>(0);

	@Transient
	@IColumn(isListShow=true,description="条数统计")
	private String strCount;
	
	@Transient
	@IColumn(isListShow=true,description="当前执行状态")
	private String strCurrenActivityNode;

	public String getStrUserName() {
		return strUserName;
	}

	public void setStrUserName(String strUserName) {
		this.strUserName = strUserName;
	}
	
	public String getStrOperationType() {
		return strOperationType;
	}

	public void setStrOperationType(String strOperationType) {
		this.strOperationType = strOperationType;
	}

	public String getStrResultType() {
		return strResultType;
	}

	public void setStrResultType(String strResultType) {
		this.strResultType = strResultType;
	}

	public String getStrParam() {
		return strParam;
	}

	public void setStrParam(String strParam) {
		this.strParam = strParam;
	}

	public String getStrContent() {
		return strContent;
	}

	public void setStrContent(String strContent) {
		this.strContent = strContent;
	}

	public String getStrDiscription() {
		return strDiscription;
	}

	public void setStrDiscription(String strDiscription) {
		this.strDiscription = strDiscription;
	}

	public void setAutoETL_Workflow(AutoETL_Workflow autoETL_Workflow) {
		this.autoETL_Workflow = autoETL_Workflow;
	}

	public AutoETL_Workflow getAutoETL_Workflow() {
		return autoETL_Workflow;
	}

	public void setAutoWorkflowLogID(String autoWorkflowLogID) {
		this.autoWorkflowLogID = autoWorkflowLogID;
	}

	public String getAutoWorkflowLogID() {
		return autoWorkflowLogID;
	}

	public void setDtDate(String dtDate) {
		this.dtDate = TypeParse.parseTimestamp(dtDate);
	}

	public Timestamp getDtDate() {
		return dtDate;
	}

	public void setAutoETL_ActivityNodeLogs(Set<AutoETL_ActivityNodeLog> autoETL_ActivityNodeLogs) {
		this.autoETL_ActivityNodeLogs = autoETL_ActivityNodeLogs;
	}

	public Set<AutoETL_ActivityNodeLog> getAutoETL_ActivityNodeLogs() {
		return autoETL_ActivityNodeLogs;
	}

	public String getStrCount() {
		if(autoETL_ActivityNodeLogs!=null && autoETL_ActivityNodeLogs.size()>0){
			int trueCount=0,falseCount=0;
			for(AutoETL_ActivityNodeLog log : autoETL_ActivityNodeLogs){
				if(log.getStrResultType().equals("1") || log.getStrResultType().equals("5")){
					trueCount++;
				}
				else{
					falseCount++;
				}
			}
			strCount="成功条数:"+trueCount+"\r\n失败条数:"+falseCount;
		}
		return strCount;
	}

	public void setStrCount(String strCount) {
	}

	
	public String getStrCurrenActivityNode() {
		if(strResultType ==null || strResultType.equals("4")){
			if(autoETL_ActivityNodeLogs!=null && autoETL_ActivityNodeLogs.size()>0){
				for(AutoETL_ActivityNodeLog autoETL_ActivityNodeLog:autoETL_ActivityNodeLogs){
					if(autoETL_ActivityNodeLog.getIntOrder().intValue()==autoETL_ActivityNodeLogs.size()){
						strCurrenActivityNode="执行中。正在执行第"+autoETL_ActivityNodeLog.getIntOrder() +"个结点及"+autoETL_ActivityNodeLog.getAutoETL_ActivityNode().getStrActivityNodeName()+"结束";
					}
				}
			}
		}
		else{
			int Count=0;
			if(autoETL_ActivityNodeLogs!=null && autoETL_ActivityNodeLogs.size()>0){
				Count=autoETL_ActivityNodeLogs.size();
			}
			strCurrenActivityNode="执行完成。总共执行"+ Count+"个结点";
		}
		return strCurrenActivityNode;
	}
	

	public void setStrCurrenActivityNode(String strCurrenActivityNode) {
	}

	
	
}

