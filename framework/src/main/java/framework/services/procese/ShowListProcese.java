package framework.services.procese;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ognl.Ognl;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.util.GUID;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IColumn;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.TActionRule;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowContext;
import framework.show.ShowField;
import framework.show.ShowFieldCondition;
import framework.show.ShowFieldValue;
import framework.show.ShowHeaderName;
import framework.show.ShowInstance;
import framework.show.ShowList;
import framework.show.ShowOperation;
import framework.show.ShowSaveOrUpdate;
import framework.show.ShowValue;

// 组装数据到ShowList对象中
public class ShowListProcese extends ShowSaveProcese implements IProcese {

	@SuppressWarnings("unchecked")
	@Override
	public Object Procese(Object serviceResult) throws Exception {

		if (serviceResult == null) {
			return super.Procese(serviceResult);
		}

		ShowList showList = new ShowList();

		Class<?> type = Class.forName(RequestManager.getTName());
		Field primaryKeyField = ReflectOperation.getPrimaryKeyField(type);

		setPageInfo(showList);

		List<Object> tempList = (List<Object>) serviceResult;

		ShowInstance showInstance = ReflectOperation.getShowInstance(
				RequestManager.getTName(), ShowParamManager
						.getShowInstanceName());

		showList.setShowView(showInstance.isShowView());
		showList.setShowModal(showInstance.isShowModal());
		Object exp = null;
		if (!StringUtils.isBlank(showInstance.getListConditionShow()))
			exp = Ognl.parseExpression(showInstance.getListConditionShow());
		if (tempList.size() > 0) { // 数据库中取到数据

			int i = 0;
			for (Object obj : tempList) {
				String color = null;
				if (exp != null)
					color = (String) Ognl.getValue(exp, obj);

				List<ShowValue> valueRow = new ArrayList<ShowValue>();
				// 首先放入主键值

				if (primaryKeyField == null) {
					valueRow.add(new ShowValue(GUID.generateGUID()));
				} else {
					Object primaryKeyValue = ReflectOperation.getFieldValue(obj, primaryKeyField.getName());
					ShowValue pkVal = new ShowValue();
					if (primaryKeyValue == null) {
						pkVal.setValue(GUID.generateGUID());

					} else {
						pkVal.setValue(primaryKeyValue.toString());

					}
					String postFieldName = "[" + i + "]."
							+ primaryKeyField.getName();
					pkVal.setPostFieldName(postFieldName);
					valueRow.add(pkVal);
				}

				// 如果有type字段（用于区分如工作流参数设置字段，根据选的工作流类型来定的type）,则放入
				if (ShowParamManager.getTypeFieldNameAndValueMap() != null) {
					String typeFieldValue = "";
					for (Map.Entry<String, String> entry : ShowParamManager.getTypeFieldNameAndValueMap().entrySet()) {
						typeFieldValue += entry.getKey() + entry.getValue()
								+ "$$$$$";
					}
					typeFieldValue = typeFieldValue.substring(0, typeFieldValue
							.length() - 5);
					valueRow.add(new ShowValue(typeFieldValue));
				} else if (ShowParamManager.getTypeFieldName() != null
						&& ShowParamManager.getTypeFieldValueMap() != null) {
					String typeFieldValue = "";
					if (ReflectOperation.getFieldValue(obj, ShowParamManager.getTypeFieldName()) != null) {
						typeFieldValue = ReflectOperation.getFieldValue(obj,ShowParamManager.getTypeFieldName()).toString();
					}
					valueRow.add(new ShowValue(ShowParamManager.getTypeFieldValueMap().get(typeFieldValue)));
				} else if (ShowParamManager.getTypeFieldName() != null) { // 根据当前记录相应字段值，为jumpbyType准备type参数(autodto)
					Object Obj = ShowParamManager.getTypeFieldName();
					String typeFieldValue = ReflectOperation.getFieldLevelValue(obj,ShowParamManager.getTypeFieldName(), true).toString();
					String targetTName = ReflectOperation.getAutoDTOTName("sessionFactory", typeFieldValue);
					String value = "";
					if (RequestManager.getActionName().startsWith("ShowListForTree")) {
						value = ApplicationManager.getShowlistlevelautodtoprefix()+ targetTName;
					} else if (RequestManager.getActionName().startsWith(
							"CKShowListForTree")) {
						value = ApplicationManager.getCKShowlistlevelautodtoprefix()+ targetTName;
					} else {
						value = ApplicationManager.getSHShowlistlevelautodtoSHprefix()+ targetTName;
					}

					if (typeFieldValue.equals(""))
						value = "";
					valueRow.add(new ShowValue(value));
				} else { // 没有type则放入空串
					valueRow.add(new ShowValue(""));
				}

				for (ShowField showField : showInstance.getShowFieldList()) {// 生成各字段值
					if (showField.isListVisible()) {
						Field field = ReflectOperation.getReflectField(type,showField.getFieldName());
						if (field != null) {
							procFieldTag(i, obj, color, valueRow, showField,field);
						}
					}
					
				}
				i++;
				showList.getValueTable().add(valueRow);
			}
	
		}
		for (ShowField showField : showInstance.getShowFieldList()) {
			if (showField.isListVisible()) { // 生成各字段显示名
				ShowHeaderName showHeaderName = new ShowHeaderName();
				showHeaderName.setShowName(showField.getFieldShowName());
				showHeaderName.setReadOnly(showField.isUpdateReadOnly());
				if (showField.getWidth() != null) {
					showHeaderName.setWidth(showField.getWidth());
				}
				String oldPostFieldName = showField.getParamName();
				if (StringUtils.isBlank(oldPostFieldName)) {
					oldPostFieldName = showField.getFieldName();
				}
				String postFieldName = "." + oldPostFieldName;
				showHeaderName.setPostFieldName(postFieldName);
				showHeaderName.setSingleTag(showField.getSingleTag());
				showList.getShowNameList().add(showHeaderName);
			}
		}

		// 对关联SaveOrUpdate的字段进行处理,取得可选择列表
		ShowSaveOrUpdate showSaveOrUpdate = (ShowSaveOrUpdate) super.Procese(null);

		showList.setNavigationName(showSaveOrUpdate.getNavigationName());
		List<ShowFieldValue> showFieldValueList = showSaveOrUpdate.getShowFieldValueList();
		List<ShowFieldCondition> showFieldConditionList = ReflectOperation.getShowFieldConditionList(type, showFieldValueList);

		for (ShowFieldCondition conditionField : showFieldConditionList) {
			String target = conditionField.getTarget();
			String targetField = conditionField.getTargetField();
			if (!StringUtils.isBlank(target)
					&& !StringUtils.isBlank(targetField)) {
				Field fieldTg = ReflectOperation.getReflectField(type,target);
				if (fieldTg != null) {
					Field fieldTgField = ReflectOperation.getReflectField(fieldTg.getType(), targetField);
					if (!ReflectOperation.isBaseType(fieldTgField.getType())) {
						conditionField.setTag(getSimpleMap(targetField,fieldTgField.getType()));
					}
				}
			}
		}

		showList.setShowCondition(showFieldConditionList); // 条件查询

		showList.setNamespace(RequestManager.getNamespace());
		String actionname = RequestManager.getActionName().substring(0,RequestManager.getActionName().indexOf("-"));
		showList.setSelectActionName(TActionRule.getCurrentLevelActionName(actionname, RequestManager.getTName()));

		showList.setOperationList(showSaveOrUpdate.getOperationList());

		for (ShowFieldValue showFieldValue : showFieldValueList) {
			for (List<ShowValue> valueTable : showList.getValueTable()) {
				for (ShowValue showValue : valueTable) {
					String postFieldName = showValue.getPostFieldName();
					if (postFieldName != null) {
						String fieldName = postFieldName.substring(postFieldName.indexOf("].") + 2);
						if (fieldName.equals(showFieldValue.getShowField().getParamName())) {
							showValue.setTag(showFieldValue.getTag());
						}
					}
				}
			}
		}

		for (ShowFieldValue showFieldValue : showFieldValueList) {
			if (showFieldValue.getShowField().isListVisible()) {
				for (ShowHeaderName showHeaderName : showList.getShowNameList()) {
					String postFieldName = showHeaderName.getPostFieldName();

					if (postFieldName != null) {
						String fieldName = postFieldName.substring(1);
						if (fieldName.equals(showFieldValue.getShowField().getParamName())) {

							showHeaderName.setTag(showFieldValue.getTag());
						}
					}
				}
			}

		}

		// 生成数据行后面链接的属性
		procLinkedConfigParam(showList, tempList);

		processLinkedColHeader(showList);
		
		
		
		return showList;
	}

