package zxptsystem.service.procese;

import framework.services.interfaces.IProcese;

public class GRZXGrantDataHiddenButtonProcese implements IProcese{

	public Object Procese(Object serviceResult) throws Exception {
		
		/*String currentLevelLevel = SessionManager.getCurrentLevel();
		String id = SessionManager.getLevelIdValue(currentLevelLevel);
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		GRZXCreditReportInfo gRZXCreditReportInfo = (GRZXCreditReportInfo)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{GRZXCreditReportInfo.class.getName(),id,null});
		
		String strVerifyType=null;
		
		if(gRZXCreditReportInfo!=null){
			strVerifyType=gRZXCreditReportInfo.getStrVerifyType();
		}
		*/
		
		return serviceResult;
	}

}
