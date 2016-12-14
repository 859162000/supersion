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
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;


@Entity
@Table(name = "CheckCaseWhen")
@IEntity(navigationName="CaseWhen校验")
public class CheckCaseWhen implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = 1760048483506520731L;

	@Id
	@Column(name = "checkCaseWhenID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkCaseWhenID;
	
	@Column(name="name",length =35,nullable=true)
	@IColumn(description="字段名",isKeyName=true)
	private String name;
	
	@Column(name="discription",length =35,nullable=true)
	@IColumn(description="描述")
	private String discription;
	
	@Column(name="caseValue",length =35,nullable=true)
	@IColumn(description="case值")
	private String caseValue;
	
	@Column(name="caseCompareType",length =35,nullable=true)
	@IColumn(description="case对比类型")
	private String caseCompareType;
	
	@Column(name="whenValue",length =35,nullable=true)
	@IColumn(description="when值")
	private String whenValue;
	
	@Column(name="whenValueScope",length =35,nullable=true)
	@IColumn(description="when值范围")
	private String whenValueScope;
	
	@Column(name="caseValueScope",length =35,nullable=true)
	@IColumn(description="case值范围")
	private String caseValueScope;
	
	@Column(name="whenCompareType",length =35,nullable=true)
	@IColumn(description="when对比类型")
	private String whenCompareType;
	
	@Column(name="whenType",length =35,nullable=true)
	@IColumn(description="when类型")
	private String whenType;
	
	@Column(name="whenCompareValue",length =35,nullable=true)
	@IColumn(description="when对比值")
	private String whenCompareValue;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "checkTableNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true)
	private CheckTableNode checkTableNode;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkCaseWhen")
	private Set<CheckCaseWhenField> checkCaseWhenFields = new HashSet<CheckCaseWhenField>(0);

	public String getCheckCaseWhenID() {
		return checkCaseWhenID;
	}

	public void setCheckCaseWhenID(String checkCaseWhenID) {
		this.checkCaseWhenID = checkCaseWhenID;
	}

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

	public String getCaseValue() {
		return caseValue;
	}

	public void setCaseValue(String caseValue) {
		this.caseValue = caseValue;
	}

	public String getWhenValueScope() {
		return whenValueScope;
	}

	public void setWhenValueScope(String whenValueScope) {
		this.whenValueScope = whenValueScope;
	}

	public String getCaseValueScope() {
		return caseValueScope;
	}

	public void setCaseValueScope(String caseValueScope) {
		this.caseValueScope = caseValueScope;
	}

	public String getCaseCompareType() {
		return caseCompareType;
	}

	public void setCaseCompareType(String caseCompareType) {
		this.caseCompareType = caseCompareType;
	}

	public String getWhenValue() {
		return whenValue;
	}

	public void setWhenValue(String whenValue) {
		this.whenValue = whenValue;
	}

	public String getWhenCompareValue() {
		return whenCompareValue;
	}

	public void setWhenCompareValue(String whenCompareValue) {
		this.whenCompareValue = whenCompareValue;
	}

	public void setCheckCaseWhenFields(Set<CheckCaseWhenField> checkCaseWhenFields) {
		this.checkCaseWhenFields = checkCaseWhenFields;
	}

	public Set<CheckCaseWhenField> getCheckCaseWhenFields() {
		return checkCaseWhenFields;
	}

	public void setWhenCompareType(String whenCompareType) {
		this.whenCompareType = whenCompareType;
	}

	public String getWhenCompareType() {
		return whenCompareType;
	}

	public void setWhenType(String whenType) {
		this.whenType = whenType;
	}

	public String getWhenType() {
		return whenType;
	}

	public void setCheckTableNode(CheckTableNode checkTableNode) {
		this.checkTableNode = checkTableNode;
	}

	public CheckTableNode getCheckTableNode() {
		return checkTableNode;
	}

}
