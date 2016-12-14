package report.service.imps;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import report.dto.CurrencyType;
import report.dto.ItemData;
import report.dto.ItemInfo;
import report.dto.ItemProperty;
import report.dto.MergeItemInfo;
import report.dto.MergeRule;
import report.dto.MergeSummary;
import report.dto.TaskRptInst;
import coresystem.dto.InstInfo;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseTShowService;
import framework.services.interfaces.MessageResult;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：ReportsSummaryService</P>
 * *********************************<br>
 * <P>类描述：数据批量汇总</P>
 * 创建人：王川<br>
 * 创建时间：2016-7-6 上午9:39:40<br>
 * 修改人：王川<br>
 * 修改时间：2016-7-6 上午9:39:40<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@SuppressWarnings("unchecked")
public class ReportsSummaryService extends BaseTShowService {
	private TaskRptInst taskRptInst = null;
	protected MergeSummary mergeSummary = null;
	protected List<MergeItemInfo> mergeItemInfoList = null;
	protected List<MergeRule> mergeRuleList = null;
	protected Map<String, Double> itemDateValueMap = new HashMap<String, Double>();

	@Override
	public Object objectResultExecute() throws Exception {
		MessageResult message = new MessageResult(true);
		super.init();
		if (null != RequestManager.getTOject()) {
			IParamObjectResultExecute dao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			TaskRptInst _taskRptInst = (TaskRptInst) RequestManager.getTOject();
			String taskID = _taskRptInst.getTaskFlow().getId();
			String[] instInfoIds = _taskRptInst.getInstInfoIds();
			String[] rptInfoIds = _taskRptInst.getRptInfoIds();
			if(instInfoIds.length==0||rptInfoIds.length==0){
				return new MessageResult(false, "请选择汇总机构和报表");
			}
			if(instInfoIds.length>1){
				return new MessageResult(false, "只能选择一家机构进行汇总");
			}
			for (String rptInfoId : rptInfoIds) {
				for (String instInfoId : instInfoIds) {
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.TaskRptInst"));
					detachedCriteria.add(Property.forName("taskFlow.id").eq(taskID));
					detachedCriteria.add(Property.forName("instInfo.strInstCode").eq(instInfoId));
					detachedCriteria.add(Property.forName("rptInfo.strRptCode").eq(rptInfoId));
					List<TaskRptInst> taskRptInstList = (List<TaskRptInst>) dao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
					if (taskRptInstList.size() == 1) {
						taskRptInst = taskRptInstList.get(0);
						MessageResult mrs = mergerSignalRptInst();
						//+taskRptInst.getInstInfo().getStrInstName()+"_"
						message.getMessageList().add("【"+taskRptInst.getRptInfo().getStrBCode()+"】"+mrs.getMessage());
					} 
				}
			}
		}
		message.AlertTranslate();
		return message;

	}

	/**
	 * <p>方法描述: 单表单机构汇总</p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @return
	 * @throws Exception
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-7-6 上午9:40:49</p>
	 */
	protected MessageResult mergerSignalRptInst() throws Exception {
		Map<String, Object> paramValueMap = null;
		if ("2".equals(taskRptInst.getSubmitStatus()) || "2".equals(taskRptInst.getSubmitStatus())) {
			return new MessageResult(false, "已提交或审核通过的报表不能进行该操作");
		}
		paramValueMap = new HashMap<String, Object>();
		paramValueMap.put("dtDate", TypeParse.parseString(taskRptInst.getTaskFlow().getDtTaskDate(), "yyyy-MM-dd"));
		String strInstCode = taskRptInst.getInstInfo().getStrInstCode();
		Integer precise = taskRptInst.getRptInfo().getIntPrecise();
		String summaryName = taskRptInst.getRptInfo().getStrMergeInst();

		String message = DoSummary(summaryName, paramValueMap, strInstCode, precise);
		if (message.equals("")) {
			return new MessageResult(this.getSuccessMessage());
		} else {
			return new MessageResult(false, this.getErrorMessage() + "\\r\\n" + message);
		}
	}

