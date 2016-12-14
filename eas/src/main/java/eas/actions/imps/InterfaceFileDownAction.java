package eas.actions.imps;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import framework.actions.imps.BaseSTNameAndIdAction;
import framework.interfaces.RequestManager;
import framework.services.interfaces.DownloadResult;

public class InterfaceFileDownAction  extends BaseSTNameAndIdAction{
	
	private static final long serialVersionUID = 1L;
	
	/**East接口下载*/
	private String endDate;				//上报日期（East的可结束日期）
	private String startDate;			//开始时间（East的可开始日期）
	private String reportInstinfo;		//内部机构号（区分机构）
	private String strCheckbox;					//新老客户文件列表
	private String createFormat;		//生成格式
	
	public InputStream getInputStream(){ // 供action返回后，struts/tomcat传输文件给浏览器时调用
		
		return ((DownloadResult)this.getServiceResult()).getInputStream();
	}
	
	public String execute()throws Exception {
		
		Map<String,String> checkParam = new LinkedHashMap<String,String>();

		checkParam.put("strCheckbox", strCheckbox);
		checkParam.put("endDate", endDate);
		checkParam.put("startDate", startDate);
		checkParam.put("reportInstinfo", reportInstinfo);
		checkParam.put("createFormat", createFormat);
		
		RequestManager.setReportCheckParam(checkParam);
		return super.execute();
		
	}
	
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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

	public void setStrCheckbox(String strCheckbox) {
		this.strCheckbox = strCheckbox;
	}

	public String getStrCheckbox() {
		return strCheckbox;
	}

	public String getCreateFormat() {
		return createFormat;
	}

	public void setCreateFormat(String createFormat) {
		this.createFormat = createFormat;
	}



}
