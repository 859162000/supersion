package report.service.imps;

import report.helper.CheckInstanceCopy;
import framework.interfaces.IParamObjectResultExecute;
import framework.reportCheck.CheckInstance;

public class CheckInstanceCopyService implements IParamObjectResultExecute{
	
	public Object paramObjectResultExecute(Object param) throws Exception {
		CheckInstance checkInstance = null;
		
		if(param.getClass().equals(String.class)){
			checkInstance = CheckInstanceCopy.CopyCheckInstance(param.toString());
		}
		else{
			checkInstance = CheckInstanceCopy.CopyCheckInstance((report.dto.CheckInstance)param);
		}
		
		return checkInstance;
	}
}
