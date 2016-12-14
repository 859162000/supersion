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
@Table(name = "CheckExtendParam")
@IEntity(navigationName="校验实例参数",description="校验实例参数")
public class CheckExtendParam implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = -3550897630282122174L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "instanceName")
	@IColumn(description="实例")
	private CheckInstance checkInstance;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoParamID")
	@IColumn(description="参数")
	private AutoETL_Param autoETL_Param;
	
	@Transient
	@IColumn(isIdListField = true, target="autoETL_Param")
	private String[] autoETL_ParamIdList;
	
	@Id
	@Column(name = "checkExtendParamId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkExtendParamId;
	
	public CheckInstance getCheckInstance() {
		return checkInstance;
	}

	public void setCheckInstance(CheckInstance checkInstance) {
		this.checkInstance = checkInstance;
	}

	public void setAutoETL_Param(AutoETL_Param autoETL_Param) {
		this.autoETL_Param = autoETL_Param;
	}

	public AutoETL_Param getAutoETL_Param() {
		return autoETL_Param;
	}

	public void setAutoETL_ParamIdList(String[] autoETL_ParamIdList) {
		this.autoETL_ParamIdList = autoETL_ParamIdList;
	}

	public String[] getAutoETL_ParamIdList() {
		return autoETL_ParamIdList;
	}

	public void setCheckExtendParamId(String checkExtendParamId) {
		this.checkExtendParamId = checkExtendParamId;
	}

	public String getCheckExtendParamId() {
		return checkExtendParamId;
	}
}
