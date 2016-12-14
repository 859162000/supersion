package dbgssystem.service.process;

import java.util.List;

import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowFieldValue;
import framework.show.ShowInstance;
import framework.show.ShowInstanceValue;
/**
 * 历史数据显示新增对数据报告日期处理
 * @author xiajieli
 *
 */
public class HistoryShowSaveForDBGSProcese  implements IProcese{

	public Object Procese(Object serviceResult) throws Exception {
		
		String tName = RequestManager.getTName();
		String showInstanceName = ShowParamManager.getShowInstanceName();
		
		ShowInstance showInstance = ReflectOperation.getShowInstance(tName, showInstanceName);
		ShowInstanceValue showInstanceValue = new ShowInstanceValue(showInstance);
		
		List<ShowFieldValue> showFieldValueList = showInstanceValue.getShowFieldValueList();
		for(ShowFieldValue showFieldValue :showFieldValueList){
			if(showFieldValue.getShowField().getFieldName().equals("extend2")){
				showFieldValue.getShowField().setSingleTag("dateFieldNoSlash");
				showFieldValue.getShowField().setFieldShowName("数据报告日期");
			}
		}
		return serviceResult;
	}

}