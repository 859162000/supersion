package zxptsystem.service.check;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import zxptsystem.dto.AutoDTO_QY_DKYW_JC;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.interfaces.ICheckWithParam;
import framework.services.interfaces.MessageResult;

/**
 * 企业贷款业务-合同信息段
 * 同一基础段且业务发生日期相同的情况下，同一合同信息只可存在一笔
 * @author xiajieli 
 *
 */
public class QY_HTXXCheck implements ICheckWithParam {
	
	@SuppressWarnings("unchecked")
	@Override
	public MessageResult Check(Map<String, Object> mapObject) throws Exception {
		MessageResult messageResult = new MessageResult();
			
		Object objforeinId = mapObject.get("FOREIGNID");
		IParamObjectResultExecute singleObjectFindByIdDao =(IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		AutoDTO_QY_DKYW_JC autoDTO_QY_DKYW_JC = (AutoDTO_QY_DKYW_JC) singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_DKYW_JC.class.getName(),objforeinId,null});
		
		String extend2= "";
		
		if(mapObject.get("EXTEND2")!=null){
			extend2=mapObject.get("EXTEND2").toString();
		}
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria=null;
		detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_HTXX"));
		if(mapObject.get("AUTOID")!=null){
			detachedCriteria.add(Restrictions.ne("autoID", mapObject.get("AUTOID")));
		}
		detachedCriteria.add(Restrictions.eq("FOREIGNID", autoDTO_QY_DKYW_JC));
		detachedCriteria.add(Restrictions.eq("extend2", extend2));
		
		List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		if(objectList.size()>0){
			messageResult.setSuccess(false);
			messageResult.getMessageSet().add("同一基础段且业务发生日期相同的情况下，同一合同信息只可存在一笔");
		}
		
		return messageResult;
		
	}

}
