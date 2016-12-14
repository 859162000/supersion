package zxptsystem.service.imps;

import org.hibernate.criterion.DetachedCriteria;

import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.imps.BaseService;
import framework.services.interfaces.LogicParamManager;

public class ShowConditionResetService extends BaseService{

	@Override
	public void initSuccessResult() throws Exception {
		
		// 清空当前查询条件
		if(LogicParamManager.getDetachedCriteria() != null){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(RequestManager.getTName()));
			LogicParamManager.setDetachedCriteria(detachedCriteria);
		}

		// 清空查询框信息
		SessionManager.setTCondition(null, RequestManager.getTName());
	}
}
