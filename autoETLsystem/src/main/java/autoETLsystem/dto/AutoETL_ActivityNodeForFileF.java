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

import extend.dto.ReportModel_Field;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "AutoETL_ActivityNodeForFileF")
@IEntity(navigationName="文件结点设置字段",description="文件结点设置字段")
public class AutoETL_ActivityNodeForFileF implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoRelationFieldID", nullable = false)
	@IColumn(description="表字段",isKeyName=true, isNullable = false)
	private ReportModel_Field autoETL_SourceField;
	
	@Column(name="intOrder",nullable=false)
	@IColumn(description="顺序", isNullable = false)
	private Integer intOrder;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoETL_ActivityNodeForFileID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNodeForFile autoETL_ActivityNodeForFile;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	public void setAutoETL_SourceField(ReportModel_Field autoETL_SourceField) {
		this.autoETL_SourceField = autoETL_SourceField;
	}

	public ReportModel_Field getAutoETL_SourceField() {
		return autoETL_SourceField;
	}

	public void setAutoETL_ActivityNodeForFile(
			AutoETL_ActivityNodeForFile autoETL_ActivityNodeForFile) {
		this.autoETL_ActivityNodeForFile = autoETL_ActivityNodeForFile;
	}

	public AutoETL_ActivityNodeForFile getAutoETL_ActivityNodeForFile() {
		return autoETL_ActivityNodeForFile;
	}
	
	public void setIntOrder(String intOrder) {
		this.intOrder =TypeParse.parseInt(intOrder);
	}

	public Integer getIntOrder() {
		return intOrder;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
}
