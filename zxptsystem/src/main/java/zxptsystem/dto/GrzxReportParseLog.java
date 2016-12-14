package zxptsystem.dto;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

/**
 * 
 * @description <p>个人征信报告解析日志</P>
 * @createTime 2016-9-18 下午05:50:20
 * @updateTime 2016-9-18 下午05:50:20
 * @author Liutao
 * @updater Liutao
 */
@Entity
@Table(name="GrzxReportParseLog")
@IEntity(description="个人征信报告解析日志")
public class GrzxReportParseLog implements Serializable {
	
	private static final long serialVersionUID = -125472494461388304L;
	
	@Id
	@Column(name = "autoId", length = 32)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String autoId;
	
	@Transient
	@IColumn(description="申请ID", isNullable=false, isListShow=true)
	private String applicationId;
	
	@Transient
	@IColumn(description="申请客户名称", isNullable=false, isListShow=true)
	private String applicationCustomerName;
	
	@Transient
	@IColumn(description="报告信息来源", isNullable=false, isListShow=true)
	private String applicationInfoSource;
	
	@IColumn(description="解析状态", tagMethodName="getParseFlags", isNullable=false)
	@Column(name="parseFlag", length=1, nullable=false)
	private String parseFlag;
	
	@IColumn(description="推送状态", tagMethodName="getSendFlags", isNullable=false)
	@Column(name="sendFlag", length=1, nullable=false)
	private String sendFlag;
	
	@IColumn(description="异常原因", isNullable=true)
	@Column(name="exceptionReason", length=200, nullable=true)
	private String exceptionReason;
	
	@IColumn(description="个人征信报告文件路径", isNullable=false)
	@Column(name="htmlFilePath", length=200, nullable=false)
	private String htmlFilePath;

	@IColumn(description="备注", isNullable=true)
	@Column(name="remark", length=200, nullable=true)
	private String remark;
	
	//@Column(name="grzxQueryLog", length=32, nullable=false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grzxQueryLogId", nullable=false)
	@IColumn(description="查询日志", isNullable=false, isKeyName=true)
	//@OneToOne(fetch=FetchType.EAGER, optional=false, cascade=CascadeType.REFRESH)
	private GRZXQueryLog grzxQueryLog;

	@IColumn(description="报告编号", isNullable=true)
	@Column(name="reportNo", length=50, nullable=true)
	private String reportNo;
	
	public static Map<String, String> getParseFlags() {
		return ShowContext.getInstance().getShowEntityMap().get("parseFlag");
	}
	
	public static Map<String, String> getSendFlags() {
		return ShowContext.getInstance().getShowEntityMap().get("sendFlag");
	}
	
	public String getAutoId() {
		return autoId;
	}

	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}

	public String getApplicationId() {
		return this.grzxQueryLog.getGrZXCreditReportInfo().getId();
	}
	
	public String getApplicationCustomerName() {
		if(null==this.grzxQueryLog.getGrZXCreditReportInfo()
				|| null==this.grzxQueryLog.getGrZXCreditReportInfo().getStrCustomerID()
				|| StringUtils.isBlank(this.grzxQueryLog.getGrZXCreditReportInfo().getStrCustomerID().getStrCustomerName())){
			return "";
		} else {
			return this.grzxQueryLog.getGrZXCreditReportInfo().getStrCustomerID().getStrCustomerName();
		}
	}

	public String getApplicationInfoSource() {
		if(null==this.grzxQueryLog.getGrZXCreditReportInfo() 
				|| StringUtils.isBlank(this.grzxQueryLog.getGrZXCreditReportInfo().getStrInfoSource())){
			return "";
		} else {
			return this.grzxQueryLog.getGrZXCreditReportInfo().getStrCustomerID().getStrCustomerName();
		}
	}

	public String getParseFlag() {
		return parseFlag;
	}

	public void setParseFlag(String parseFlag) {
		this.parseFlag = parseFlag;
	}

	public String getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}

	public String getExceptionReason() {
		return exceptionReason;
	}

	public void setExceptionReason(String exceptionReason) {
		this.exceptionReason = exceptionReason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public GRZXQueryLog getGrzxQueryLog() {
		return grzxQueryLog;
	}

	public void setGrzxQueryLog(GRZXQueryLog grzxQueryLog) {
		this.grzxQueryLog = grzxQueryLog;
	}

	public String getHtmlFilePath() {
		return htmlFilePath;
	}

	public void setHtmlFilePath(String htmlFilePath) {
		this.htmlFilePath = htmlFilePath;
	}

	public String getReportNo() {
		return reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}
	
	
}
