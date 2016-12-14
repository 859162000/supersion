package report.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.struts2.ServletActionContext;

import coresystem.dto.InstInfo;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;


@Table(name = "TestTaskRptInstStatisical")
@IEntity(description="TaskRptInstCount",navigationName="TaskRptInstCount")
public class TaskRptInstStatisical implements java.io.Serializable{
	/**  **/
	private static final long serialVersionUID = 8708796646083276364L;


	@IColumn(description="聚合类型",isListShow=true,tagMethodName="getTaskRptInstStatisicalFiledListName")
	private String FieldListName;
	
	

	@SuppressWarnings("unchecked")
	public static Map<String,Object> getTaskRptInstStatisicalFiledListName(){
		Map<String,Object> mapList = new LinkedHashMap<String,Object>();           
		mapList=(Map<String, Object>) ((Map<String,Object>)ServletActionContext.getContext().get("request")).get("report.dto.TaskRptInstStatisicalshowListData");

		if(mapList==null){
			return new LinkedHashMap<String,Object>();
		}
		return mapList;
	}
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strTaskID", nullable = false)
	private TaskFlow taskFlow;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strRptCode", nullable = false)
	private RptInfo rptInfo;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strInstCode", nullable = false)
	private InstInfo instInfo;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dtTaskDate", nullable = false)
	@IColumn(description="数据时间", isNullable = false)
	private Date dtTaskDate;



	@IColumn(description="已提交",isListShow=true)
	private BigDecimal countSubmitStatus;
	
	@IColumn(description="未提交",isListShow=true)
	private BigDecimal countUnSubmitStatus;
	
	@IColumn(description="已校验",isListShow=true)
	private BigDecimal countCheckState;
	
	@IColumn(description="校验成功",isListShow=true)
	private BigDecimal countCheckSuccessState;
	
	@IColumn(description="校验失败",isListShow=true)
	private BigDecimal countCheckFaildState;
	
	
	@IColumn(description="未校验",isListShow=true)
	private BigDecimal countUnCheckState;
	

	@IColumn(description="已审核",isListShow=true)
	private BigDecimal countAllowState;
	
	@IColumn(description="审核成功",isListShow=true)
	private BigDecimal countAllowSuccessState;
	
	@IColumn(description="审核失败",isListShow=true)
	private BigDecimal countAllowFaildState;
	
	@IColumn(description="未审核",isListShow=true)
	private BigDecimal countUnAllowState;
	
	
	
	@IColumn(description="总条数",isListShow=true)
	private BigDecimal countTotaltaskFlow;

	public String getFieldListName() {
		return FieldListName;
	}

	public void setFieldListName(String fieldListName) {
		FieldListName = fieldListName;
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

	public Date getDtTaskDate() {
		return dtTaskDate;
	}

	public void setDtTaskDate(String dtTaskDate) {
		this.dtTaskDate = TypeParse.parseDate(dtTaskDate);
	}

	public BigDecimal getCountSubmitStatus() {
		return countSubmitStatus;
	}

	public void setCountSubmitStatus(String countSubmitStatus) {
		this.countSubmitStatus = TypeParse.parseBigDecimal(countSubmitStatus);
	}

	public BigDecimal getCountUnSubmitStatus() {
		return countUnSubmitStatus;
	}

	public void setCountUnSubmitStatus(String countUnSubmitStatus) {
		this.countUnSubmitStatus = TypeParse.parseBigDecimal(countUnSubmitStatus);
	}

	public BigDecimal getCountCheckState() {
		return countCheckState;
	}

	public void setCountCheckState(String countCheckState) {
		this.countCheckState = TypeParse.parseBigDecimal(countCheckState);
	}

	public BigDecimal getCountCheckSuccessState() {
		return countCheckSuccessState;
	}
	
	public void setCountCheckSuccessState(String countCheckSuccessState) {
		this.countCheckSuccessState = TypeParse.parseBigDecimal(countCheckSuccessState);
	}
	
	public BigDecimal getCountCheckFaildState() {
		return countCheckFaildState;
	}

	public void setCountCheckFaildState(String countCheckFaildState) {
		this.countCheckFaildState = TypeParse.parseBigDecimal(countCheckFaildState);
	}
	
	public BigDecimal getCountUnCheckState() {
		return countUnCheckState;
	}

	public void setCountUnCheckState(String countUnCheckState) {
		this.countUnCheckState = TypeParse.parseBigDecimal(countUnCheckState);
	}

	
	public BigDecimal getCountAllowState() {
		return countAllowState;
	}

	public void setCountAllowState(String countAllowState) {
		this.countAllowState = TypeParse.parseBigDecimal(countAllowState);
	}
	
	public BigDecimal getCountAllowSuccessState() {
		return countAllowSuccessState;
	}

	public void setCountAllowSuccessState(String countAllowSuccessState) {
		this.countAllowSuccessState = TypeParse.parseBigDecimal(countAllowSuccessState);
	}

	public BigDecimal getCountAllowFaildState() {
		return countAllowFaildState;
	}

	public void setCountAllowFaildState(String countAllowFaildState) {
		this.countAllowFaildState = TypeParse.parseBigDecimal(countAllowFaildState);
	}
	
	
	public BigDecimal getCountUnAllowState() {
		return countUnAllowState;
	}

	public void setCountUnAllowState(String countUnAllowState) {
		this.countUnAllowState = TypeParse.parseBigDecimal(countUnAllowState);
	}

	public BigDecimal getCountTotaltaskFlow() {
		return countTotaltaskFlow;
	}

	public void setCountTotaltaskFlow(String countTotaltaskFlow) {
		this.countTotaltaskFlow = TypeParse.parseBigDecimal(countTotaltaskFlow);
	}
	
	
	

	

	

	
}
