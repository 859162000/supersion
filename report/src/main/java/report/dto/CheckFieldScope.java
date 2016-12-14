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
@Table(name = "CheckFieldScope")
@IEntity(navigationName="完整性校验",description="完整性校验")
public class CheckFieldScope implements java.io.Serializable{
	/**  **/
	private static final long serialVersionUID = -1824527666905201067L;

	@Id
	@Column(name = "checkFieldScopeID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkFieldScopeID;
	
	@Column(name="belongType",length =35,nullable=false)
	@IColumn(description="所属类型",isKeyName=true)
	private String belongType;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "checkFieldScopeListID", nullable = false)
	@IColumn(isSingleTagHidden=true)
	private CheckFieldScopeList checkFieldScopeList;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkFieldScope")
	private Set<CheckFieldScopeLeftField> checkFieldScopeLeftFields = new HashSet<CheckFieldScopeLeftField>(0);

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkFieldScope")
	private Set<CheckFieldScopeRightField> checkFieldScopeRightFields = new HashSet<CheckFieldScopeRightField>(0);

	public String getCheckFieldScopeID() {
		return checkFieldScopeID;
	}

	public void setCheckFieldScopeID(String checkFieldScopeID) {
		this.checkFieldScopeID = checkFieldScopeID;
	}

	public String getBelongType() {
		return belongType;
	}

	public void setBelongType(String belongType) {
		this.belongType = belongType;
	}

	public CheckFieldScopeList getCheckFieldScopeList() {
		return checkFieldScopeList;
	}

	public void setCheckFieldScopeList(CheckFieldScopeList checkFieldScopeList) {
		this.checkFieldScopeList = checkFieldScopeList;
	}

	public Set<CheckFieldScopeLeftField> getCheckFieldScopeLeftFields() {
		return checkFieldScopeLeftFields;
	}

	public void setCheckFieldScopeLeftFields(
			Set<CheckFieldScopeLeftField> checkFieldScopeLeftFields) {
		this.checkFieldScopeLeftFields = checkFieldScopeLeftFields;
	}

	public Set<CheckFieldScopeRightField> getCheckFieldScopeRightFields() {
		return checkFieldScopeRightFields;
	}

	public void setCheckFieldScopeRightFields(
			Set<CheckFieldScopeRightField> checkFieldScopeRightFields) {
		this.checkFieldScopeRightFields = checkFieldScopeRightFields;
	}
}
