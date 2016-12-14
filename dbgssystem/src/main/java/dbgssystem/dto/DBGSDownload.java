package dbgssystem.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.ReportInstInfo;
import framework.helper.FrameworkFactory;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IParamObjectResultExecute;
import framework.show.ShowContext;

@Entity
@Table(name = "DBGSDownload")
public class DBGSDownload implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@IColumn(description="报文序号")
	@Column(name = "strReportCode", length = 50)
	private String strReportCode;
	
	@IColumn(description="报文名称")
	@Column(name = "strReportName", length = 200)
	private String strReportName;
	
	@IColumn(tagMethodName="getReportTypeTag",description="报文种类")
	@Column(name = "strReportType", length = 200)
	private String strReportType;
	
	public static Map<String,String> getReportTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("DB_ReportType");
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dtDate")
	private Date dtDate;
	
	@IColumn(tagMethodName="getJRJGCodeTag",description="报送金融机构")
	@Column(name = "strJRJGCode")
	private String strJRJGCode;
	
	public static Map<String,String> getJRJGCodeTag() throws Exception{
		Map<String,String> reportInstInfoMap=new HashMap<String,String>();
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportInstInfo.class);
		detachedCriteria.add(Restrictions.or(Restrictions.eq("suit.strSuitCode","23"),Restrictions.isNull("suit.strSuitCode")));
		List<ReportInstInfo> reportInstInfoList = (List<ReportInstInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		for (ReportInstInfo reportInstInfo : reportInstInfoList) {
			reportInstInfoMap.put(reportInstInfo.getStrReportInstCode(), reportInstInfo.getStrReportInstName());
		}
		return reportInstInfoMap;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "SJBGRQ", length = 50, nullable = true)
	@IColumn(description="数据报告日期")
	private Date SJBGRQ;

	public void setDtDate(Date dtDate) {
		this.dtDate = dtDate;
	}

	public Date getDtDate() {
		return dtDate;
	}

	public void setStrJRJGCode(String strJRJGCode) {
		this.strJRJGCode = strJRJGCode;
	}

	public String getStrJRJGCode() {
		return strJRJGCode;
	}

	public void setStrReportType(String strReportType) {
		this.strReportType = strReportType;
	}

	public String getStrReportType() {
		return strReportType;
	}
	
	public String getStrReportCode() {
		return strReportCode;
	}

	public void setStrReportCode(String strReportCode) {
		this.strReportCode = strReportCode;
	}

	public String getStrReportName() {
		return strReportName;
	}

	public void setStrReportName(String strReportName) {
		this.strReportName = strReportName;
	}

	public void setSJBGRQ(String sJBGRQ) {
		SJBGRQ = TypeParse.parseDate(sJBGRQ);
	}

	public Date getSJBGRQ() {
		return SJBGRQ;
	}

	
}

