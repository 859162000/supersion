package autoETLsystem.dto;

import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "AutoETL_ActivityNodeLog")
@IEntity(navigationName="活动结点日志",description="活动结点日志")
public class AutoETL_ActivityNodeLog implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@Column(name = "intOrder")
	@IColumn(description="执行顺序")
	private Integer intOrder;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeID",nullable=false)
	private AutoETL_ActivityNode autoETL_ActivityNode;

	
	@IColumn(tagMethodName="getResultTypeTag",description="结果", isNullable = false)
	@Column(name = "strResultType", nullable = false)
	private String strResultType;
	
	public static Map<String,String> getResultTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("resultType");
	}
	
	@IColumn(tagMethodName="getActivityNodeExcuteTypeTag",description="执行状态")
	@Column(name = "strActivityNodeExcuteType")
	private String strActivityNodeExcuteType;
	
	public static Map<String,String> getActivityNodeExcuteTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("activityNodeExcuteType");
	}
	
	@Column(name="strParam",length = 200)
	@IColumn(description="参数")
	private String strParam;
	
	@Column(name="strContent",length = 4000,nullable=false)
	@IColumn(description="内容", isNullable = false)
	private String strContent;
	
	
	
	@Column(name="strLines",length = 50)
	@IColumn(description="记录数")
	private String strLines;
	
	@Column(name="strDiscription",length = 4000)
	@IColumn(description="描述")
	private String strDiscription;
	
	@IColumn(isNullable = false)
	@JoinColumn(name = "autoETLWorkflowLogID",nullable=false)
	@ManyToOne(fetch = FetchType.EAGER)
	AutoETL_WorkflowLog autoETL_WorkflowLog;
	
	
	@Id
	@Column(name = "autoActivityNodeLogID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeLogID;


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
		this.strDiscription = strDiscription.replace("\n", " ");
	}

	public void setAutoETL_ActivityNode(AutoETL_ActivityNode autoETL_ActivityNode) {
		this.autoETL_ActivityNode = autoETL_ActivityNode;
	}

	public AutoETL_ActivityNode getAutoETL_ActivityNode() {
		return autoETL_ActivityNode;
	}

	public void setAutoActivityNodeLogID(String autoActivityNodeLogID) {
		this.autoActivityNodeLogID = autoActivityNodeLogID;
	}

	public String getAutoActivityNodeLogID() {
		return autoActivityNodeLogID;
	}
	
	public AutoETL_WorkflowLog getAutoETL_WorkflowLog() {
		return autoETL_WorkflowLog;
	}

	public void setAutoETL_WorkflowLog(AutoETL_WorkflowLog autoETLWorkflowLog) {
		autoETL_WorkflowLog = autoETLWorkflowLog;
	}

	public void setIntOrder(Integer intOrder) {
		this.intOrder = intOrder;
	}

	public Integer getIntOrder() {
		return intOrder;
	}

	public void setStrLines(String strLines) {
		this.strLines = strLines;
	}

	public String getStrLines() {
		return strLines;
	}

	public void setStrActivityNodeExcuteType(String strActivityNodeExcuteType) {
		this.strActivityNodeExcuteType = strActivityNodeExcuteType;
	}

	public String getStrActivityNodeExcuteType() {
		return strActivityNodeExcuteType;
	}
}

