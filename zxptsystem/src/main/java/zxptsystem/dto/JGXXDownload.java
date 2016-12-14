package zxptsystem.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.ReportInstInfo;

import framework.helper.FrameworkFactory;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IParamObjectResultExecute;
import framework.show.ShowContext;

@Entity
@Table(name = "JGXXDownload")
public class JGXXDownload implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "autoJGXXDownLoadID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "framework.interfaces.AssignedGUIDGenerator")
	private String autoJGXXDownLoadID;
	
	@IColumn(description="报文序号")
	@Column(name = "strJgReportCode", length = 50)
	private String strJgReportCode;
	
	@IColumn(description="报文名称")
	@Column(name = "strJgReportName", length = 200)
	private String strJgReportName;
	
	@IColumn(tagMethodName="getJgReportTypeTag",description="报文文件种类")
	@Column(name = "strJgReportType", length = 200)
	private String strJgReportType;
	
	public static Map<String,String> getJgReportTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("JgReportType");
	}
	
	@IColumn(tagMethodName="getJgSendTypeTag",description="上报类型")
	@Column(name = "strJgSendType")
	private String strJgSendType;
	
	public static Map<String,String> getJgSendTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("JgSendType");
	}

	@IColumn(tagMethodName="getJgJRJGCodeTag",description="报送金融机构")
	@Column(name = "strJgJRJGCode")
	private String strJgJRJGCode;
	
	public static Map<String,String> getJgJRJGCodeTag() throws Exception{
		Map<String,String> reportInstInfoMap=new HashMap<String,String>();
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportInstInfo.class);
		detachedCriteria.add(Restrictions.or(Restrictions.in("suit.strSuitCode",new String[]{"21","24"}),Restrictions.isNull("suit.strSuitCode")));
		List<ReportInstInfo> reportInstInfoList = (List<ReportInstInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		for (ReportInstInfo reportInstInfo : reportInstInfoList) {
			reportInstInfoMap.put(reportInstInfo.getStrReportInstCode(), reportInstInfo.getStrReportInstName());
		}
		return reportInstInfoMap;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "XXGXRQ", length = 50, nullable = true)
	@IColumn(description="信息更新日期")
	private Date XXGXRQ;

	public Date getXXGXRQ() {
		return XXGXRQ;
	}

	public void setXXGXRQ(String in) {
		XXGXRQ =TypeParse.parseDate(in);
	}

	public void setStrJgJRJGCode(String strJgJRJGCode) {
		this.strJgJRJGCode = strJgJRJGCode;
	}

	public String getStrJgJRJGCode() {
		return strJgJRJGCode;
	}

	public void setStrJgSendType(String strJgSendType) {
		this.strJgSendType = strJgSendType;
	}

	public String getStrJgSendType() {
		return strJgSendType;
	}

	public void setStrJgReportType(String strJgReportType) {
		this.strJgReportType = strJgReportType;
	}

	public String getStrJgReportType() {
		return strJgReportType;
	}

	public String getStrJgReportCode() {
		return strJgReportCode;
	}

	public void setStrJgReportCode(String strJgReportCode) {
		this.strJgReportCode = strJgReportCode;
	}

	public String getStrJgReportName() {
		return strJgReportName;
	}

	public void setStrJgReportName(String strJgReportName) {
		this.strJgReportName = strJgReportName;
	}

	public void setAutoJGXXDownLoadID(String autoJGXXDownLoadID) {
		this.autoJGXXDownLoadID = autoJGXXDownLoadID;
	}

	public String getAutoJGXXDownLoadID() {
		return autoJGXXDownLoadID;
	}
}
