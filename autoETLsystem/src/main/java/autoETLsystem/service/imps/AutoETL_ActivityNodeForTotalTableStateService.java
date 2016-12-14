package autoETLsystem.service.imps;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import extend.dto.ReportModel_Table;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.imps.BaseObjectDaoResultService;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;

/**
 * 统计并显示所有接口表，用于统计表状态界面
 * @author xiajieli
 *
 */
public class AutoETL_ActivityNodeForTotalTableStateService extends BaseObjectDaoResultService{
	
	   @Override
		public Object objectResultExecute() throws Exception {
		   ShowSaveOrUpdate processResult=(ShowSaveOrUpdate)super.objectResultExecute();
		   for(ShowFieldValue showFieldValue:processResult.getShowFieldValueList()){
			   if(showFieldValue.getShowField().getFieldName().equals("reportModel_Table")){
				   LinkedHashMap<String, String> thisMap = new LinkedHashMap<String, String>();
				   LinkedHashMap<String, String> t1 = (LinkedHashMap<String, String>)showFieldValue.getTag();
				   Iterator<Map.Entry<String, String>> iter = t1.entrySet().iterator();
					Map.Entry<String, String> tmpEntry = null;
					IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
					while (iter.hasNext()) {
						tmpEntry = iter.next();
						String TableKey = tmpEntry.getKey();
						ReportModel_Table reportModel_Table = (ReportModel_Table)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{ReportModel_Table.class.getName(),TableKey,null});
						if(reportModel_Table!=null){
							boolean flag=false;
								if(reportModel_Table.getStrAddFields().equals("1")){
									flag=true;
								}
							if(flag){
								thisMap.put(tmpEntry.getKey(), tmpEntry.getValue());
							}
						}
					}
					showFieldValue.setTag(thisMap);
				   break;
			   }
		   }
			return processResult;
		}

	}
