package autoETLsystem.dto;

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

import coresystem.dto.InstInfo;
import extend.dto.ReportModel_Table;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "AutoETL_ActivityNodeForTTState")
@IEntity(navigationName="统计表状态",description="统计表状态")
public class AutoETL_ActivityNodeForTotalTableState implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strModelCode", nullable = false)
	private ReportModel_Table reportModel_Table;
	
	@Transient
	@IColumn(isIdListField = true, target="reportModel_Table")
	private String[] reportModel_TableIdList;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strInstCode", nullable = false)
	private InstInfo instInfo;
	
	@Transient
	@IColumn(isIdListField = true, target="instInfo")
	private String[] instInfoIdList;
	
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNode autoETL_ActivityNode;
	

	@Id
	@Column(name = "autoActivityNodeForTTStateID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeForTTStateID;


	public ReportModel_Table getReportModel_Table() {
		return reportModel_Table;
	}


	public void setReportModel_Table(ReportModel_Table reportModelTable) {
		reportModel_Table = reportModelTable;
	}


	public String[] getReportModel_TableIdList() {
		return reportModel_TableIdList;
	}


	public void setReportModel_TableIdList(String[] reportModelTableIdList) {
		reportModel_TableIdList = reportModelTableIdList;
	}


	public InstInfo getInstInfo() {
		return instInfo;
	}


	public void setInstInfo(InstInfo instInfo) {
		this.instInfo = instInfo;
	}


	public String[] getInstInfoIdList() {
		return instInfoIdList;
	}


	public void setInstInfoIdList(String[] instInfoIdList) {
		this.instInfoIdList = instInfoIdList;
	}


	public String getStrDiscription() {
		return strDiscription;
	}


	public void setStrDiscription(String strDiscription) {
		this.strDiscription = strDiscription;
	}


	public AutoETL_ActivityNode getAutoETL_ActivityNode() {
		return autoETL_ActivityNode;
	}


	public void setAutoETL_ActivityNode(AutoETL_ActivityNode autoETLActivityNode) {
		autoETL_ActivityNode = autoETLActivityNode;
	}


	public String getAutoActivityNodeForTTStateID() {
		return autoActivityNodeForTTStateID;
	}


	public void setAutoActivityNodeForTTStateID(String autoActivityNodeForTTStateID) {
		this.autoActivityNodeForTTStateID = autoActivityNodeForTTStateID;
	}


}

