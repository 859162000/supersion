package report.service.check;


import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.RptInfo;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;



public class RptInfoUniqueSaveCheck extends BaseConstructor  implements ICheck{
	
  
@SuppressWarnings("unchecked")
public MessageResult Check() throws Exception {
	   
	   MessageResult messageResult = new MessageResult();
	   Object tObjct = RequestManager.getTOject();
 	   String strRptPath = ReflectOperation.getFieldShowValue(tObjct,"strRptPath");

	   IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
	   DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RptInfo.class);
	   detachedCriteria.add(Restrictions.eq("strRptPath", strRptPath));
	   List<RptInfo> rptinfoList = (List<RptInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
	   if(rptinfoList.size()>0){
		   messageResult.setSuccess(false);
			messageResult.setMessage("报表代码+版本号 必须唯一，请检查修改");
	   }  
      return   messageResult;
	}
		
	

}
