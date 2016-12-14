package report.service.translate;


import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;

import framework.dao.imps.OrderBySqlFormula;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;
import framework.show.ShowContext;

public class ShowTaskModelInstOrderByTableNameTranslate implements ITranslate{

	@Override
	public void Translate() throws Exception {
		
		Class<?> type = Class.forName(RequestManager.getTName());

		DetachedCriteria detachedCriteria = null;
		if(LogicParamManager.getDetachedCriteria() == null){
			detachedCriteria = DetachedCriteria.forClass(type);
		}
		else{
			detachedCriteria = LogicParamManager.getDetachedCriteria();
		}
		
		Map<String,String>  orderByTableNameMap = ShowContext.getInstance().getShowEntityMap().get("ShowTaskModelInstOrderByTableNameMap");
		
		if(orderByTableNameMap!=null){
			
			detachedCriteria.setFetchMode("reportModel_Table", org.hibernate.FetchMode.JOIN); 
			detachedCriteria.createAlias("reportModel_Table", "t1");  
			
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append(" case ");
			
			for (Map.Entry<String, String> entry : orderByTableNameMap.entrySet()) {
				stringBuilder.append(" when t1x1_.strTableName='"+entry.getKey() +"' then "+entry.getValue()+"");
			}
			stringBuilder.append(" else 100 end,this_.id ASC ");
			detachedCriteria.addOrder(OrderBySqlFormula.sqlFormula(stringBuilder.toString()));
		}
		
	}

}
