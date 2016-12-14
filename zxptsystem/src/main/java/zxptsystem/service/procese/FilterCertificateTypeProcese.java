package zxptsystem.service.procese;

import java.util.LinkedHashMap;

import zxptsystem.dto.EIS_ENTCustomernBasicInfo;
import zxptsystem.dto.EIS_PERCustomernBasicInfo;
import framework.interfaces.SessionManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;

public class FilterCertificateTypeProcese implements IProcese {

	@SuppressWarnings("unchecked")
	@Override
	public Object Procese(Object serviceResult) throws Exception {
		Object obj=Class.forName(SessionManager.getPreviousLevelTName()).newInstance();
		
		ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;
		for(ShowFieldValue sfv :showSaveOrUpdate.getShowFieldValueList()){
			if(sfv.getShowField().getFieldName().equals("certificateType")){
				LinkedHashMap<String,String> map = (LinkedHashMap<String,String>)sfv.getTag();
				if(obj instanceof EIS_ENTCustomernBasicInfo){
					map.remove("0");map.remove("1");map.remove("2");map.remove("3");map.remove("4");map.remove("5");map.remove("6");
					map.remove("7");map.remove("8");map.remove("9");map.remove("A");map.remove("B");map.remove("C");map.remove("X");
				}else if(obj instanceof EIS_PERCustomernBasicInfo){
					map.remove("E");map.remove("F");map.remove("G");map.remove("H");map.remove("Z");
				}
			}
		}
		return serviceResult;
	}

}
