package report.service.procese;

import java.util.List;
import report.dto.analyse.Rep_ItemWarningResult;
import framework.services.interfaces.IProcese;
import framework.show.ShowList;
import framework.show.ShowValue;

public class ShowListWarningRsProcess implements IProcese{
	@Override
	public Object Procese(Object serviceResult) throws Exception {
		ShowList showList = (ShowList)serviceResult;
		if(null != showList){
			List<List<ShowValue>> showVList = showList.getValueTable();
			for(List<ShowValue> vList : showVList){
				for(ShowValue v: vList){
					if(null != v.getPostFieldName() && v.getPostFieldName().indexOf("warningVal") != -1){
						if(("0").equals(v.getValue())){
							v.setValue("正常");
							v.setShowColor("green");
						}
						if(("1").equals(v.getValue())){
							v.setValue("预警");
							v.setShowColor("yellow");
						}
						if(("2").equals(v.getValue())){
							v.setValue("高危");
							v.setShowColor("red");
						}
						break;
					}
				}
			}
		}
		return serviceResult;
	}

}
