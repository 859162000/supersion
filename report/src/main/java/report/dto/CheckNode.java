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

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "CheckNode")
@IEntity(navigationName="校验结点",description="校验结点")
public class CheckNode implements java.io.Serializable{
	/**  **/
	private static final long serialVersionUID = 8907460552093514728L;

	@Id
	@Column(name = "checkNodeID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkNodeID;
	
	@IColumn(tagMethodName="getCheckNodeTypeTag",description="结点类型")
	@Column(name = "checkNodeType", nullable = false)
	private String checkNodeType;
	
	public static Map<String,String> getCheckNodeTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("checkNodeType");
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "instanceName", nullable = true)
	@IColumn(isSingleTagHidden=true)
	private CheckInstance checkInstance;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkNode")
	private Set<CheckFieldAggregationList> checkFieldAggregationLists = new HashSet<CheckFieldAggregationList>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkNode")
	private Set<CheckFieldScopeList> checkFieldScopeList = new HashSet<CheckFieldScopeList>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkNode")
	private Set<CheckConsistentList> checkConsistentLists = new HashSet<CheckConsistentList>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkNode")
	private Set<CheckTable> checkTables = new HashSet<CheckTable>(0);
	
	public String getCheckNodeID() {
		return checkNodeID;
	}

	public void setCheckNodeID(String checkNodeID) {
		this.checkNodeID = checkNodeID;
	}

	public String getCheckNodeType() {
		return checkNodeType;
	}

	public void setCheckNodeType(String checkNodeType) {
		this.checkNodeType = checkNodeType;
	}

	public void setCheckInstance(CheckInstance checkInstance) {
		this.checkInstance = checkInstance;
	}

	public CheckInstance getCheckInstance() {
		return checkInstance;
	}


	public void setCheckTables(Set<CheckTable> checkTables) {
		this.checkTables = checkTables;
	}

	public Set<CheckTable> getCheckTables() {
		return checkTables;
	}

	public void setCheckFieldAggregationLists(
			Set<CheckFieldAggregationList> checkFieldAggregationLists) {
		this.checkFieldAggregationLists = checkFieldAggregationLists;
	}

	public Set<CheckFieldAggregationList> getCheckFieldAggregationLists() {
		return checkFieldAggregationLists;
	}

	public void setCheckConsistentLists(Set<CheckConsistentList> checkConsistentLists) {
		this.checkConsistentLists = checkConsistentLists;
	}

	public Set<CheckConsistentList> getCheckConsistentLists() {
		return checkConsistentLists;
	}

	public Set<CheckFieldScopeList> getCheckFieldScopeList() {
		return checkFieldScopeList;
	}

	public void setCheckFieldScopeList(Set<CheckFieldScopeList> checkFieldScopeList) {
		this.checkFieldScopeList = checkFieldScopeList;
	}
}
