package coresystem.service.check;

import java.util.List;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import coresystem.dto.PF_LegalInfo;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.SessionManager;
import framework.security.LoginInfo;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.MessageResult;

public class MultiLegalCodeCheck implements ICheck {

	@Override
	public MessageResult Check() throws Exception {
		MessageResult messageResult = new MessageResult();
		
		String isMultiLegal = ServletActionContext.getRequest().getParameter("isMultiLegal");
		SessionManager.setLoginInfo(new LoginInfo());		
		if(null!=isMultiLegal && !isMultiLegal.equals("")){
			String legalCode = ServletActionContext.getRequest().getParameter("legalCode");
			if(null!=legalCode && !legalCode.equals("")){
				IParamObjectResultExecute singleObjectFindByCriteriaDao = 
					(IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");		
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PF_LegalInfo.class);
				detachedCriteria.add(Restrictions.eq("strLegalCode", legalCode));
				List<PF_LegalInfo> list = 
					((List<PF_LegalInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null }));		
				if(list.size()==0){
					messageResult.setSuccess(false);
					messageResult.setMessage("请设置正确的法人Code！");
				}
			}else{
				messageResult.setSuccess(false);
				messageResult.setMessage("请在导航Url里设置法人Code！");
			}
		}
		return messageResult;
	}

}
