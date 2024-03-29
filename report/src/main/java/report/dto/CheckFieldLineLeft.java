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
@Table(name = "CheckFieldLineLeft")
@IEntity(navigationName="非聚合左边字段",description="非聚合左边字段")
public class CheckFieldLineLeft implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = -9086863747694003002L;

	@Id
	@Column(name = "checkFieldLineLeftID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkFieldLineLeftID;
	
	@Column(name="name",length =35,nullable=true)
	@IColumn(description="字段名",isKeyName=true)
	private String name;

	@Column(name="discription",length =35,nullable=true)
	@IColumn(description="描述")
	private String discription;
	
	@Column(name="joinType",length =35,nullable=true)
	@IColumn(description="连接类型")
	private String joinType;
	
	@Column(name="value",length =35,nullable=true)
	@IColumn(description="值")
	private String value;
	
	@Column(name="valueRule",length =35,nullable=true)
	@IColumn(description="值规则")
	private String valueRule;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "checkFieldLineID", nullable = true)
	@IColumn(isSingleTagHidden=true)
	private CheckFieldLine checkFieldLine;

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getValueRule() {
		return valueRule;
	}

	public void setValueRule(String valueRule) {
		this.valueRule = valueRule;
	}

	public void setCheckFieldLineLeftID(String checkFieldLineLeftID) {
		this.checkFieldLineLeftID = checkFieldLineLeftID;
	}

	public String getCheckFieldLineLeftID() {
		return checkFieldLineLeftID;
	}

	public void setCheckFieldLine(CheckFieldLine checkFieldLine) {
		this.checkFieldLine = checkFieldLine;
	}

	public CheckFieldLine getCheckFieldLine() {
		return checkFieldLine;
	}
}
