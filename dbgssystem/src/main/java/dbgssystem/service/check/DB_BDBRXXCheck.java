package dbgssystem.service.check;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import dbgssystem.dto.AutoDTO_DB_DBXX_JC;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.interfaces.ICheckWithParam;
import framework.services.interfaces.MessageResult;
 
/**
 * 被担保人信息段：被担保人信息段里面的被担保人证件号码不能与基础段的证件号码重复
 * @author xiajieli
 *
 */
public class DB_BDBRXXCheck implements ICheckWithParam{

	@SuppressWarnings("unchecked")
	public MessageResult Check(Map<String, Object> mapObject) throws Exception {
		
		MessageResult messageResult = new MessageResult();
		
		Object BDBRZJLX=mapObject.get("BDBRZJLX");
		Object BDBRZJHM=mapObject.get("BDBRZJHM");
		Object FOREIGNID=mapObject.get("FOREIGNID");
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoDTO_DB_DBXX_JC.class);
		detachedCriteria.add(Restrictions.eq("BDBRZJLX", BDBRZJLX));
		detachedCriteria.add(Restrictions.eq("BDBRZJHM", BDBRZJHM));
		detachedCriteria.add(Restrictions.eq("autoID", FOREIGNID));
		List<AutoDTO_DB_DBXX_JC> autoDTO_DB_DBXX_JCList = (List<AutoDTO_DB_DBXX_JC>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});			
		
		if(autoDTO_DB_DBXX_JCList.size()>0){
			 messageResult.setSuccess(false);
			 messageResult.getMessageSet().add("同一基础段下，被担保人证件号码不能重复"); 
		}
		
		return messageResult;
	}
	


}