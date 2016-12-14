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
@Table(name = "AutoETL_ActivityNodeForKettle")
@IEntity(navigationName="Kettle活动结点",description="Kettle活动结点")
public class AutoETL_ActivityNodeForKettle implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name="strKettlePath",length = 250,nullable=false)
	@IColumn(description="Kettle路径", isNullable = false,isSpecialCharCheck=true)
	private String strKettlePath;
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNode autoETL_ActivityNode;
	

	@Id
	@Column(name = "autoActivityNodeForKettleID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeForKettleID;


	public String getStrKettlePath() {
		return strKettlePath;
	}


	public void setStrKettlePath(String strKettlePath) {
		this.strKettlePath = strKettlePath;
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


	public String getAutoActivityNodeForKettleID() {
		return autoActivityNodeForKettleID;
	}


	public void setAutoActivityNodeForKettleID(String autoActivityNodeForKettleID) {
		this.autoActivityNodeForKettleID = autoActivityNodeForKettleID;
	}
 
	
	
}

