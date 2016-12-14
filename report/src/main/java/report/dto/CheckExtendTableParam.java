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
@Table(name = "CheckExtendTableParam")
@IEntity(navigationName="表校验参数",description="表校验参数")
public class CheckExtendTableParam implements java.io.Serializable{
	/**  **/
	private static final long serialVersionUID = 4623863082409189459L;

	@Id
	@Column(name = "checkExtendTableParamId", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String checkExtendTableParamId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "checkTableID", nullable = false)
	@IColumn(isSingleTagHidden=true)
	private CheckTable checkTable;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoParamID")
	@IColumn(description="参数")
	private AutoETL_Param autoETL_Param;
	
	@Transient
	@IColumn(isIdListField = true, target="autoETL_Param")
	private String[] autoETL_ParamIdList;

	public void setCheckTable(CheckTable checkTable) {
		this.checkTable = checkTable;
	}

	public CheckTable getCheckTable() {
		return checkTable;
	}

	public void setAutoETL_ParamIdList(String[] autoETL_ParamIdList) {
		this.autoETL_ParamIdList = autoETL_ParamIdList;
	}

	public String[] getAutoETL_ParamIdList() {
		return autoETL_ParamIdList;
	}

	public void setCheckExtendTableParamId(String checkExtendTableParamId) {
		this.checkExtendTableParamId = checkExtendTableParamId;
	}

	public String getCheckExtendTableParamId() {
		return checkExtendTableParamId;
	}

	public void setAutoETL_Param(AutoETL_Param autoETL_Param) {
		this.autoETL_Param = autoETL_Param;
	}

	public AutoETL_Param getAutoETL_Param() {
		return autoETL_Param;
	}
}
