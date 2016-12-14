package zxptsystem.actions.imps;

import java.io.File;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import framework.actions.imps.BaseSAction;
import framework.interfaces.RequestManager;
import framework.services.interfaces.DownloadResult;

public class JiaJieMiFileAction  extends BaseSAction{
	
	private static final long serialVersionUID = 1L;
	
    private File uploadFile;
    private String uploadFileFileName;
    
    public String execute() throws Exception {

		Map<String,String> checkParam = new LinkedHashMap<String,String>();
		checkParam.put("uploadFileFileName", uploadFileFileName);
		RequestManager.setReportCheckParam(checkParam);
		
		RequestManager.setUploadFile(uploadFile);

        return super.execute();
	}
	
    public InputStream getInputStream(){ // 供action返回后，struts/tomcat传输文件给浏览器时调用
		return ((DownloadResult)this.getServiceResult()).getInputStream();
	}
    
    public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
	
	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public String getUploadFileFileName() {
		return uploadFileFileName;
	}
	
	
}
