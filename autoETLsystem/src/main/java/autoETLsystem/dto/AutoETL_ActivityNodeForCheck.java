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

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "AutoETL_ActivityNodeForCheck")
@IEntity(navigationName="校验实例",description="校验实例")
public class AutoETL_ActivityNodeForCheck implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="strCheckName",length = 200, nullable = false)
	@IColumn(description="实例名称", isNullable = false)
	private String strCheckName;
	
	@IColumn(tagMethodName="getCheckSourceTag",description="实例来源", isNullable = false)
	@Column(name = "strInstanceSourceType", nullable = false)
	private String strInstanceSourceType;
	
	public static Map<String,String> getCheckSourceTag(){
		return ShowContext.getInstance().getShowEntityMap().get("checkInstanceSource");
	}
	
	@IColumn(tagMethodName="getActivityNodeErrorTag",description="校验错误时结点状态", isNullable = false)
	@Column(name = "strActivityNodeErrorType", nullable = false)
	private String strActivityNodeErrorType;
	
	public static Map<String,String> getActivityNodeErrorTag(){
		return ShowContext.getInstance().getShowEntityMap().get("activityNodeErrorType");
	}

	@Column(name="strEntityClass",length = 200)
	@IColumn(description="实体类")
	private String strEntityClass;
	
	@Column(name="strCheckClass",length = 200)
	@IColumn(description="校验类")
	private String strCheckClass;
	
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

	public String getStrDiscription() {
		return strDiscription;
	}

	public void setStrDiscription(String strDiscription) {
		this.strDiscription = strDiscription;
	}

	public void setAutoETL_ActivityNode(AutoETL_ActivityNode autoETL_ActivityNode) {
		this.autoETL_ActivityNode = autoETL_ActivityNode;
	}

	public AutoETL_ActivityNode getAutoETL_ActivityNode() {
		return autoETL_ActivityNode;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setStrCheckName(String strCheckName) {
		this.strCheckName = strCheckName;
	}

	public String getStrCheckName() {
		return strCheckName;
	}

	public void setStrInstanceSourceType(String strInstanceSourceType) {
		this.strInstanceSourceType = strInstanceSourceType;
	}

	public String getStrInstanceSourceType() {
		return strInstanceSourceType;
	}

	public void setStrActivityNodeErrorType(String strActivityNodeErrorType) {
		this.strActivityNodeErrorType = strActivityNodeErrorType;
	}

	public String getStrActivityNodeErrorType() {
		return strActivityNodeErrorType;
	}

	public void setStrCheckClass(String strCheckClass) {
		this.strCheckClass = strCheckClass;
	}

	public String getStrCheckClass() {
		return strCheckClass;
	}

	public void setStrEntityClass(String strEntityClass) {
		this.strEntityClass = strEntityClass;
	}

	public String getStrEntityClass() {
		return strEntityClass;
	}
}


