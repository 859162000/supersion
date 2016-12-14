package report.service.imps;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

import report.dto.TaskRptInst;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.TActionRule;
import framework.services.imps.BaseTShowService;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowField;
import framework.show.ShowFieldValue;
import framework.show.ShowOperation;
import framework.show.ShowSaveOrUpdate;

/**
 * 项目名称：report<br>
 * *********************************<br>
 * <P>类名称：ShowTaskRptInstForMegerService</P>
 * *********************************<br>
 * <P>类描述：汇总任务机构报表显示</P>
 * 创建人：王川<br>
 * 创建时间：2016-7-5 下午2:33:25<br>
 * 修改人：王川<br>
 * 修改时间：2016-7-5 下午2:33:25<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@SuppressWarnings("unchecked")
public class ShowTaskRptInstForMegerService extends BaseTShowService {

	@Override
	public Object objectResultExecute() throws Exception {
		super.objectResultExecute();
		String tName = RequestManager.getTName();
		ShowSaveOrUpdate showSaveOrUpdate = new ShowSaveOrUpdate();
		showSaveOrUpdate.setOperationList(getShowOperation(tName));

		// 含有汇总机构的报表查询
		String taskID = RequestManager.getLevelIdValue();
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.TaskRptInst"));
		IParamObjectResultExecute dao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		detachedCriteria.add(Property.forName("taskFlow.id").eq(taskID));
		DetachedCriteria sub = DetachedCriteria.forClass(Class.forName("report.dto.MergeRule"));
		sub.setProjection(Property.forName("higherInst.strInstCode"));
		detachedCriteria.add(Property.forName("instInfo.strInstCode").in(sub));
		List<TaskRptInst> taskRptInstList = (List<TaskRptInst>) dao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
		//任务信息
		ShowFieldValue task_showFieldValue = new ShowFieldValue();
		ShowField task_showField = new ShowField();
		task_showField.setParamName("taskFlow.id");
		task_showField.setFieldName("taskFlow.id");
		task_showField.setSingleTag("hiddenField");
		task_showFieldValue.setShowField(task_showField);
		task_showFieldValue.setFieldValue(taskID);
		
		// 机构信息
		ShowFieldValue inst_showFieldValue = new ShowFieldValue();
		ShowField inst_showField = new ShowField();
		inst_showField.setParamName("instInfoIds");
		inst_showField.setFieldName("instInfoIds");
		inst_showField.setFieldShowName("机构信息");
		inst_showField.setSingleTag("multipleSelectField");
		inst_showFieldValue.setShowField(inst_showField);
		LinkedHashMap<String, String> inst_tag = new LinkedHashMap<String, String>();
		inst_showFieldValue.setTag(inst_tag);
		
		// 报表信息
		ShowFieldValue rep_showFieldValue = new ShowFieldValue();
		ShowField rep_showField = new ShowField();
		rep_showField.setParamName("rptInfoIds");
		rep_showField.setFieldName("rptInfoIds");
		rep_showField.setFieldShowName("报表信息");
		rep_showField.setSingleTag("multipleSelectField");
		rep_showFieldValue.setShowField(rep_showField);
		LinkedHashMap<String, String> rep_tag = new LinkedHashMap<String, String>();
		rep_showFieldValue.setTag(rep_tag);

		for (TaskRptInst taskRptInst : taskRptInstList) {
			inst_tag.put(taskRptInst.getInstInfo().getStrInstCode(), taskRptInst.getInstInfo().getStrInstName());
			rep_tag.put(taskRptInst.getRptInfo().getStrRptCode(),taskRptInst.getRptInfo().getStrBCode()+"_"+ taskRptInst.getRptInfo().getStrRptName());
		}

		showSaveOrUpdate.getShowFieldValueList().add(task_showFieldValue);
		showSaveOrUpdate.getShowFieldValueList().add(inst_showFieldValue);
		showSaveOrUpdate.getShowFieldValueList().add(rep_showFieldValue);

		return showSaveOrUpdate;
	}

	/**
	 * <p>方法描述:构建操作按钮  </p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param tName
	 * @return
	 * @throws Exception
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-7-5 下午2:34:11</p>
	 *
	 */
	public List<ShowOperation> getShowOperation(String tName) throws Exception {
		List<ShowOperation> operationList = new ArrayList<ShowOperation>();
		if (ShowParamManager.getOperationMap() != null) {

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
					List<Field> fieldList = ReflectOperation.getOneToManyField(Class.forName(tName));
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
								isMarth = true; // red*RPTCheckType:1%2@RPTSendType:2
												// 不匹配，isMarth =
												// false，blue*RPTCheckType:2@RPTSendType:2
												// 匹配上后，isMarth的值不变，还是false
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
		}
		return operationList;
	}

}