	/**
	 * @param i
	 * @param obj
	 * @param color
	 * @param valueRow
	 * @param showField
	 * @param field
	 * @throws Exception
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void procFieldTag(int i, Object obj, String color,
			List<ShowValue> valueRow, ShowField showField, Field field)
			throws Exception {
		String strValue = "";

		String tagMethodName = null; // 检查是否对应取列表方法
		if (field.isAnnotationPresent(IColumn.class)) {
			IColumn iColumn = field.getAnnotation(IColumn.class);
			if (!StringUtils.isBlank(iColumn.tagMethodName())) {
				tagMethodName = iColumn.tagMethodName();
			}
		}
		String key = null;
		if (!StringUtils.isBlank(tagMethodName)) { // 有列表方法对应,调用取得列表map
			Object value = ReflectOperation.getFieldValue(obj, field.getName());
			Method method = ReflectOperation.getReflectMethod(
								Class.forName(RequestManager.getTName()),
								tagMethodName);
			Map<String, String> tagMap = (Map<String, String>) method.invoke(Class.forName(RequestManager.getTName()));
			if (value != null) {
				strValue = tagMap.get(value.toString());
				key = value.toString();
			}
		} 
		else 
		{ // 没有列表方法
			if (showField.getFieldTargetPrimaryKeyName() == null) 
			{// 也没有对应表，直接取字段值
				Object objValue = ReflectOperation.getFieldValue(obj, field.getName());
				if (objValue != null) 
				{
					if (objValue.getClass().equals(java.sql.Date.class)
							|| objValue.getClass().equals(java.util.Date.class)) {
						strValue = TypeParse.parseString((java.util.Date) objValue,"yyyy-MM-dd");
					} 
					else 
					{
						try 
						{
							if (showField.isThousandth()) {
								if (!StringUtils.isBlank(objValue.toString())) {
									BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
									strValue = bdCurrencyValue.toString();
								}
							}
							if (showField.getReportUnitCode() != 0) 
							{
								strValue = processReportUnit(showField,strValue, objValue);
							} 
							else 
							{
								strValue = objValue.toString();
							}
						} 
						catch (Exception ex) 
						{
							strValue = objValue.toString();
						}
					}
				}
			}
			else 
			{ // 有关联表，则取关联表的关键名
				Object value = ReflectOperation.getFieldValue(obj, field.getName());
				if (value != null) {
					Object objVal = ReflectOperation.getFieldValue(value,showField.getFieldTargetPrimaryKeyName());
					if (objVal != null)
						strValue = ReflectOperation.getFieldValue(
										value,
										showField.getFieldTargetPrimaryKeyName()).toString();
				}

			}
			

			
		}
		ShowValue showValue = new ShowValue(strValue,
				showField.getWidth(), showField.isTitleField(),
				showField.isImageField(),showField.isSplitField());
		showValue.setShowColor(color);
		String oldPostFieldName = showField.getParamName();
		if (StringUtils.isBlank(oldPostFieldName)) {
			oldPostFieldName = showField.getFieldName();
		}
		String postFieldName = "[" + i + "]."
				+ oldPostFieldName;
		showValue.setPostFieldName(postFieldName);
		showValue.setSingleTag(showField.getSingleTag());
		showValue.setKey(key);
		valueRow.add(showValue);
	}

	/**
	 * @param showList
	 * @param tempList
	 * @throws Exception
	 * @throws ClassNotFoundException
	 */
	private void procLinkedConfigParam(ShowList showList, List<Object> tempList)
			throws Exception, ClassNotFoundException {
		if (ShowParamManager.getLinkedMap() != null) {
			List<List<ShowOperation>> linkedList = new ArrayList<List<ShowOperation>>();
			for (int ii = 0; ii < tempList.size(); ii++) {
				List<ShowOperation> linked = new ArrayList<ShowOperation>();
				for (Map.Entry<String, String> operation : ShowParamManager.getLinkedMap().entrySet()) {
					ShowOperation showOperation = new ShowOperation();
					showOperation.setOperationName(operation.getKey()); // 配置的操作名
					String[] operationValues = operation.getValue().split(",");
					String operationValue = operationValues[0]; // 配置的操作如：Get-ShowUpdate
					showOperation.setOperationNamespace(TActionRule.getNamespace(operationValue));
					showOperation.setOperationAction(TActionRule.getCurrentLevelOperationActionName(
									operationValue, RequestManager.getTName()));
					showOperation.setOperationType(TActionRule.getOperationType(operationValue));

					if (operationValues.length > 1) {
						if (operationValues[1] != null
								&& !operationValues[1].equals("")) {
							showOperation.setWidth(operationValues[1]);
						}
					}
					if (operationValues.length > 2) {
						showOperation.setImageUrl(operationValues[2]);
					}
					if (operationValues.length > 3) {
						String flag = "";
						if (operationValues.length == 4) {
							flag = "1";
						} else {
							flag = operationValues[4];
						}

						String strColor = "";
						String[] condtions = operationValues[3].split("\\|");
						if (flag.equals("1")) {// 明细段
							strColor = procDetailShowColor(tempList, ii,showOperation, strColor, condtions);

						}
						if (flag.equals("2")) {// 根据状态改变修改链接的颜色(当前段)
							strColor = procShowColorByCurStatus(tempList,ii, strColor, condtions);
						} 
						else if (flag.equals("3")) 
						{// 任务层（根据基础段状态改变链接颜色）

							strColor = procShowColorByTaskStatus(tempList,ii, strColor, condtions);
						}

						if (strColor.startsWith("image")) {
							showOperation.setImageUrl(strColor.substring(6));
						} else {
							showOperation.setColor(strColor);
						}
					}

					if (showOperation.getColor() == null
							|| showOperation.getColor().equals("")) {
						showOperation.setColor("#4169E1");
					}
					linked.add(showOperation);
				}
				linkedList.add(linked);
			}

			showList.setLinkedList(linkedList);
		}
	}

