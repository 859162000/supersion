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
@Table(name = "CheckExtendScopeParam")
@IEntity(navigationName="完整性校验参数",description="完整性校验参数")
public class CheckExtendScopeParam implements java.io.Serializable{
	/**  **/
	private static final long serialVersionUID = 3459386395587123137L;

	@Id
	@Column(name = "checkExtendScopeParamId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkExtendScopeParamId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "checkFieldScopeListID")
	@IColumn(description="完整性校验")
	private CheckFieldScopeList checkFieldScopeList;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoParamID")
	@IColumn(description="参数")
	private AutoETL_Param autoETL_Param;
	
	@Transient
	@IColumn(isIdListField = true, target="autoETL_Param")
	private String[] autoETL_ParamIdList;

	public String getCheckExtendScopeParamId() {
		return checkExtendScopeParamId;
	}

	public void setCheckExtendScopeParamId(String checkExtendScopeParamId) {
		this.checkExtendScopeParamId = checkExtendScopeParamId;
	}

	public CheckFieldScopeList getCheckFieldScopeList() {
		return checkFieldScopeList;
	}

	public void setCheckFieldScopeList(CheckFieldScopeList checkFieldScopeList) {
		this.checkFieldScopeList = checkFieldScopeList;
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

	
}
