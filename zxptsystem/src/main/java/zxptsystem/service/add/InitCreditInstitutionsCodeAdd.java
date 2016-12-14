package zxptsystem.service.add;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import zxptsystem.dto.QYZXCreditReportInfo;
import zxptsystem.dto.QYZXGrantUserInfo;
import coresystem.dto.UserInfo;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.security.SecurityContext;
import framework.services.interfaces.IAdd;

public class InitCreditInstitutionsCodeAdd implements IAdd {

	@Override
	public void Add() throws Exception {
		Object object = RequestManager.getTOject();
		if(object instanceof QYZXCreditReportInfo){
			String userCode=((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag()).getStrUserCode();
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(QYZXGrantUserInfo.class);
			detachedCriteria.add(Restrictions.eq("strUserCode", userCode));
			List<QYZXGrantUserInfo> grantUserInfoList =((List<QYZXGrantUserInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {detachedCriteria, null }));
			if(grantUserInfoList.size()>0){
				ReflectOperation.setFieldValue(object, "strOrgCode",grantUserInfoList.get(0).getStrInstCode());
			}
		}		
		ReflectOperation.setFieldValue(object, "timeCreateDate",new java.sql.Timestamp(System.currentTimeMillis()));
	}
}
