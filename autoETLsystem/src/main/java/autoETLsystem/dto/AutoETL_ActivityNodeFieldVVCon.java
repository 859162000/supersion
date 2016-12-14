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
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "AutoETL_ActivityNodeFieldVVCon")
@IEntity(navigationName="设置视图字段条件",description="设置视图字段条件")
public class AutoETL_ActivityNodeFieldVVCon implements java.io.Serializable{

private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoRelationFieldViewID")
	@IColumn(description="源视图字段")
	private AutoETL_ViewField autoRelationFieldViewID;

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
	@JoinColumn(name = "activityNodeFieldVVCaseID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNodeFieldVVCa autoETL_ActivityNodeFieldVVCa;
	
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

	public Integer getIntOrder() {
		return intOrder;
	}

	public void setIntOrder(Integer intOrder) {
		this.intOrder = intOrder;
	}

	public String getStrConditionJoinType() {
		return strConditionJoinType;
	}

	public void setStrConditionJoinType(String strConditionJoinType) {
		this.strConditionJoinType = strConditionJoinType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAutoRelationFieldViewID(AutoETL_ViewField autoRelationFieldViewID) {
		this.autoRelationFieldViewID = autoRelationFieldViewID;
	}

	public AutoETL_ViewField getAutoRelationFieldViewID() {
		return autoRelationFieldViewID;
	}

	public void setAutoETL_ActivityNodeFieldVVCa(
			AutoETL_ActivityNodeFieldVVCa autoETL_ActivityNodeFieldVVCa) {
		this.autoETL_ActivityNodeFieldVVCa = autoETL_ActivityNodeFieldVVCa;
	}

	public AutoETL_ActivityNodeFieldVVCa getAutoETL_ActivityNodeFieldVVCa() {
		return autoETL_ActivityNodeFieldVVCa;
	}

}
