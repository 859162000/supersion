package report.service.imps;

import report.dto.MRepPackInfo;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseObjectDaoResultService;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.imps.FileHandler;

public class DownloadRepPackService  extends BaseObjectDaoResultService{
	
	/** 升级包路径 **/
	private static String updateDir = "Download/RPTUpdate";
	
	@Override
	public void initSuccessResult() throws Exception {
		if(RequestManager.getId() == null || RequestManager.getId().toString().equals("")){
			RequestManager.setId(RequestManager.getLevelIdValue());
		}
		super.initSuccessResult();
		MRepPackInfo result = (MRepPackInfo) this.getServiceResult();
		try{
			DownloadResult downloadResult = new FileHandler().GetStreamResultFromPath(updateDir+"/"+result.getPackName()+".zip", 1024*1024*5);
			if(downloadResult.getInputStream() == null){
				this.setServiceResult(new MessageResult(false,"没有附件,不能下载"));
			}
			else{
				this.setServiceResult(downloadResult);
			}
		}
		catch(Exception ex){
			this.setServiceResult(new MessageResult(false,ex.getMessage()));
		}
	}
}
