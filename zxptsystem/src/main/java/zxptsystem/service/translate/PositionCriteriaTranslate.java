package zxptsystem.service.translate;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.struts2.ServletActionContext;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import zxptsystem.dto.View_JG_JGJBXX;
import zxptsystem.dto.View_QY_JKRGKXX;

import extend.dto.SystemParam;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;
import framework.show.ShowContext;

public class PositionCriteriaTranslate implements ITranslate{
	
	private Map<String, String> ConditionMap;
	
	public void Translate() throws Exception {

		Class<?> type = Class.forName(RequestManager.getTName());
		DetachedCriteria detachedCriteria = null;
		if(LogicParamManager.getDetachedCriteria() == null){
			detachedCriteria = DetachedCriteria.forClass(type);
		}
		else{
			detachedCriteria = LogicParamManager.getDetachedCriteria();
		}
		
		String viewDTOName = View_QY_JKRGKXX.class.getName();
		IParamObjectResultExecute byIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		String id = "qyzx_strVersion";
		SystemParam qyzxVersionObj = (SystemParam)byIdDao.paramObjectResultExecute(new Object[]{"extend.dto.SystemParam", id , null});
		if (qyzxVersionObj != null && qyzxVersionObj.getStrParamValue().equals("2.2")) {
			viewDTOName = View_JG_JGJBXX.class.getName();
		}
		
		Map<String, String> positionMap = ShowContext.getInstance().getShowEntityMap().get(RequestManager.getTName()+"-Position");
		if(positionMap != null){
			for (Entry<String, String> entry : positionMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				if(key.startsWith(viewDTOName)){
					Object positionFieldValue = ServletActionContext.getContext().getSession().get(key);
					if(positionFieldValue!=null && !positionFieldValue.equals("")){
						if(value.indexOf(".")>0){
							detachedCriteria.createCriteria(value.split("\\.")[0],DetachedCriteria.LEFT_JOIN)
							.add(Restrictions.or(Restrictions.isNull(value.split("\\.")[1]), 
												Restrictions.eq(value.split("\\.")[1], positionFieldValue.toString())
												)
								);
							continue;
						}
						else{
							detachedCriteria.add(Restrictions.eq(value, positionFieldValue.toString()));
						}
					}
				}
			}
		}

		LogicParamManager.setDetachedCriteria(detachedCriteria);
	}

	public Map<String, String> getConditionMap() {
		return ConditionMap;
	}

	public void setConditionMap(Map<String, String> conditionMap) {
		ConditionMap = conditionMap;
	}
}
