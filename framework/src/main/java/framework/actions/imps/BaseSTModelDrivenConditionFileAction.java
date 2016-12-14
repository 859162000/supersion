package framework.actions.imps;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import framework.interfaces.RequestManager;

public class BaseSTModelDrivenConditionFileAction  extends BaseSTModelDrivenConditionAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private File uploadFile;
    private String uploadFileFileName;
	
    public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
	
	@Override
    public String execute() throws Exception {

		Map<String,String> checkParam = new LinkedHashMap<String,String>();
		checkParam.put("uploadFileFileName", uploadFileFileName);
		RequestManager.setReportCheckParam(checkParam);
		
		RequestManager.setUploadFile(uploadFile);

        return super.execute();
	}


	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public String getUploadFileFileName() {
		return uploadFileFileName;
	}
	

}
