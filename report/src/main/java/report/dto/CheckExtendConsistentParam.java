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
@Table(name = "CheckExtendConsistentParam")
@IEntity(navigationName="一致性校验参数",description="一致性校验参数")
public class CheckExtendConsistentParam implements java.io.Serializable{

	/**  **/
	private static final long serialVersionUID = -1429065157183612006L;

	@Id
	@Column(name = "checkExtendConsistentParamId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkExtendConsistentParamId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "checkConsistentListID")
	@IColumn(description="一致性校验")
	private CheckConsistentList checkConsistentList;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoParamID")
	@IColumn(description="参数")
	private AutoETL_Param autoETL_Param;
	
	@Transient
	@IColumn(isIdListField = true, target="autoETL_Param")
	private String[] autoETL_ParamIdList;

	public String getCheckExtendConsistentParamId() {
		return checkExtendConsistentParamId;
	}

	public void setCheckExtendConsistentParamId(String checkExtendConsistentParamId) {
		this.checkExtendConsistentParamId = checkExtendConsistentParamId;
	}

	public CheckConsistentList getCheckConsistentList() {
		return checkConsistentList;
	}

	public void setCheckConsistentList(CheckConsistentList checkConsistentList) {
		this.checkConsistentList = checkConsistentList;
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
