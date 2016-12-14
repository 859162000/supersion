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
@Table(name = "CheckFieldLine")
@IEntity(navigationName="非聚合校验",description="非聚合校验")
public class CheckFieldLine implements java.io.Serializable{
	/**  **/
	private static final long serialVersionUID = -849892681981699418L;

	@Id
	@Column(name = "checkFieldLineID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkFieldLineID;
	
	@Column(name="fieldType",length =35,nullable=true)
	@IColumn(description="字段类型",isKeyName=true)
	private String fieldType;
	
	@Column(name="compareType",length =35,nullable=true)
	@IColumn(description="对比类型")
	private String compareType;
	
	@Column(name="startRate",length =35,nullable=true)
	@IColumn(description="开始率")
	private Double startRate;
	
	@Column(name="endRate",length =35,nullable=true)
	@IColumn(description="结束率")
	private Double endRate;
	
	@Column(name="startValue",length =35,nullable=true)
	@IColumn(description="开始值")
	private Double startValue;
	
	@Column(name="endValue",length =35,nullable=true)
	@IColumn(description="结束值")
	private Double endValue;
	
	@Column(name="procedureName",length =35,nullable=true)
	@IColumn(description="存储过程名字")
	private String procedureName;
	
	@Column(name="procedureParam",length =35,nullable=true)
	@IColumn(description="存储过程参数")
	private String procedureParam;
	
	@Column(name="procedureDiscription",length =35,nullable=true)
	@IColumn(description="存储过程描述")
	private String procedureDiscription;
	
	@Column(name="ignoreVal",length =35,nullable=true)
	@IColumn(description="忽略值")
	private String ignoreVal;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "checkTableNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true)
	private CheckTableNode checkTableNode;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkFieldLine")
	private Set<CheckFieldLineLeft> checkFieldLineLefts = new HashSet<CheckFieldLineLeft>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkFieldLine")
	private Set<CheckFieldLineRight> checkFieldLineRights = new HashSet<CheckFieldLineRight>(0);

	public String getCheckFieldLineID() {
		return checkFieldLineID;
	}

	public void setCheckFieldLineID(String checkFieldLineID) {
		this.checkFieldLineID = checkFieldLineID;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getCompareType() {
		return compareType;
	}

	public void setCompareType(String compareType) {
		this.compareType = compareType;
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

	public String getIgnoreVal() {
		return ignoreVal;
	}

	public void setIgnoreVal(String ignoreVal) {
		this.ignoreVal = ignoreVal;
	}

	public void setCheckFieldLineLefts(Set<CheckFieldLineLeft> checkFieldLineLefts) {
		this.checkFieldLineLefts = checkFieldLineLefts;
	}

	public Set<CheckFieldLineLeft> getCheckFieldLineLefts() {
		return checkFieldLineLefts;
	}

	public void setCheckFieldLineRights(Set<CheckFieldLineRight> checkFieldLineRights) {
		this.checkFieldLineRights = checkFieldLineRights;
	}

	public Set<CheckFieldLineRight> getCheckFieldLineRights() {
		return checkFieldLineRights;
	}

	public void setStartRate(String startRate) {
		this.startRate = TypeParse.parseDouble(startRate);
	}

	public Double getStartRate() {
		return startRate;
	}

	public void setEndRate(String endRate) {
		this.endRate = TypeParse.parseDouble(endRate);
	}

	public Double getEndRate() {
		return endRate;
	}

	public void setStartValue(String startValue) {
		this.startValue = TypeParse.parseDouble(startValue);
	}

	public Double getStartValue() {
		return startValue;
	}

	public void setEndValue(String endValue) {
		this.endValue = TypeParse.parseDouble(endValue);
	}

	public Double getEndValue() {
		return endValue;
	}

	public void setCheckTableNode(CheckTableNode checkTableNode) {
		this.checkTableNode = checkTableNode;
	}

	public CheckTableNode getCheckTableNode() {
		return checkTableNode;
	}
	
	

}
