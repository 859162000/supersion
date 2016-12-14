package report.service.procese;

import java.util.HashMap;
import java.util.Map;

import extend.dto.ReportModel_Field;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.SessionManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;

public class DetailedInfoProcese implements IProcese{

	@SuppressWarnings("unchecked")
	public Object Procese(Object serviceResult) throws Exception {
		
//			String currentLevelLevel = SessionManager.getCurrentLevel();
//			String id = SessionManager.getLevelIdValue(currentLevelLevel);
//			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
//			ItemsCalculation itemsCalculation = (ItemsCalculation)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{ItemsCalculation.class.getName(),id,null});
//
//			if(serviceResult.getClass().equals(ShowSaveOrUpdate.class)){
//				ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;
//				for(ShowFieldValue showFieldValue : showSaveOrUpdate.getShowFieldValueList()){
//					if(showFieldValue.getShowField().getFieldName().equals("reportModel_Field")){
//						Map<String,String> tagMap = (Map<String,String>)showFieldValue.getTag();
//						Map<String,String> temPTagMap = new HashMap<String,String>();
//						for(ReportModel_Field autoETL_Field : itemsCalculation.getReportModel_Table().getReportModel_Fields()){
//							if(tagMap.containsKey(autoETL_Field.getAutoFieldID())){
//								temPTagMap.put(autoETL_Field.getAutoFieldID(), tagMap.get(autoETL_Field.getAutoFieldID()));
//							}
//						}
//						showFieldValue.setTag(temPTagMap);
//						break;
//					}	
//				}
//				
//			}

			return serviceResult;
		}

	}