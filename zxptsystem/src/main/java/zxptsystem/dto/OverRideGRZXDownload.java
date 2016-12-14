package zxptsystem.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.ReportInstInfo;


import framework.helper.FrameworkFactory;
import framework.interfaces.IColumn;
import framework.interfaces.IParamObjectResultExecute;
import framework.show.ShowContext;

@Entity
@Table(name = "OverRideGRZXDownload")
public class OverRideGRZXDownload implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@IColumn(description="报文序号")
	@Column(name = "strGrReportCode", length = 50)
	private String strGrReportCode;
	
	@IColumn(description="报文名称")
	@Column(name = "strGrReportName", length = 200)
	private String strGrReportName;
	
	@IColumn(tagMethodName="getGrReportTypeTag",description="报文文件种类")
	@Column(name = "strGrReportType", length = 200)
	private String strGrReportType;
	
	public static Map<String,String> getGrReportTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("GrReportType");
	}
	
	@IColumn(tagMethodName="getGrSendTypeTag",description="上报类型")
	@Column(name = "strGrSendType")
	private String strGrSendType;
	
	public static Map<String,String> getGrSendTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("GrSendType");
	}

	@IColumn(tagMethodName="getGrJRJGCodeTag",description="报送金融机构")
	@Column(name = "strGrJRJGCode")
	private String strGrJRJGCode;
	
	public static Map<String,String> getGrJRJGCodeTag() throws Exception{
		Map<String,String> reportInstInfoMap=new HashMap<String,String>();
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportInstInfo.class);
		detachedCriteria.add(Restrictions.or(Restrictions.eq("suit.strSuitCode","22"),Restrictions.isNull("suit.strSuitCode")));
		List<ReportInstInfo> reportInstInfoList = (List<ReportInstInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		for (ReportInstInfo reportInstInfo : reportInstInfoList) {
			reportInstInfoMap.put(reportInstInfo.getStrReportInstCode(), reportInstInfo.getStrReportInstName());
		}
		return reportInstInfoMap;
	}

	@IColumn(description="数据开始年月日")
	@Column(name = "strGRSJFSNY", length = 50)
	private String strGRSJFSNY;
	
	@IColumn(description="数据发生截止日期")
	@Column(name = "endGRSJFSNY", length = 50)
	private String endGRSJFSNY;
	
	@IColumn(description="数据发生起始日期")
	@Column(name = "beginGRSJFSNY", length = 50)
	private String beginGRSJFSNY;

	public String getBeginGRSJFSNY() {
		return beginGRSJFSNY;
	}

	public void setBeginGRSJFSNY(String beginGRSJFSNY) {
		this.beginGRSJFSNY = beginGRSJFSNY;
	}

	public void setStrGRSJFSNY(String strGRSJFSNY) {
		this.strGRSJFSNY = strGRSJFSNY;
	}

	public String getStrGRSJFSNY() {
		return strGRSJFSNY;
	}
	
	public void setStrGrJRJGCode(String strGrJRJGCode) {
		this.strGrJRJGCode = strGrJRJGCode;
	}

	public String getStrGrJRJGCode() {
		return strGrJRJGCode;
	}

	public void setStrGrSendType(String strGrSendType) {
		this.strGrSendType = strGrSendType;
	}

	public String getStrGrSendType() {
		return strGrSendType;
	}

	public void setStrGrReportType(String strGrReportType) {
		this.strGrReportType = strGrReportType;
	}

	public String getStrGrReportType() {
		return strGrReportType;
	}
	
	public String getStrGrReportCode() {
		return strGrReportCode;
	}

	public void setStrGrReportCode(String strGrReportCode) {
		this.strGrReportCode = strGrReportCode;
	}

	public String getStrGrReportName() {
		return strGrReportName;
	}

	public void setStrGrReportName(String strGrReportName) {
		this.strGrReportName = strGrReportName;
	}

	public String getEndGRSJFSNY() {
		return endGRSJFSNY;
	}

	public void setEndGRSJFSNY(String endGRSJFSNY) {
		this.endGRSJFSNY = endGRSJFSNY;
	}
	
	

}