	protected void insertSummaryItem(List<ItemData> saveOrUpdateItemDataList, List<MergeRule> mergeRuleList, MergeRule mergeRule, MergeItemInfo mergeItemInfo, Map<String, Object> paramValueMap, Map<String, Double> itemDateValueMap, Integer precise) throws Exception {
		ItemData itemData = getItemData(mergeRule, mergeItemInfo, paramValueMap, true);
		Double value = 0.0;
		for (MergeRule subMergeRule : mergeRuleList) {
			if (subMergeRule.getHigherInst() != null && mergeRule.getInstInfo().getStrInstCode().equals(subMergeRule.getHigherInst().getStrInstCode())) {
				String subkey = getItemDataKey(paramValueMap, subMergeRule.getInstInfo().getStrInstCode(), mergeItemInfo);
				if (mergeRule.getStrMergeType().equals("1")) {
					value += itemDateValueMap.get(subkey);
				} else if (mergeRule.getStrMergeType().equals("2")) {
					value -= itemDateValueMap.get(subkey);
				}
			}
		}
		String key = getItemDataKey(paramValueMap, mergeRule.getInstInfo().getStrInstCode(), mergeItemInfo);
		itemDateValueMap.put(key, value);

		String format = "0";
		int digital = 1;
		if (precise != null && precise >= 0) {
			digital = precise;
		}
		for (int i = 0; i < digital; i++) {
			if (i == 0) {
				format += ".";
			}
			format += "0";
		}
		DecimalFormat decimalFormat = new DecimalFormat(format);
		itemData.setStrValue(decimalFormat.format(value));
		//2016-07-12 wc update
		itemData.setValue1(itemData.getStrValue());

		saveOrUpdateItemDataList.add(itemData);
	}

	protected ItemData getItemData(MergeRule mergeRule, MergeItemInfo mergeItemInfo, Map<String, Object> paramValueMap, boolean isNewWhenNoExist) throws Exception {
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ItemData.class);
		detachedCriteria.add(Restrictions.eq("itemInfo", mergeItemInfo.getItemInfo()));
		detachedCriteria.add(Restrictions.eq("itemProperty", mergeItemInfo.getItemProperty()));
		detachedCriteria.add(Restrictions.eq("currencyType", mergeItemInfo.getCurrencyType()));
		detachedCriteria.add(Restrictions.eq("instInfo", mergeRule.getInstInfo()));
		if (!StringUtils.isBlank(mergeItemInfo.getStrExtendDimension1())) {
			detachedCriteria.add(Restrictions.eq("strExtendDimension1", mergeItemInfo.getStrExtendDimension1()));
		}
		if (!StringUtils.isBlank(mergeItemInfo.getStrExtendDimension2())) {
			detachedCriteria.add(Restrictions.eq("strExtendDimension2", mergeItemInfo.getStrExtendDimension2()));
		}
		if (!StringUtils.isBlank(mergeItemInfo.getStrFreq())) {
			detachedCriteria.add(Restrictions.eq("strFreq", mergeItemInfo.getStrFreq()));
		}
		//wc-20160523
		List<Field> itemCols = ReflectOperation.getColumnFieldList(ItemData.class);
		for (Field itemCol : itemCols) {
			if (paramValueMap.get(itemCol.getName()) != null) {
				detachedCriteria.add(Restrictions.eq(itemCol.getName(), paramValueMap.get(itemCol.getName())));
			}
		}

