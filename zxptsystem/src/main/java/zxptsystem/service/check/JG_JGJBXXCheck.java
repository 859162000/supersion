package zxptsystem.service.check;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.interfaces.ICheckWithParam;
import framework.services.interfaces.MessageResult;

/**
 * 机构基本信息(机构状态段)校验
 * 1、1029当客户类型（基础段）为基本户时，基本户状态（机构状态段）为必填项
 * @author xiajieli
 *
 */
public class JG_JGJBXXCheck implements ICheckWithParam {
	
	@SuppressWarnings("unchecked")
	@Override
	public MessageResult Check(Map<String, Object> mapObject) throws Exception {
		
		MessageResult messageResult = new MessageResult();
		
		try{
			Object JBHZT=mapObject.get("JBHZT");
			Object FOREIGNID=mapObject.get("FOREIGNID");
			
	    	if(JBHZT==null || JBHZT.equals("")){
	    		//查询基础段
	    		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				 DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_JG_JGJBXX_JC"));
				 if(FOREIGNID!=null){
					 detachedCriteria.add(Restrictions.eq("autoID",FOREIGNID.toString()));
				 }
				 List<Object> objectJCList= (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				 if(objectJCList.size()>0){
					 Object KHLX=ReflectOperation.getFieldValue(objectJCList.get(0), "KHLX");
					 if(KHLX!=null && KHLX.toString().equals("1")){//客户类型为基本户
						 messageResult.setSuccess(false);
						 messageResult.getMessageSet().add("当基础段中客户类型为基本户时，基本户状态为必填项");
					 }
				 }
	    	}
				 
		}catch(Exception ex){
			ExceptionLog.CreateLog(ex);
			throw new Exception(ex);
		}
		
		return messageResult;
		
	}
	

	
}
