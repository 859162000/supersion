package ncr.actions.imps;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import framework.actions.imps.BaseSAction;
import framework.interfaces.RequestManager;

public class FeedbackReportAction  extends BaseSAction{
	
	private static final long serialVersionUID = 1L;

	private File uploadFile;
    private String uploadFileFileName;
    private String strRadio;

	public String execute()throws Exception {
		
		Map<String,String> checkParam = new LinkedHashMap<String,String>();
		checkParam.put("uploadFileFileName", uploadFileFileName);
		checkParam.put("strRadio", strRadio);
		
		RequestManager.setReportCheckParam(checkParam);
		RequestManager.setUploadFile(uploadFile);
//		((Map<String,Object>)ServletActionContext.getContext().get("request")).put("strRadio", strRadio);

		return super.execute();
	}
	
	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public void setStrRadio(String strRadio) {
		this.strRadio = strRadio;
	}

	public String getStrRadio() {
		return strRadio;
	}
}
