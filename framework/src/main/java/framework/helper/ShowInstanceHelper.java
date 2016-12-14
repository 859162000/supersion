package framework.helper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.interfaces.ApplicationManager;
import framework.interfaces.IColumn;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.TActionRule;
import framework.security.DataSecurity;
import framework.security.SecurityContext;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.ShowParamManager;
import framework.services.interfaces.MessageResult.ErrorField;
import framework.show.ShowField;
import framework.show.ShowFieldValue;
import framework.show.ShowInstance;
import framework.show.ShowInstanceValue;
import framework.show.ShowOperation;
import framework.show.ShowSaveOrUpdate;

/**
 * 项目名称：framework<br>
 * *********************************<br>
 * <P>类名称：ShowSaveHelper</P>
 * *********************************<br>
 * <P>类描述：显示界面帮助工具类</P>
 * 创建人：王川<br>
 * 创建时间：2016-8-9 上午11:18:04<br>
 * 修改人：王川<br>
 * 修改时间：2016-8-9 上午11:18:04<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@SuppressWarnings("unchecked")
public class ShowInstanceHelper {

	/**
	 * <p>方法描述:装载结果集数据 </p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param serviceResult
	 * @param showSaveOrUpdate
	 * @throws Exception
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-8-9 上午11:08:45</p>
	 */
	public static void parseReulstShowData(Object serviceResult, ShowSaveOrUpdate showSaveOrUpdate) throws Exception {
		Class<?> type = serviceResult.getClass();
		List<ShowFieldValue> showFieldValueList = showSaveOrUpdate.getShowFieldValueList();

		for (ShowFieldValue showFieldValue : showFieldValueList) {
			ShowField showField = showFieldValue.getShowField();

			if (LogicParamManager.getServiceResult() == null) {
				showFieldValue.setReadOnly(showField.isUpdateReadOnly());
			}

			Field field = ReflectOperation.getReflectField(type, showField.getFieldName());
			if (field != null) {
				Object value = ReflectOperation.getFieldValue(serviceResult, field.getName());
				if (value != null) {
					if (ReflectOperation.isBaseType(value)) {
						if (value.getClass().equals(java.util.Date.class) || value.getClass().equals(java.sql.Date.class)) {
							showFieldValue.setFieldValue(TypeParse.parseString((java.util.Date) value, "yyyy-MM-dd"));
						} else {
							try {
								if (showField.isThousandth()) {
									if (!StringUtils.isBlank(value.toString())) {
										BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
										value = bdCurrencyValue.toString();
									}
								}
								if (showField.getReportUnitCode() != 0) {
									if (showField.getReportUnitCode() == 1) {//元
										if (!StringUtils.isBlank(value.toString())) {
											BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
											value = bdCurrencyValue.divide(new BigDecimal("1"));
										}
									} else if (showField.getReportUnitCode() == 2) {//十元
										if (!StringUtils.isBlank(value.toString())) {
											BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
											value = bdCurrencyValue.divide(new BigDecimal("10"));
										}
									} else if (showField.getReportUnitCode() == 3) {//百元
										if (!StringUtils.isBlank(value.toString())) {
											BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
											value = bdCurrencyValue.divide(new BigDecimal("100"));
										}
									} else if (showField.getReportUnitCode() == 4) {//千元
										if (!StringUtils.isBlank(value.toString())) {
											BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
											value = bdCurrencyValue.divide(new BigDecimal("1000"));
										}
									} else if (showField.getReportUnitCode() == 5) {//万元
										if (!StringUtils.isBlank(value.toString())) {
											BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
											value = bdCurrencyValue.divide(new BigDecimal("10000"));
										}
									} else if (showField.getReportUnitCode() == 6) {//十万
										if (!StringUtils.isBlank(value.toString())) {
											BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
											value = bdCurrencyValue.divide(new BigDecimal("100000"));
										}
									} else if (showField.getReportUnitCode() == 7) {//百万
										if (!StringUtils.isBlank(value.toString())) {
											BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
											value = bdCurrencyValue.divide(new BigDecimal("1000000"));
										}
									} else if (showField.getReportUnitCode() == 8) {//千万
										if (!StringUtils.isBlank(value.toString())) {
											BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
											value = bdCurrencyValue.divide(new BigDecimal("10000000"));
										}
									} else if (showField.getReportUnitCode() == 9) {//亿
										if (!StringUtils.isBlank(value.toString())) {
											BigDecimal bdCurrencyValue = new BigDecimal(value.toString());
											value = bdCurrencyValue.divide(new BigDecimal("100000000"));
										}
									}
								}
								showFieldValue.setFieldValue(value.toString());
							} catch (Exception ex) {
								showFieldValue.setFieldValue(value.toString());
							}

						}
					} else {
						if (showField.getSingleTag().equals(ApplicationManager.getSingleTagMultipleSelect())) {
							Field idListField = ReflectOperation.getIdListField(serviceResult.getClass().getName(), field);
							IColumn idListFieldColumn = idListField.getAnnotation(IColumn.class);
							Class<?> tType = ReflectOperation.getGenericClass(field.getGenericType());
							Field targetField = ReflectOperation.getFieldByName(tType, idListFieldColumn.mappedBy());
							Field PrimaryKeyField = ReflectOperation.getPrimaryKeyField(targetField.getType());
							Set<Object> objectSet = (Set<Object>) value;
							String[] values = new String[objectSet.size()];
							int i = 0;
							for (Object object : objectSet) {
								Object value1 = ReflectOperation.getFieldValue(object, targetField.getName());
								Object value2 = ReflectOperation.getFieldValue(value1, PrimaryKeyField.getName());
								values[i] = value2.toString();
								i++;
							}
							showFieldValue.setFieldValue(values);
						} else if (showField.getSingleTag().equals(ApplicationManager.getSingleTagSelect()) || showField.getSingleTag().equals(ApplicationManager.getSingleTagHidden()) || showField.getSingleTag().equals(ApplicationManager.getModelField())) {
							Object value1 = null;
							if (showField.getFieldTargetPrimaryKey() != null) {
								value1 = ReflectOperation.getFieldValue(value, showField.getFieldTargetPrimaryKey());
							}
							if (value1 != null) {
								showFieldValue.setFieldValue(value1.toString());
							}
						}
					}
				}
			}
		}

		showSaveOrUpdate.setId(ReflectOperation.getPrimaryKeyValue(serviceResult).toString());

		if (LogicParamManager.getServiceResult() != null) {
			Object paramServiceResult = LogicParamManager.getServiceResult();
			if (paramServiceResult.getClass().equals(MessageResult.class)) {
				MessageResult messageResult = (MessageResult) paramServiceResult;

				for (ErrorField errorField : messageResult.getErrorFieldList()) {
					for (ShowFieldValue showFieldValue : showFieldValueList) {
						if (errorField.getFieldName() != null && showFieldValue.getShowField().getFieldName() != null) {
							if (errorField.getFieldName().equals(showFieldValue.getShowField().getFieldName())) {
								showFieldValue.setShowColor(errorField.getColor());

								String message = errorField.getMessage();
								if (message.startsWith(showFieldValue.getShowField().getFieldShowName())) {
									if (message.length() >= showFieldValue.getShowField().getFieldShowName().length() + 2) {
										//message = message.substring(showFieldValue.getShowField().getFieldShowName().length() + 1);
										message = message.replaceFirst(showFieldValue.getShowField().getFieldShowName(), "");
										message = message.replace(":", "").replace("：", "");
										if (message.getBytes().length == 24) {
											message = "." + message;
										}
									}
								}
								showFieldValue.setCheckMessage(message);
								break;
							}
						}

					}
				}
			}
		}
	}
	
	
	/**
	 * <p>方法描述:根据className和 showInstanceName获取界面显示信息</p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param className
	 * @param showInstanceName
	 * @return
	 * @throws Exception
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-8-9 下午2:10:23</p>
	 */
	public static ShowSaveOrUpdate getShowClassFromInstance(String className,String showInstanceName) throws Exception {
		ShowInstance showInstance = ReflectOperation.getShowInstance(className, showInstanceName);
		return getShowClassFromInstance(className, showInstance);
	}

	/**
	 * <p>方法描述: 根据class 的showinstace 配置获取界面显示信息</p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param className
	 * @param showInstance
	 * @return
	 * @throws Exception
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-8-9 上午11:28:32</p>
	 */
	public static ShowSaveOrUpdate getShowClassFromInstance(String className, ShowInstance showInstance) throws Exception {
		ShowInstanceValue showInstanceValue = new ShowInstanceValue(showInstance);
		// 处理关联列
		List<Field> joinColumnFieldList = ReflectOperation.getJoinColumnFieldList(className);
		for (Field joinField : joinColumnFieldList) {
			Map<String, String> simpleMap = getSimpleMap(joinField.getName(), joinField.getType());
			for (ShowFieldValue showFieldValue : showInstanceValue.getShowFieldValueList()) {
				ShowField showField = showFieldValue.getShowField();
				if (joinField.getName().equals(showField.getFieldName())) {
					showFieldValue.setTag(simpleMap);
					break;
				}
			}
		}

		// 带列表的列
		List<Field> idListTargetFieldList = ReflectOperation.getIdListTargetFieldList(className);
		for (Field idListTargetField : idListTargetFieldList) {
			if (ReflectOperation.isBaseType(idListTargetField.getType())) {
				continue;
			}
			Field idListField = ReflectOperation.getIdListField(className, idListTargetField);
			IColumn idListFieldColumn = idListField.getAnnotation(IColumn.class);
			Field targetField = null;
			if (ReflectOperation.isCollectionType(idListTargetField.getType().getName())) {
				Class<?> type = ReflectOperation.getGenericClass(idListTargetField.getGenericType());
				targetField = ReflectOperation.getFieldByName(type, idListFieldColumn.mappedBy());
			} else {
				targetField = ReflectOperation.getFieldByName(className, idListFieldColumn.target());
			}
			Map<String, String> simpleMap = getSimpleMap(idListTargetField.getName(), targetField.getType());
			for (ShowFieldValue showFieldValue : showInstanceValue.getShowFieldValueList()) {
				ShowField showField = showFieldValue.getShowField();
				if (idListField.getName().equals(showField.getFieldTargetPrimaryKey())) {
					showFieldValue.setTag(simpleMap);
					break;
				}
			}
		}

		// 带处理函数的列
		List<Field> haveMethodNameList = ReflectOperation.getHaveMethodNameList(className);
		for (Field haveMethodName : haveMethodNameList) {
			IColumn iColumn = haveMethodName.getAnnotation(IColumn.class);
			String methodName = iColumn.tagMethodName();
			for (ShowFieldValue showFieldValue : showInstanceValue.getShowFieldValueList()) {
				ShowField showField = showFieldValue.getShowField();
				if (haveMethodName.getName().equals(showField.getFieldName())) {
					Method method = ReflectOperation.getReflectMethod(Class.forName(className), methodName);
					showFieldValue.setTag(method.invoke(Class.forName(className)));
					break;
				}
			}
		}

		RequestManager.setTName(className);
		ShowSaveOrUpdate showSaveOrUpdate = new ShowSaveOrUpdate();
		showSaveOrUpdate.setNavigationName(showInstance.getNavigationName());
		showSaveOrUpdate.setShowFieldValueList(showInstanceValue.getShowFieldValueList());
		showSaveOrUpdate.setNamespace(RequestManager.getNamespace());
		showSaveOrUpdate.setTName(className);
		//封装操作按钮
		buidOperations(showSaveOrUpdate);
		return showSaveOrUpdate;
	}

	/**
	 * <p>方法描述: 构建操作按钮菜单项</p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param showSaveOrUpdate
	 * @throws Exception
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-8-3 上午10:32:31</p>
	 */
	public static void buidOperations(ShowSaveOrUpdate showSaveOrUpdate) throws Exception {
		if (ShowParamManager.getOperationMap() != null) {
			List<ShowOperation> operationList = new ArrayList<ShowOperation>();
			for (Map.Entry<String, String> operation : ShowParamManager.getOperationMap().entrySet()) {
				ShowOperation showOperation = new ShowOperation();
				showOperation.setOperationName(operation.getKey());
				String[] operationValues = operation.getValue().split(",");
				String operationValue = operationValues[0]; // 配置的操作如：Get-ShowUpdate
				showOperation.setOperationNamespace(TActionRule.getNamespace(operationValue));
				showOperation.setOperationAction(TActionRule.getCurrentLevelOperationActionName(operationValue, RequestManager.getTName()));
				showOperation.setOperationType(TActionRule.getOperationType(operationValue));
				if (operationValues.length > 1) {
					showOperation.setWidth(operationValues[1]);
				}
				if (operationValues.length > 2) {
					showOperation.setImageUrl(operationValues[2]);
				}
				if (operationValues.length > 3) {
					String strColor = "";
					String[] condtions = operationValues[3].split("\\|");

					List<Field> fieldList = ReflectOperation.getOneToManyField(Class.forName(RequestManager.getTName()));
					Field actionFiled = null;
					for (Field field : fieldList) {
						if (showOperation.getOperationAction().contains(ReflectOperation.getGenericClass(field.getGenericType()).getName())) {
							actionFiled = field;
							break;
						}
					}
					Collection<?> objectValue = (Collection<?>) ReflectOperation.getFieldValue(RequestManager.getTOject(), actionFiled.getName());
					if (objectValue.size() != 0) {
						for (Object o : objectValue) {
							boolean isMarth = true;
							for (int j = 0; j < condtions.length; j++) {
								isMarth = true; // red*RPTCheckType:1%2@RPTSendType:2  不匹配，isMarth = false，blue*RPTCheckType:2@RPTSendType:2 匹配上后，isMarth的值不变，还是false
								String[] str = condtions[j].split("\\*");
								String strLogic = str[1];
								String[] strLogicFieldValues = strLogic.split("@");
								for (String strLogicFieldValue : strLogicFieldValues) {
									String strLogicField = strLogicFieldValue.split(":")[0];
									String[] strLogicValues = strLogicFieldValue.split(":")[1].split("%");

									String configValue = ReflectOperation.getFieldValue(o, strLogicField).toString();
									boolean isFieldMarch = false;
									for (String strLogicValue : strLogicValues) {
										if (configValue.equals(strLogicValue)) {
											isFieldMarch = true;
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
							if (isMarth) {
								break;
							}
						}
					}

					if (strColor.startsWith("image")) {
						showOperation.setImageUrl(strColor.substring(6));
					} else {
						showOperation.setColor(strColor);
					}
				}
				operationList.add(showOperation);
			}
			showSaveOrUpdate.setOperationList(operationList);
		}
	}

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
		if (type.getName().indexOf("AutoDTO_") > -1) {
			return simpleMap;
		}

		Field primaryKeyField = ReflectOperation.getPrimaryKeyField(type);
		Field keyNameField = ReflectOperation.getKeyNameField(type);
		String defaultLogicDaoBeanId = LogicParamManager.getDefaultLogicDaoBeanId();
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(type);

		//默认值过滤
		if (LogicParamManager.getFieldValueMap() != null && LogicParamManager.getFieldValueMap().containsKey(fieldName)) {
			String fieldValue = LogicParamManager.getFieldValueMap().get(fieldName);
			String[] fieldValues = fieldValue.split("-");
			for (int i = 0; i < fieldValues.length; i++) {
				String[] foreignfield = fieldValues[i].split("_");
				String[] foreignfieldValues = foreignfield[1].split(",");
				detachedCriteria.add(Restrictions.in(foreignfield[0], foreignfieldValues));
			}
		}

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
