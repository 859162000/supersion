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
@Table(name = "CheckFieldAggregationList")
@IEntity(navigationName="聚合校验",description="聚合校验")
public class CheckFieldAggregationList implements java.io.Serializable{ 
	
	/**  **/
	private static final long serialVersionUID = 3592333146603454306L;

	@Id
	@Column(name = "checkFieldAggregationListID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkFieldAggregationListID;
	
	@Column(name="discription",length =35,nullable=false)
	@IColumn(description="描述",isKeyName=true)
	private String discription;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "checkNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true)
	private CheckNode checkNode;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkFieldAggregationList")
	private Set<CheckExtendAggregationParam> checkExtendAggregationParams = new HashSet<CheckExtendAggregationParam>(0);
	
	@Transient
	@IColumn(isIdListField = true, target="checkExtendAggregationParams", mappedBy = "autoETL_Param")
	private String[] autoETL_ParamIdList;

	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "checkFieldAggregationList")
	private Set<CheckFieldAggregation> checkFieldAggregations = new HashSet<CheckFieldAggregation>(0);
	

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public void setCheckNode(CheckNode checkNode) {
		this.checkNode = checkNode;
	}

	public CheckNode getCheckNode() {
		return checkNode;
	}

	public void setCheckFieldAggregations(Set<CheckFieldAggregation> checkFieldAggregations) {
		this.checkFieldAggregations = checkFieldAggregations;
	}

	public Set<CheckFieldAggregation> getCheckFieldAggregations() {
		return checkFieldAggregations;
	}

	public void setCheckFieldAggregationListID(
			String checkFieldAggregationListID) {
		this.checkFieldAggregationListID = checkFieldAggregationListID;
	}

	public String getCheckFieldAggregationListID() {
		return checkFieldAggregationListID;
	}

	public void setCheckExtendAggregationParams(
			Set<CheckExtendAggregationParam> checkExtendAggregationParams) {
		this.checkExtendAggregationParams = checkExtendAggregationParams;
	}

	public Set<CheckExtendAggregationParam> getCheckExtendAggregationParams() {
		return checkExtendAggregationParams;
	}

	public void setAutoETL_ParamIdList(String[] autoETL_ParamIdList) {
		this.autoETL_ParamIdList = autoETL_ParamIdList;
	}

	public String[] getAutoETL_ParamIdList() {
		return autoETL_ParamIdList;
	}

}
