package report.helper;



import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.ReportInstInfo;
import report.dto.ReportInstSubInfo;

import coresystem.dto.InstInfo;
import coresystem.dto.UserInfo;


import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.security.SecurityContext;

public class ReportInstUtils {
	/**
	 * 得到当前用户所在的金融机构
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentUserInJRJGCode(String strSuitCode) throws Exception{
		String userCode=((UserInfo)SecurityContext.getInstance().getLoginInfo().getTag()).getStrUserCode();
		String JRJGDM="";
		
		if(userCode.equals(SecurityContext.getInstance().getSysUserCode())){
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportInstSubInfo.class);
			List<ReportInstSubInfo> reportInstSubInfoList=(List<ReportInstSubInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
	
			singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			detachedCriteria = DetachedCriteria.forClass(ReportInstInfo.class);
			
			
			if(strSuitCode.indexOf(",")>0){
				String[] strSuit=strSuitCode.split(",");
				detachedCriteria.add(Restrictions.or(Restrictions.in("suit.strSuitCode",strSuit),Restrictions.isNull("suit.strSuitCode")));
			}else{
				detachedCriteria.add(Restrictions.or(Restrictions.eq("suit.strSuitCode",strSuitCode),Restrictions.isNull("suit.strSuitCode")));
			}
			
			List<ReportInstInfo> reportInstInfoList=(List<ReportInstInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
	
			if(reportInstInfoList.size()>0){
				JRJGDM=reportInstInfoList.get(0).getStrReportInstCode();
			}
		}
		else if(userCode!=null && !StringUtils.isBlank(userCode)){
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserInfo.class);
			detachedCriteria.add(Restrictions.eq("strUserCode", userCode));
			List<UserInfo> userInfoList=(List<UserInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			
			singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			detachedCriteria = DetachedCriteria.forClass(InstInfo.class);
			detachedCriteria.add(Restrictions.eq("strInstCode", userInfoList.get(0).getInstInfo().getStrInstCode()));
			List<InstInfo> instInfoList=(List<InstInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
	
			singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			detachedCriteria = DetachedCriteria.forClass(ReportInstSubInfo.class);
			detachedCriteria.add(Restrictions.eq("instInfo", instInfoList.get(0)));
			List<ReportInstSubInfo> reportInstSubInfoList=(List<ReportInstSubInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
	
			singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			detachedCriteria = DetachedCriteria.forClass(ReportInstInfo.class);
			
			if(strSuitCode.indexOf(",")>0){
				String[] strSuit=strSuitCode.split(",");
				detachedCriteria.add(Restrictions.or(Restrictions.in("suit.strSuitCode",strSuit),Restrictions.isNull("suit.strSuitCode")));
			}else{
				detachedCriteria.add(Restrictions.or(Restrictions.eq("suit.strSuitCode",strSuitCode),Restrictions.isNull("suit.strSuitCode")));
			}
			
			List<String> reportList=new ArrayList<String>();
			for (int i=0;i<reportInstSubInfoList.size();i++) {
				reportList.add(reportInstSubInfoList.get(i).getReportInstInfo().getStrReportInstCode());
			}
			detachedCriteria.add(Restrictions.in("strReportInstCode", reportList));
			List<ReportInstInfo> reportInstInfoList=(List<ReportInstInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
	
			if(reportInstInfoList.size()>0){
				JRJGDM=reportInstInfoList.get(0).getStrReportInstCode();
			}
		}
		return JRJGDM;
		
	}
}
