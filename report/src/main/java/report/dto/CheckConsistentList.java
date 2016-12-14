package report.dto;


import java.util.HashSet;
import java.util.Map;
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
import framework.show.ShowContext;


@Entity
@Table(name = "CheckConsistentList")
@IEntity(navigationName="一致性校验")
public class CheckConsistentList implements java.io.Serializable{

	/**  **/
	private static final long serialVersionUID = -7814843592363267968L;

	@Id
	@Column(name = "checkConsistentListID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkConsistentListID;
	
	@IColumn(tagMethodName="getConsistenttypeTag",description="类型")
	@Column(name = "consistenttype", nullable = false,length=1)
	private String consistenttype;
	
	public static Map<String,String> getConsistenttypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("consistenttypeTag");
	}
	
	@Column(name="discription",length =35,nullable=true)
	@IColumn(description="描述",isKeyName=true)
	private String discription;
	
	@Column(name="ignoreVal",length =35,nullable=true)
	@IColumn(description="忽略值")
	private String ignoreVal;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkConsistentList")
	private Set<CheckExtendConsistentParam> checkExtendConsistentParams = new HashSet<CheckExtendConsistentParam>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkConsistentList")
	private Set<CheckConsistentGroup> checkConsistentGroups = new HashSet<CheckConsistentGroup>(0);
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "checkNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true)
	private CheckNode checkNode;
	
	@Transient
	@IColumn(isIdListField = true, target="checkExtendConsistentParams", mappedBy = "autoETL_Param")
	private String[] autoETL_ParamIdList;
	

	public String getConsistenttype() {
		return consistenttype;
	}

	public void setConsistenttype(String consistenttype) {
		this.consistenttype = consistenttype;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public void setCheckConsistentListID(String checkConsistentListID) {
		this.checkConsistentListID = checkConsistentListID;
	}

	public String getCheckConsistentListID() {
		return checkConsistentListID;
	}

	public void setIgnoreVal(String ignoreVal) {
		this.ignoreVal = ignoreVal;
	}

	public String getIgnoreVal() {
		return ignoreVal;
	}

	public void setCheckConsistentGroups(Set<CheckConsistentGroup> checkConsistentGroups) {
		this.checkConsistentGroups = checkConsistentGroups;
	}

	public Set<CheckConsistentGroup> getCheckConsistentGroups() {
		return checkConsistentGroups;
	}

	public void setCheckNode(CheckNode checkNode) {
		this.checkNode = checkNode;
	}

	public CheckNode getCheckNode() {
		return checkNode;
	}

	public void setCheckExtendConsistentParams(
			Set<CheckExtendConsistentParam> checkExtendConsistentParams) {
		this.checkExtendConsistentParams = checkExtendConsistentParams;
	}

	public Set<CheckExtendConsistentParam> getCheckExtendConsistentParams() {
		return checkExtendConsistentParams;
	}

	public void setAutoETL_ParamIdList(String[] autoETL_ParamIdList) {
		this.autoETL_ParamIdList = autoETL_ParamIdList;
	}

	public String[] getAutoETL_ParamIdList() {
		return autoETL_ParamIdList;
	}
}
