package autoETLsystem.service.procese;

import java.util.LinkedHashMap;
import extend.dto.ReportModel_Table;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.interfaces.IProcese;
import framework.show.ShowContext;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;

public class ForFieldTypeProcese implements IProcese{

	@SuppressWarnings("unchecked")
	public Object Procese(Object serviceResult) throws Exception {
//		LinkedHashMap map = null;
//		String tableId="";
//		ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;
//		for(ShowFieldValue showFieldValue : showSaveOrUpdate.getShowFieldValueList()){			
//			if(showFieldValue.getShowField().getFieldName().equals("reportModel_Table")){
//				tableId=showFieldValue.getFieldValue().toString();
//				break;
//			}
//		}
//		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
//		ReportModel_Table object = (ReportModel_Table)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{ReportModel_Table.class.getName(),tableId,null});
//		String databaseType=object.getDataSource().getStrDatabaseType();
//		if(databaseType.equals("1")){
//			map=(LinkedHashMap)ShowContext.getInstance().getShowEntityMap().get("fieldTypeMSSql");
//		}else if(databaseType.equals("2")){
//			map=(LinkedHashMap)ShowContext.getInstance().getShowEntityMap().get("fieldTypeOracle");
//		}else if(databaseType.equals("3")){
//			map=(LinkedHashMap)ShowContext.getInstance().getShowEntityMap().get("fieldTypeDB2");
//		}else if(databaseType.equals("4")){
//			map=(LinkedHashMap)ShowContext.getInstance().getShowEntityMap().get("fieldTypeMySql");
//		}
//		
//		for(ShowFieldValue showFieldValue : showSaveOrUpdate.getShowFieldValueList()){
//			if(showFieldValue.getShowField().getFieldName().equals("strFieldType")){
//				showFieldValue.setTag(map);
//				break;
//			}			
//		}
		return serviceResult;
	}

}
