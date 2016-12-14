package extend.dto;

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

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "ReportModel_FieldCondition")
@IEntity(navigationName="字段条件",description="字段条件")
public class ReportModel_FieldCondition implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name="targetField",length = 200)
	@IColumn(description="子属性") // 子对象的子属性，用于将默认的下拉列表显示成文本输入
	private String targetField;
	
	@Column(name="isVisible")
	@IColumn(description="是否显示", tagMethodName="getIsVisibleTag")
	private String isVisible;
	
	public static Map<String,String> getIsVisibleTag() {
		return ShowContext.getInstance().getShowEntityMap().get("isPrimaryKey");
	}
	
	@Column(name="description",length = 200)
	@IColumn(description="描述")
	private String description;
	
	@Column(name="isASC")
	@IColumn(description="是否升序", tagMethodName="getIsASCTag")
	private String isASC;
	
	public static Map<String,String> getIsASCTag() {
		return ShowContext.getInstance().getShowEntityMap().get("isPrimaryKey");
	}
	
	@Column(name="conditionOrder")
	@IColumn(description="排序优先级")
	private Integer conditionOrder;
	
	@Column(name="comparison")
	@IColumn(description="比较条件", tagMethodName="getComparisonTag")
	private String comparison;
	
	public static Map<String,String> getComparisonTag() {
		return ShowContext.getInstance().getShowEntityMap().get("compareCondition");
	}
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoFieldID", nullable = false)
	private ReportModel_Field reportModel_Field;
	
	@Id
	@Column(name = "autoConditionID", length = 32)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String autoConditionID;

	public void setTargetField(String targetField) {
		this.targetField = targetField;
	}

	public String getTargetField() {
		return targetField;
	}

	public void setIsVisible(String isVisible) {
		this.isVisible = isVisible;
	}

	public String getIsVisible() {
		return isVisible;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setIsASC(String isASC) {
		this.isASC = isASC;
	}

	public String getIsASC() {
		return isASC;
	}

	public void setConditionOrder(Integer order) {
		this.conditionOrder = order;
	}

	public Integer getConditionOrder() {
		return conditionOrder;
	}

	public void setComparison(String comparison) {
		this.comparison = comparison;
	}

	public String getComparison() {
		return comparison;
	}

	public void setAutoConditionID(String autoConditionID) {
		this.autoConditionID = autoConditionID;
	}

	public String getAutoConditionID() {
		return autoConditionID;
	}

	public void setReportModel_Field(ReportModel_Field reportModel_Field) {
		this.reportModel_Field = reportModel_Field;
	}

	public ReportModel_Field getReportModel_Field() {
		return reportModel_Field;
	}
}
