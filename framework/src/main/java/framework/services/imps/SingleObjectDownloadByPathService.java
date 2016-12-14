package framework.services.imps;

import framework.interfaces.IObjectResultExecute;
import framework.services.interfaces.DownloadResult;
import framework.services.interfaces.IFileHandler;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.imps.FileHandler;

public class SingleObjectDownloadByPathService implements IObjectResultExecute {

    private Integer bufferSize;
    private String path; 
    
	public void setBufferSize(Integer bufferSize) {
		this.bufferSize = bufferSize;
	}

	public Integer getBufferSize() {
		return bufferSize;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public Object objectResultExecute() throws Exception {
		try{
			IFileHandler fileHandler = new FileHandler();
			DownloadResult downloadResult = fileHandler.GetStreamResultFromPath(path, bufferSize);
			if(downloadResult.getInputStream() == null){
				return new MessageResult(false,"下载失败");
			}
			return downloadResult;
		}
		catch(Exception ex){
			return new MessageResult(false,ex.getMessage());
		}
	}
}
