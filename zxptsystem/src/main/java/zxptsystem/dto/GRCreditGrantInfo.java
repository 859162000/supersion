package zxptsystem.dto;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "GRCreditGrantInfo")
@IEntity(description="个人征信授权信息")
public class GRCreditGrantInfo implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "autoID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoID;

	@Column(name = "strCreditGrantName", length = 100)
	@IColumn(description="授权信息名称",isNullable = false)
	private String strCreditGrantName;
	
	@Column(name="strDesc", length = 100) 
	@IColumn(description="授权信息描述")
	private String strDesc;

	@Column(name="strLockState", length = 1) 
	@IColumn(tagMethodName="getLockStateTag",description="锁定状态",isNullable = false)
	private String strLockState;

	public static Map<String,String> getLockStateTag(){
		return ShowContext.getInstance().getShowEntityMap().get("GRGrantDataLockState");
	}
	
	public String getAutoID() {
		return autoID;
	}

	public void setAutoID(String autoID) {
		this.autoID = autoID;
	}

	public String getStrCreditGrantName() {
		return strCreditGrantName;
	}

	public void setStrCreditGrantName(String strCreditGrantName) {
		this.strCreditGrantName = strCreditGrantName;
	}

	public String getStrDesc() {
		return strDesc;
	}

	public void setStrDesc(String strDesc) {
		this.strDesc = strDesc;
	}

	public String getStrLockState() {
		return strLockState;
	}

	public void setStrLockState(String strLockState) {
		this.strLockState = strLockState;
	}	
}

