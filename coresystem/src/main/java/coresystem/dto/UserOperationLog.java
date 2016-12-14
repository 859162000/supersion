package coresystem.dto;

import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;


@Entity
@Table(name = "UserOperationLog")
@IEntity(description="用户操作日志")
public class UserOperationLog implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	
	@Column(name="strUserCode",length = 50,nullable=true)
	@IColumn(description="用户ID")
	private String strUserCode;
	
	@Column(name = "strUserName", length = 50,nullable=true)
	@IColumn(description="用户名称")
	private String strUserName;
	
	@Column(name = "strInstName", length = 50,nullable=true)
	@IColumn(description="用户机构")
	private String strInstName;

	

	@Column(name = "strIpAddress", length = 50,nullable=true)
	@IColumn(description="操作主机")
	private String strIpAddress;
	
	@IColumn(description="操作时间")
	@Column(name = "dtOperationTime",nullable=true)
	private Timestamp dtOperationTime;
	
	
	@Column(name = "strOperationType", length = 50,nullable=true)
	@IColumn(description="日志类型",tagMethodName="getUserOperationType")
	private String strOperationType;

	public static Map<String,String> getUserOperationType(){
		return ShowContext.getInstance().getShowEntityMap().get("UserOperationType");
	}
	

	@Column(name = "strDetailActionDesc", length = 8000,nullable=true)
	@IColumn(description="操作详细描述")
	private String strDetailActionDesc;
	
	@Column(name = "strReason", length = 4000,nullable=true)
	@IColumn(description="错误描述")
	private String strReason;
	
	public void setId(String ID)
	{
		this.id=ID;
	}
	public String getId()
	{
		return this.id;
	}
	
	public void setStrUserCode(String strUserCode) {
		this.strUserCode = strUserCode;
	}

	public String getStrUserCode() {
		return strUserCode;
	}
	
	

	public void setStrUserName(String strUserName) {
		this.strUserName = strUserName;
	}

	public String getStrUserName() {
		return strUserName;
	}
	
	public void setStrInstName(String strInstName) {
		this.strInstName = strInstName;
	}

	public String getStrInstName() {
		return strInstName;
	}


	public void setStrIpAddress(String strIpAddress) {
		this.strIpAddress = strIpAddress;
	}

	public String getStrIpAddress() {
		return this.strIpAddress;
	}
	

	public void setDtOperationTime(String dtOperationTime) {
		this.dtOperationTime = TypeParse.parseTimestamp(dtOperationTime);
	}

	public Timestamp getDtOperationTime() {
		return this.dtOperationTime;
	}
	
	
	public void setStrOperationType(String strOperationType) {
		this.strOperationType = strOperationType;
	}

	public String getStrOperationType() {
		return this.strOperationType;
	}
	
	public void setStrDetailActionDesc(String strDetailActionDesc) {
		this.strDetailActionDesc = strDetailActionDesc;
	}

	public String getStrDetailActionDesc() {
		return this.strDetailActionDesc;
	}
	
	public void setStrReason(String strReason) {
		this.strReason = strReason;
	}

	public String getStrReason() {
		return this.strReason;
	}

}
