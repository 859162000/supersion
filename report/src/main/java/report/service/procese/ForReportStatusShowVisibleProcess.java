package report.service.procese;

import java.util.List;
import framework.interfaces.ApplicationManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;

public class ForReportStatusShowVisibleProcess implements IProcese{

	public Object Procese(Object serviceResult) throws Exception {
		
		ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;
		List<ShowFieldValue> showFieldValueList = showSaveOrUpdate.getShowFieldValueList();

		for(ShowFieldValue showFieldValue :showFieldValueList){
			if(showFieldValue.getShowField().getFieldName().equals("RPTFeedbackType") || 
					showFieldValue.getShowField().getFieldName().equals("RPTSubmitStatus") || 
					showFieldValue.getShowField().getFieldName().equals("RPTVerifyType")|| 
					showFieldValue.getShowField().getFieldName().equals("RPTSendType")|| 
					showFieldValue.getShowField().getFieldName().equals("RPTCheckType")){
				    showFieldValue.getShowField().setSingleTag(ApplicationManager.getSingleTagSelect());
				    showFieldValue.setReadOnly(true);
			}
			else if(					
					showFieldValue.getShowField().getFieldName().equals("instInfo") ||
					showFieldValue.getShowField().getFieldName().equals("operationUser") ||
					showFieldValue.getShowField().getFieldName().equals("dtDate") || 
					showFieldValue.getShowField().getFieldName().equals("FOREIGNID") ||
					showFieldValue.getShowField().getFieldName().equals("lastUpdateDate") || 
					showFieldValue.getShowField().getFieldName().equals("XZXH") ||
					showFieldValue.getShowField().getFieldName().equals("extend1") ||
					showFieldValue.getShowField().getFieldName().equals("extend2") ||
					showFieldValue.getShowField().getFieldName().equals("extend3") ||
					showFieldValue.getShowField().getFieldName().equals("extend4") ||
					showFieldValue.getShowField().getFieldName().equals("extend5") )
			{
				showFieldValue.getShowField().setSingleTag(ApplicationManager.getSingleTagHidden());
			}
		}
		return serviceResult;
	}

}
