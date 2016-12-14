package zxptsystem.service.imps;

import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseObjectDaoResultService;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.IFileHandler;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.imps.FileHandler;

public class SingleObjectShowImgService extends BaseObjectDaoResultService {
	@Override
	public void initSuccessResult() throws Exception {
		
		if(RequestManager.getId() == null || RequestManager.getId().toString().equals("")){
			RequestManager.setId(RequestManager.getLevelIdValue());
		}

		super.initSuccessResult();
		
		Object result = this.getServiceResult();
		byte[] byteData=null;
		String fileName="";
		
		if(null !=result){
			byteData = (byte[])ReflectOperation.getFieldValue(result, "byteData");
			fileName = (String)ReflectOperation.getFieldValue(result, "strFileName");
		}		

		IFileHandler fileHandler = new FileHandler();
		try{
			DownloadResult downloadResult = fileHandler.GetStreamResultFromBytes(byteData, fileName, 2048);
			
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
