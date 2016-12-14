package zxptsystem.service.imps;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import zxptsystem.dto.EIS_AuthorizationDocumentInfo;

import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.imps.SingleObjectShowListService;
import framework.services.interfaces.LogicParamManager;

public class AuthorizationDocumentInfoShowListLevelDocService extends
		SingleObjectShowListService {

	@Override
	public void initSuccessResult() throws Exception {
		Object tObject= RequestManager.getTOject();
		String strProfileType="";
		String className=SessionManager.getPreviousLevelTName();
		if(Class.forName(className).newInstance() instanceof zxptsystem.dto.EIS_ENTCustomernBasicInfo){
			strProfileType="1";
		}else if(Class.forName(className).newInstance() instanceof zxptsystem.dto.EIS_PERCustomernBasicInfo){
			strProfileType="2";
		}
		
		String currentLevelLevel = SessionManager.getCurrentLevel();
		String id = SessionManager.getLevelIdValue(currentLevelLevel);
		DetachedCriteria detachedCriteria = LogicParamManager.getDetachedCriteria();
		if(tObject instanceof EIS_AuthorizationDocumentInfo){
			detachedCriteria.add(Restrictions.eq("profileType", strProfileType));
		}else{
			if(strProfileType.equals("1")){
				detachedCriteria.add(Restrictions.in("certificateType", new String[]{"E","F","G","H","Z"}));
			}else{
				detachedCriteria.add(Restrictions.in("certificateType", new String[]{"0","1","2","3","4","5","6","7","8","9","A","B","C","X"}));
			}
		}
		
		detachedCriteria.add(Restrictions.eq("strCustomerID", id));
		super.initSuccessResult();
	}
}
