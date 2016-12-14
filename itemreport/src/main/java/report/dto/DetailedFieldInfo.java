package report.dto;

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
import framework.show.ShowContext;

@Entity
@Table(name = "DetailedFieldInfo")
public class DetailedFieldInfo implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = 3250546171014243037L;

	@Id
	@Column(name = "autoDetailedFieldID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoDetailedFieldID;

	public void setAutoDetailedFieldID(String autoDetailedFieldID) {
		this.autoDetailedFieldID = autoDetailedFieldID;
	}

	public String getAutoDetailedFieldID() {
		return autoDetailedFieldID;
	}
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoRelationFieldID")
	@IColumn(description="字段")
	private ReportModel_Field autoRelationFieldID;

	@IColumn(tagMethodName="getConditionTypeTag",description="条件类型")
	@Column(name = "strConditionType")
	private String strConditionType;
	
	public static Map<String,String> getConditionTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("conditionType");
	}

	@Column(name="strValue",length = 50)
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
	@JoinColumn(name = "autoDetailedID")
	@IColumn(isSingleTagHidden=true)
	private DetailedInfo detailedInfo;

	public ReportModel_Field getAutoRelationFieldID() {
		return autoRelationFieldID;
	}

	public void setAutoRelationFieldID(ReportModel_Field autoRelationFieldID) {
		this.autoRelationFieldID = autoRelationFieldID;
	}

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

	public String getStrConditionJoinType() {
		return strConditionJoinType;
	}

	public void setStrConditionJoinType(String strConditionJoinType) {
		this.strConditionJoinType = strConditionJoinType;
	}

	public Integer getIntOrder() {
		return intOrder;
	}

	public void setIntOrder(String intOrder) {
		this.intOrder =TypeParse.parseInt(intOrder);
	}

	public DetailedInfo getDetailedInfo() {
		return detailedInfo;
	}

	public void setDetailedInfo(DetailedInfo detailedInfo) {
		this.detailedInfo = detailedInfo;
	}

	
}