	/**
	 * @param tempList
	 * @param ii
	 * @param showOperation
	 * @param strColor
	 * @param condtions
	 * @return
	 * @throws Exception
	 */
	private String procDetailShowColor(List<Object> tempList, int ii,
			ShowOperation showOperation, String strColor, String[] condtions)
			throws Exception {
		List<Field> fieldList = ReflectOperation
				.getOneToManyField(tempList.get(ii)
						.getClass());
		Field actionFiled = null;
		for (Field field : fieldList) {
			if (showOperation
					.getOperationAction()
					.contains(
							ReflectOperation
									.getGenericClass(
											field
													.getGenericType())
									.getName())) {
				actionFiled = field;
				break;
			}
		}
		if (actionFiled != null) {
			Collection<?> objectValue = (Collection<?>) ReflectOperation
					.getFieldValue(tempList.get(ii),
							actionFiled.getName());

			if (objectValue.size() != 0) {
				for (int j = 0; j < condtions.length; j++) {
					boolean isMarth = true; // red*RPTCheckType:1%2@RPTSendType:2
											// 不匹配，isMarth
											// =
											// false，blue*RPTCheckType:2@RPTSendType:2
											// 匹配上后，isMarth的值不变，还是false
					String[] str = condtions[j]
							.split("\\*");
					String strLogic = str[1];
					String[] strLogicFieldValues = strLogic
							.split("@");
					for (String strLogicFieldValue : strLogicFieldValues) {
						String strLogicField = strLogicFieldValue
								.split(":")[0];
						String[] strLogicValues = strLogicFieldValue
								.split(":")[1]
								.split("%");

						boolean isFieldMarch = false;
						for (Object o : objectValue) {
							isMarth = true;
							String configValue = ReflectOperation
									.getFieldValue(o,
											strLogicField)
									.toString();
							for (String strLogicValue : strLogicValues) {
								if (configValue
										.equals(strLogicValue)) {
									isFieldMarch = true;
									break;
								}
							}
							if (isFieldMarch) {
								break;
							}
						}
						if (!isFieldMarch) {
							isMarth = false;
						}
						if (!isMarth) {
							break;
						}
					}
					if (isMarth) {
						strColor = str[0];
						break;
					}
				}
			}
		}
		return strColor;
	}

