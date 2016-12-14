package autoETLsystem.dto;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

/**
 * 项目名称：autoETLsystem<br>
 * *********************************<br>
 * <P>类名称：AutoETL_MSG</P>
 * *********************************<br>
 * <P>类描述：短信服务器</P>
 * 创建人：王川<br>
 * 创建时间：2016-5-9 上午11:47:56<br>
 * 修改人：王川<br>
 * 修改时间：2016-5-9 上午11:47:56<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@Entity
@Table(name = "AutoETL_MSG")
@IEntity(navigationName="MSG",description="短信服务器")
public class AutoETL_MSG implements java.io.Serializable{

	/**  **/
	private static final long serialVersionUID = 3601142543759337289L;


	@Id
	@Column(name = "autoMSGID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoMSGID;
	
	
	@Column(name="ispName",length = 200,nullable=false)
	@IColumn(description="短息服务商",isKeyName=true, isNullable = false)
	private String ispName;
	
	@Column(name="ispServer",length = 200,nullable=false)
	@IColumn(description="短信发送URL",isSpecialCharCheck=true, isNullable = false)
	private String ispServer;
	
	@Column(name="charset",length = 30,nullable=false)
	@IColumn(tagMethodName="getCharsetTag", description="字符编码", isNullable = false)
	private String charset;
	public static Map<String,String> getCharsetTag(){
		return ShowContext.getInstance().getShowEntityMap().get("charset");
	}
	
	@Column(name="strDiscription",length = 200)
	@IColumn(description="描述")
	private String strDiscription;
	
	@Column(name="msgTemplete",length = 500)
	@IColumn(description="消息实例",isSpecialCharCheck=true)
	private String msgTemplete;
	
	@Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "autoETL_MSG")
	private Set<AutoETL_ActivityNodeForMsg> autoETL_ActivityNodeForMsgs = new HashSet<AutoETL_ActivityNodeForMsg>(0);
	
	public String getAutoMSGID() {
		return autoMSGID;
	}

	public void setAutoMSGID(String autoMSGID) {
		this.autoMSGID = autoMSGID;
	}

	public String getIspServer() {
		return ispServer;
	}

	public void setIspServer(String ispServer) {
		this.ispServer = ispServer;
	}

	
	public String getIspName() {
		return ispName;
	}

	public void setIspName(String ispName) {
		this.ispName = ispName;
	}

	public String getStrDiscription() {
		return strDiscription;
	}

	public void setStrDiscription(String strDiscription) {
		this.strDiscription = strDiscription;
	}

	public Set<AutoETL_ActivityNodeForMsg> getAutoETL_ActivityNodeForMsgs() {
		return autoETL_ActivityNodeForMsgs;
	}

	public void setAutoETL_ActivityNodeForMsgs(Set<AutoETL_ActivityNodeForMsg> autoETL_ActivityNodeForMsgs) {
		this.autoETL_ActivityNodeForMsgs = autoETL_ActivityNodeForMsgs;
	}

	public String getMsgTemplete() {
		return msgTemplete;
	}

	public void setMsgTemplete(String msgTemplete) {
		this.msgTemplete = msgTemplete;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
	
}




