package zxptsystem.helper.GR;

public class EnquiryParameter implements java.io.Serializable{

	private static final long serialVersionUID = -3880079316756849198L;

	/*
	 * length 50
	 * desc 查询申请顺序号，唯一性，不能为空
	 * null 不能为空
	 */
	private String enquiryNO;

	/*
	 * length 2
	 * desc 查询原因
	 * null 不能为空
	 *  01-贷款审批
		02-信用卡审批
		03-担保资格审查
		04-特约商户实名审查
		05-担保资格审查
		08-本人查询
		09-其他
	 */
	private String strEnquiryReason;
	
	/*
	 * length 1
	 * desc 查询类型
	 * null 不能为空
	 *  0-信用报告查询   
		1-身份信息核查
	 */
	private String strEnquiryType;
	
	/*信用报告版式
	 * length 2
	 * desc 查询类型
	 * null 不能为空
	 *  30-银行版    
		31-银行异议版
	 */
	private String reportFormat;
	/*
	 * length 8
	 * desc 查询截止日期
	 * null 不能为空
	 * 查询个人征信某个截止日期前的日期，建议以提交日期为准。
	 */
	private String strEndDate;
	
	/*
	 * 查询网络类型
	 *  01-个人征信互联网查询
		02-个人征信内联网查询
		09-其他
	 */
	private String enquiryNetworkType;
	
	/*
	 * length 30
	 * desc 被查询人姓名
	 * null 不能为空
	 */
	private String strIND_Name;
	
	/*
	 * length 1
	 * desc 被查询人证件类型
	 * null 不能为空
	 * 0-身份证
		1-户口簿
		2-护照
		3-军官证
		4-士兵证
		5-港澳居民来往内地通行证
		6-台湾同胞来往内地通行证
		7-临时身份证
		8-外国人居留证
		9-警官证
		A-香港身份证
		B-澳门身份证
		C-台湾身份证
		X-其他证件
	 */
	private String strID_Type;
	
	/*
	 * length 18
	 * desc 被查询人证件号码
	 * null 不能为空
	 */
	private String strID_NO;
	
	/*
	 * length 30
	 * desc 查询操作人员
	 * null 不能为空
	 * 查询操作人员的姓名，做日志用，详细到员工。
	 */
	private String strEnquiryUser;
	
	/*
	 * length 60
	 * desc 被查询人地址
	 * null 能为空
	 */
	private String strIND_address;
	
	/*
	 * length 60
	 * desc 被查询人联系方式，备案使用，可为空
	 * null 能为空
	 */
	private String strIND_phone;

	public String getStrEnquiryReason() {
		return strEnquiryReason;
	}

	public void setStrEnquiryReason(String strEnquiryReason) {
		this.strEnquiryReason = strEnquiryReason;
	}

	public String getStrEndDate() {
		return strEndDate;
	}

	public void setStrEndDate(String strEndDate) {
		this.strEndDate = strEndDate;
	}

	public String getStrIND_Name() {
		return strIND_Name;
	}

	public void setStrIND_Name(String strINDName) {
		strIND_Name = strINDName;
	}

	public String getStrID_Type() {
		return strID_Type;
	}

	public void setStrID_Type(String strIDType) {
		strID_Type = strIDType;
	}

	public String getStrID_NO() {
		return strID_NO;
	}

	public void setStrID_NO(String strIDNO) {
		strID_NO = strIDNO;
	}

	public String getStrEnquiryUser() {
		return strEnquiryUser;
	}

	public void setStrEnquiryUser(String strEnquiryUser) {
		this.strEnquiryUser = strEnquiryUser;
	}

	public String getStrIND_address() {
		return strIND_address;
	}

	public void setStrIND_address(String strINDAddress) {
		strIND_address = strINDAddress;
	}

	public String getStrIND_phone() {
		return strIND_phone;
	}

	public void setStrIND_phone(String strINDPhone) {
		strIND_phone = strINDPhone;
	}

	public void setEnquiryNO(String enquiryNO) {
		this.enquiryNO = enquiryNO;
	}

	public String getEnquiryNO() {
		return enquiryNO;
	}

	public void setStrEnquiryType(String strEnquiryType) {
		this.strEnquiryType = strEnquiryType;
	}

	public String getStrEnquiryType() {
		return strEnquiryType;
	}

	public void setReportFormat(String reportFormat) {
		this.reportFormat = reportFormat;
	}

	public String getReportFormat() {
		return reportFormat;
	}

	public void setEnquiryNetworkType(String enquiryNetworkType) {
		this.enquiryNetworkType = enquiryNetworkType;
	}

	public String getEnquiryNetworkType() {
		return enquiryNetworkType;
	}

}
