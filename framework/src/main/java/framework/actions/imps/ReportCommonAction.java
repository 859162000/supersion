package framework.actions.imps;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;

import framework.actions.imps.BaseSTAction;
import framework.interfaces.RequestManager;

public class ReportCommonAction extends BaseSTAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String dtDate;
	
	private String strInstCode;
	
	private String strTaskRptInstID;
	
	private String strFreq;
	
	@Override
    public String execute() throws Exception {
		Map<String,String> checkParam = new LinkedHashMap<String,String>();

		if(!StringUtils.isBlank(dtDate)){
			checkParam.put("dtDate", dtDate);
		}
		
		if(!StringUtils.isBlank(strInstCode)){
			checkParam.put("strInstCode", strInstCode);
		}
		
		if(!StringUtils.isBlank(strTaskRptInstID)){
			checkParam.put("strTaskRptInstID", strTaskRptInstID);
		}
		
		if(!StringUtils.isBlank(strFreq)){
			checkParam.put("strFreq", strFreq);
		}
		
		RequestManager.setReportCheckParam(checkParam);
		return super.execute();
	}

	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public String getDtDate() {
		return dtDate;
	}

	public void setStrInstCode(String strInstCode) {
		this.strInstCode = strInstCode;
	}

	public String getStrInstCode() {
		return strInstCode;
	}

	public void setStrTaskRptInstID(String strTaskRptInstID) {
		this.strTaskRptInstID = strTaskRptInstID;
	}

	public String getStrTaskRptInstID() {
		return strTaskRptInstID;
	}

	public void setStrFreq(String strFreq) {
		this.strFreq = strFreq;
	}

	public String getStrFreq() {
		return strFreq;
	}
}
