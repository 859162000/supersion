package report.service.imps;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;

import report.dto.MRepUpgradeInfo;
import coresystem.dto.PF_LegalInfo;
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
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：UpgradeLeRepService</P>
 * *********************************<br>
 * <P>类描述：法人制度升级配置</P>
 * 创建人：王川<br>
 * 创建时间：2016-10-20 下午4:54:14<br>
 * 修改人：王川<br>
 * 修改时间：2016-10-20 下午4:54:14<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@SuppressWarnings("unchecked")
public class UpgradeLeRepService extends BaseTShowService {

	@Override
	public Object objectResultExecute() throws Exception {
		super.objectResultExecute();
		String tName = RequestManager.getTName();
		ShowSaveOrUpdate showSaveOrUpdate = new ShowSaveOrUpdate();
		showSaveOrUpdate.setOperationList(getShowOperation(tName));
		
		IParamObjectResultExecute dao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		// 制度包查询
		ShowFieldValue pack_showFieldValue = new ShowFieldValue();
		ShowField pack_showField = new ShowField();
		pack_showField.setParamName("packInfoIds");
		pack_showField.setFieldName("packInfoIds");
		pack_showField.setFieldShowName("制度包信息");
		pack_showField.setSingleTag("multipleSelectField");
		pack_showFieldValue.setShowField(pack_showField);
		LinkedHashMap<String, String> pack_tag = new LinkedHashMap<String, String>();
		pack_showFieldValue.setTag(pack_tag);
		DetachedCriteria dc_pack = DetachedCriteria.forClass(MRepUpgradeInfo.class);
		dc_pack.add(Property.forName("strStatus").eq("1"));
		dc_pack.addOrder(Order.desc("updateDate"));
		List<MRepUpgradeInfo> list_pack = (List<MRepUpgradeInfo>) dao.paramObjectResultExecute(new Object[] { dc_pack, null });
		for(MRepUpgradeInfo l:list_pack){
			pack_tag.put(l.getUuid(), l.getPackName());
		}
		
		
		// 法人信息
		ShowFieldValue leg_showFieldValue = new ShowFieldValue();
		ShowField leg_showField = new ShowField();
		leg_showField.setParamName("legalIds");
		leg_showField.setFieldName("legalIds");
		leg_showField.setFieldShowName("法人信息");
		leg_showField.setSingleTag("multipleSelectField");
		leg_showFieldValue.setShowField(leg_showField);
		LinkedHashMap<String, String> leg_tag = new LinkedHashMap<String, String>();
		leg_showFieldValue.setTag(leg_tag);
		DetachedCriteria dc_leg = DetachedCriteria.forClass(PF_LegalInfo.class);
		List<PF_LegalInfo> list_leg = (List<PF_LegalInfo>) dao.paramObjectResultExecute(new Object[] { dc_leg, null });
		for(PF_LegalInfo l:list_leg){
			leg_tag.put(l.getId(), l.getStrLegalName());
		}

		showSaveOrUpdate.getShowFieldValueList().add(pack_showFieldValue);
		showSaveOrUpdate.getShowFieldValueList().add(leg_showFieldValue);

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
								isMarth = true; 
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
