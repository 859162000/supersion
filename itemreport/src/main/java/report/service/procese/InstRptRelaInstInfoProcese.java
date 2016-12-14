package report.service.procese;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldCondition;
import framework.show.ShowList;

/**
 * 
 * 将idList字段转换成selectField字段
 * 此类主要是为了避免界面在展示时，条件展示不完整以及报错的情况
 * 注：此类只用于"机构表单关系"的展示所用
 */
public class InstRptRelaInstInfoProcese implements IProcese {

	private List<Object> listObj = null;
	private Map<String, String> instInfoMap = new HashMap<String, String>();
	private List<ShowFieldCondition> showCondition = null;
	private IParamObjectResultExecute objectResultExecute = null;
	
	@SuppressWarnings("unchecked")
	@Override
	public Object Procese(Object serviceResult) throws Exception {
		if(ShowList.class.isInstance(serviceResult)){
			showCondition = (List<ShowFieldCondition>) ReflectOperation.getFieldValue(serviceResult, "showCondition");
			
			objectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindAllDao");
			listObj = (List<Object>)objectResultExecute.paramObjectResultExecute(new Object[]{"coresystem.dto.InstInfo", null});
			if(listObj != null && listObj.size() > 0){
				String key = null;
				String value = null;
				for(Object obj : listObj){
					key = ReflectOperation.getFieldValue(obj, "strInstCode").toString();
					value = ReflectOperation.getFieldValue(obj, "strInstName").toString();
					instInfoMap.put(key, value);
				}
			}
			
			if(showCondition != null && showCondition.size() > 0){
				String fieldName = null;
				for(ShowFieldCondition condition : showCondition){
					fieldName = ReflectOperation.getFieldValue(condition, "fieldName").toString();
					if(fieldName.equals("instInfo")){
						condition.setParamName("instInfo.strInstCode");
						condition.setSingleTag("selectField");
						condition.setTag(instInfoMap);
						break;
					}
				}
			}
		}
		return serviceResult;
	}

}
