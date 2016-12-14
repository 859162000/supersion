package zxptsystem.service.procese;

import java.util.List;
import zxptsystem.dto.QYZXQueryLog;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.interfaces.IProcese;
import framework.show.ShowList;
import framework.show.ShowValue;

public class CreditReport2CustomerProcess implements IProcese {

	@Override
	public Object Procese(Object serviceResult) throws Exception {
		if (serviceResult instanceof ShowList) {
			ShowList showList = (ShowList) serviceResult;
			List<List<ShowValue>> listss= showList.getValueTable();
			QYZXQueryLog log = null;
			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
			for(List<ShowValue> lists:listss){
				for(ShowValue sv:lists){
					if(null!=sv.getPostFieldName()){
						if(sv.getPostFieldName().endsWith("qyZXCreditReportInfo.id")){
							sv.setValue(log.getQyZXCreditReportInfo().getStrCustomerID().getStrCustomerName());
							break;
						}
						if(sv.getPostFieldName().endsWith(".id")){
							log = (QYZXQueryLog)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{
									QYZXQueryLog.class.getName()
									,sv.getValue()
									,framework.security.SecurityContext.getInstance().getLoginInfo().getSessionFactory()});
							continue;
						}
					}					
				}
			}
			return showList;
		}
		return serviceResult;
	}
}
