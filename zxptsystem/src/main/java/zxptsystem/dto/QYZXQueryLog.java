package zxptsystem.dto;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import coresystem.dto.InstInfo;
import coresystem.dto.UserInfo;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "QYZXQueryLog")
@IEntity(description="企业征信查询日志")
public class QYZXQueryLog implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@IColumn(isNullable = false,description="申请信息")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "qyZXCreditReportInfo")
	private QYZXCreditReportInfo qyZXCreditReportInfo;
	
	@IColumn(isNullable = false,description="操作用户")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strUserCode")
	private UserInfo userInfo;
	
	@Column(name="dtBeginDate")
	@IColumn(description="查询开始时间")
	private Timestamp dtBeginDate;
	
	@Column(name="dtEndDate")
	@IColumn(description="查询结束时间")
	private Timestamp dtEndDate;
	
	@Column(name = "strRHActNo", length = 50)
	@IColumn(description="人行账户")
	private String strRHActNo;
	
	@Column(name = "strQueryType", length = 50)
	@IColumn(description="查询类型")
	private String strQueryType;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strInstCode")
	@IColumn(description="行内机构", isNullable = false)
	private InstInfo instInfo;
	
	@Column(name = "strCostType",length=2)
	@IColumn(description="计费类型")
	private String strCostType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public QYZXCreditReportInfo getQyZXCreditReportInfo() {
		return qyZXCreditReportInfo;
	}

	public void setQyZXCreditReportInfo(QYZXCreditReportInfo qyZXCreditReportInfo) {
		this.qyZXCreditReportInfo = qyZXCreditReportInfo;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public Timestamp getDtBeginDate() {
		return dtBeginDate;
	}

	public void setDtBeginDate(Timestamp dtBeginDate) {
		this.dtBeginDate = dtBeginDate;
	}

	public Timestamp getDtEndDate() {
		return dtEndDate;
	}

	public void setDtEndDate(Timestamp dtEndDate) {
		this.dtEndDate = dtEndDate;
	}

	public String getStrRHActNo() {
		return strRHActNo;
	}

	public void setStrRHActNo(String strRHActNo) {
		this.strRHActNo = strRHActNo;
	}

	public String getStrQueryType() {
		return strQueryType;
	}

	public void setStrQueryType(String strQueryType) {
		this.strQueryType = strQueryType;
	}

	public InstInfo getInstInfo() {
		return instInfo;
	}

	public void setInstInfo(InstInfo instInfo) {
		this.instInfo = instInfo;
	}

	public String getStrCostType() {
		return strCostType;
	}

	public void setStrCostType(String strCostType) {
		this.strCostType = strCostType;
	}
}

