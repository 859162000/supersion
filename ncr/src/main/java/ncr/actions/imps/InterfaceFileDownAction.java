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
	
	/**�¿ͻ�����*/
	private String startDate;			//��ʼʱ�䣨East�Ŀɿ�ʼ���ڣ�
	private String strCheckbox;					//���Ͽͻ��ļ��б�

	private String reportInstinfo;		//�ڲ������ţ����ֻ�����
	private String Year;				//��
	private String Month;				//��
	private String Day;					//��

	public InputStream getInputStream(){ // ��action���غ�struts/tomcat�����ļ��������ʱ����
		
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
