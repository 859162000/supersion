package report.dto;

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

@Entity
@Table(name = "CheckFieldAggregationRight")
@IEntity(navigationName="右边聚合校验字段",description="右边聚合校验字段")
public class CheckFieldAggregationRightField implements java.io.Serializable{
	/**  **/
	private static final long serialVersionUID = -9064540040959292843L;

	@Id
	@Column(name = "checkFieldAggregationRightID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkFieldAggregationRightFieldID;
	
	@Column(name="discription",length =35,nullable=true)
	@IColumn(description="描述",isKeyName=true)
	private String discription;
	
	@Column(name="joinType",length =35,nullable=true)
	@IColumn(description="连接类型")
	private String joinType;
	
	@Column(name="value",length =35,nullable=true)
	@IColumn(description="值")
	private String value;
	
	@Column(name="procedureName",length =35,nullable=true)
	@IColumn(description="存储过程名")
	private String procedureName;
	
	@Column(name="procedureParam",length =35,nullable=true)
	@IColumn(description="存储过程参数")
	private String procedureParam;
	
	@Column(name="procedureDiscription",length =35,nullable=true)
	@IColumn(description="存储过程描述")
	private String procedureDiscription;
	
	@Column(name="procedureResultField",length =35,nullable=true)
	@IColumn(description="存储过程结果字段")
	private String procedureResultField;
	
	@Column(name="procedureSplitField",length =35,nullable=true)
	@IColumn(description="存储过程分割字段")
	private String procedureSplitField;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "checkFieldAggregationID", nullable = false)
	@IColumn(isSingleTagHidden=true)
	private CheckFieldAggregation checkFieldAggregation;

	public CheckFieldAggregation getCheckFieldAggregation() {
		return checkFieldAggregation;
	}

	public void setCheckFieldAggregation(CheckFieldAggregation checkFieldAggregation) {
		this.checkFieldAggregation = checkFieldAggregation;
	}

	public void setCheckFieldAggregationRightFieldID(
			String checkFieldAggregationRightFieldID) {
		this.checkFieldAggregationRightFieldID = checkFieldAggregationRightFieldID;
	}

	public String getCheckFieldAggregationRightFieldID() {
		return checkFieldAggregationRightFieldID;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getJoinType() {
		return joinType;
	}

	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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

	public String getProcedureResultField() {
		return procedureResultField;
	}

	public void setProcedureResultField(String procedureResultField) {
		this.procedureResultField = procedureResultField;
	}

	public String getProcedureSplitField() {
		return procedureSplitField;
	}

	public void setProcedureSplitField(String procedureSplitField) {
		this.procedureSplitField = procedureSplitField;
	}
	
}
