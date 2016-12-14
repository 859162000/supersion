package report.service.imps;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import coresystem.dto.UserInfo;

import framework.security.SecurityContext;
import framework.services.imps.SingleObjectShowListService;
import framework.services.interfaces.LogicParamManager;
/**
 * 根据申请人过滤显示数据
 * @author Administrator
 *
 */
public class DownloadResourceShowListService extends SingleObjectShowListService{

	@Override
	public void initSuccessResult() throws Exception {

		if(!SecurityContext.getInstance().getLoginInfo().isAdministrator()){
			UserInfo userInfo = (UserInfo)SecurityContext.getInstance().getLoginInfo().getTag();
			String strUserName = userInfo.getStrUserName();
			DetachedCriteria detachedCriteria = LogicParamManager.getDetachedCriteria();
			detachedCriteria.add(Restrictions.eq("proposer", strUserName));
		}

		super.initSuccessResult();

	}
}
