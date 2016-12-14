package coresystem.service.translate;

import java.util.List;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import coresystem.dto.PF_LegalInfo;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.security.SecurityContext;
import framework.services.interfaces.ITranslate;

public class LegalInfoTranslate implements ITranslate {

	@SuppressWarnings("unchecked")
	@Override
	public void Translate() throws Exception {
		String legalCode = ServletActionContext.getRequest().getParameter("legalCode");
		
		if(null!=legalCode && !legalCode.equals("")){
			IParamObjectResultExecute singleObjectFindByCriteriaDao = 
				(IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");		
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PF_LegalInfo.class);
			detachedCriteria.add(Restrictions.eq("strLegalCode", legalCode));
			List<PF_LegalInfo> list = 
				((List<PF_LegalInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null }));		
						
			if(list.size()>0){
				SecurityContext.getInstance().getLoginInfo().setLegalInfo(list.get(0));
				SecurityContext.getInstance().getLoginInfo().setSessionFactory(list.get(0).getStrSessionFactory());
			}else{
				SecurityContext.getInstance().getLoginInfo().setLegalInfo(null);
			}	
		}			
	}
}
