package autoETLsystem.service.procese;

import java.util.HashMap;
import java.util.Map;
import autoETLsystem.dto.AutoETL_ActivityNodeFieldVVCa;
import extend.dto.AutoETL_ViewField;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.SessionManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldCondition;
import framework.show.ShowFieldValue;
import framework.show.ShowList;
import framework.show.ShowSaveOrUpdate;

public class AutoETL_ActivityNodeFieldVVConPorcese implements IProcese{

	@SuppressWarnings("unchecked")
	public Object Procese(Object serviceResult) throws Exception {
		
		String currentLevelLevel = SessionManager.getCurrentLevel();
		String id = SessionManager.getLevelIdValue(currentLevelLevel);
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		AutoETL_ActivityNodeFieldVVCa autoETL_ActivityNodeFieldVVCase = (AutoETL_ActivityNodeFieldVVCa)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoETL_ActivityNodeFieldVVCa.class.getName(),id,null});

		if(serviceResult.getClass().equals(ShowSaveOrUpdate.class)){
			ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;
			for(ShowFieldValue showFieldValue : showSaveOrUpdate.getShowFieldValueList()){
				if(showFieldValue.getShowField().getFieldName().equals("autoRelationFieldViewID")){
					Map<String,String> tagMap = (Map<String,String>)showFieldValue.getTag();
					Map<String,String> temPTagMap = new HashMap<String,String>();
					for(AutoETL_ViewField autoETL_ViewField : autoETL_ActivityNodeFieldVVCase.getAutoETL_ActivityNodeFieldViewV().getAutoETL_ActivityNodeForCV().getAutoETL_SourceView().getAutoETL_ViewFields()){
						if(tagMap.containsKey(autoETL_ViewField.getAutoViewFieldID())){
							temPTagMap.put(autoETL_ViewField.getAutoViewFieldID(), tagMap.get(autoETL_ViewField.getAutoViewFieldID()));
						}
					}
					showFieldValue.setTag(temPTagMap);
				}	
			}
			
		}
		else if(serviceResult.getClass().equals(ShowList.class)){
			ShowList showlist = (ShowList)serviceResult;
			for(ShowFieldCondition showFieldCondition : showlist.getShowCondition()){
				if(showFieldCondition.getFieldName().equals("autoRelationFieldViewID")){
					Map<String,String> tagMap = (Map<String,String>)showFieldCondition.getTag();
					Map<String,String> temPTagMap = new HashMap<String,String>();
					for(AutoETL_ViewField autoETL_ViewField : autoETL_ActivityNodeFieldVVCase.getAutoETL_ActivityNodeFieldViewV().getAutoETL_ActivityNodeForCV().getAutoETL_SourceView().getAutoETL_ViewFields()){
						if(tagMap.containsKey(autoETL_ViewField.getAutoViewFieldID())){
							temPTagMap.put(autoETL_ViewField.getAutoViewFieldID(), tagMap.get(autoETL_ViewField.getAutoViewFieldID()));
						}
					}
					showFieldCondition.setTag(temPTagMap);
				}	
			}
		}

		return serviceResult;
	}

}
