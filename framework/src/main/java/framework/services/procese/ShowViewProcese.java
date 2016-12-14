package framework.services.procese;

import java.util.List;

import framework.services.interfaces.IProcese;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;

public class ShowViewProcese implements IProcese{

	public Object Procese(Object serviceResult) throws Exception {

		ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;
		List<ShowFieldValue> showFieldValueList = showSaveOrUpdate.getShowFieldValueList();
		
		for(ShowFieldValue showFieldValue : showFieldValueList){
			showFieldValue.setReadOnly(true);
		}
		
		return showSaveOrUpdate;
	}

}

