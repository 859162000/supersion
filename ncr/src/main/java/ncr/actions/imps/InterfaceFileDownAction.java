package ncr.actions.imps;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.xwork.StringUtils;

import framework.actions.imps.BaseSTNameAndIdAction;
import framework.interfaces.RequestManager;
import framework.services.interfaces.DownloadResult;

public class InterfaceFileDownAction  extends BaseSTNameAndIdAction{
	
	private static final long serialVersionUID = 1L;
	
	/**新客户风险*/
	private String startDate;			//开始时间（East的可开始日期）
	private String strCheckbox;					//新老客户文件列表

	private String reportInstinfo;		//内部机构号（区分机构）
	private String Year;				//年
	private String Month;				//月
	private String Day;					//天

	public InputStream getInputStream(){ // 供action返回后，struts/tomcat传输文件给浏览器时调用
		
		return ((DownloadResult)this.getServiceResult()).getInputStream();
	}
	
	public String execute()throws Exception {
		
		Map<String,String> checkParam = new LinkedHashMap<String,String>();
		

		if(startDate!=null){
			Year = startDate.split("-")[0]; 
			Month = startDate.split("-")[1];
			if(startDate.split("-").length>2){
				Day = startDate.split("-")[2];
			}
		}
		

		checkParam.put("Year", Year);
		checkParam.put("Month", Month);
		checkParam.put("Day", Day);
		
		checkParam.put("strCheckbox", strCheckbox);
		checkParam.put("startDate", startDate);
		checkParam.put("reportInstinfo", reportInstinfo);
		
		RequestManager.setReportCheckParam(checkParam);
		return super.execute();
		
	}

	public String getStrCheckbox() {
		return strCheckbox;
	}

	public void setStrCheckbox(String strCheckbox) {
		this.strCheckbox = strCheckbox;
	}
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getReportInstinfo() {
		return reportInstinfo;
	}

	public void setReportInstinfo(String reportInstinfo) {
		this.reportInstinfo = reportInstinfo;
	}

	public String getYear() {
		return Year;
	}

	public void setYear(String year) {
		Year = year;
	}

	public String getMonth() {
		return Month;
	}

	public void setMonth(String month) {
		Month = month;
	}

	public String getDay() {
		return Day;
	}

	public void setDay(String day) {
		Day = day;
	}

}
