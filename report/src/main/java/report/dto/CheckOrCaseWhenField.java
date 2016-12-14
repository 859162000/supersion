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
@Table(name = "CheckOrCaseWhenField")
@IEntity(navigationName="条件字段校验",description="条件字段校验")
public class CheckOrCaseWhenField implements java.io.Serializable{
	/**  **/
	private static final long serialVersionUID = 872437842107907983L;

	@Id
	@Column(name = "checkOrCaseWhenFieldID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkOrCaseWhenFieldID;
	
	@Column(name="name",length =35,nullable=true)
	@IColumn(description="字段名",isKeyName=true)
	private String name;
	
	@Column(name="discription",length =35,nullable=true)
	@IColumn(description="描述")
	private String discription;
	
	@Column(name="joinType",length =35,nullable=true)
	@IColumn(description="连接类型")
	private String joinType;

	@Column(name="whenValueRule",length =35,nullable=true)
	@IColumn(description="when值规则")
	private String whenValueRule;
	
	@Column(name="whenValue",length =35,nullable=true)
	@IColumn(description="when值")
	private String whenValue;
	
	@Column(name="whenValueScope",length =35,nullable=true)
	@IColumn(description="when值范围")
	private String whenValueScope;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "checkOrCaseWhenId", nullable = true)
	@IColumn(isSingleTagHidden=true)
	private CheckOrCaseWhen checkOrCaseWhen;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getWhenValueRule() {
		return whenValueRule;
	}

	public void setWhenValueRule(String whenValueRule) {
		this.whenValueRule = whenValueRule;
	}

	public String getWhenValue() {
		return whenValue;
	}

	public void setWhenValue(String whenValue) {
		this.whenValue = whenValue;
	}

	public String getWhenValueScope() {
		return whenValueScope;
	}

	public void setWhenValueScope(String whenValueScope) {
		this.whenValueScope = whenValueScope;
	}

	public void setCheckOrCaseWhenFieldID(String checkOrCaseWhenFieldID) {
		this.checkOrCaseWhenFieldID = checkOrCaseWhenFieldID;
	}

	public String getCheckOrCaseWhenFieldID() {
		return checkOrCaseWhenFieldID;
	}

	public void setCheckOrCaseWhen(CheckOrCaseWhen checkOrCaseWhen) {
		this.checkOrCaseWhen = checkOrCaseWhen;
	}

	public CheckOrCaseWhen getCheckOrCaseWhen() {
		return checkOrCaseWhen;
	}
}
