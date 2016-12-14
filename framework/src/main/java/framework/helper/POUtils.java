package framework.helper;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.security.DataSecurity;
import framework.security.SecurityContext;
import framework.services.interfaces.LogicParamManager;

/**
 * 项目名称：framework<br>
 * *********************************<br>
 * <P>类名称：POUtils</P>
 * *********************************<br>
 * <P>类描述：PO 实体帮助工具类</P>
 * 创建人：王川<br>
 * 创建时间：2016-8-9 上午9:20:17<br>
 * 修改人：王川<br>
 * 修改时间：2016-8-9 上午9:20:17<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@SuppressWarnings("unchecked")
public class POUtils {

	/**
	 * <p>方法描述: 获取关联的 select下拉框 数据</p>
	 *
	 * <p>方法备注:+权限过滤 </p>
	 *
	 * @param fieldName
	 * @param type
	 * @return map
	 * @throws Exception
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-8-9 上午9:20:51</p>
	 */
	public static Map<String, String> getSimpleMap(String fieldName, Class<?> type) throws Exception {
		Map<String, String> simpleMap = new LinkedHashMap<String, String>();
		Field primaryKeyField = ReflectOperation.getPrimaryKeyField(type);
		Field keyNameField = ReflectOperation.getKeyNameField(type);
		String defaultLogicDaoBeanId = "singleObjectFindByCriteriaDao";
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(type);
		//数据权限过滤
		Map<String, Map<String, String>> dataSecurityMap = SecurityContext.getInstance().getDataSecurityMap();
		if (dataSecurityMap != null) {
			for (Map.Entry<String, Map<String, String>> entry : dataSecurityMap.entrySet()) {
				if (entry.getValue().containsKey(RequestManager.getTName())) {
					String dataSecurityFieldName = entry.getValue().get(RequestManager.getTName());
					if (fieldName.equals(dataSecurityFieldName)) {
						Set<DataSecurity> dataSecuritySet = SecurityContext.getInstance().getLoginInfo().getDataSecuritySet();
						Set<String> currentDataSecurity = new HashSet<String>();
						for (DataSecurity dataSecurity : dataSecuritySet) {
							if (dataSecurity.getClassName().equals(RequestManager.getTName()) && dataSecurity.getFieldName().equals(dataSecurityFieldName)) {
								currentDataSecurity = dataSecurity.getDataSet();
								break;
							}
						}
						if (currentDataSecurity.size() == 0) {
							detachedCriteria.add(Restrictions.eq(primaryKeyField.getName(), null));
						} else {
							detachedCriteria.add(Restrictions.in(primaryKeyField.getName(), currentDataSecurity));
						}
					}
				}
			}
		}

		LogicParamManager.setDetachedCriteria(detachedCriteria);
		IParamObjectResultExecute defaultLogicDaoDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean(defaultLogicDaoBeanId);
		List<Object> objectList = (List<Object>) defaultLogicDaoDao.paramObjectResultExecute(null);

		if (keyNameField != null) {
			for (Object object : objectList) {
				Object objFieldVal = ReflectOperation.getFieldValue(object, keyNameField.getName());
				if (objFieldVal != null)
					simpleMap.put(ReflectOperation.getFieldValue(object, primaryKeyField.getName()).toString(), objFieldVal.toString());
			}
		} else {
			for (Object object : objectList) {
				if (object != null)
					simpleMap.put(ReflectOperation.getFieldValue(object, primaryKeyField.getName()).toString(), ReflectOperation.getFieldValue(object, primaryKeyField.getName()).toString());
			}
		}
		return simpleMap;
	}
}
