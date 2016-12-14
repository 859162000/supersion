package report.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "RptSubmitInfo")
@IEntity(description="报表报送信息")
public class RptSubmitInfo implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = -4057691069658232007L;
	@Id
	@Column(name = "taskRptInstId", length = 32)
	private String taskRptInstId;
	
	public void setTaskRptInstId(String taskRptInstId)
	{
		this.taskRptInstId=taskRptInstId;
	}
	public String getTaskRptInstId()
	{
		return this.taskRptInstId;
	}
	
	@Column(name = "strBankName", length = 250, nullable = true)
	@IColumn(description="报送单位", isKeyName=false, isNullable = true)
	private String strBankName;
	
	public void setStrBankName(String strBankName)
	{
		this.strBankName=strBankName;
	}
	public String getStrBankName()
	{
		return this.strBankName==null?"":strBankName;
	}
	
	@Column(name = "strRptDate", length = 50, nullable = true)
	@IColumn(description="报送日期", isKeyName=false, isNullable = true)
	private String strRptDate;
	
	public void setStrRptDate(String strRptDate)
	{
		this.strRptDate=strRptDate;
	}
	public String getStrRptDate()
	{
		return this.strRptDate==null?"":strRptDate;
	}
	
	@Column(name = "strReporter", length = 250, nullable = true)
	@IColumn(description="填报人", isKeyName=false, isNullable = true)
	private String strReporter;
	
	public void setStrReporter(String strReporter)
	{
		this.strReporter=strReporter;
	}
	public String getStrReporter()
	{
		return this.strReporter==null?"":strReporter;
	}
	
	@Column(name = "strChecker", length = 250, nullable = true)
	@IColumn(description="审核人", isKeyName=false, isNullable = true)
	private String strChecker;
	
	public void setStrChecker(String strChecker)
	{
		this.strChecker=strChecker;
	}
	public String getStrChecker()
	{
		return this.strChecker==null?"":strChecker;
	}
	
	@Column(name = "strManager", length = 250, nullable = true)
	@IColumn(description="负责人", isKeyName=false, isNullable = true)
	private String strManager;
	
	public void setStrManager(String strManager)
	{
		this.strManager=strManager;
	}
	public String getStrManager()
	{
		return this.strManager==null?"":strManager;
	}
	
	
	

}
