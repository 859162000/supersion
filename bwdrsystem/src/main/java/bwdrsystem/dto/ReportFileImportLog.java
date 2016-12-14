package bwdrsystem.dto;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import coresystem.dto.UserInfo;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.show.ShowContext;

/**
 * 
 * @description <p>报文导入日志DTO</P>
 * @createDate 2016/08/08 16:24 PM
 * @createDate 2016/08/08 16:24 PM
 * @author Liutao
 */
@Entity
@Table(name="ReportFileImportLog")
public class ReportFileImportLog implements java.io.Serializable{
	
	private static final long serialVersionUID = 7697927021246834759L;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;
	
	@Column(name = "dtDate", nullable = true)
	@IColumn(description="报文数据日期", isNullable=true)
	@Temporal(TemporalType.DATE)
	private Date dtDate;

	@Column(name = "importDate", nullable = false)
	@IColumn(description="报文导入日期", isNullable=false)
	@Temporal(TemporalType.DATE)
	private Date importDate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="strUserCode", nullable=true)
	@IColumn(description="导入人员", isNullable=true)
	private UserInfo operator;
	
	@Column(name = "reportFileName", length = 200, nullable = false)
	@IColumn(description="报文文件名称", isNullable=false)
	private String reportFileName;
	
	@Column(name = "reportName", length = 50, nullable = false)
	@IColumn(description="报文名称", isNullable=false)
	private String reportName;
	
	@Column(name = "reportType", length = 200, nullable = true)
	@IColumn(description="报文类型", isNullable=true)
	private String reportType;
	
	@Column(name="strReportInstCode", length=50, nullable=false)
	@IColumn(description="报送机构代码", isNullable=false)
	private String strReportInstCode;
	
	@Transient
	@IColumn(description="报送机构名称",isListShow=true, isNullable=true)
	private String strReportInstName;

	@Column(name = "importDataCount", nullable = false)
	@IColumn(description="导入数据条数", isNullable=false)
	private int importDataCount;
	
	@Column(name="remark", length=200, nullable = true)
	@IColumn(description="备注", isNullable=true)
	private String remark;
	
	@Column(name="result", length=10, nullable = false)
	@IColumn(description="导入结果", tagMethodName="getImportResult", isNullable=false)
	private String result;
	
	public static Map<String,String> getImportResult(){
		return ShowContext.getInstance().getShowEntityMap().get("reportImportResult");
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDtDate() {
		return dtDate;
	}

	public void setDtDate(String in) {
		this.dtDate = TypeParse.parseDate(in);
	}

	public Date getImportDate() {
		return importDate;
	}

	public void setImportDate(String in) {
		this.importDate = TypeParse.parseDate(in);;
	}

	public UserInfo getOperator() {
		return operator;
	}

	public void setOperator(UserInfo operator) {
		this.operator = operator;
	}

	public String getReportFileName() {
		return reportFileName;
	}

	public void setReportFileName(String reportFileName) {
		this.reportFileName = reportFileName;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	
	public String getStrReportInstCode() {
		return strReportInstCode;
	}

	public void setStrReportInstCode(String strReportInstCode) {
		this.strReportInstCode = strReportInstCode;
	}

	public String getStrReportInstName() {
		return strReportInstName;
	}

	public void setStrReportInstName(String strReportInstName) {
		this.strReportInstName = strReportInstName;
	}

	public int getImportDataCount() {
		return importDataCount;
	}

	public void setImportDataCount(int importDataCount) {
		this.importDataCount = importDataCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	
}
