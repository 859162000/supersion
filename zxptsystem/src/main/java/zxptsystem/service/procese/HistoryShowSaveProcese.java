package zxptsystem.service.procese;

import java.util.List;

import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowFieldValue;
import framework.show.ShowInstance;
import framework.show.ShowInstanceValue;

public class HistoryShowSaveProcese implements IProcese{

	public Object Procese(Object serviceResult) throws Exception {
		
		String tName = RequestManager.getTName();
		String showInstanceName = ShowParamManager.getShowInstanceName();
		
		ShowInstance showInstance = ReflectOperation.getShowInstance(tName, showInstanceName);
		ShowInstanceValue showInstanceValue = new ShowInstanceValue(showInstance);
		
		List<ShowFieldValue> showFieldValueList = showInstanceValue.getShowFieldValueList();
		for(ShowFieldValue showFieldValue :showFieldValueList){
			if(showFieldValue.getShowField().getFieldName().equals("extend2")){
				showFieldValue.getShowField().setSingleTag("dateFieldNoSlash");
				showFieldValue.getShowField().setFieldShowName("业务发生日期");
			}
		}
		return serviceResult;
	}

}
