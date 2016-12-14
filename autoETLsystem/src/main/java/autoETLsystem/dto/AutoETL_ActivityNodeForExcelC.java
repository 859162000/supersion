package autoETLsystem.dto;

import java.util.Map;

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
import framework.show.ShowContext;

@Entity
@Table(name = "AutoETL_ActivityNodeForExcelC")
@IEntity(navigationName="Excle活动结点条件",description="Excle活动结点条件")
public class AutoETL_ActivityNodeForExcelC implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoRelationFieldID")
	@IColumn(description="源表字段")
	private ReportModel_Field autoRelationFieldID;

	@IColumn(tagMethodName="getConditionTypeTag",description="条件类型")
	@Column(name = "strConditionType")
	private String strConditionType;
	
	public static Map<String,String> getConditionTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("conditionType");
	}

	@Column(name="strValue",length = 4000)
	@IColumn(description="字段值")
	private String strValue;
	
	@IColumn(tagMethodName="getConditionJoinTypeTag",description="连接类型")
	@Column(name = "strConditionJoinType")
	private String strConditionJoinType;
	
	public static Map<String,String> getConditionJoinTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("conditionJoinType");
	}
	
	@Column(name="intOrder",nullable=false)
	@IColumn(description="顺序", isNullable = false)
	private Integer intOrder;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoETL_ActivityNodeForExcelID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNodeForExcel autoETL_ActivityNodeForExcel;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;

	public String getStrConditionType() {
		return strConditionType;
	}

	public void setStrConditionType(String strConditionType) {
		this.strConditionType = strConditionType;
	}

	public String getStrValue() {
		return strValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}


	public void setIntOrder(String intOrder) {
		this.intOrder =TypeParse.parseInt(intOrder);
	}

	public Integer getIntOrder() {
		return intOrder;
	}

	public void setStrConditionJoinType(String strConditionJoinType) {
		this.strConditionJoinType = strConditionJoinType;
	}

	public String getStrConditionJoinType() {
		return strConditionJoinType;
	}

	public void setAutoRelationFieldID(ReportModel_Field autoRelationFieldID) {
		this.autoRelationFieldID = autoRelationFieldID;
	}

	public ReportModel_Field getAutoRelationFieldID() {
		return autoRelationFieldID;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setAutoETL_ActivityNodeForExcel(
			AutoETL_ActivityNodeForExcel autoETL_ActivityNodeForExcel) {
		this.autoETL_ActivityNodeForExcel = autoETL_ActivityNodeForExcel;
	}

	public AutoETL_ActivityNodeForExcel getAutoETL_ActivityNodeForExcel() {
		return autoETL_ActivityNodeForExcel;
	}

}
