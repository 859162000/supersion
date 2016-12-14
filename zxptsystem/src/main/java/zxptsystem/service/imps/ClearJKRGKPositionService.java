package zxptsystem.service.imps;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.imps.BaseService;
import framework.services.interfaces.LogicParamManager;

public class ClearJKRGKPositionService extends BaseService{
	
	@Override
	public void initSuccessResult() throws Exception {
		Object object = RequestManager.getTOject();
		
		// 清空定位信息
		List<Field> fieldList= ReflectOperation.getColumnFieldList(object.getClass());
		for (int i = 0; i < fieldList.size(); i++) {
			String fieldName = fieldList.get(i).getName();
			ServletActionContext.getContext().getSession().remove(object.getClass().getName()+"."+fieldName);
		}
		
		// 清空当前查询条件
		if(LogicParamManager.getDetachedCriteria() != null){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(RequestManager.getTName()));
			LogicParamManager.setDetachedCriteria(detachedCriteria);
		}

		// 清空查询框信息
		SessionManager.setTCondition(null, RequestManager.getTName());

		// 将当前定位设置为false
		ServletActionContext.getContext().getSession().put(object.getClass().getName()+".JKRGKPosition", "false");
	}
}
