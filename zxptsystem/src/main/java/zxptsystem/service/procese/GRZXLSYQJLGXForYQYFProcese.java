package zxptsystem.service.procese;

import java.util.List;

import framework.interfaces.ApplicationManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldCondition;
import framework.show.ShowList;
/**
 * 历史逾期记录更新逾期月份设置日期控件
 * @author xiajieli
 *
 */
public class GRZXLSYQJLGXForYQYFProcese implements IProcese {

	@Override
	public Object Procese(Object serviceResult) throws Exception {
		
		List<ShowFieldCondition> ShowFieldConditionList= ((ShowList)serviceResult).getShowCondition();
		
		for (int i = 0; i < ShowFieldConditionList.size(); i++) {
			
			ShowFieldCondition showFieldCondition = ShowFieldConditionList.get(i);
			
			if(showFieldCondition.getFieldName().equals("startYQYF") || showFieldCondition.getFieldName().equals("endYQYF")){ 
				ShowFieldConditionList.get(i).setSingleTag(ApplicationManager.getSingleTagDateMonth());
			}
		}
		return serviceResult;
	}
}
