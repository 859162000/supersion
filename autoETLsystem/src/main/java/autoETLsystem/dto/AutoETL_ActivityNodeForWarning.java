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

/**
 * 项目名称：autoETLsystem<br>
 * *********************************<br>
 * <P>类名称：AutoETL_ActivityNodeForMail</P>
 * *********************************<br>
 * <P>类描述：邮件节点信息</P>
 * 创建人：王川<br>
 * 创建时间：2016-5-9 下午2:46:23<br>
 * 修改人：王川<br>
 * 修改时间：2016-5-9 下午2:46:23<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@Entity
@Table(name = "AutoETL_ActivityNodeForWarning")
@IEntity(navigationName="预警工作流节点",description="预警工作流节点")
public class AutoETL_ActivityNodeForWarning implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = 7309572463658023545L;

	@Id
	@Column(name = "autoActivityNodeForWarningID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeForWarningID;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNode autoETL_ActivityNode;
	
	/** 名称 **/
	@Column(name="strName",length=100)
	@IColumn(description="名称")
	private String strName;
	
	/** 预警模型 **/
	@IColumn(description="预警模型")
	@Column(name="strWaringModel",length=500)
	private String strWaringModel;
	
	/** 描述 **/
	@IColumn(description="描述")
	@Column(name="strDesc",length=200)
	private String strDesc;

	public String getAutoActivityNodeForWarningID() {
		return autoActivityNodeForWarningID;
	}

	public void setAutoActivityNodeForWarningID(String autoActivityNodeForWarningID) {
		this.autoActivityNodeForWarningID = autoActivityNodeForWarningID;
	}

	public AutoETL_ActivityNode getAutoETL_ActivityNode() {
		return autoETL_ActivityNode;
	}

	public void setAutoETL_ActivityNode(AutoETL_ActivityNode autoETL_ActivityNode) {
		this.autoETL_ActivityNode = autoETL_ActivityNode;
	}

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public String getStrWaringModel() {
		return strWaringModel;
	}

	public void setStrWaringModel(String strWaringModel) {
		this.strWaringModel = strWaringModel;
	}

	public String getStrDesc() {
		return strDesc;
	}

	public void setStrDesc(String strDesc) {
		this.strDesc = strDesc;
	}
	
	
	
}

