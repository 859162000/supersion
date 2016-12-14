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
import org.hibernate.annotations.GenericGenerator;
import extend.helper.HelpTool;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "QYZXCreditReportInfo")
@IEntity(description="企业征信查询信息")
public class QYZXCreditReportInfo implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", length = 50)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	///人行登录界面的机构代码
	@Column(name = "strOrgCode", length = 50)
	@IColumn(description="机构代码",isSingleTagHidden=true,isNullable = false)
	private String strOrgCode;
		
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strCustomerID")
	@IColumn(description="授权客户",isKeyName=true,isNullable = false)
	private EIS_ENTCustomernBasicInfo strCustomerID;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strDocId")
	@IColumn(description="授权资料ID",isSingleTagHidden=true)
	private EIS_AuthorizationDocumentInfo strDocId;
	
	@Column(name = "strDocName", length = 50, nullable = true)
	@IColumn(description="授权资料名称")
	private String strDocName;
	
	@IColumn(tagMethodName="getQueryCauseTag",description="查询原因")
	@Column(name = "strQueryCause", nullable =true)
	private String strQueryCause;
	
	public static Map<String,String> getQueryCauseTag() {
		try {
			return HelpTool.getSystemConstVal("QYZXCreditQueryCause");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Column(name = "strQueryVersion", length = 50, nullable = false)
	@IColumn(tagMethodName="getQueryVersion",description="查询版本",isNullable = false)
	private String strQueryVersion;
	
	public static Map<String,String> getQueryVersion(){
		try {
			return HelpTool.getSystemConstVal("QYZXQueryVersion");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Column(name = "categoryCode",length = 2, nullable = false)
	@IColumn(tagMethodName="getCategoryCodeMap",description="费用类别",isNullable = false)
	private String categoryCode;
	
	public static Map<String,String> getCategoryCodeMap(){
		try {
			return HelpTool.getSystemConstVal("QYZXFeeCategoryDesc");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Column(name = "timeCreateDate")
	@IColumn(description="申请报告提交时间",isSingleTagHidden=true)
	private Timestamp timeCreateDate;
	
	@Column(name = "timeVerifyDateTime")
	@IColumn(description="审核通过时间",isSingleTagHidden=true)
	private Timestamp timeVerifyDateTime;
		
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
	/*
	 * 位数值的表示
	 * 		1，未查询。
	 * 		2，查询中。
	 * 		3，查询成功。
	 * 		4，查询失败
	 * 位数代表种类
	 * 		1，离线明细。
	 * 		2，离线一般。
	 */
	@Column(name = "strQueryStatus", length = 2)
	@IColumn(description="查询状态",isSingleTagHidden=true)
	private String strQueryStatus;
		
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

	public void setStrQueryVersion(String strQueryVersion) {
		this.strQueryVersion = strQueryVersion;
	}

	public String getStrQueryVersion() {
		return strQueryVersion;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setTimeCreateDate(Timestamp timeCreateDate) {
		this.timeCreateDate = timeCreateDate;
	}

	public Timestamp getTimeCreateDate() {
		return timeCreateDate;
	}

	public void setTimeVerifyDateTime(Timestamp timeVerifyDateTime) {
		this.timeVerifyDateTime = timeVerifyDateTime;
	}

	public Timestamp getTimeVerifyDateTime() {
		return timeVerifyDateTime;
	}
	
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setStrCustomerID(EIS_ENTCustomernBasicInfo strCustomerID) {
		this.strCustomerID = strCustomerID;
	}

	public EIS_ENTCustomernBasicInfo getStrCustomerID() {
		return strCustomerID;
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

	public void setStrOrgCode(String strOrgCode) {
		this.strOrgCode = strOrgCode;
	}

	public String getStrOrgCode() {
		return strOrgCode;
	}
	
}

