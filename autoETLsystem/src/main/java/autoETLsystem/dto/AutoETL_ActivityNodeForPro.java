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

import extend.dto.AutoETL_Procedure;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "AutoETL_ActivityNodeForPro")
@IEntity(navigationName="存储过程执行",description="存储过程执行")
public class AutoETL_ActivityNodeForPro implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNode autoETL_ActivityNode;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoProcedureID", nullable = false)
	private AutoETL_Procedure autoETL_Procedure;
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;
	

	@Id
	@Column(name = "autoActivityNodeForProID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeForProID;


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

	public void setAutoActivityNodeForProID(String autoActivityNodeForProID) {
		this.autoActivityNodeForProID = autoActivityNodeForProID;
	}

	public String getAutoActivityNodeForProID() {
		return autoActivityNodeForProID;
	}

	public void setAutoETL_Procedure(AutoETL_Procedure autoETL_Procedure) {
		this.autoETL_Procedure = autoETL_Procedure;
	}

	public AutoETL_Procedure getAutoETL_Procedure() {
		return autoETL_Procedure;
	}
	


}

