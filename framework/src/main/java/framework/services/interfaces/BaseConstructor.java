package framework.services.interfaces;

import framework.helper.ExceptionLog;
import framework.interfaces.RequestManager;

public class BaseConstructor {
	
	private Object baseObject;

	public BaseConstructor(){
		try{
			this.baseObject = RequestManager.getTOject();
		}
		catch(Exception ex){
			ExceptionLog.CreateLog(ex);
		}
    }
	
	public BaseConstructor(Object baseObject){
		this.baseObject = baseObject;
	}

	public void setBaseObject(Object baseObject) {
		this.baseObject = baseObject;
	}

	public Object getBaseObject() {
		return baseObject;
	}
}

