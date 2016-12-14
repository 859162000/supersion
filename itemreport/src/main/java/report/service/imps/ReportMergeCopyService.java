package report.service.imps;

import java.util.Map;

import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseTShowService;
import framework.services.interfaces.MessageResult;

public class ReportMergeCopyService extends ReportMergeService implements IParamObjectResultExecute{

	public Object paramObjectResultExecute(String calculationName,Map<String,Object> paramValueMap,Integer precise,String rptFreq) throws Exception {
		//ReportMergeService ReportMergeService=new ReportMergeService();
		if(paramValueMap ==null || (paramValueMap !=null && paramValueMap.isEmpty())){
			if(super.getErrorMessage() !=null){
				return new MessageResult(false,super.getErrorMessage() + "\\r\\n" + "参数为空"); 
			}
			return new MessageResult(false,"参数为空");
		}
		String message = super.DoMerge(calculationName,paramValueMap,precise,rptFreq);
		
        if(message.equals("")){
			return new MessageResult("计算成功");  
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
		Integer precise=null;
		String rptFreq=null;
		if(objects.length>=4)
		{
			if(objects[0] !=null ){
				calculationName=(String)objects[0];
			}
			if(objects[1] !=null){ 
				paramValueMap=(Map<String,Object>)objects[1];
			}
			if(objects[2] !=null){ 
				precise=TypeParse.parseInt(objects[2].toString());
			}
			if(objects[3]!=null)
			{
				rptFreq=(String)objects[3];
			}
			return paramObjectResultExecute(calculationName,paramValueMap,precise,rptFreq);
		}
		throw new Exception("缺少参数(参数包括计算实例、精度、报表频度、计算参数（数据日期、机构）)");
	}
	
	
	
}
