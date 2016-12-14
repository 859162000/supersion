package framework.services.procese;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.LogicParamManager;

public class ProcedureExcurteProcese  extends BaseConstructor  implements IProcese{
	
	public ProcedureExcurteProcese(){
		super();
	}
	
	public ProcedureExcurteProcese(Object baseObject){
		super(baseObject);
	}


	public Object Procese(Object serviceResult) throws Exception {
		if(!StringUtils.isBlank(LogicParamManager.getStrProcedureName())){
			String[] procedureParams = LogicParamManager.getStrProcedureParamNames();
			
			String callprcedure = "";
			Map<String,Object> procedureParam=new HashMap<String,Object>();
			if(procedureParams!=null){
				Object tObject=this.getBaseObject();
				
				for(int i =0;i<procedureParams.length;i++){
					procedureParam.put(procedureParams[i], ReflectOperation.getFieldValue(tObject, procedureParams[i]));
					if(!StringUtils.isBlank(callprcedure)){
						callprcedure += ",";
					}
					callprcedure += "?";
				}
			}
			
			String prcedureString = "{call " + LogicParamManager.getStrProcedureName() +"(" + callprcedure + ")}";
			IParamVoidResultExecute paramVoidResultExecute = (IParamVoidResultExecute)FrameworkFactory.CreateBean("excuteProcedureDao");
			paramVoidResultExecute.paramVoidResultExecute(new Object[]{prcedureString,procedureParam,null});
			
		}
		return serviceResult;
	} 

}
