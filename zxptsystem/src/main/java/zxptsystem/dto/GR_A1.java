package zxptsystem.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.interfaces.RequestManager;
import framework.services.interfaces.LogicParamManager;

@Entity
@Table(name="GR_A1")
@IEntity(description="报告头标识")
public class GR_A1 implements Serializable{

	private static final long serialVersionUID = 3508385652264696067L;
	
	@Id
	@Column(name="ReportNo", length=50, nullable=false)
	@IColumn(description="报告编号", isNullable=false)
	private String ReportNo;
	
	@Column(name="QueryRequestTime", length=50, nullable=true)
	@IColumn(description="查询请求时间", isNullable=true)
	private String QueryRequestTime;
	
	@Column(name="ReportTime", length=50, nullable=true)
	@IColumn(description="报告时间", isNullable=true)
	private String ReportTime;

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_A2> grA2Set= new HashSet<GR_A2>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_B1> grB1Set= new HashSet<GR_B1>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_B2> grB2Set= new HashSet<GR_B2>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_B3> grB3Set= new HashSet<GR_B3>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_B41> grB41Set= new HashSet<GR_B41>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_C1> grC1Set= new HashSet<GR_C1>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_C21> grC21Set= new HashSet<GR_C21>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_C22> grC22Set= new HashSet<GR_C22>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_C23> grC23Set= new HashSet<GR_C23>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_C24> grC24Set= new HashSet<GR_C24>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_C31> grC31Set= new HashSet<GR_C31>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_C32> grC32Set= new HashSet<GR_C32>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_C33> grC33Set= new HashSet<GR_C33>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_C34> grC34Set= new HashSet<GR_C34>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_D1> grD1Set= new HashSet<GR_D1>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_D2> grD2Set= new HashSet<GR_D2>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_D31> grD31Set= new HashSet<GR_D31>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_D32> grD32Set= new HashSet<GR_D32>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_D33> grD33Set= new HashSet<GR_D33>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_D34> grD34Set= new HashSet<GR_D34>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_D41> grD41Set= new HashSet<GR_D41>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_D42> grD42Set= new HashSet<GR_D42>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_D43> grD43Set= new HashSet<GR_D43>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_D44> grD44Set= new HashSet<GR_D44>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_D51> grD51Set= new HashSet<GR_D51>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_D52> grD52Set= new HashSet<GR_D52>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_D53> grD53Set= new HashSet<GR_D53>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_D54> grD54Set= new HashSet<GR_D54>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_D6> grD6Set= new HashSet<GR_D6>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_E1> grE1Set= new HashSet<GR_E1>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_E21> grE21Set= new HashSet<GR_E21>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_E22> grE22Set= new HashSet<GR_E22>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_E3> grE3Set= new HashSet<GR_E3>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_E4> grE4Set= new HashSet<GR_E4>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_E51> grE51Set= new HashSet<GR_E51>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_E52> grE52Set= new HashSet<GR_E52>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_E6> grE6Set= new HashSet<GR_E6>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_E7> grE7Set= new HashSet<GR_E7>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_E8> grE8Set= new HashSet<GR_E8>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_E9> grE9Set= new HashSet<GR_E9>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_E10> grE10Set= new HashSet<GR_E10>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_F1> grF1Set= new HashSet<GR_F1>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_F2> grF2Set= new HashSet<GR_F2>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_G1> grG1Set= new HashSet<GR_G1>(0);
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "ReportNo")
	private Set<GR_G2> grG2Set= new HashSet<GR_G2>(0);
	
	public String getReportNo() {
		return ReportNo;
	}

	public void setReportNo(String reportNo) {
		ReportNo = reportNo;
	}

	public String getQueryRequestTime() {
		return QueryRequestTime;
	}

	public void setQueryRequestTime(String queryRequestTime) {
		QueryRequestTime = queryRequestTime;
	}

	public String getReportTime() {
		return ReportTime;
	}

	public void setReportTime(String reportTime) {
		ReportTime = reportTime;
	}

	public Set<GR_A2> getGrA2Set() {
		return grA2Set;
	}

	public void setGrA2Set(Set<GR_A2> grA2Set) {
		this.grA2Set = grA2Set;
	}

	public Set<GR_B1> getGrB1Set() {
		return grB1Set;
	}

	public void setGrB1Set(Set<GR_B1> grB1Set) {
		this.grB1Set = grB1Set;
	}

	public Set<GR_B2> getGrB2Set() {
		return grB2Set;
	}

	public void setGrB2Set(Set<GR_B2> grB2Set) {
		this.grB2Set = grB2Set;
	}

	public Set<GR_B3> getGrB3Set() {
		return grB3Set;
	}

	public void setGrB3Set(Set<GR_B3> grB3Set) {
		this.grB3Set = grB3Set;
	}

	public Set<GR_B41> getGrB41Set() {
		return grB41Set;
	}

	public void setGrB41Set(Set<GR_B41> grB41Set) {
		this.grB41Set = grB41Set;
	}

	public Set<GR_C1> getGrC1Set() {
		return grC1Set;
	}

	public void setGrC1Set(Set<GR_C1> grC1Set) {
		this.grC1Set = grC1Set;
	}

	public Set<GR_C21> getGrC21Set() {
		return grC21Set;
	}

	public void setGrC21Set(Set<GR_C21> grC21Set) {
		this.grC21Set = grC21Set;
	}

	public Set<GR_C22> getGrC22Set() {
		return grC22Set;
	}

	public void setGrC22Set(Set<GR_C22> grC22Set) {
		this.grC22Set = grC22Set;
	}

	public Set<GR_C23> getGrC23Set() {
		return grC23Set;
	}

	public void setGrC23Set(Set<GR_C23> grC23Set) {
		this.grC23Set = grC23Set;
	}

	public Set<GR_C24> getGrC24Set() {
		return grC24Set;
	}

	public void setGrC24Set(Set<GR_C24> grC24Set) {
		this.grC24Set = grC24Set;
	}

	public Set<GR_C31> getGrC31Set() {
		return grC31Set;
	}

	public void setGrC31Set(Set<GR_C31> grC31Set) {
		this.grC31Set = grC31Set;
	}

	public Set<GR_C32> getGrC32Set() {
		return grC32Set;
	}

	public void setGrC32Set(Set<GR_C32> grC32Set) {
		this.grC32Set = grC32Set;
	}

	public Set<GR_C33> getGrC33Set() {
		return grC33Set;
	}

	public void setGrC33Set(Set<GR_C33> grC33Set) {
		this.grC33Set = grC33Set;
	}

	public Set<GR_C34> getGrC34Set() {
		return grC34Set;
	}

	public void setGrC34Set(Set<GR_C34> grC34Set) {
		this.grC34Set = grC34Set;
	}

	public Set<GR_D1> getGrD1Set() {
		return grD1Set;
	}

	public void setGrD1Set(Set<GR_D1> grD1Set) {
		this.grD1Set = grD1Set;
	}

	public Set<GR_D2> getGrD2Set() {
		return grD2Set;
	}

	public void setGrD2Set(Set<GR_D2> grD2Set) {
		this.grD2Set = grD2Set;
	}

	public Set<GR_D31> getGrD31Set() {
		return grD31Set;
	}

	public void setGrD31Set(Set<GR_D31> grD31Set) {
		this.grD31Set = grD31Set;
	}

	public Set<GR_D32> getGrD32Set() {
		return grD32Set;
	}

	public void setGrD32Set(Set<GR_D32> grD32Set) {
		this.grD32Set = grD32Set;
	}

	public Set<GR_D33> getGrD33Set() {
		return grD33Set;
	}

	public void setGrD33Set(Set<GR_D33> grD33Set) {
		this.grD33Set = grD33Set;
	}

	public Set<GR_D34> getGrD34Set() {
		return grD34Set;
	}

	public void setGrD34Set(Set<GR_D34> grD34Set) {
		this.grD34Set = grD34Set;
	}

	public Set<GR_D41> getGrD41Set() {
		return grD41Set;
	}

	public void setGrD41Set(Set<GR_D41> grD41Set) {
		this.grD41Set = grD41Set;
	}

	public Set<GR_D42> getGrD42Set() {
		return grD42Set;
	}

	public void setGrD42Set(Set<GR_D42> grD42Set) {
		this.grD42Set = grD42Set;
	}

	public Set<GR_D43> getGrD43Set() {
		return grD43Set;
	}

	public void setGrD43Set(Set<GR_D43> grD43Set) {
		this.grD43Set = grD43Set;
	}

	public Set<GR_D44> getGrD44Set() {
		return grD44Set;
	}

	public void setGrD44Set(Set<GR_D44> grD44Set) {
		this.grD44Set = grD44Set;
	}

	public Set<GR_D51> getGrD51Set() {
		return grD51Set;
	}

	public void setGrD51Set(Set<GR_D51> grD51Set) {
		this.grD51Set = grD51Set;
	}

	public Set<GR_D52> getGrD52Set() {
		return grD52Set;
	}

	public void setGrD52Set(Set<GR_D52> grD52Set) {
		this.grD52Set = grD52Set;
	}

	public Set<GR_D53> getGrD53Set() {
		return grD53Set;
	}

	public void setGrD53Set(Set<GR_D53> grD53Set) {
		this.grD53Set = grD53Set;
	}

	public Set<GR_D54> getGrD54Set() {
		return grD54Set;
	}

	public void setGrD54Set(Set<GR_D54> grD54Set) {
		this.grD54Set = grD54Set;
	}

	public Set<GR_D6> getGrD6Set() {
		return grD6Set;
	}

	public void setGrD6Set(Set<GR_D6> grD6Set) {
		this.grD6Set = grD6Set;
	}

	public Set<GR_E1> getGrE1Set() {
		return grE1Set;
	}

	public void setGrE1Set(Set<GR_E1> grE1Set) {
		this.grE1Set = grE1Set;
	}

	public Set<GR_E21> getGrE21Set() {
		return grE21Set;
	}

	public void setGrE21Set(Set<GR_E21> grE21Set) {
		this.grE21Set = grE21Set;
	}

	public Set<GR_E22> getGrE22Set() {
		return grE22Set;
	}

	public void setGrE22Set(Set<GR_E22> grE22Set) {
		this.grE22Set = grE22Set;
	}

	public Set<GR_E3> getGrE3Set() {
		return grE3Set;
	}

	public void setGrE3Set(Set<GR_E3> grE3Set) {
		this.grE3Set = grE3Set;
	}

	public Set<GR_E4> getGrE4Set() {
		return grE4Set;
	}

	public void setGrE4Set(Set<GR_E4> grE4Set) {
		this.grE4Set = grE4Set;
	}

	public Set<GR_E51> getGrE51Set() {
		return grE51Set;
	}

	public void setGrE51Set(Set<GR_E51> grE51Set) {
		this.grE51Set = grE51Set;
	}

	public Set<GR_E52> getGrE52Set() {
		return grE52Set;
	}

	public void setGrE52Set(Set<GR_E52> grE52Set) {
		this.grE52Set = grE52Set;
	}

	public Set<GR_E6> getGrE6Set() {
		return grE6Set;
	}

	public void setGrE6Set(Set<GR_E6> grE6Set) {
		this.grE6Set = grE6Set;
	}

	public Set<GR_E7> getGrE7Set() {
		return grE7Set;
	}

	public void setGrE7Set(Set<GR_E7> grE7Set) {
		this.grE7Set = grE7Set;
	}

	public Set<GR_E8> getGrE8Set() {
		return grE8Set;
	}

	public void setGrE8Set(Set<GR_E8> grE8Set) {
		this.grE8Set = grE8Set;
	}

	public Set<GR_E9> getGrE9Set() {
		return grE9Set;
	}

	public void setGrE9Set(Set<GR_E9> grE9Set) {
		this.grE9Set = grE9Set;
	}

	public Set<GR_E10> getGrE10Set() {
		return grE10Set;
	}

	public void setGrE10Set(Set<GR_E10> grE10Set) {
		this.grE10Set = grE10Set;
	}

	public Set<GR_F1> getGrF1Set() {
		return grF1Set;
	}

	public void setGrF1Set(Set<GR_F1> grF1Set) {
		this.grF1Set = grF1Set;
	}

	public Set<GR_F2> getGrF2Set() {
		return grF2Set;
	}

	public void setGrF2Set(Set<GR_F2> grF2Set) {
		this.grF2Set = grF2Set;
	}

	public Set<GR_G1> getGrG1Set() {
		return grG1Set;
	}

	public void setGrG1Set(Set<GR_G1> grG1Set) {
		this.grG1Set = grG1Set;
	}

	public Set<GR_G2> getGrG2Set() {
		return grG2Set;
	}

	public void setGrG2Set(Set<GR_G2> grG2Set) {
		this.grG2Set = grG2Set;
	}
	
	
}
