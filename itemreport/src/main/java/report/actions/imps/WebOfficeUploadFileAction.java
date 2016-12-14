package report.actions.imps;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import framework.actions.imps.BaseSAction;
import framework.interfaces.RequestManager;

public class WebOfficeUploadFileAction  extends BaseSAction{

	private static final long serialVersionUID = 1L;
	private String path; 
	private File uploadFile;
	private String CalcInstName; 
	
	@Override
	public String execute() throws Exception {

		Map<String,String> checkParam = new LinkedHashMap<String,String>();
		checkParam.put("path", path);
		checkParam.put("CalcInstName", CalcInstName);
		
		RequestManager.setReportCheckParam(checkParam);
		RequestManager.setUploadFile(uploadFile);
		
		super.execute();
		return null;
	}

    public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	

	public void setCalcInstName(String calcInstName) {
		CalcInstName = calcInstName;
	}

	public String getCalcInstName() {
		return CalcInstName;
	}

}
