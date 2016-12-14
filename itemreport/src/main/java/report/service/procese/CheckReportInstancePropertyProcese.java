package report.service.procese;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.CalRoundMethod;
import report.dto.CalculationExampleInfo;
import report.dto.CheckInstance;
import report.dto.RptInfo;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.MessageResult;

public class CheckReportInstancePropertyProcese implements IProcese {

	public Object Procese(Object serviceResult) throws Exception {
		if(serviceResult instanceof MessageResult){
			Object o = RequestManager.getTOject();
			if(o instanceof RptInfo){
				MessageResult result = (MessageResult)serviceResult;
				if(result.isSuccess()){
					String calculationName = ReflectOperation.getFieldValue(o, "strCalcInst").toString();
					String CheckName = ReflectOperation.getFieldValue(o, "strCheckInst").toString();
                    
                    
					IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
					DetachedCriteria detachedCriteria ;
					IParamVoidResultExecute insertDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveAllDao");
					if(calculationName!=null && !calculationName.trim().equals("")){
						String[] calNameAry=calculationName.split(",");
						detachedCriteria = DetachedCriteria.forClass(CalculationExampleInfo.class);
						detachedCriteria.add(Restrictions.in("strCalculationName",calNameAry));
						List<CalculationExampleInfo> list = 
							(List<CalculationExampleInfo>)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
						if(list.size()<calNameAry.length){
							
							Set<String> setCalInstance=new HashSet<String>();
							for(CalculationExampleInfo instance:list)
							{
								setCalInstance.add(instance.getStrCalculationName());
							}
							List<Object> objectList=new ArrayList<Object>();
							for(String calName:calNameAry)
							{
								if(!setCalInstance.contains(calName))
								{
									CalculationExampleInfo cei= new CalculationExampleInfo();
									cei.setStrCalculationName(calculationName);
									cei.setStrDescription(ReflectOperation.getFieldValue(o, "strRptName").toString());
									cei.setStrCalRoundMethod(CalRoundMethod.FirstCal.toString());
									cei.setIntPrecise("2");
									objectList.add(cei);
								}
								
							}
							
							insertDao.paramVoidResultExecute(new Object[]{objectList,null});
						}
					}
					if(CheckName!=null && !CheckName.trim().equals("")){
						String[] checkNameAry=CheckName.split(",");
						detachedCriteria = DetachedCriteria.forClass(CheckInstance.class);
						detachedCriteria.add(Restrictions.in("instanceName",checkNameAry));
						List<CheckInstance> list = 
							(List<CheckInstance>)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
						if(list.size()<checkNameAry.length){
							Set<String> setCheckInstance=new HashSet<String>();
							for(CheckInstance instance:list)
							{
								setCheckInstance.add(instance.getInstanceName());
							}
							List<Object> objectList=new ArrayList<Object>();
							for(String chkName:checkNameAry)
							{
								if(!setCheckInstance.contains(chkName))
								{
									CheckInstance ci= new CheckInstance();
									ci.setInstanceName(CheckName);
									ci.setDblTolerance("0.0");
									objectList.add(ci);
								}
								
							}
							insertDao.paramVoidResultExecute(new Object[]{objectList,null});
						}
					}
				}
			}			
		}
		return serviceResult;
	}
}
