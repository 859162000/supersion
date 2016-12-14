package report.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.GenericGenerator;

import extend.dto.AutoETL_Param;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "CheckExtendAggregationParam")
@IEntity(navigationName="聚合校验参数",description="聚合校验参数")
public class CheckExtendAggregationParam implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = -8370446728575015052L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "checkFieldAggregationListID")
	@IColumn(description="聚合校验")
	private CheckFieldAggregationList checkFieldAggregationList;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoParamID")
	@IColumn(description="参数")
	private AutoETL_Param autoETL_Param;
	
	@Transient
	@IColumn(isIdListField = true, target="autoETL_Param")
	private String[] autoETL_ParamIdList;
	
	@Id
	@Column(name = "checkExtendAggregationParamId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkExtendAggregationParamId;

	public CheckFieldAggregationList getCheckFieldAggregationList() {
		return checkFieldAggregationList;
	}

	public void setCheckFieldAggregationList(
			CheckFieldAggregationList checkFieldAggregationList) {
		this.checkFieldAggregationList = checkFieldAggregationList;
	}

	public AutoETL_Param getAutoETL_Param() {
		return autoETL_Param;
	}

	public void setAutoETL_Param(AutoETL_Param autoETLParam) {
		autoETL_Param = autoETLParam;
	}

	public String[] getAutoETL_ParamIdList() {
		return autoETL_ParamIdList;
	}

	public void setAutoETL_ParamIdList(String[] autoETLParamIdList) {
		autoETL_ParamIdList = autoETLParamIdList;
	}

	public String getCheckExtendAggregationParamId() {
		return checkExtendAggregationParamId;
	}

	public void setCheckExtendAggregationParamId(
			String checkExtendAggregationParamId) {
		this.checkExtendAggregationParamId = checkExtendAggregationParamId;
	}
}
