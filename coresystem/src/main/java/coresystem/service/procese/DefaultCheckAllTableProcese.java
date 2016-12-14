package coresystem.service.procese;

import java.util.List;
import java.util.Map;
import coresystem.dto.RoleClassInst;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;

public class DefaultCheckAllTableProcese implements IProcese  {

	public Object Procese(Object serviceResult) throws Exception {
		RoleClassInst roleClassInst=(RoleClassInst)RequestManager.getTOject();
		if(roleClassInst!=null){
			if((roleClassInst.getRoleInfo()==null || roleClassInst.getRoleInfo().getStrRoleCode().equals("")) && roleClassInst.getInstInfo()==null){
				Map<String,String> classTag=roleClassInst.getClassTag();
				String[] strClassIdList=new String[classTag.size()];
				int i = 0;
				for (Map.Entry<String, String> entry:classTag.entrySet()) {
					strClassIdList[i]=entry.getKey();
					i++;
				}
				ShowSaveOrUpdate ShowSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;
				List<ShowFieldValue> showFieldValueList = ShowSaveOrUpdate.getShowFieldValueList();
				for (int j = 0; j < showFieldValueList.size(); j++) {
					if(showFieldValueList.get(j).getShowField().getFieldName().equals("strClassCode")){
						showFieldValueList.get(j).setFieldValue(strClassIdList);
					}
				}
				
			}
		}
		
		return serviceResult;
	}

}
