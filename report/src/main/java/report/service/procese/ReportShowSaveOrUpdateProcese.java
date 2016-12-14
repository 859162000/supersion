package report.service.procese;

import java.util.List;
import framework.interfaces.ApplicationManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowField;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;

public class ReportShowSaveOrUpdateProcese  implements IProcese{
	
	public Object Procese(Object serviceResult) throws Exception {
		
		ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;

		List<ShowFieldValue> showFieldValueList = showSaveOrUpdate.getShowFieldValueList();
		
		for(ShowFieldValue showFieldValue : showFieldValueList){
			ShowField showField = showFieldValue.getShowField();
			if(showField.getFieldName().equals("RPTCheckType") || showField.getFieldName().equals("FOREIGNID")){
				showField.setSingleTag(ApplicationManager.getSingleTagHidden());
			}
		}
		return showSaveOrUpdate;
	}

}
