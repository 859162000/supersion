package report.service.procese;

import java.util.ArrayList;
import java.util.List;

import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldCondition;
import framework.show.ShowList;

public class IsShowOrHiddenForStatusField implements IProcese {

	public Object Procese(Object serviceResult) throws Exception {
		ShowList showlist = (ShowList)serviceResult;
		List<ShowFieldCondition> showFieldConditionList=new ArrayList<ShowFieldCondition>();
		for(ShowFieldCondition showFieldCondition : showlist.getShowCondition()){
			if(RequestManager.getActionName().contains("SHShowListLevelAUTODTOSHMX")){
				if(!showFieldCondition.getFieldName().equals("RPTCheckType")){
					showFieldConditionList.add(showFieldCondition);
				}
			}
			else if(RequestManager.getActionName().contains("SHShowListLevelAUTODTOSH")){
				if(!showFieldCondition.getFieldName().equals("RPTSubmitStatus") && !showFieldCondition.getFieldName().equals("RPTCheckType")){
					showFieldConditionList.add(showFieldCondition);
				}
			}else if(RequestManager.getActionName().contains("SHReviewShowListForTree")){
				if(!showFieldCondition.getFieldName().equals("strCheckState")){
					showFieldConditionList.add(showFieldCondition);
				}
			}
		}
		showlist.setShowCondition(showFieldConditionList);
		return showlist;
	}

}