	/**
	 * @param tempList
	 * @param ii
	 * @param strColor
	 * @param condtions
	 * @return
	 * @throws Exception
	 */
	private String procShowColorByCurStatus(List<Object> tempList, int ii,
			String strColor, String[] condtions) throws Exception {
		for (int j = 0; j < condtions.length; j++) {
			boolean isConditionMarch = false;
			String[] str = condtions[j].split("\\*");
			String strLogic = str[1];
			String[] strLogicFieldValues = strLogic
					.split("@"); /*
								 * orangered*RPTCheckType
								 * :1%3 |
								 * forestgreen*RPTCheckType
								 * : 2@RPTSendType:1
								 */

			int k = 0;
			for (String strLogicFieldValue : strLogicFieldValues) {
				String strLogicField = strLogicFieldValue
						.split(":")[0];
				String[] strLogicValues = strLogicFieldValue
						.split(":")[1].split("%");

				String configValue = ReflectOperation
						.getFieldValue(
								tempList.get(ii),
								strLogicField)
						.toString();
				boolean isMarth = false;
				for (String strLogicValue : strLogicValues) {
					if (configValue
							.equals(strLogicValue)) {
						k++;
						if (strLogicFieldValues.length == k) {
							isMarth = true;
							break;
						}
					}
				}
				if (isMarth) {
					isConditionMarch = true;
					break;
				}
			}
			if (isConditionMarch) {
				strColor = str[0];
				break;
			}
		}
		return strColor;
	}

