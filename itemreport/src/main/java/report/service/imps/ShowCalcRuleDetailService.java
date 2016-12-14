package report.service.imps;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import extend.dto.ReportModel_Field;
import extend.dto.ReportModel_Table;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseDTService;

public class ShowCalcRuleDetailService extends BaseDTService {

	@Override
	public void initSuccessResult() throws Exception {
		String tableName = RequestManager.getLevelIdValue();		
		Map<String,String> calcuFunName=new LinkedHashMap<String,String>();
		calcuFunName.put("SUM", "SUM");
		calcuFunName.put("AVG", "AVG");
		calcuFunName.put("MAX", "MAX");
		calcuFunName.put("MIN", "MIN");
		calcuFunName.put("ABS", "ABS");
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportModel_Table.class);
		detachedCriteria.add(Restrictions.eq("strTableName", tableName));
		
		IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");		
		List<ReportModel_Table> tableList = (List<ReportModel_Table>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("CalcuFunc", calcuFunName);
		
		if(tableList.size()>0){
			detachedCriteria = DetachedCriteria.forClass(ReportModel_Field.class);
			detachedCriteria.add(Restrictions.eq("reportModel_Table", tableList.get(0)));			
	    	List<ReportModel_Field> objectListItemInfo = (List<ReportModel_Field>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});	    	   	
	    	result.put("objectListItemInfo", objectListItemInfo);
		}
		
    	this.setServiceResult(result);
	}

}
