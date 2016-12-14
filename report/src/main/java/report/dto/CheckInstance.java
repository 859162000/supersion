package report.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;


@Entity
@Table(name = "CheckInstance")
@IEntity(navigationName="校验实例",description="校验实例")
public class CheckInstance implements java.io.Serializable{
	/**  **/
	private static final long serialVersionUID = 8625037668852497099L;

	@Id
	@Column(name="instanceName",length =35,nullable=false)
	@IColumn(description="校验实例名",isKeyName=true)
	private String instanceName;
	

	@Column(name="dblTolerance",length = 18,precision=6,nullable=false)
	@IColumn(description="容差范围",isNullable = false)
    private Double dblTolerance;
	
	public Double getDblTolerance()
	{
		return this.dblTolerance;
	}
	public void setDblTolerance(String dblTolerance)
	{
		this.dblTolerance =TypeParse.parseDouble(dblTolerance);
	}
	
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkInstance")
	private Set<CheckNode> checkNodes = new HashSet<CheckNode>(0);
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkInstance")
	private Set<CheckExtendParam> checkExtendParams = new HashSet<CheckExtendParam>(0);
	
	/*@Transient
	@IColumn(isIdListField = true, target="checkExtendParams", mappedBy = "autoETL_Param")
	private String[] checkExtendParamsIdList;*/
	
	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public void setCheckNodes(Set<CheckNode> checkNodes) {
		this.checkNodes = checkNodes;
	}

	public Set<CheckNode> getCheckNodes() {
		return checkNodes;
	}

	public void setCheckExtendParams(Set<CheckExtendParam> checkExtendParams) {
		this.checkExtendParams = checkExtendParams;
	}

	public Set<CheckExtendParam> getCheckExtendParams() {
		return checkExtendParams;
	}

	/*public void setCheckExtendParamsIdList(String[] checkExtendParamsIdList) {
		this.checkExtendParamsIdList = checkExtendParamsIdList;
	}

	public String[] getCheckExtendParamsIdList() {
		return checkExtendParamsIdList;
	}*/
}
