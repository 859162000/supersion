package testsystem.dto;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import coresystem.dto.InstInfo;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Table(name = "SingleTestStatisical")
@IEntity(description="聚合类型",navigationName="聚合类型")
public class TestOnetoTableStatisical {
	
	@IColumn(description="聚合类型",isListShow=true)
	private String FieldListName;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="strInstCode",nullable=false)
	@IColumn(description="部门",isListShow=false)
	public InstInfo department;
	
	/*@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="aid",nullable=false)
	@IColumn(description="地区",isListShow=false)
	public Oa_AreaInfo areaInfo;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="costcode",nullable=false)
	@IColumn(description="费用类别",isListShow=false)
	public Oa_CostType costType;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="oaProjectInfo",nullable=true)
	@IColumn(description="项目",isListShow=false)
	public Oa_ProjectInfo oaProjectInfo;*/
	
	@IColumn(description="总报销金额",isListShow=true)
	private Integer summoneysum;
	
	@IColumn(description="总打款金额",isListShow=true)
	private Integer summoneyend;
	
	public String getFieldListName() {
		return FieldListName;
	}

	public void setFieldListName(String fieldListName) {
		FieldListName = fieldListName;
	}

	public InstInfo getDepartment() {
		return department;
	}

	public void setDepartment(InstInfo department) {
		this.department = department;
	}

	/*public Oa_AreaInfo getAreaInfo() {
		return areaInfo;
	}

	public void setAreaInfo(Oa_AreaInfo areaInfo) {
		this.areaInfo = areaInfo;
	}

	public Oa_CostType getCostType() {
		return costType;
	}

	public void setCostType(Oa_CostType costType) {
		this.costType = costType;
	}

	public Oa_ProjectInfo getOaProjectInfo() {
		return oaProjectInfo;
	}

	public void setOaProjectInfo(Oa_ProjectInfo oaProjectInfo) {
		this.oaProjectInfo = oaProjectInfo;
	}*/

	public Integer getSummoneysum() {
		return summoneysum;
	}

	public void setSummoneysum(Integer summoneysum) {
		this.summoneysum = summoneysum;
	}

	public Integer getSummoneyend() {
		return summoneyend;
	}

	public void setSummoneyend(Integer summoneyend) {
		this.summoneyend = summoneyend;
	}


}