		List<ItemData> itemDataList = (List<ItemData>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria, null });

		ItemData itemData = null;
		if (itemDataList.size() > 0) {
			itemData = itemDataList.get(0);
			itemData.setStrCheckState("1");
			itemData.setStrCheckResult(null);
		}
		if (itemData == null && isNewWhenNoExist) {
			itemData = getNewItemData(mergeRule, mergeItemInfo, paramValueMap);
		}
		return itemData;
	}

	protected ItemData getNewItemData(MergeRule mergeRule, MergeItemInfo mergeItemInfo, Map<String, Object> paramValueMap) throws Exception {
		ItemData itemData;
		itemData = new ItemData();
		itemData.setItemInfo(mergeItemInfo.getItemInfo());
		itemData.setItemProperty(mergeItemInfo.getItemProperty());
		itemData.setCurrencyType(mergeItemInfo.getCurrencyType());
		itemData.setInstInfo(mergeRule.getInstInfo());
		if (!StringUtils.isBlank(mergeItemInfo.getStrExtendDimension1())) {
			itemData.setStrExtendDimension1(mergeItemInfo.getStrExtendDimension1());
		}
		if (!StringUtils.isBlank(mergeItemInfo.getStrExtendDimension2())) {
			itemData.setStrExtendDimension2(mergeItemInfo.getStrExtendDimension2());
		}
		itemData.setStrFreq(mergeItemInfo.getStrFreq());
		//wc-2016-06-23
		List<Field> itemCols = ReflectOperation.getColumnFieldList(ItemData.class);
		for (Field itemCol : itemCols) {
			if (paramValueMap.get(itemCol.getName()) != null) {
				ReflectOperation.setFieldValue(itemData, itemCol.getName(), paramValueMap.get(itemCol.getName()));
			}
		}
		itemData.setStrCheckState("1");
		itemData.setStrCheckResult(null);
		return itemData;
	}

	private Integer getLevelMergeRule(List<MergeRule> mergeRuleList, MergeRule rootMergeRule, MergeRule mergeRule, Integer level) {

		if (rootMergeRule == mergeRule) {
			return level;
		}
		if (mergeRule.getHigherInst() == null) {
			return null;
		}

		for (MergeRule tempMergeRule : mergeRuleList) {
			if (mergeRule.getHigherInst().getStrInstCode().equals(tempMergeRule.getInstInfo().getStrInstCode())) {
				return getLevelMergeRule(mergeRuleList, rootMergeRule, tempMergeRule, level + 1);
			}
		}
		return null;
	}

	protected String getItemDataKey(Map<String, Object> paramValueMap, String strInstCode, MergeItemInfo mergeItemInfo) {
		String key = "";
		for (Map.Entry<String, Object> entryParam : paramValueMap.entrySet()) {
			key += entryParam.getValue().toString() + "-";
		}
		key += strInstCode + "-" + mergeItemInfo.getItemInfo().getStrItemCode() + "-" + mergeItemInfo.getItemProperty().getStrPropertyCode() + "-" + mergeItemInfo.getCurrencyType().getStrCurrencyCode();
		if (!StringUtils.isBlank(mergeItemInfo.getStrExtendDimension1())) {
			key += "-" + mergeItemInfo.getStrExtendDimension1();
		}
		if (!StringUtils.isBlank(mergeItemInfo.getStrExtendDimension2())) {
			key += "-" + mergeItemInfo.getStrExtendDimension2();
		}
		if (!StringUtils.isBlank(mergeItemInfo.getStrFreq())) {
			key += "-" + mergeItemInfo.getStrFreq();
		}
		return key;
	}

	public String DoSummary(String summaryName, Map<String, Object> paramValueMap, String strInstCode, Integer precise) throws Exception {

		String message = "";
		if (StringUtils.isBlank(summaryName)) {
			message = "汇总实例不能为空";
			return message;
		}

		mergeSummary = getSummaryInfo(summaryName);
		if (mergeSummary == null) {
			message = "汇总实例不存在";
			return message;
		}

		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");

		processParam(paramValueMap);

		mergeRuleList = getMergeRuleList(singleObjectFindByCriteriaDao,paramValueMap);

		MergeRule rootMergeRule = getRootMergeRule(strInstCode);
		if (rootMergeRule == null) {
			message = "汇总机构不在汇总树中";
			return message;
		}

		Set<InstInfo> childreeInst = new HashSet<InstInfo>();
		Map<MergeRule, Integer> levelMergeRuleMap = new HashMap<MergeRule, Integer>();
		Integer maxLevel = getMergeRuleLevel(rootMergeRule, childreeInst, levelMergeRuleMap);

		message = checkChildrenInstData(singleObjectFindByCriteriaDao, childreeInst);
		if (!"".equals(message)) {
			return message;
		}

		mergeItemInfoList = getMergeItemList(singleObjectFindByCriteriaDao,paramValueMap);

		String errorMessage = "";
		List<ItemData> saveOrUpdateItemDataList = new ArrayList<ItemData>();

		for (MergeItemInfo mergeItemInfo : mergeItemInfoList) {
			for (int i = maxLevel; i > 0; i--) {
				for (Map.Entry<MergeRule, Integer> entry : levelMergeRuleMap.entrySet()) {
					if (entry.getValue().equals(i)) {
						String itemDataKey = getItemDataKey(paramValueMap, entry.getKey().getInstInfo().getStrInstCode(), mergeItemInfo);
						if (!itemDateValueMap.containsKey(itemDataKey)) {
							ItemData itemData = getItemData(entry.getKey(), mergeItemInfo, paramValueMap, false);

							if (itemData != null) {
								Double doubleValue = TypeParse.parseDouble(itemData.getStrValue());
								if (doubleValue == null || Double.compare(Double.MAX_VALUE, doubleValue) == 0) {
									message = itemDataKey + " 非金额类型";
									return message;
								}
								itemDateValueMap.put(itemDataKey, doubleValue);
							} else {
								itemDateValueMap.put(itemDataKey, 0.0);
							}
						}
					}
				}

				Set<String> strInstCodeSet = new HashSet<String>();
				for (Map.Entry<MergeRule, Integer> entry : levelMergeRuleMap.entrySet()) {
					if (entry.getValue().equals(i)) {
						if (!strInstCodeSet.contains(entry.getKey().getHigherInst().getStrInstCode())) {
							strInstCodeSet.add(entry.getKey().getHigherInst().getStrInstCode());
							for (MergeRule mergeRule : mergeRuleList) {
								if (entry.getKey().getHigherInst().getStrInstCode().equals(mergeRule.getInstInfo().getStrInstCode())) {
									insertSummaryItem(saveOrUpdateItemDataList, mergeRuleList, mergeRule, mergeItemInfo, paramValueMap, itemDateValueMap, precise);
									break;
								}
							}

						}
					}
				}
			}
		}

		if (errorMessage.equals("")) {
			saveItemData(saveOrUpdateItemDataList);
		}

		return errorMessage;
	}

	@SuppressWarnings("unused")
	private void getData(Map<String, Object> paramValueMap, MergeRule rootMergeRule) throws Exception {
		IParamObjectResultExecute singleObjectFindByCriteriaDao;
		DetachedCriteria detachedCriteria;
		Set<ItemInfo> lstItemInfo = new HashSet<ItemInfo>();
		Set<ItemProperty> lstItemProperty = new HashSet<ItemProperty>();
		Set<CurrencyType> lstCurrencyType = new HashSet<CurrencyType>();
		for (MergeItemInfo itemInfo : mergeItemInfoList) {
			lstItemProperty.add(itemInfo.getItemProperty());
			lstItemInfo.add(itemInfo.getItemInfo());
			lstCurrencyType.add(itemInfo.getCurrencyType());
		}
		singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");

		detachedCriteria = DetachedCriteria.forClass(ItemData.class);
		detachedCriteria.add(Restrictions.in("itemInfo", lstItemInfo));
		detachedCriteria.add(Restrictions.in("itemProperty", lstItemProperty));
		detachedCriteria.add(Restrictions.in("currencyType", lstCurrencyType));
		detachedCriteria.add(Restrictions.eq("instInfo", rootMergeRule.getInstInfo()));

		//wc-20160523
		List<Field> itemCols = ReflectOperation.getColumnFieldList(ItemData.class);
		for (Field itemCol : itemCols) {
			if (paramValueMap.get(itemCol.getName()) != null) {
				detachedCriteria.add(Restrictions.eq(itemCol.getName(), paramValueMap.get(itemCol.getName())));
			}
		}

		List<ItemData> itemDataList = (List<ItemData>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
	}

	private Integer getMergeRuleLevel(MergeRule rootMergeRule, Set<InstInfo> childreeInst, Map<MergeRule, Integer> levelMergeRuleMap) {
		Integer maxLevel = 0;
		for (MergeRule mergeRule : mergeRuleList) {
			Integer level = getLevelMergeRule(mergeRuleList, rootMergeRule, mergeRule, 0);
			if (level != null && level > 0) {
				levelMergeRuleMap.put(mergeRule, level);
				childreeInst.add(mergeRule.getInstInfo());
				if (level > maxLevel) {
					maxLevel = level;
				}
			}
		}
		return maxLevel;
	}

	private MergeRule getRootMergeRule(String strInstCode) {
		for (MergeRule mergeRule : mergeRuleList) {
			if (mergeRule.getInstInfo().getStrInstCode().equals(strInstCode)) {
				return mergeRule;
			}
		}
		return null;
	}

	private List<MergeRule> getMergeRuleList(IParamObjectResultExecute singleObjectFindByCriteriaDao,Map<String, Object> paramValueMap) throws Exception {
		Date dtdate = TypeParse.parseDate(paramValueMap.get("dtDate").toString());
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MergeRule.class);
		
		detachedCriteria.add(Restrictions.eq("mergeInst", mergeSummary.getMergeInstance()));
		detachedCriteria.add(Restrictions.le("startdate", dtdate));
		detachedCriteria.add(Restrictions.ge("enddate", dtdate));
		
		List<MergeRule> mergeRuleList = (List<MergeRule>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
		return mergeRuleList;
	}

	private String checkChildrenInstData(IParamObjectResultExecute singleObjectFindByCriteriaDao, Set<InstInfo> childreeInst) throws Exception {
		String message;
		DetachedCriteria detachedCriteria;
		if (taskRptInst != null) {
			detachedCriteria = DetachedCriteria.forClass(TaskRptInst.class);
			detachedCriteria.add(Restrictions.in("instInfo", childreeInst));
			detachedCriteria.add(Restrictions.in("strAllowState", new String[] { "1", "3" }));
			detachedCriteria.add(Restrictions.eq("taskFlow", taskRptInst.getTaskFlow()));
			detachedCriteria.add(Restrictions.eq("rptInfo", taskRptInst.getRptInfo()));
			List<TaskRptInst> lstTaskRptInst = (List<TaskRptInst>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
			if (lstTaskRptInst.size() > 0) {
				message = "下级机构存在未审核的数据:\\r\\n";
				for (TaskRptInst task : lstTaskRptInst) {
					message += task.getInstInfo().getStrInstName() + "\\r\\n";
				}
				return message;
			}
		}
		return "";
	}

	private List<MergeItemInfo> getMergeItemList(IParamObjectResultExecute singleObjectFindByCriteriaDao,Map<String, Object> paramValueMap) throws Exception {
		Date dtdate = TypeParse.parseDate(paramValueMap.get("dtDate").toString());
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MergeItemInfo.class);

		detachedCriteria.add(Restrictions.eq("mergeSummary", mergeSummary));
		detachedCriteria.add(Restrictions.le("startdate", dtdate));
		detachedCriteria.add(Restrictions.ge("enddate", dtdate));
		
		List<MergeItemInfo> mergeItemInfoList = (List<MergeItemInfo>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
		return mergeItemInfoList;
	}

	private MergeSummary getSummaryInfo(String summaryName) throws Exception {
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		MergeSummary mergeSummary = (MergeSummary) singleObjectFindByIdDao.paramObjectResultExecute(new Object[] { MergeSummary.class.getName(), summaryName, null });
		return mergeSummary;
	}

	private void processParam(Map<String, Object> paramValueMap) {
		Object objDtDate = paramValueMap.get("dtDate");
		if (objDtDate != null) {
			if (objDtDate instanceof String) {
				objDtDate = TypeParse.parseDate(objDtDate.toString());
				paramValueMap.put("dtDate", objDtDate);

			}

		}

	}

	protected void saveItemData(List<ItemData> saveOrUpdateItemDataList) throws Exception {
		IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("itemDataCacheDao");
		singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[] { saveOrUpdateItemDataList, null });
	}

}
