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
import extend.dto.AutoETL_ViewField;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "AutoETL_ActivityNodeRelationV")
@IEntity(navigationName="设置条件",description="设置条件")
public class AutoETL_ActivityNodeRelationV implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoSourceViewFieldID")
	@IColumn(description="源视图字段")
	private AutoETL_ViewField autoSourceViewFieldID;

	@IColumn(tagMethodName="getConditionTypeTag",description="条件类型")
	@Column(name = "strConditionType")
	private String strConditionType;
	
	public static Map<String,String> getConditionTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("conditionType");
	}

	@Column(name="strValue",length = 4000)
	@IColumn(description="字段值",isSpecialCharCheck=true)
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
	@JoinColumn(name = "autoActivityNodeForCVID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNodeForCV autoETL_ActivityNodeForCV;
	
	@Id
	@Column(name = "autoActivityNodeRelationVID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeRelationVID;

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

	public void setAutoETL_ActivityNodeForCV(AutoETL_ActivityNodeForCV autoETL_ActivityNodeForCV) {
		this.autoETL_ActivityNodeForCV = autoETL_ActivityNodeForCV;
	}

	public AutoETL_ActivityNodeForCV getAutoETL_ActivityNodeForCV() {
		return autoETL_ActivityNodeForCV;
	}

	public void setAutoActivityNodeRelationVID(
			String autoActivityNodeRelationVID) {
		this.autoActivityNodeRelationVID = autoActivityNodeRelationVID;
	}

	public String getAutoActivityNodeRelationVID() {
		return autoActivityNodeRelationVID;
	}

	public void setAutoSourceViewFieldID(AutoETL_ViewField autoSourceViewFieldID) {
		this.autoSourceViewFieldID = autoSourceViewFieldID;
	}

	public AutoETL_ViewField getAutoSourceViewFieldID() {
		return autoSourceViewFieldID;
	}

}


