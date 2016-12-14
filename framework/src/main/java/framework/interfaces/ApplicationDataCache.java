package framework.interfaces;

import framework.helper.ExceptionLog;

public class ApplicationDataCache {

	private static ApplicationDataCache applicationDataCache = null; 
	
	private Object data;
	
	public boolean isLock() {
		return isLock;
	}
	public void setLock(boolean isLock) {
		this.isLock = isLock;
	}
	private boolean isLock;

	synchronized public static ApplicationDataCache getInstance(){  
		if (applicationDataCache == null)  {
        	applicationDataCache = new ApplicationDataCache();  
        }
        return applicationDataCache;  
	}  
	private ApplicationDataCache() { 
		
    }
	
	public void StartUse(){
		while(isLock){
			try {
				Thread.sleep(100000);
			} 
			catch (InterruptedException e) {
				ExceptionLog.CreateLog(e);
			}
		}
		isLock = true;
	}
	
	public void EndUse(){
		releaseData();
		isLock = false;
	}
	
	public void releaseData(){
		data = null;
	}
	
	public void setData(Object data) {
		this.data = data;
	}
	public Object getData() {
		return data;
	}  
}
