package zxptsystem.service.imps;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import coresystem.dto.UserInfo;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import zxptsystem.dto.GRZXCreditReportInfo;
import zxptsystem.dto.GRZXQueryDetailLog;
import zxptsystem.dto.GRZXQueryLog;
import zxptsystem.dto.QYZXCreditReportInfo;
import zxptsystem.dto.QYZXQueryLog;

public class CreditReportLogHelper {
	
	public static String LogQyInfo(String id,String type,String rhId,String sessionFactory,UserInfo userInfo){
		QYZXQueryLog log = new QYZXQueryLog();
		log.setDtBeginDate(new Timestamp(System.currentTimeMillis()));
		try {
			log.setUserInfo(userInfo);
			log.setInstInfo(userInfo.getInstInfo());			
		} catch (Exception e) {
			e.printStackTrace();
		}
		QYZXCreditReportInfo info =null;
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		try {
			info = (QYZXCreditReportInfo)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{
						QYZXCreditReportInfo.class.getName()
						,id
						,sessionFactory});
		} catch (Exception e1) {
			e1.printStackTrace();
		}		
		
		info.setId(id);
		log.setQyZXCreditReportInfo(info);
		log.setStrRHActNo(rhId);
		log.setStrQueryType(type);
		
		IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
		try {
			singleObjectSaveDao.paramVoidResultExecute(new Object[]{log
					,sessionFactory});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return log.getId();
	}
	
	public static void updateQyLogEndDateById(String id,String sessionFactory){
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
		
		QYZXQueryLog log;
		try {
			log = (QYZXQueryLog)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{QYZXQueryLog.class.getName(),id,sessionFactory});
			log.setDtEndDate(new Timestamp(System.currentTimeMillis()));
			singleObjectUpdateDao.paramVoidResultExecute(new Object[] {log, sessionFactory});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public static void updateGrLogById(String id,String sessionFactory){
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		IParamVoidResultExecute singleObjectUpdateDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(GRZXQueryDetailLog.class);
		GRZXQueryLog logInfo = new GRZXQueryLog();
		logInfo.setId(id);
		detachedCriteria.add(Restrictions.eq("gRZXQueryLogId", logInfo));
		detachedCriteria.addOrder(Order.desc("dtEndDate"));
		
		GRZXQueryLog log;
		try {
			List<GRZXQueryDetailLog> gRZXQueryDetailLogs = (List<GRZXQueryDetailLog>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,sessionFactory});
			log = (GRZXQueryLog)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{GRZXQueryLog.class.getName(),id,sessionFactory});
			if(null!=gRZXQueryDetailLogs){
				log.setStrQueryStatus(gRZXQueryDetailLogs.get(0).getStrQueryStatus());
				log.setIntQueryCount(gRZXQueryDetailLogs.size());
				log.setStrException(gRZXQueryDetailLogs.get(0).getStrException());
			}else{
				log.setStrQueryStatus("");
				log.setIntQueryCount(0);
				log.setStrException("");
			}			
			singleObjectUpdateDao.paramVoidResultExecute(new Object[] {log, sessionFactory});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void LogGrDetailInfo(String gRZXQueryLogId,String strRHActNo
			,String strRHActNoPwd,String strUrl
			,Timestamp dtBeginDate,Timestamp dtEndDate
			,String strQueryStatus,String strException
			,String strRemark,String sessionFactory){
		GRZXQueryDetailLog detailLog = new GRZXQueryDetailLog();
		GRZXQueryLog log = new GRZXQueryLog();
		log.setId(gRZXQueryLogId);
		detailLog.setGRZXQueryLogId(log);
		detailLog.setStrRHActNo(strRHActNo);
		detailLog.setStrRHActNoPwd(strRHActNoPwd);
		detailLog.setStrUrl(strUrl);
		detailLog.setDtBeginDate(dtBeginDate);
		detailLog.setDtEndDate(dtEndDate);
		detailLog.setStrQueryStatus(strQueryStatus);
		detailLog.setStrException(strException);
		detailLog.setStrRemark(strRemark);
		
		IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
		try {
			singleObjectSaveDao.paramVoidResultExecute(new Object[]{detailLog,sessionFactory});			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String LogGrInfo(String id,String type,String sessionFactory,UserInfo userInfo){
		GRZXCreditReportInfo info =null;
		String logId=null;
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(GRZXQueryLog.class);
		detachedCriteria.add(Restrictions.eq("grZXCreditReportInfo.id", id));
		
		try {
			List<GRZXQueryLog> gRZXQueryLogs = (List<GRZXQueryLog>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,sessionFactory});
			if(gRZXQueryLogs.size()>0){
				logId=gRZXQueryLogs.get(0).getId();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		if(null!=logId){
			return logId;
		}
		
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		try {
			info = (GRZXCreditReportInfo)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{
						GRZXCreditReportInfo.class.getName()
						,id
						,sessionFactory});
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		GRZXQueryLog log = new GRZXQueryLog();
		log.setUserInfo(userInfo);
		log.setInstInfo(userInfo.getInstInfo());
		
		info.setId(id);
		log.setGrZXCreditReportInfo(info);
		log.setStrQueryType(type);
		log.setTmExecTime(info.getTimeVerifyDateTime());
		log.setStrCostType(info.getCategoryCode());		
		
		IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
		try {
			singleObjectSaveDao.paramVoidResultExecute(new Object[]{log,sessionFactory});			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return log.getId();
	}
}
