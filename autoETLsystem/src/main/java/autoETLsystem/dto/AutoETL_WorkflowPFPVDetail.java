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
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "AutoETL_WorkflowPFPVDetail")
@IEntity(navigationName="设置参数",description="设置参数")
public class AutoETL_WorkflowPFPVDetail implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoWorkflowPFPVID")
	private AutoETL_WorkflowPFPV autoETL_WorkflowPFPV;                                                                                  

	@Column(name="intOrder",nullable=false)
	@IColumn(description="顺序", isNullable = false)
	private Integer intOrder;

	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoParamID", nullable = false)
	private AutoETL_Param autoETL_Param;

	@Id
	@Column(name = "autoWorkflowPFPVDetailID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoWorkflowPFPVDetailID;

	public void setAutoETL_Param(AutoETL_Param autoETL_Param) {
		this.autoETL_Param = autoETL_Param;
	}


	public AutoETL_Param getAutoETL_Param() {
		return autoETL_Param;
	}

	public void setIntOrder(String intOrder) {
		this.intOrder =TypeParse.parseInt(intOrder);
	}


	public Integer getIntOrder() {
		return intOrder;
	}


	public void setAutoWorkflowPFPVDetailID(String autoWorkflowPFPVDetailID) {
		this.autoWorkflowPFPVDetailID = autoWorkflowPFPVDetailID;
	}


	public String getAutoWorkflowPFPVDetailID() {
		return autoWorkflowPFPVDetailID;
	}


	public void setAutoETL_WorkflowPFPV(AutoETL_WorkflowPFPV autoETL_WorkflowPFPV) {
		this.autoETL_WorkflowPFPV = autoETL_WorkflowPFPV;
	}


	public AutoETL_WorkflowPFPV getAutoETL_WorkflowPFPV() {
		return autoETL_WorkflowPFPV;
	}

}