	/**
	 * @param tempList
	 * @param ii
	 * @param strColor
	 * @param condtions
	 * @return
	 * @throws Exception
	 * @throws ClassNotFoundException
	 */
	private String procShowColorByTaskStatus(List<Object> tempList, int ii,
			String strColor, String[] condtions) throws Exception,
			ClassNotFoundException {
		Object taskFlow = ReflectOperation
				.getFieldValue(tempList.get(ii),
						"taskFlow");
		Object instInfo = ReflectOperation
				.getFieldValue(tempList.get(ii),
						"instInfo");
		Object reportModel_Table = ReflectOperation
				.getFieldValue(tempList.get(ii),
						"reportModel_Table");

		
		String clsName= null;
		Map<String,String> GetNamespaceByEntityNameMap=ShowContext.getInstance().getShowEntityMap().get("GetNamespaceByEntityName");
		String strTableName=ReflectOperation.getFieldValue(reportModel_Table, "strTableName").toString();
		
		for (Entry<String, String> tableName : GetNamespaceByEntityNameMap.entrySet()) {
			if(strTableName.contains(tableName.getKey())){
				clsName=tableName.getValue() + strTableName;
				break;
			}
		}
		DetachedCriteria detachedCriteria=null;
		IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
		for(String condition :condtions){
			detachedCriteria = DetachedCriteria.forClass(Class.forName(clsName));
			detachedCriteria.add(Restrictions.eq("instInfo", instInfo));
			detachedCriteria.add(Restrictions.eq("dtDate",ReflectOperation.getFieldValue(taskFlow, "dtTaskDate")));
			
			String[] item = condition.split("\\*");
			String color = item[0];
			String[] strLogic = item[1].split("@");
			for(String logics:strLogic){
				String[] kv = logics.split(":");
				String[] v = kv[1].split("%");
				if(v.length>1){
					detachedCriteria.add(Restrictions.in(kv[0], v));
				}else{
					detachedCriteria.add(Restrictions.eq(kv[0], kv[1]));
				}				
			}
			int count=(Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			if(count>0){
				strColor=color;
				break;
			}
		}
		return strColor;
	}

	/**
	 * @param showList
	 */
	private void processLinkedColHeader(ShowList showList) {
		// 生成数据行后面链接的属性
		if (ShowParamManager.getLinkedMap() != null) {
			for (Map.Entry<String, String> operation : ShowParamManager
					.getLinkedMap().entrySet()) {
				ShowHeaderName showHeaderName = new ShowHeaderName();
				showHeaderName.setShowName(operation.getKey());

				String[] operationValues = operation.getValue().split(",");
				if (operationValues.length > 1
						&& !operationValues[1].trim().equals("")) {
					showHeaderName.setWidth(Integer
							.parseInt(operationValues[1]));
				}

				showList.getShowLinkNameList().add(showHeaderName);
			}
		}
	}

	/**
	 * @param showList
	 */
	private void setPageInfo(ShowList showList) {
		int pageSize = ShowParamManager.getPageSize();
		if (LogicParamManager.getTotalCount() != null
				&& LogicParamManager.getTotalCount() > pageSize) { // 已经显示过，后来再发起的页查看
			showList.getShowPage().setShowPage(true);
			showList.getShowPage().setTotalCount(
					LogicParamManager.getTotalCount());
			showList.getShowPage().setPageSize(pageSize);
			showList.getShowPage().setCurrentPage(
					RequestManager.getCurrentPage());
			if (LogicParamManager.getTotalCount() % pageSize == 0) {
				showList.getShowPage().setTotalPage(
						LogicParamManager.getTotalCount() / pageSize);
			} else {
				showList.getShowPage().setTotalPage(
						LogicParamManager.getTotalCount() / pageSize + 1);
			}

			if (RequestManager.getCurrentPage() != 1) {
				showList.getShowPage().setShowPrePage(true);
			}

			if (RequestManager.getCurrentPage() != showList.getShowPage()
					.getTotalPage()) {
				showList.getShowPage().setShowNextPage(true);
			}
		}
	}

	/**
	 * @param showField
	 * @param strValue
	 * @param objValue
	 * @return
	 */
	private String processReportUnit(ShowField showField, String strValue,
			Object objValue) {
		if (showField.getReportUnitCode() == 1) {// 元
			if (!StringUtils.isBlank(objValue.toString())) {
				BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
				strValue = bdCurrencyValue.divide(new BigDecimal("1"))
						.toString();
			}
		} else if (showField.getReportUnitCode() == 2) {// 十元
			if (!StringUtils.isBlank(objValue.toString())) {
				BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
				strValue = bdCurrencyValue.divide(new BigDecimal("10"))
						.toString();
			}
		} else if (showField.getReportUnitCode() == 3) {// 百元
			if (!StringUtils.isBlank(objValue.toString())) {
				BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
				strValue = bdCurrencyValue.divide(new BigDecimal("100"))
						.toString();
			}
		} else if (showField.getReportUnitCode() == 4) {// 千元
			if (!StringUtils.isBlank(objValue.toString())) {
				BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
				strValue = bdCurrencyValue.divide(new BigDecimal("1000"))
						.toString();
			}
		} else if (showField.getReportUnitCode() == 5) {// 万元
			if (!StringUtils.isBlank(objValue.toString())) {
				BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
				strValue = bdCurrencyValue.divide(new BigDecimal("10000"))
						.toString();
			}
		} else if (showField.getReportUnitCode() == 6) {// 十万
			if (!StringUtils.isBlank(objValue.toString())) {
				BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
				strValue = bdCurrencyValue.divide(new BigDecimal("100000"))
						.toString();
			}
		} else if (showField.getReportUnitCode() == 7) {// 百万
			if (!StringUtils.isBlank(objValue.toString())) {
				BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
				strValue = bdCurrencyValue.divide(new BigDecimal("1000000"))
						.toString();
			}
		} else if (showField.getReportUnitCode() == 8) {// 千万
			if (!StringUtils.isBlank(objValue.toString())) {
				BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
				strValue = bdCurrencyValue.divide(new BigDecimal("10000000"))
						.toString();
			}
		} else if (showField.getReportUnitCode() == 9) {// 亿
			if (!StringUtils.isBlank(objValue.toString())) {
				BigDecimal bdCurrencyValue = new BigDecimal(objValue.toString());
				strValue = bdCurrencyValue.divide(new BigDecimal("100000000"))
						.toString();
			}
		}
		return strValue;
	}
}