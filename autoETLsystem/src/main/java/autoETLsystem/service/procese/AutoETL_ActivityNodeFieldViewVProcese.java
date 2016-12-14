package autoETLsystem.service.procese;

import java.util.HashMap;
import java.util.Map;
import autoETLsystem.dto.AutoETL_ActivityNodeForCV;
import extend.dto.AutoETL_ViewField;
import extend.dto.ReportModel_Field;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.SessionManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldCondition;
import framework.show.ShowFieldValue;
import framework.show.ShowList;
import framework.show.ShowSaveOrUpdate;

public class AutoETL_ActivityNodeFieldViewVProcese implements IProcese{

	@SuppressWarnings("unchecked")
	public Object Procese(Object serviceResult) throws Exception {
		
		String currentLevelLevel = SessionManager.getCurrentLevel();
		String id = SessionManager.getLevelIdValue(currentLevelLevel);
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		AutoETL_ActivityNodeForCV autoETL_ActivityNodeForCV = (AutoETL_ActivityNodeForCV)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoETL_ActivityNodeForCV.class.getName(),id,null});

		if(serviceResult.getClass().equals(ShowSaveOrUpdate.class)){
			ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;
			for(ShowFieldValue showFieldValue : showSaveOrUpdate.getShowFieldValueList()){
				if(showFieldValue.getShowField().getFieldName().equals("autoETL_SourceViewField")){
					Map<String,String> tagMap = (Map<String,String>)showFieldValue.getTag();
					Map<String,String> temPTagMap = new HashMap<String,String>();
					for(AutoETL_ViewField autoETL_ViewField : autoETL_ActivityNodeForCV.getAutoETL_SourceView().getAutoETL_ViewFields()){
						if(tagMap.containsKey(autoETL_ViewField.getAutoViewFieldID())){
							temPTagMap.put(autoETL_ViewField.getAutoViewFieldID(), tagMap.get(autoETL_ViewField.getAutoViewFieldID()));
						}
					}
					showFieldValue.setTag(temPTagMap);
				}	
				if(showFieldValue.getShowField().getFieldName().equals("reportModel_TagetField")){
					Map<String,String> tagMap = (Map<String,String>)showFieldValue.getTag();
					Map<String,String> temPTagMap = new HashMap<String,String>();
					for(ReportModel_Field autoETL_Field : autoETL_ActivityNodeForCV.getAutoETL_TargetTable().getReportModel_Fields()){
						if(tagMap.containsKey(autoETL_Field.getAutoFieldID())){
							temPTagMap.put(autoETL_Field.getAutoFieldID(), tagMap.get(autoETL_Field.getAutoFieldID()));
						}
					}
					showFieldValue.setTag(temPTagMap);
				}	
			}
			
		}
		else if(serviceResult.getClass().equals(ShowList.class)){
			ShowList showlist = (ShowList)serviceResult;
			for(ShowFieldCondition showFieldCondition : showlist.getShowCondition()){
				if(showFieldCondition.getFieldName().equals("autoETL_SourceViewField")){
					Map<String,String> tagMap = (Map<String,String>)showFieldCondition.getTag();
					Map<String,String> temPTagMap = new HashMap<String,String>();
					for(AutoETL_ViewField autoETL_ViewField : autoETL_ActivityNodeForCV.getAutoETL_SourceView().getAutoETL_ViewFields()){
						if(tagMap.containsKey(autoETL_ViewField.getAutoViewFieldID())){
							temPTagMap.put(autoETL_ViewField.getAutoViewFieldID(), tagMap.get(autoETL_ViewField.getAutoViewFieldID()));
						}
					}
					showFieldCondition.setTag(temPTagMap);
				}	
				if(showFieldCondition.getFieldName().equals("reportModel_TagetField")){
					Map<String,String> tagMap = (Map<String,String>)showFieldCondition.getTag();
					Map<String,String> temPTagMap = new HashMap<String,String>();
					for(ReportModel_Field autoETL_Field : autoETL_ActivityNodeForCV.getAutoETL_TargetTable().getReportModel_Fields()){
						if(tagMap.containsKey(autoETL_Field.getAutoFieldID())){
							temPTagMap.put(autoETL_Field.getAutoFieldID(), tagMap.get(autoETL_Field.getAutoFieldID()));
						}
					}
					showFieldCondition.setTag(temPTagMap);
				}	
			}
		}

		return serviceResult;
	}

}
