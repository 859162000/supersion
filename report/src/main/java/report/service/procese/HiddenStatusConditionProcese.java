package report.service.procese;

import java.util.List;

import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldCondition;
import framework.show.ShowList;

/**
 * 通用补录界面去除审核状态，审核界面去除校验状态和提交状态
 * @author xiajieli
 *
 */
public class HiddenStatusConditionProcese implements IProcese {

	@Override
	public Object Procese(Object serviceResult) throws Exception {
		
		List<ShowFieldCondition> ShowFieldConditionList= ((ShowList)serviceResult).getShowCondition();
		
		for (int i = 0; i < ShowFieldConditionList.size(); i++) {
			
			ShowFieldCondition showFieldCondition = ShowFieldConditionList.get(i);
			
			if(RequestManager.getActionName().startsWith("ShowListLevelAUTODTO") 
					&& showFieldCondition.getFieldName().equals("RPTVerifyType")){ 
				// 补录界面去除审核状态
				ShowFieldConditionList.remove(i);
				break;
			}
			else if(RequestManager.getActionName().startsWith("SHShowListLevelAUTODTOSH") 
					&& (showFieldCondition.getFieldName().equals("RPTCheckType") 
					|| showFieldCondition.getFieldName().equals("RPTSubmitStatus"))){ 
				// 审核界面去除校验状态和提交状态
				ShowFieldConditionList.remove(i);
				i--;
			}
		}
		
		return serviceResult;
	}

}
