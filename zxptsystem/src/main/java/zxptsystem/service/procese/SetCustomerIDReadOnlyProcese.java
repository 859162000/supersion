package zxptsystem.service.procese;

import java.util.ArrayList;
import zxptsystem.dto.EIS_ENTCustomernBasicInfo;
import zxptsystem.dto.EIS_IdManagement;
import zxptsystem.dto.EIS_PERCustomernBasicInfo;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;

public class SetCustomerIDReadOnlyProcese implements IProcese {

	@Override
	public Object Procese(Object serviceResult) throws Exception {
		String tName = RequestManager.getTName();
		Object obj =Class.forName(tName).newInstance();
		
		ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;
		for(ShowFieldValue showFieldValue : showSaveOrUpdate.getShowFieldValueList()){
			if(showFieldValue.getShowField().getFieldName().equals("strCustomerID")){
				showFieldValue.setReadOnly(true);
				String[] names=tName.split("\\.");
				IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
				EIS_IdManagement idManagement = (EIS_IdManagement)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{
						EIS_IdManagement.class.getName()
						,names[names.length-1]
						       ,null});
				
				int index=0;
				if(null != idManagement){
					index=idManagement.getCurIndex();
				}else{
					idManagement = new EIS_IdManagement();
					idManagement.setStrTableName(names[names.length-1]);
				}
				index++;
				idManagement.setCurIndex(index);
				java.text.DecimalFormat df = new java.text.DecimalFormat("0000000");
				if(obj instanceof EIS_ENTCustomernBasicInfo){
					showFieldValue.setFieldValue("OS"+df.format(index));
				}else if(obj instanceof EIS_PERCustomernBasicInfo){
					showFieldValue.setFieldValue("OI"+df.format(index));
				}
				
				ArrayList<Object> list = new ArrayList<Object>();
				list.add(idManagement);
				IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
				singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{ list, null});
			}
		}
		return serviceResult;
	}

}
