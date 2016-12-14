package framework.services.procese;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import framework.services.interfaces.IProcese;
import framework.services.interfaces.LogicParamManager;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;

// 添加默认值，用于在界面显示默认值
public class DefaultValueProcese  implements IProcese{

	public Object Procese(Object serviceResult) throws Exception {
		
		ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate)serviceResult;

		if(LogicParamManager.getDefaultValueMap() != null){
			List<ShowFieldValue> showFieldValueList = showSaveOrUpdate.getShowFieldValueList();

			for(ShowFieldValue showFieldValue : showFieldValueList){
				for(Map.Entry<String, String> defaultValue : LogicParamManager.getDefaultValueMap().entrySet()){
					if(showFieldValue.getShowField().getFieldName().equals(defaultValue.getKey())){
						
						String value = defaultValue.getValue();
						if(value.equals("SystemDate")){ // 指定为系统时间时
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
							value = simpleDateFormat.format(new Date()); // 当前时间作为默认值
						}
						showFieldValue.setFieldValue(value);
						break;
					}
				}
			}
		}

		return showSaveOrUpdate;
	}
}

