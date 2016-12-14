package framework.services.imps;

import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.IFileHandler;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.imps.FileHandler;

public class SingleObjectDownloadByFieldService extends BaseObjectDaoResultService{

    private Integer bufferSize;
    private String datafield;
    private String fileNamefield;

	@Override
	public void initSuccessResult() throws Exception {
		
		if(RequestManager.getId() == null || RequestManager.getId().toString().equals("")){
			RequestManager.setId(RequestManager.getLevelIdValue());
		}

		super.initSuccessResult();
		
		Object result = this.getServiceResult();
		byte[] byteData = (byte[])ReflectOperation.getFieldValue(result, datafield);
		String fileName = (String)ReflectOperation.getFieldValue(result, fileNamefield);

		IFileHandler fileHandler = new FileHandler();
		try{
			DownloadResult downloadResult = fileHandler.GetStreamResultFromBytes(byteData, fileName, bufferSize);
			
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
	
	public void setDatafield(String datafield) {
		this.datafield = datafield;
	}

	public String getDatafield() {
		return datafield;
	}

	public void setFileNamefield(String fileNamefield) {
		this.fileNamefield = fileNamefield;
	}

	public String getFileNamefield() {
		return fileNamefield;
	}
	
	public void setBufferSize(Integer bufferSize) {
		this.bufferSize = bufferSize;
	}

	public Integer getBufferSize() {
		return bufferSize;
	}
}

