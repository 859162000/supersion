package zxptsystem.dto;

import java.sql.Timestamp;
import java.util.HashMap;
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
import extend.helper.HelpTool;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "GRZXCreditReportInfo")
@IEntity(description="个人征信查询信息")
public class GRZXCreditReportInfo implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", length = 50)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "framework.interfaces.AssignedGUIDGenerator")  
	private String id;
		
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strCustomerID")
	@IColumn(description="授权客户",isNullable = false)
	private EIS_PERCustomernBasicInfo strCustomerID;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strDocId")
	@IColumn(description="授权资料ID",isSingleTagHidden=true)
	private EIS_AuthorizationDocumentInfo strDocId;
	
	@Column(name = "profileName", length = 50, nullable = true)
	@IColumn(description="授权资料")
	private String strDocName;
	
	@IColumn(tagMethodName="getQueryCauseTag",description="查询原因")
	@Column(name = "strQueryCause", nullable =true,length=2)
	private String strQueryCause;
	
	public static Map<String,String> getQueryCauseTag() {
		try {
			return HelpTool.getSystemConstVal("GRZXQueryCause");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@IColumn(tagMethodName="getCreditReportTypeTag",description="信用报告板式")
	@Column(name = "strCreditReportType", nullable =true,length=2)
	private String strCreditReportType;	
	
	public static Map<String,String> getCreditReportTypeTag() {
		try {
			return HelpTool.getSystemConstVal("GRZXCreditReportType");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@IColumn(tagMethodName="getCreditTypeTag",description="查询类型")
	@Column(name = "strCreditType", nullable =true,length = 1)
	private String strCreditType;
	
	public static Map<String,String> getCreditTypeTag() {
		try {
			return HelpTool.getSystemConstVal("GRZXCreditType");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Column(name = "strInfoSource", length = 1, nullable = true)
	@IColumn(tagMethodName="getInfoSourceMap",description="信息来源",isSingleTagHidden=true)
	private String strInfoSource;
	
	public static Map<String,String> getInfoSourceMap(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("0", "人行");
		map.put("1", "本地");
		return map;
	}
	
	@Column(name = "categoryCode",length = 2, nullable = false)
	@IColumn(tagMethodName="getCategoryCodeMap",description="费用类别",isNullable = false)
	private String categoryCode;
	
	public static Map<String,String> getCategoryCodeMap(){
		try {
			return HelpTool.getSystemConstVal("GRZXFeeCategoryDesc");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Column(name = "timeCreateDate")
	@IColumn(description="提交时间",isSingleTagHidden=true)
	private Timestamp timeCreateDate;
	
	@Column(name = "timeVerifyDateTime")
	@IColumn(description="审核通过时间",isSingleTagHidden=true)
	private Timestamp timeVerifyDateTime;
	
	@Column(name = "dtEndDate")
	@IColumn(description="查询结束日期",isSingleTagHidden=true)
	private Timestamp dtEndDate;
	
//	@IColumn(description="查询截止日期")
//	@Column(name = "endDate")
//	private Timestamp endDate;
			
	@Column(name = "strSubmitStatus", length = 50)
	@IColumn(tagMethodName="getstrSubmitStatus",description="提交状态",isSingleTagHidden=true)
	private String strSubmitStatus;
	
	public static Map<String,String> getstrSubmitStatus(){
		return ShowContext.getInstance().getShowEntityMap().get("strSubmitStatus");
	}
	
	@Column(name = "strVerifyType", length = 50)
	@IColumn(tagMethodName="getstrVerifyType",description="审核状态",isSingleTagHidden=true)
	private String strVerifyType;
	
	public static Map<String,String> getstrVerifyType(){
		return ShowContext.getInstance().getShowEntityMap().get("strVerifyType");
	}
	
	@Column(name = "strQueryStatus", length = 5)
	@IColumn(description="查询状态",isSingleTagHidden=true,isListShow = false)
	private String strQueryStatus;
	
	@Transient
	@IColumn(description="查询状态",isListShow = true)
	private String strQueryStatusName;
	
	public String getStrQueryStatusName() {
		String c = this.getStrQueryStatus().substring(0,1);
		try {
			strQueryStatusName=HelpTool.getSystemConstVal("CreditReportQueryStatus").get("0"+c);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strQueryStatusName;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "operationUser")
	@IColumn(description="操作用户",isNullable = true,isSingleTagHidden=true)
	private coresystem.dto.UserInfo operationUser;

	public coresystem.dto.UserInfo getOperationUser() {
		return operationUser;
	}

	public void setOperationUser(coresystem.dto.UserInfo in) {
		operationUser = in;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "instInfo", nullable = true)
	@IColumn(description="操作机构",isNullable = true,isSingleTagHidden=true)
	private coresystem.dto.InstInfo instInfo;

	public coresystem.dto.InstInfo getInstInfo() {
		return instInfo;
	}

	public void setInstInfo(coresystem.dto.InstInfo in) {
		instInfo = in;
	}
	
	public String getStrVerifyType() {
		return strVerifyType;
	}

	public void setStrVerifyType(String strVerifyType) {
		this.strVerifyType = strVerifyType;
	}

	public void setStrSubmitStatus(String strSubmitStatus) {
		this.strSubmitStatus = strSubmitStatus;
	}

	public String getStrSubmitStatus() {
		return strSubmitStatus;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setStrCustomerID(EIS_PERCustomernBasicInfo strCustomerID) {
		this.strCustomerID = strCustomerID;
	}

	public EIS_PERCustomernBasicInfo getStrCustomerID() {
		return strCustomerID;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setTimeCreateDate(Timestamp timeCreateDate) {
		this.timeCreateDate = timeCreateDate;
	}

	public Timestamp getTimeCreateDate() {
		return timeCreateDate;
	}

	public void setStrDocId(EIS_AuthorizationDocumentInfo strDocId) {
		this.strDocId = strDocId;
	}

	public EIS_AuthorizationDocumentInfo getStrDocId() {
		return strDocId;
	}

	public void setStrDocName(String strDocName) {
		this.strDocName = strDocName;
	}

	public String getStrDocName() {
		return strDocName;
	}

	public void setStrQueryStatus(String strQueryStatus) {
		this.strQueryStatus = strQueryStatus;
	}

	public String getStrQueryStatus() {
		return strQueryStatus;
	}

	public void setStrQueryCause(String strQueryCause) {
		this.strQueryCause = strQueryCause;
	}

	public String getStrQueryCause() {
		return strQueryCause;
	}

	public void setStrCreditReportType(String strCreditReportType) {
		this.strCreditReportType = strCreditReportType;
	}

	public String getStrCreditReportType() {
		return strCreditReportType;
	}

	public void setStrCreditType(String strCreditType) {
		this.strCreditType = strCreditType;
	}

	public String getStrCreditType() {
		return strCreditType;
	}

//	public void setEndDate(Timestamp endDate) {
//		this.endDate = endDate;
//	}
//
//	public Timestamp getEndDate() {
//		return endDate;
//	}

	public void setDtEndDate(Timestamp dtEndDate) {
		this.dtEndDate = dtEndDate;
	}

	public Timestamp getDtEndDate() {
		return dtEndDate;
	}

	public void setStrInfoSource(String strInfoSource) {
		this.strInfoSource = strInfoSource;
	}

	public String getStrInfoSource() {
		return strInfoSource;
	}

	public void setTimeVerifyDateTime(Timestamp timeVerifyDateTime) {
		this.timeVerifyDateTime = timeVerifyDateTime;
	}

	public Timestamp getTimeVerifyDateTime() {
		return timeVerifyDateTime;
	}
}

