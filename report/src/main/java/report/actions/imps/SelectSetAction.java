package report.actions.imps;

import java.util.LinkedHashMap;
import java.util.Map;
import framework.actions.imps.BaseSTNameAndIdAction;
import framework.interfaces.RequestManager;

public class SelectSetAction  extends BaseSTNameAndIdAction{

	private static final long serialVersionUID = 1L;
	
	private String AutoTableID;	
	private String SuitID;		
	private String TaskID;		
	private String InstID;		
	private String FileID;		
	private String RptID;	
	private String ReportModel_TableID;	
	private String TmpName;

	public String execute()throws Exception {
		Map<String,String> checkParam = new LinkedHashMap<String,String>();
		checkParam.put("AutoTableID", AutoTableID);
		checkParam.put("SuitID", SuitID);
		checkParam.put("TaskID", TaskID);
		checkParam.put("InstID", InstID);
		checkParam.put("FileID", FileID);
		checkParam.put("RptID", RptID);
		checkParam.put("ReportModel_TableID", ReportModel_TableID);
		checkParam.put("TmpName", TmpName);
		RequestManager.setReportCheckParam(checkParam);
		
		super.execute();
		return null;
	}

	public void setAutoTableID(String autoTableID) {
		AutoTableID = autoTableID;
	}

	public String getAutoTableID() {
		return AutoTableID;
	}

	public String getInstID() {
		return InstID;
	}

	public void setInstID(String instID) {
		InstID = instID;
	}

	public String getFileID() {
		return FileID;
	}

	public void setFileID(String fileID) {
		FileID = fileID;
	}

	public String getReportmodel_tableID() {
		return ReportModel_TableID;
	}

	public void setReportModel_TableID(String reportModelTableID) {
		ReportModel_TableID = reportModelTableID;
	}

	public String getSuitID() {
		return SuitID;
	}

	public void setSuitID(String suitID) {
		SuitID = suitID;
	}

	public String getTaskID() {
		return TaskID;
	}

	public void setTaskID(String taskID) {
		TaskID = taskID;
	}

	public String getTmpName() {
		return TmpName;
	}

	public void setTmpName(String tmpName) {
		TmpName = tmpName;
	}

	public String getRptID() {
		return RptID;
	}

	public void setRptID(String rptID) {
		RptID = rptID;
	}


}
