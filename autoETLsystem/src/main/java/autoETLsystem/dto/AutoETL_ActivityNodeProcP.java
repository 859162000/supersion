package autoETLsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import extend.dto.AutoETL_Param;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "AutoETL_ActivityNodeProcP")
@IEntity(navigationName="存储过程启动参数",description="存储过程启动参数")
public class AutoETL_ActivityNodeProcP implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNode autoETL_ActivityNode;

	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoParamID")
	private AutoETL_Param autoETL_Param;
	
	@IColumn(isIdListField = true, target="autoETL_Param")
	private String[] autoETL_ParamIdList;
	
	@Id
	@Column(name = "autoActivityNodeProcPID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeProcPID;

	public void setAutoETL_ActivityNode(AutoETL_ActivityNode autoETL_ActivityNode) {
		this.autoETL_ActivityNode = autoETL_ActivityNode;
	}

	public AutoETL_ActivityNode getAutoETL_ActivityNode() {
		return autoETL_ActivityNode;
	}

	public void setAutoActivityNodeProcPID(String autoActivityNodeProcPID) {
		this.autoActivityNodeProcPID = autoActivityNodeProcPID;
	}

	public String getAutoActivityNodeProcPID() {
		return autoActivityNodeProcPID;
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

}

