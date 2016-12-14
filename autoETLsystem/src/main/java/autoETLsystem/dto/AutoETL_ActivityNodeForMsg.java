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

import extend.dto.AutoETL_DataSource;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

/**
 * 项目名称：autoETLsystem<br>
 * *********************************<br>
 * <P>类名称：AutoETL_ActivityNodeForMsg</P>
 * *********************************<br>
 * <P>类描述：短信节点配置信息</P>
 * 创建人：王川<br>
 * 创建时间：2016-5-9 下午2:46:36<br>
 * 修改人：王川<br>
 * 修改时间：2016-5-9 下午2:46:36<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@Entity
@Table(name = "AutoETL_ActivityNodeForMsg")
@IEntity(navigationName="MSG工作流节点",description="MSG工作流节点")
public class AutoETL_ActivityNodeForMsg implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoETL_MSG", nullable = false)
	@IColumn(description="短信服务器", isNullable = false)
	private AutoETL_MSG autoETL_MSG;
	
	@IColumn(tagMethodName="getActivityNodeErrorTag",description="短信发送失败时结点状态", isNullable = false)
	@Column(name = "strActivityNodeErrorType", nullable = false)
	private String strActivityNodeErrorType;
	
	public static Map<String,String> getActivityNodeErrorTag(){
		return ShowContext.getInstance().getShowEntityMap().get("activityNodeErrorType");
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoETL_DataSourceID")
	@IColumn(description="SQL启动条件源会话工厂")
	private AutoETL_DataSource autoETL_DataSource;
	
	@Column(name="strSqlCondition",length = 2000)
	@IColumn(description="SQL条件", isSpecialCharCheck=true)
	private String strSqlCondition;
	
	@Column(name="strReceives",length = 500)
	@IColumn(description="收信人", isSpecialCharCheck=true)
	private String strReceives;

	@Column(name="strMsgData",length = 500)
	@IColumn(description="内容", isSpecialCharCheck=true)
	private String strMsgData;
	
	@Column(name="strSendTime",length = 30)
	@IColumn(description="发送时间", isSpecialCharCheck=true)
	private String strSendTime;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNode autoETL_ActivityNode;
	
	@Id
	@Column(name = "autoActivityNodeForMsgID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeForMsgID;

	public AutoETL_MSG getAutoETL_MSG() {
		return autoETL_MSG;
	}

	public void setAutoETL_MSG(AutoETL_MSG autoETL_MSG) {
		this.autoETL_MSG = autoETL_MSG;
	}

	public String getStrActivityNodeErrorType() {
		return strActivityNodeErrorType;
	}

	public void setStrActivityNodeErrorType(String strActivityNodeErrorType) {
		this.strActivityNodeErrorType = strActivityNodeErrorType;
	}

	public AutoETL_DataSource getAutoETL_DataSource() {
		return autoETL_DataSource;
	}

	public void setAutoETL_DataSource(AutoETL_DataSource autoETL_DataSource) {
		this.autoETL_DataSource = autoETL_DataSource;
	}

	public String getStrSqlCondition() {
		return strSqlCondition;
	}

	public void setStrSqlCondition(String strSqlCondition) {
		this.strSqlCondition = strSqlCondition;
	}

	public String getStrMsgData() {
		return strMsgData;
	}

	public void setStrMsgData(String strMsgData) {
		this.strMsgData = strMsgData;
	}

	public AutoETL_ActivityNode getAutoETL_ActivityNode() {
		return autoETL_ActivityNode;
	}

	public void setAutoETL_ActivityNode(AutoETL_ActivityNode autoETL_ActivityNode) {
		this.autoETL_ActivityNode = autoETL_ActivityNode;
	}

	public String getAutoActivityNodeForMsgID() {
		return autoActivityNodeForMsgID;
	}

	public void setAutoActivityNodeForMsgID(String autoActivityNodeForMsgID) {
		this.autoActivityNodeForMsgID = autoActivityNodeForMsgID;
	}

	public String getStrReceives() {
		return strReceives;
	}

	public void setStrReceives(String strReceives) {
		this.strReceives = strReceives;
	}

	public String getStrSendTime() {
		return strSendTime;
	}

	public void setStrSendTime(String strSendTime) {
		this.strSendTime = strSendTime;
	}

	
}

