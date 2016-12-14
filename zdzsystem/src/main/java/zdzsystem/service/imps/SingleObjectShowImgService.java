package zdzsystem.service.imps;

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
		
		String tName=RequestManager.getTName().trim();
		super.initSuccessResult();
		
		Object result = this.getServiceResult();
		byte[] byteData=null;
		String fileName="";
		
		if(null !=result){
			if(tName.equals("zdzsystem.dto.AutoDTO_ZDZ_CXQQWSNR") || tName.equals("zdzsystem.dto.AutoDTO_ZDZ_FYSFKZWSNR")){//文书
				byteData = (byte[])ReflectOperation.getFieldValue(result, "WSNR");
				fileName = (String)ReflectOperation.getFieldValue(result, "WJMC");
			}
			else if(tName.equals("zdzsystem.dto.AutoDTO_ZDZ_CXQQFGGZZZJNR") || tName.equals("zdzsystem.dto.AutoDTO_ZDZ_FYSFKZFGGZZZJNR")){//工作证
				byteData = (byte[])ReflectOperation.getFieldValue(result, "GZZ");
			}
			else if(tName.equals("zdzsystem.dto.AutoDTO_ZDZ_CXQQFGGWZZJRN") || tName.equals("zdzsystem.dto.AutoDTO_ZDZ_FYSFKZFGGWZZJNR")){//公务证
				byteData = (byte[])ReflectOperation.getFieldValue(result, "GWZ");
			}
			
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
