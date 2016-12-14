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

import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "AutoETL_ActivityNodeForCal")
@IEntity(navigationName="计算实例",description="计算实例")
public class AutoETL_ActivityNodeForCalculate implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="strCalculateName",length = 200, nullable = false)
	@IColumn(description="实例名称", isNullable = false,isSpecialCharCheck=true)
	private String strCalculateName;
	
	@IColumn(tagMethodName="getActivityNodeErrorTag",description="计算错误时结点状态", isNullable = false)
	@Column(name = "strActivityNodeErrorType", nullable = false)
	private String strActivityNodeErrorType;
	
	public static Map<String,String> getActivityNodeErrorTag(){
		return ShowContext.getInstance().getShowEntityMap().get("activityNodeErrorType");
	}
	
	@Column(name = "strFreq",nullable = true)
	@IColumn(tagMethodName="getCheckResultTag", description="报表频度",isNullable=true)
	private String strFreq;
	
	public static Map<String,String> getCheckResultTag(){
		return ShowContext.getInstance().getShowEntityMap().get("reportFreq");
	}
	public void setStrFreq(String strFreq) {
		this.strFreq = strFreq;
	}

	public String getStrFreq() {
		return strFreq;
	}

	@Column(name = "intPrecise")
	@IColumn(description="精度位数")
	private Integer intPrecise;
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNode autoETL_ActivityNode;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;

	public String getStrCalculateName() {
		return strCalculateName;
	}

	public void setStrCalculateName(String strCalculateName) {
		this.strCalculateName = strCalculateName;
	}

	public String getStrActivityNodeErrorType() {
		return strActivityNodeErrorType;
	}

	public void setStrActivityNodeErrorType(String strActivityNodeErrorType) {
		this.strActivityNodeErrorType = strActivityNodeErrorType;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Integer getIntPrecise() {
		return intPrecise;
	}

	public void setIntPrecise(String intPrecise) {
		this.intPrecise = TypeParse.parseInt(intPrecise);
	}

}


