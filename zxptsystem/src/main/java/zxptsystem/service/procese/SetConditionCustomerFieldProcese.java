package zxptsystem.service.procese;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.security.DataSecurity;
import framework.security.SecurityContext;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldCondition;
import framework.show.ShowFieldValue;
import framework.show.ShowList;
import framework.show.ShowSaveOrUpdate;

public class SetConditionCustomerFieldProcese implements IProcese {

	@Override
	public Object Procese(Object serviceResult) throws Exception {
		Set<DataSecurity> dataSecuritySet = SecurityContext.getInstance().getLoginInfo().getDataSecuritySet();
		Iterator<DataSecurity> iterator = dataSecuritySet.iterator();		
		
		Set<String> vSet=null;
		String fieldName="";
		String className1 = RequestManager.getTName();
		java.lang.reflect.Field field = ReflectOperation.getFieldByName(className1, "strCustomerID");
		
		String filedClassName = field.getType().getName();
		while(iterator.hasNext()){
			DataSecurity dataSecurity = iterator.next();
			if(dataSecurity.getClassName().equals(filedClassName)){
				vSet = dataSecurity.getDataSet();
				fieldName= dataSecurity.getFieldName();
				break;
			}
		}
		HashMap<String,String> map = new HashMap<String,String>();
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(filedClassName));
		detachedCriteria.add(Restrictions.in(fieldName+".strUserCode", vSet.toArray()));
		List<Object> autoETL_WorkflowList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		for(Object obj : autoETL_WorkflowList){
			map.put(ReflectOperation.getFieldValue(obj, "strCustomerID").toString()
					, ReflectOperation.getFieldValue(obj, "strCustomerName").toString());
		}
		
		if(serviceResult instanceof ShowList){
			ShowList list = (ShowList)serviceResult;
			for(ShowFieldCondition sfc :list.getShowCondition()){
				if(sfc.getFieldName().equals("strCustomerID")){
					sfc.setSingleTag("selectExtendField");		
					sfc.setTag(map);
				}
			}
		}else if(serviceResult instanceof ShowSaveOrUpdate){
			ShowSaveOrUpdate ssou = (ShowSaveOrUpdate)serviceResult;
			List<ShowFieldValue> list = ssou.getShowFieldValueList();
			for(ShowFieldValue sfv : list){
				if(sfv.getShowField().getFieldName().equals("strCustomerID")){
					sfv.getShowField().setSingleTag("selectExtendField");
					sfv.setTag(map);
				}
			}
		}
		return serviceResult;
	}

}
