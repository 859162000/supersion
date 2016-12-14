package report.service.procese;

import java.util.HashMap;
import java.util.Map;

import extend.dto.ReportModel_Field;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.SessionManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldCondition;
import framework.show.ShowFieldValue;
import framework.show.ShowList;
import framework.show.ShowSaveOrUpdate;

public class DetailedFieldInfoProcese implements IProcese{

	@SuppressWarnings("unchecked")
	public Object Procese(Object serviceResult) throws Exception {
		
//		String currentLevelLevel = SessionManager.getCurrentLevel();
//		String id = SessionManager.getLevelIdValue(currentLevelLevel);
//		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
//		DetailedInfo detailedInfo = (DetailedInfo)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{DetailedInfo.class.getName(),id,null});
//
//		if(serviceResult.getClass().equals(ShowSaveOrUpdate.class)){
//			ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;
//			for(ShowFieldValue showFieldValue : showSaveOrUpdate.getShowFieldValueList()){
//				if(showFieldValue.getShowField().getFieldName().equals("autoRelationFieldID")){
//					Map<String,String> tagMap = (Map<String,String>)showFieldValue.getTag();
//					Map<String,String> temPTagMap = new HashMap<String,String>();
//					for(ReportModel_Field autoETL_Field : detailedInfo.getItemsCalculation().getReportModel_Table().getReportModel_Fields()){
//						if(tagMap.containsKey(autoETL_Field.getAutoFieldID())){
//							temPTagMap.put(autoETL_Field.getAutoFieldID(), tagMap.get(autoETL_Field.getAutoFieldID()));
//						}
//					}
//					showFieldValue.setTag(temPTagMap);
//				}	
//			}
//		}
//
//		else if(serviceResult.getClass().equals(ShowList.class)){
//			ShowList showlist = (ShowList)serviceResult;
//			for(ShowFieldCondition showFieldCondition : showlist.getShowCondition()){
//				if(showFieldCondition.getFieldName().equals("autoRelationFieldID")){
//					Map<String,String> tagMap = (Map<String,String>)showFieldCondition.getTag();
//					Map<String,String> temPTagMap = new HashMap<String,String>();
//					for(ReportModel_Field autoETL_Field : detailedInfo.getItemsCalculation().getReportModel_Table().getReportModel_Fields()){
//						if(tagMap.containsKey(autoETL_Field.getAutoFieldID())){
//							temPTagMap.put(autoETL_Field.getAutoFieldID(), tagMap.get(autoETL_Field.getAutoFieldID()));
//						}
//					}
//					showFieldCondition.setTag(temPTagMap);
//					
//				}	
//			}
//		}

		return serviceResult;
	}

}
