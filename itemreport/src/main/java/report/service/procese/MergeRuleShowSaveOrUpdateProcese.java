package report.service.procese;

import java.util.HashMap;
import java.util.Map;

import report.dto.MergeInstance;
import report.dto.MergeRule;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.SessionManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;

public class MergeRuleShowSaveOrUpdateProcese implements IProcese{

	@SuppressWarnings("unchecked")
	public Object Procese(Object serviceResult) throws Exception {
		
		String currentLevelLevel = SessionManager.getCurrentLevel();
		String id = SessionManager.getLevelIdValue(currentLevelLevel);
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		MergeInstance mergeInstance = (MergeInstance)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{MergeInstance.class.getName(),id,null});

		ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;
		for(ShowFieldValue showFieldValue : showSaveOrUpdate.getShowFieldValueList()){
			if(showFieldValue.getShowField().getFieldName().equals("instInfo")){
				Map<String,String> tagMap = (Map<String,String>)showFieldValue.getTag();
				Map<String,String> temPTagMap = new HashMap<String,String>();
				
				for(Map.Entry<String, String> entry : tagMap.entrySet()){
					boolean isExsist = false;
					for(MergeRule mergeRule : mergeInstance.getMergeRules()){
						if(entry.getKey().equals(mergeRule.getInstInfo().getStrInstCode())){
							isExsist = true;
							break;
						}
					}
					if(!isExsist){
						temPTagMap.put(entry.getKey(), entry.getValue());
					}
				}
				showFieldValue.setTag(temPTagMap);
			}
			else if(showFieldValue.getShowField().getFieldName().equals("higherInst")){
				Map<String,String> tagMap = (Map<String,String>)showFieldValue.getTag();
				Map<String,String> temPTagMap = new HashMap<String,String>();
				for(Map.Entry<String, String> entry : tagMap.entrySet()){
					boolean isExsist = false;
					for(MergeRule mergeRule : mergeInstance.getMergeRules()){
						if(entry.getKey().equals(mergeRule.getInstInfo().getStrInstCode())){
							isExsist = true;
							break;
						}
					}
					if(isExsist){
						temPTagMap.put(entry.getKey(), entry.getValue());
					}
				}
				showFieldValue.setTag(temPTagMap);
			}
		}

		
		return serviceResult;
	}
}
