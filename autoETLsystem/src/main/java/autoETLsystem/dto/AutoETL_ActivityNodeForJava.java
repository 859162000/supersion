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
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "AutoETL_ActivityNodeForJava")
@IEntity(navigationName="JAVA程序活动结点",description="JAVA程序活动结点")
public class AutoETL_ActivityNodeForJava implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="strClass",length = 200,nullable=false)
	@IColumn(description="类名", isNullable = false)
	private String strClass;
	
	@Column(name="strFixParameter",length = 350,nullable=true)
	@IColumn(description="固定参数", isNullable = true,isSpecialCharCheck=true)
	private String strFixParameter;
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNode autoETL_ActivityNode;
	

	@Id
	@Column(name = "autoActivityNodeForNETID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeForNETID;
 
	
	public AutoETL_ActivityNode getAutoETL_ActivityNode() {
		return autoETL_ActivityNode;
	}

	public void setAutoETL_ActivityNode(AutoETL_ActivityNode autoETLActivityNode) {
		autoETL_ActivityNode = autoETLActivityNode;
	}

	public String getStrClass() {
		return strClass;
	}

	public void setStrClass(String strClass) {
		this.strClass = strClass;
	}

	public String getStrDiscription() {
		return strDiscription;
	}

	public void setStrDiscription(String strDiscription) {
		this.strDiscription = strDiscription;
	}

	public void setAutoActivityNodeForNETID(String autoActivityNodeForNETID) {
		this.autoActivityNodeForNETID = autoActivityNodeForNETID;
	}

	public String getAutoActivityNodeForNETID() {
		return autoActivityNodeForNETID;
	}

	public void setStrFixParameter(String strFixParameter) {
		this.strFixParameter = strFixParameter;
	}

	public String getStrFixParameter() {
		return strFixParameter;
	}

}

