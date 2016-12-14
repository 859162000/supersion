package zxptsystem.service.procese;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import framework.helper.ReflectOperation;
import framework.interfaces.ApplicationManager;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldCondition;
import framework.show.ShowList;

public class ShowDownLoadAddGRZXRportProcess implements IProcese{

	@Override
	public Object Procese(Object serviceResult) throws Exception {
		Object tObject=RequestManager.getTOject();
		ShowList showList=(ShowList)serviceResult;
		List<ShowFieldCondition> showFieldValueList = showList.getShowCondition();
		for (ShowFieldCondition showFieldCondition : showFieldValueList) {
			if(showFieldCondition.getFieldName().equals("strGRSJFSNY")){
				showFieldCondition.setSingleTag(ApplicationManager.getSingleTagHidden());
			}
			if(showFieldCondition.getFieldName().equals("endGRSJFSNY")||showFieldCondition.getFieldName().equals("beginGRSJFSNY")){
				SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd");
				showFieldCondition.setSingleTag("dateFieldNoSlash");
				ReflectOperation.setFieldValue(tObject, showFieldCondition.getFieldName(),dateFormat.format(new Date()));
			}
			if(showFieldCondition.getFieldName().equals("strGrReportType")){
				Map tag = (Map)showFieldCondition.getTag();
				 Map map=new HashMap();
				Object object = tag.get(1);
				Set keySet = tag.keySet();
				for (Object object2 : keySet) {
				if(object2.equals("1")){
					map.put(object2, tag.get(object2));
				}
				}
				showFieldCondition.setTag(map);
			}
		}
		showList.setShowCondition(showFieldValueList);
		return showList;
	}

}
