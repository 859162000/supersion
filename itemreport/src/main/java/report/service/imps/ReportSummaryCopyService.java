package report.service.imps;

import java.util.Map;

import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.interfaces.MessageResult;

public class ReportSummaryCopyService extends ReportSummaryService implements IParamObjectResultExecute{

	
	public Object paramObjectResultExecute(String calculationName,Map<String,Object> paramValueMap,String strInstCodeName,Integer precise) throws Exception {
		//ReportMergeService ReportMergeService=new ReportMergeService();
		if(paramValueMap ==null || (paramValueMap !=null && paramValueMap.isEmpty())){
			if(super.getErrorMessage() !=null){
				return new MessageResult(false,super.getErrorMessage() + "\\r\\n" + "参数为空"); 
			}
			return new MessageResult(false,"参数为空");
		}
		String message = super.DoSummary(calculationName,paramValueMap,strInstCodeName,precise);
		
        if(message.equals("")){
			return new MessageResult(super.getSuccessMessage());  
		}
		else{
			if(super.getErrorMessage() !=null){
				return new MessageResult(false, super.getErrorMessage() + "\\r\\n" + message);  
			}
			else{
				return new MessageResult(false, message);  
			}
		}
	}

	
	@SuppressWarnings("unchecked")
	public Object paramObjectResultExecute(Object param) throws Exception {
		
		Object[] objects=(Object[]) param;
		String calculationName=null;
		Map<String,Object> paramValueMap=null;
		String strInstCodeName=null;
		Integer precise=null;
		
		if(objects[0] !=null ){
			calculationName=(String)objects[0];
		}
		if(objects[1] !=null){ 
			paramValueMap=(Map<String,Object>)objects[1];
		}
		if(objects[2] !=null ){
			strInstCodeName=(String)objects[2];
		}
		if(objects[3] !=null){ 
			precise=TypeParse.parseInt(objects[3].toString());
		}
		return paramObjectResultExecute(calculationName,paramValueMap,strInstCodeName,precise);
	}
	
	
	
}
