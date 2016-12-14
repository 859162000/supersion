package dbgssystem.actions.imps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import framework.actions.imps.BaseAction;
import framework.helper.FrameworkFactory;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.TActionRule;

public class RunRmiServiceAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	public static Map<String,String[]> DtoConfig;
	static{
		new RemoteThreadService().run();
		dbgssystem.helper.BatchContext.getInstance();
		framework.show.ShowContext.getInstance();
		DtoConfig = initDtoBeanConfig();
		System.out.println("static code block start!....");
	}
	
	private static Map<String,String[]> initDtoBeanConfig(){
		
		List<String> dtos = new ArrayList<String>();
		dtos.add("dbgssystem.dto.AutoDTO_DB_DBXX_JC");//担保合同基础段
		dtos.add("dbgssystem.dto.AutoDTO_DB_DBHTXX");//担保合同
		dtos.add("dbgssystem.dto.AutoDTO_DB_BDBRXX");//被担保人
		dtos.add("dbgssystem.dto.AutoDTO_DB_ZQRJZHTXX");//债权人
		dtos.add("dbgssystem.dto.AutoDTO_DB_FDBRXX");//反担保人
		dtos.add("dbgssystem.dto.AutoDTO_DB_SJZBZRXX");//实际在保
		dtos.add("dbgssystem.dto.AutoDTO_DB_DCGKXX");//代偿概况
		dtos.add("dbgssystem.dto.AutoDTO_DB_DCMXXX");//代偿明细
		dtos.add("dbgssystem.dto.AutoDTO_DB_BFJNGKXX");//保费概况
		dtos.add("dbgssystem.dto.AutoDTO_DB_BFJNMXXX");//保费明细
		dtos.add("dbgssystem.dto.AutoDTO_DB_ZCMXXX");//追偿明细 
		
		Map<String,String[]> map=new HashMap<String,String[]>();
		for(String dto:dtos){
			String[] args=new String[2];
			if(dto.equals("dbgssystem.dto.AutoDTO_DB_DBXX_JC")){
				args[0]=GetServiceBeanId(dto,"SaveLevelAUTODTO","singleObjectSaveLevelAutoDTOService");
				args[1]=GetServiceBeanId(dto,"UpdateLevelAUTODTO","singleObjectUpdateLevelAutoDTOService");
			}else{
				args[0]=GetServiceBeanId(dto,"SaveLevelAutoDTOMX","singleObjectSaveLevelAutoDTOMXService");
				args[1]=GetServiceBeanId(dto,"UpdateLevelAutoDTOMX","singleObjectUpdateLevelAutoDTOMXService");
			}
			map.put(dto, args);
		}
		
		return map;
	}
	private static String GetServiceBeanId(String tName,String action,String defaultServiceBeanId){
		String serviceBeanId=null;
		IObjectResultExecute service=null;
		try{
			serviceBeanId=TActionRule.getServiceBeanName(tName, action);
			service=(IObjectResultExecute)FrameworkFactory.CreateBean(serviceBeanId);
			
			if(service==null)
			{
				serviceBeanId=defaultServiceBeanId;
				service=(IObjectResultExecute)FrameworkFactory.CreateBean(serviceBeanId);
			}
		}catch(Exception e){
			e.printStackTrace();
		}		
		if(service==null)
		{
			serviceBeanId=null;
		}
		return serviceBeanId;
	}
	@Override
	public String execute() throws Exception {
		initDtoBeanConfig();
		return super.execute();
	}

}
