package report.service.procese;

import java.util.List;
import framework.interfaces.ApplicationManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowField;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;

public class SetStatusFieldHiddenProcess implements IProcese{

	public Object Procese(Object serviceResult) throws Exception {
		
		ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;
		List<ShowFieldValue> showFieldValueList = showSaveOrUpdate.getShowFieldValueList();
		
		for(ShowFieldValue showFieldValue : showFieldValueList){
			ShowField showField = showFieldValue.getShowField();
			if(showField.getFieldName().equals("dtDate") || 
					showField.getFieldName().equals("FOREIGNID") || 
					showField.getFieldName().equals("instInfo") || 
					showField.getFieldName().equals("lastUpdateDate") || 
					showField.getFieldName().equals("operationUser") || 
					showField.getFieldName().equals("RPTCheckType") || 
					showField.getFieldName().equals("RPTSendType") || 
					showField.getFieldName().equals("RPTSubmitStatus") || 
					showField.getFieldName().equals("RPTVerifyType")  || 
					showField.getFieldName().equals("RPTFeedbackType") ||
					showField.getFieldName().equals("extend1") ||
					showField.getFieldName().equals("extend2") ||
					showField.getFieldName().equals("extend3") ||
					showField.getFieldName().equals("extend4") ||
					showField.getFieldName().equals("extend5") ||
					showField.getFieldName().equals("XZXH")){
				showField.setSingleTag(ApplicationManager.getSingleTagHidden());
			}
			if(showField.getFieldName().equals("autoID")){
				if(showField.getFieldName()!=null || showField.getFieldName()!=""){
					showFieldValue.setReadOnly(showFieldValue.getShowField().isUpdateReadOnly());
				}
			}
		}
		
		return showSaveOrUpdate;
	}

}
