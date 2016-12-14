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
@Table(name = "AutoETL_ActivityNodeForMail")
@IEntity(navigationName="MAIL工作流节点",description="MAIL工作流节点")
public class AutoETL_ActivityNodeForMail implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoMAILID", nullable = false)
	@IColumn(description="邮件服务器", isNullable = false)
	private AutoETL_MAIL autoETL_MAIL;
	
	@Column(name="strMailSubject",length = 200)
	@IColumn(description="邮件标题", isSpecialCharCheck=true)
	private String strMailSubject;
	
	@IColumn(tagMethodName="getActivityNodeErrorTag",description="邮件发送失败时结点状态", isNullable = false)
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
	
	/** 收件人信息用 , 分割，可关联用户信息表 **/
	@Column(name="strReceivers",length = 2000,nullable=false)
	@IColumn(description="收件人", isSpecialCharCheck=true, isNullable = false)
	private String strReceivers;

	@Column(name="strMailContent",length = 4000)
	@IColumn(description="邮件内容", isSpecialCharCheck=true)
	private String strMailContent;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoActivityNodeID", nullable = false)
	@IColumn(isSingleTagHidden=true, isNullable = false)
	private AutoETL_ActivityNode autoETL_ActivityNode;
	
	@Id
	@Column(name = "autoActivityNodeForMailID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoActivityNodeForMailID;

	public AutoETL_MAIL getAutoETL_MAIL() {
		return autoETL_MAIL;
	}

	public void setAutoETL_MAIL(AutoETL_MAIL autoETL_MAIL) {
		this.autoETL_MAIL = autoETL_MAIL;
	}

	public String getStrReceivers() {
		return strReceivers;
	}

	public void setStrReceivers(String strReceivers) {
		this.strReceivers = strReceivers;
	}

	public String getStrMailContent() {
		return strMailContent;
	}

	public void setStrMailContent(String strMailContent) {
		this.strMailContent = strMailContent;
	}

	public String getStrMailSubject() {
		return strMailSubject;
	}

	public void setStrMailSubject(String strMailSubject) {
		this.strMailSubject = strMailSubject;
	}

	public String getStrActivityNodeErrorType() {
		return strActivityNodeErrorType;
	}

	public void setStrActivityNodeErrorType(String strActivityNodeErrorType) {
		this.strActivityNodeErrorType = strActivityNodeErrorType;
	}

	public AutoETL_ActivityNode getAutoETL_ActivityNode() {
		return autoETL_ActivityNode;
	}

	public void setAutoETL_ActivityNode(AutoETL_ActivityNode autoETL_ActivityNode) {
		this.autoETL_ActivityNode = autoETL_ActivityNode;
	}

	public String getAutoActivityNodeForMailID() {
		return autoActivityNodeForMailID;
	}

	public void setAutoActivityNodeForMailID(String autoActivityNodeForMailID) {
		this.autoActivityNodeForMailID = autoActivityNodeForMailID;
	}

	public String getStrSqlCondition() {
		return strSqlCondition;
	}

	public void setStrSqlCondition(String strSqlCondition) {
		this.strSqlCondition = strSqlCondition;
	}

	public AutoETL_DataSource getAutoETL_DataSource() {
		return autoETL_DataSource;
	}

	public void setAutoETL_DataSource(AutoETL_DataSource autoETL_DataSource) {
		this.autoETL_DataSource = autoETL_DataSource;
	}
	
}

