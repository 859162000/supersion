package report.service.procese;

import java.util.List;

import framework.helper.ReflectOperation;
import framework.interfaces.ApplicationManager;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowFieldValue;
import framework.show.ShowInstance;
import framework.show.ShowInstanceValue;


public class ShowReportStatusProcese implements IProcese{

	public Object Procese(Object serviceResult) throws Exception {
		
		String tName = RequestManager.getTName();
		String showInstanceName = ShowParamManager.getShowInstanceName();
		
		ShowInstance showInstance = ReflectOperation.getShowInstance(tName, showInstanceName);
		ShowInstanceValue showInstanceValue = new ShowInstanceValue(showInstance);
		
		List<ShowFieldValue> showFieldValueList = showInstanceValue.getShowFieldValueList();
		for(ShowFieldValue showFieldValue :showFieldValueList){
			if(showFieldValue.getShowField().isListVisible()){
				if(showFieldValue.getShowField().getFieldName().equals("RPTFeedbackType") || 
						showFieldValue.getShowField().getFieldName().equals("RPTSubmitStatus") || 
						showFieldValue.getShowField().getFieldName().equals("RPTVerifyType")|| 
						showFieldValue.getShowField().getFieldName().equals("RPTSendType")|| 
						showFieldValue.getShowField().getFieldName().equals("RPTCheckType") ||
						showFieldValue.getShowField().getFieldName().equals("instInfo") ||
						showFieldValue.getShowField().getFieldName().equals("operationUser")){
					showFieldValue.getShowField().setSingleTag(ApplicationManager.getSingleTagSelect());
				}
			}
		}
		
		return serviceResult;
	}

}
