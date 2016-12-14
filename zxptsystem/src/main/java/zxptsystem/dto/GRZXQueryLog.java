package zxptsystem.dto;

import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import coresystem.dto.InstInfo;
import coresystem.dto.UserInfo;
import extend.helper.HelpTool;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "GRZXQueryLog")
@IEntity(description="个人征信查询日志")
public class GRZXQueryLog implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;

	@IColumn(isNullable = false,description="申请ID")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoGRZXCreditReportInfoID")
	private GRZXCreditReportInfo grZXCreditReportInfo;	

	@Transient
	@IColumn(isNullable = false,description="申请客户名称",isListShow = true)
	private String customerName;
	
	@Transient
	@IColumn(isNullable = false,description="报告信息来源",isListShow = true)
	private String strInfoSource;
	
	@Column(name = "tmExecTime")
	@IColumn(description="申请执行时间")
	private Timestamp tmExecTime;
	
	@IColumn(isNullable = false,description="操作用户")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strUserCode")
	private UserInfo userInfo;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strInstCode")
	@IColumn(description="行内机构", isNullable = false)
	private InstInfo instInfo;	
	
	@Column(name = "strQueryType", length = 50)
	@IColumn(description="查询类型")
	private String strQueryType;
	
	@Transient
	@IColumn(description="查询类型",isListShow = true)
	private String strQueryTypeName;
	
	public String getStrQueryTypeName() {
		return ShowContext.getInstance().getShowEntityMap().get("configParam").get(this.getStrQueryType());
	}
	
	@Column(name = "strCostType",length=2)
	@IColumn(description="计费类型",tagMethodName="getCategoryCodeMap")
	private String strCostType;
		
	public static Map<String,String> getCategoryCodeMap(){
		try {
			return HelpTool.getSystemConstVal("GRZXFeeCategoryDesc");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Column(name = "strQueryStatus", length = 10)
	@IColumn(description="查询状态")
	private String strQueryStatus;
	
	@Column(name = "intQueryCount")
	@IColumn(description="查询次数")
	private int intQueryCount;
	
	@Column(name = "strException", length = 100)
	@IColumn(description="异常原因")
	private String strException;
	
	@Column(name = "strRemark", length = 100)
	@IColumn(description="备注")
	private String strRemark;
	
	public String getCustomerName() {
		if(null==grZXCreditReportInfo){
			return "";
		}
		if(null==grZXCreditReportInfo.getStrCustomerID() || grZXCreditReportInfo.getStrCustomerID().equals("")){
			return "";
		}
		return grZXCreditReportInfo.getStrCustomerID().getStrCustomerName();
	}

	public String getStrInfoSource() {
		if(null==grZXCreditReportInfo){
			return "";
		}
		return GRZXCreditReportInfo.getInfoSourceMap().get(grZXCreditReportInfo.getStrInfoSource());
	}	
	
	public Timestamp getTmExecTime() {
		return tmExecTime;
	}

	public void setTmExecTime(Timestamp tmExecTime) {
		this.tmExecTime = tmExecTime;
	}

	public String getStrQueryStatus() {
		return strQueryStatus;
	}

	public void setStrQueryStatus(String strQueryStatus) {
		this.strQueryStatus = strQueryStatus;
	}

	public int getIntQueryCount() {
		return intQueryCount;
	}

	public void setIntQueryCount(int intQueryCount) {
		this.intQueryCount = intQueryCount;
	}

	public String getStrException() {
		return strException;
	}

	public void setStrException(String strException) {
		if(null!=strException && strException.length()>100){
			strException=strException.substring(0, 99);
		}
		this.strException = strException;
	}

	public String getStrRemark() {
		return strRemark;
	}

	public void setStrRemark(String strRemark) {
		if(null!=strRemark && strRemark.length()>100){
			strRemark=strRemark.substring(0, 99);
		}
		this.strRemark = strRemark;
	}
	
	public GRZXCreditReportInfo getGrZXCreditReportInfo() {
		return grZXCreditReportInfo;
	}
		
	public void setGrZXCreditReportInfo(GRZXCreditReportInfo grZXCreditReportInfo) {
		this.grZXCreditReportInfo = grZXCreditReportInfo;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public void setInstInfo(InstInfo instInfo) {
		this.instInfo = instInfo;
	}

	public InstInfo getInstInfo() {
		return instInfo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public void setStrQueryType(String strQueryType) {
		this.strQueryType = strQueryType;
	}

	public String getStrQueryType() {
		return strQueryType;
	}

	public void setStrCostType(String strCostType) {
		this.strCostType = strCostType;
	}

	public String getStrCostType() {
		return strCostType;
	}
}

