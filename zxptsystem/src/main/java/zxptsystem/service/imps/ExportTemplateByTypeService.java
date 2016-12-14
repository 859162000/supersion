package zxptsystem.service.imps;

import java.io.File;

import org.apache.struts2.ServletActionContext;

import framework.interfaces.RequestManager;
import framework.services.imps.BaseService;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.imps.FileHandler;

public class ExportTemplateByTypeService extends BaseService{

	@Override
	public void initSuccessResult() throws Exception {
		
		String tName=RequestManager.getTName();
		
		String contextPath = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
		contextPath+="Download"+File.separatorChar+"ZXCX"+File.separatorChar+tName+".xls";
		File file= new File(contextPath);
		if(file.exists()){
			DownloadResult downloadResult = new FileHandler().GetStreamResultFromPath("Download"+File.separatorChar+"ZXCX"+File.separatorChar+tName+".xls", 1024*1024);
			if(downloadResult.getInputStream() == null){
				this.setServiceResult(new MessageResult(false,"下载失败"));
			}
			else{
				this.setServiceResult(downloadResult);
			}
		}else{
			this.setServiceResult(new MessageResult(false,"下载失败:模板文件不存在！"));
		}
	}
}
