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
@Table(name = "CheckConsistentGroup")
@IEntity(navigationName="一致性校验分组")
public class CheckConsistentGroup implements java.io.Serializable{

	/**  **/
	private static final long serialVersionUID = 4664845448849113272L;

	@Id
	@Column(name = "checkConsistentGroupID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkConsistentGroupID;
	
	@Column(name="keyField",length =35,nullable=true)
	@IColumn(description="主键字段",isKeyName=true)
	private String keyField;
	
	@Column(name="keyDiscription",length =35,nullable=true)
	@IColumn(description="主键字段描述")
	private String keyDiscription;
	
	@Column(name="nameField",length =35,nullable=true)
	@IColumn(description="名称字段")
	private String nameField;
	
	@Column(name="nameDiscription",length =35,nullable=true)
	@IColumn(description="名称字段描述")
	private String nameDiscription;
	
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
	@JoinColumn(name = "checkConsistentListID")
	@IColumn(isSingleTagHidden=true)
	private CheckConsistentList checkConsistentList;

	public String getCheckConsistentGroupID() {
		return checkConsistentGroupID;
	}

	public void setCheckConsistentGroupID(String checkConsistentGroupID) {
		this.checkConsistentGroupID = checkConsistentGroupID;
	}

	public String getKeyField() {
		return keyField;
	}

	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}

	public String getKeyDiscription() {
		return keyDiscription;
	}

	public void setKeyDiscription(String keyDiscription) {
		this.keyDiscription = keyDiscription;
	}

	public String getNameField() {
		return nameField;
	}

	public void setNameField(String nameField) {
		this.nameField = nameField;
	}

	public void setCheckConsistentList(CheckConsistentList checkConsistentList) {
		this.checkConsistentList = checkConsistentList;
	}

	public CheckConsistentList getCheckConsistentList() {
		return checkConsistentList;
	}

	public void setNameDiscription(String nameDiscription) {
		this.nameDiscription = nameDiscription;
	}

	public String getNameDiscription() {
		return nameDiscription;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureParam(String procedureParam) {
		this.procedureParam = procedureParam;
	}

	public String getProcedureParam() {
		return procedureParam;
	}

	public void setProcedureDiscription(String procedureDiscription) {
		this.procedureDiscription = procedureDiscription;
	}

	public String getProcedureDiscription() {
		return procedureDiscription;
	}
}
