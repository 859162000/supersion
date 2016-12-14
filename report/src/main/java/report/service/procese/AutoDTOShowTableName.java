package report.service.procese;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import extend.dto.ReportModel_Table;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;

public class AutoDTOShowTableName implements IProcese{

	public Object Procese(Object serviceResult) throws Exception {
		
		String tableName=null;
		String strTName=RequestManager.getTName();
		strTName=strTName.substring(strTName.indexOf("AutoDTO_")+8, strTName.length());
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportModel_Table.class);
		detachedCriteria.add(Restrictions.eq("strTableName", strTName));
		List<ReportModel_Table> reportModel_TableList = (List<ReportModel_Table>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		if(reportModel_TableList.size()>0){
			tableName=reportModel_TableList.get(0).getStrChinaName();
		}
		
		RequestManager.setActionAutoDTOTableName(tableName);
		return serviceResult;
	}

}
