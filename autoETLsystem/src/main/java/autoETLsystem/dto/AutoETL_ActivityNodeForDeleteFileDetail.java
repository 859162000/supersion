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
@Table(name = "AutoETL_ActivityNodeForDFileD")
@IEntity(navigationName="设置参数",description="设置参数")
public class AutoETL_ActivityNodeForDeleteFileDetail implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "activityNodeForDeleteFileID")
	private AutoETL_ActivityNodeForDeleteFile autoETL_ActivityNodeForDeleteFile;                                                                                  

	@Column(name="intOrder",nullable=false)
	@IColumn(description="顺序", isNullable = false)
	private Integer intOrder;

	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoParamID", nullable = false)
	private AutoETL_Param autoETL_Param;

	@Id
	@Column(name = "autoForDeleteFileDetailID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoForDeleteFileDetailID;

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

	public void setAutoETL_ActivityNodeForDeleteFile(
			AutoETL_ActivityNodeForDeleteFile autoETL_ActivityNodeForDeleteFile) {
		this.autoETL_ActivityNodeForDeleteFile = autoETL_ActivityNodeForDeleteFile;
	}


	public AutoETL_ActivityNodeForDeleteFile getAutoETL_ActivityNodeForDeleteFile() {
		return autoETL_ActivityNodeForDeleteFile;
	}


	public void setAutoForDeleteFileDetailID(String autoForDeleteFileDetailID) {
		this.autoForDeleteFileDetailID = autoForDeleteFileDetailID;
	}


	public String getAutoForDeleteFileDetailID() {
		return autoForDeleteFileDetailID;
	}
}

