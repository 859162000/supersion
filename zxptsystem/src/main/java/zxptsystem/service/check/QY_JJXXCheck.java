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
 * 企业征信-借据信息
 * 1、贷款投向仅允许填报小类(小类code为5位)
 * 2、同一基础段且业务发生日期相同的情况下，同一借据信息只可存在一笔
 * @author xiajieli
 *
 */
public class QY_JJXXCheck implements ICheckWithParam {
	
	@SuppressWarnings("unchecked")
	@Override
	public MessageResult Check(Map<String, Object> mapObject) throws Exception {
		MessageResult messageResult = new MessageResult();
			
		Object objforeinId = mapObject.get("FOREIGNID");
		IParamObjectResultExecute singleObjectFindByIdDao =(IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		AutoDTO_QY_DKYW_JC autoDTO_QY_DKYW_JC = (AutoDTO_QY_DKYW_JC) singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoDTO_QY_DKYW_JC.class.getName(),objforeinId,null});
		
		String extend2= "";
		String strJJBH="";
		
		if(mapObject.get("EXTEND2")!=null){
			extend2=mapObject.get("EXTEND2").toString();
		}
		if(mapObject.get("JJBH")!=null){
			strJJBH=mapObject.get("JJBH").toString();
		}
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria=null;
		detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.AutoDTO_QY_JJXX"));
		if(mapObject.get("AUTOID")!=null){
			detachedCriteria.add(Restrictions.ne("autoID", mapObject.get("AUTOID")));
		}
		detachedCriteria.add(Restrictions.eq("FOREIGNID", autoDTO_QY_DKYW_JC));
		detachedCriteria.add(Restrictions.eq("extend2", extend2));
		detachedCriteria.add(Restrictions.eq("JJBH", strJJBH));
		
		List<Object> objectList = (List<Object>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		if(objectList.size()>0){
			messageResult.setSuccess(false);
			messageResult.getMessageSet().add("同一基础段且业务发生日期相同的情况下，同一借据信息只可存在一笔");
		}
		
		if(mapObject.get("DKTX")!=null){
			String valueDKTX=mapObject.get("DKTX").toString();
			if(!valueDKTX.equals("") && valueDKTX.length()!=5){
				messageResult.setSuccess(false);
				messageResult.getMessageSet().add("贷款投向-仅允许填报小类(小类code为5位)");
			}
		}
		
	 return messageResult;
		
	}

}
