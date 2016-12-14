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
@Table(name = "CheckFieldScopeRightField")
@IEntity(navigationName="右边完整域",description="左边完整域")
public class CheckFieldScopeRightField implements java.io.Serializable{
	/**  **/
	private static final long serialVersionUID = -3278431050729501297L;

	@Id
	@Column(name = "checkFieldScopeRightFieldID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkFieldScopeRightFieldID;
	
	@Column(name="discription",length =35,nullable=false)
	@IColumn(description="描述",isKeyName=true)
	private String discription;
	
	@Column(name="name",length =35,nullable=false)
	@IColumn(description="名称")
	private String name;
	
	@Column(name="procedureDiscription",length =35,nullable=false)
	@IColumn(description="存储过程描述")
	private String procedureDiscription;
	
	@Column(name="procedureName",length =35,nullable=false)
	@IColumn(description="存储过程名")
	private String procedureName;
	
	@Column(name="procedureParam",length =35,nullable=false)
	@IColumn(description="存储过程参数")
	private String procedureParam;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "checkFieldScopeID", nullable = false)
	@IColumn(isSingleTagHidden=true)
	private CheckFieldScope checkFieldScope;

	public String getCheckFieldScopeRightFieldID() {
		return checkFieldScopeRightFieldID;
	}

	public void setCheckFieldScopeRightFieldID(String checkFieldScopeRightFieldID) {
		this.checkFieldScopeRightFieldID = checkFieldScopeRightFieldID;
	}

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

	public String getProcedureDiscription() {
		return procedureDiscription;
	}

	public void setProcedureDiscription(String procedureDiscription) {
		this.procedureDiscription = procedureDiscription;
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

	public CheckFieldScope getCheckFieldScope() {
		return checkFieldScope;
	}

	public void setCheckFieldScope(CheckFieldScope checkFieldScope) {
		this.checkFieldScope = checkFieldScope;
	}
}
