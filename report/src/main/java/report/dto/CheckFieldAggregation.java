package report.dto;


import java.util.HashSet;
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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "CheckFieldAggregation")
@IEntity(navigationName="聚合校验表",description="聚合校验表")
public class CheckFieldAggregation implements java.io.Serializable{
	/**  **/
	private static final long serialVersionUID = -8795992975769615605L;

	@Id
	@Column(name = "checkFieldAggregationID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkFieldAggregationID;
	
	@Column(name="dataPeriod",length =35,nullable=true)
	@IColumn(description="数据期数",isKeyName=true)
	private String dataPeriod;
	
	@Column(name="operationType",length =35,nullable=true)
	@IColumn(description="操作类型")
	private String operationType;
	
	@Column(name="leftSplitField",length =35,nullable=true)
	@IColumn(description="左边分割字段")
	private String leftSplitField;

	@Column(name="rightSplitField",length =35,nullable=true)
	@IColumn(description="右边分割字段")
	private String rightSplitField;
	
	@Column(name="startRate",length =35,nullable=true)
	@IColumn(description="启始变动率")
	private Double startRate;
	
	@Column(name="endRate",length =35,nullable=true)
	@IColumn(description="结束变动率")
	private Double endRate;
	
	@Column(name="startValue",length =35,nullable=true)
	@IColumn(description="启始值")
	private Double startValue;
	
	@Column(name="endValue",length =35,nullable=true)
	@IColumn(description="结束值")
	private Double endValue;
	
	@Column(name="compareType",length =35,nullable=true)
	@IColumn(description="比较类型")
	private String compareType;
	
	@Column(name="splitDiscription",length =35,nullable=true)
	@IColumn(description="分割字段描述")
	private String splitDiscription;
	
	@Column(name="fieldType",length =35,nullable=true)
	@IColumn(description="字段类型")
	private String fieldType;
	
	@Column(name="procedureName",length =35,nullable=true)
	@IColumn(description="存储过程名")
	private String procedureName;
	
	@Column(name="procedureParam",length =35,nullable=true)
	@IColumn(description="存储过程参数")
	private String procedureParam;
	
	@Column(name="procedureDiscription",length =35,nullable=true)
	@IColumn(description="存储过程描述")
	private String procedureDiscription;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "checkFieldAggregationListID", nullable = true)
	@IColumn(isSingleTagHidden=true)
	private CheckFieldAggregationList checkFieldAggregationList;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkFieldAggregation")
	private Set<CheckFieldAggregationLeftField> checkFieldAggregationLeftFields = new HashSet<CheckFieldAggregationLeftField>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkFieldAggregation")
	private Set<CheckFieldAggregationRightField> checkFieldAggregationRightFields = new HashSet<CheckFieldAggregationRightField>(0);

	public String getCheckFieldAggregationID() {
		return checkFieldAggregationID;
	}

	public void setCheckFieldAggregationID(String checkFieldAggregationID) {
		this.checkFieldAggregationID = checkFieldAggregationID;
	}

	public String getDataPeriod() {
		return dataPeriod;
	}

	public void setDataPeriod(String dataPeriod) {
		this.dataPeriod = dataPeriod;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getLeftSplitField() {
		return leftSplitField;
	}

	public void setLeftSplitField(String leftSplitField) {
		this.leftSplitField = leftSplitField;
	}

	public String getRightSplitField() {
		return rightSplitField;
	}

	public void setRightSplitField(String rightSplitField) {
		this.rightSplitField = rightSplitField;
	}

	public Double getStartRate() {
		return startRate;
	}

	public void setStartRate(String startRate) {
		this.startRate = TypeParse.parseDouble(startRate);
	}

	public Double getEndRate() {
		return endRate;
	}

	public void setEndRate(String endRate) {
		this.endRate = TypeParse.parseDouble(endRate);
	}

	public Double getStartValue() {
		return startValue;
	}

	public void setStartValue(String startValue) {
		this.startValue = TypeParse.parseDouble(startValue);
	}

	public Double getEndValue() {
		return endValue;
	}

	public void setEndValue(String endValue) {
		this.endValue = TypeParse.parseDouble(endValue);
	}

	public String getCompareType() {
		return compareType;
	}

	public void setCompareType(String compareType) {
		this.compareType = compareType;
	}

	public String getSplitDiscription() {
		return splitDiscription;
	}

	public void setSplitDiscription(String splitDiscription) {
		this.splitDiscription = splitDiscription;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public String getProcedureParam() {
		return procedureParam;
	}

	public void setProcedureParam(String procedureParam) {
		this.procedureParam = procedureParam;
	}

	public String getProcedureDiscription() {
		return procedureDiscription;
	}

	public void setProcedureDiscription(String procedureDiscription) {
		this.procedureDiscription = procedureDiscription;
	}

	public CheckFieldAggregationList getCheckFieldAggregationList() {
		return checkFieldAggregationList;
	}

	public void setCheckFieldAggregationList(
			CheckFieldAggregationList checkFieldAggregationList) {
		this.checkFieldAggregationList = checkFieldAggregationList;
	}

	public Set<CheckFieldAggregationLeftField> getCheckFieldAggregationLeftFields() {
		return checkFieldAggregationLeftFields;
	}

	public void setCheckFieldAggregationLeftFields(
			Set<CheckFieldAggregationLeftField> checkFieldAggregationLeftFields) {
		this.checkFieldAggregationLeftFields = checkFieldAggregationLeftFields;
	}

	public Set<CheckFieldAggregationRightField> getCheckFieldAggregationRightFields() {
		return checkFieldAggregationRightFields;
	}

	public void setCheckFieldAggregationRightFields(
			Set<CheckFieldAggregationRightField> checkFieldAggregationRightFields) {
		this.checkFieldAggregationRightFields = checkFieldAggregationRightFields;
	}
	
}
