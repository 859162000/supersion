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
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "CheckFieldScopeList")
@IEntity(navigationName="完整性校验列表",description="完整性校验列表")
public class CheckFieldScopeList implements java.io.Serializable{
	/**  **/
	private static final long serialVersionUID = 3096962941113344372L;

	@Id
	@Column(name = "checkFieldScopeListID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkFieldScopeListID;
	
	@Column(name="discription",length =35,nullable=false)
	@IColumn(description="描述",isKeyName=true)
	private String discription;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "checkNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true)
	private CheckNode checkNode;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkFieldScopeList")
	private Set<CheckExtendScopeParam> checkExtendScopeParams = new HashSet<CheckExtendScopeParam>(0);
	
	@Transient
	@IColumn(isIdListField = true, target="checkExtendScopeParams", mappedBy = "autoETL_Param")
	private String[] autoETL_ParamIdList;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkFieldScopeList")
	private Set<CheckFieldScope> checkFieldScopes = new HashSet<CheckFieldScope>(0);

	public String getCheckFieldScopeListID() {
		return checkFieldScopeListID;
	}

	public void setCheckFieldScopeListID(String checkFieldScopeListID) {
		this.checkFieldScopeListID = checkFieldScopeListID;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public CheckNode getCheckNode() {
		return checkNode;
	}

	public void setCheckNode(CheckNode checkNode) {
		this.checkNode = checkNode;
	}

	public Set<CheckExtendScopeParam> getCheckExtendScopeParams() {
		return checkExtendScopeParams;
	}

	public void setCheckExtendScopeParams(
			Set<CheckExtendScopeParam> checkExtendScopeParams) {
		this.checkExtendScopeParams = checkExtendScopeParams;
	}

	public String[] getAutoETL_ParamIdList() {
		return autoETL_ParamIdList;
	}

	public void setAutoETL_ParamIdList(String[] autoETLParamIdList) {
		autoETL_ParamIdList = autoETLParamIdList;
	}

	public Set<CheckFieldScope> getCheckFieldScopes() {
		return checkFieldScopes;
	}

	public void setCheckFieldScopes(Set<CheckFieldScope> checkFieldScopes) {
		this.checkFieldScopes = checkFieldScopes;
	}
	
}
