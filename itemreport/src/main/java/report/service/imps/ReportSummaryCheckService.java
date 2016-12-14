package report.service.imps;


import java.lang.reflect.Field;
import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;



import report.dto.CalRule;
import report.dto.CurrencyType;
import report.dto.ItemData;
import report.dto.ItemInfo;
import report.dto.ItemProperty;
import report.dto.MergeItemInfo;
import report.dto.MergeRule;

import report.dto.TaskRptInst;
import report.helper.DoubleUtil;
import report.service.expression.ExpressionContextKey;
import report.service.expression.interfaces.IExpressionLog;
import report.service.expression.interfaces.IExpressionLogFormatter;
import report.service.expression.parser.ItemExpParamKey;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;

import framework.interfaces.ApplicationManager;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;

import framework.services.interfaces.MessageResult;

public class ReportSummaryCheckService extends ReportSummaryService{

	private TaskRptInst taskRptInst = null;
	private Map<String,ItemData> sumMapItemData=null;
	@Override
	public Object objectResultExecute() throws Exception {
		
		super.init();
		
		Map<String,Object> paramValueMap = null;
		
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		/*if(SessionManager.getTreeTypeName(TaskRptInst.class.getName()) != null){
			String id = SessionManager.getTreeIdValue(TaskRptInst.class.getName());
			taskRptInst = (TaskRptInst)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{TaskRptInst.class.getName(),id,null});
			paramValueMap = new HashMap<String,Object>();
			paramValueMap.put("dtDate", taskRptInst.getTaskFlow().getDtTaskDate());
		}*/
		String strTaskRptInstID = RequestManager.getReportCheckParam().get("strTaskRptInstID");
		if(!StringUtils.isBlank(strTaskRptInstID)){
			String id = strTaskRptInstID;
			taskRptInst = (TaskRptInst)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{TaskRptInst.class.getName(),id,null});
			paramValueMap = new HashMap<String,Object>();
			paramValueMap.put("dtDate",taskRptInst.getTaskFlow().getDtTaskDate());
		}
		
		if(paramValueMap == null){
			return new MessageResult(false,this.getErrorMessage() + "\\r\\n" + "参数为空"); 
		}
		
		String strInstCode = taskRptInst.getInstInfo().getStrInstCode();
		Integer precise = taskRptInst.getRptInfo().getIntPrecise();
		String summaryName = taskRptInst.getRptInfo().getStrMergeInst();

		String message = DoSummary(summaryName,paramValueMap,strInstCode,precise);
		
        if(message.equals("")){
        	message=check(strInstCode,paramValueMap);
        	if(message.equals(""))
        	{
        		
        		return new MessageResult(this.getSuccessMessage(),strTaskRptInstID);
        	}
        	  
		}
		return new MessageResult(false,this.getErrorMessage() + "\\r\\n" + message,strTaskRptInstID);  
		
	}
	private String check(String strInstCode,Map<String,Object> paramValueMap) throws Exception
	{
		boolean isFail=false;
		List<ItemData> updateItemDatas=new ArrayList<ItemData>();
		if(mergeItemInfoList!=null&& itemDateValueMap !=null)
		{
			List<MergeRule> childrenMergeRule=getChildrenMergeRule(strInstCode);
			Map<String,ItemData> keyDBItemDatas=getItemDatas(paramValueMap);
			Integer precise = taskRptInst.getRptInfo().getIntPrecise();
			if(precise==null)
				precise=2;
			for(MergeItemInfo id:mergeItemInfoList)
			{
				
				String itemDataKey=getItemDataKey(paramValueMap,strInstCode,id);
				ItemData dbItemData=keyDBItemDatas.get(itemDataKey);
				Double sumItemData=itemDateValueMap.get(itemDataKey);
				
				if(dbItemData!=null&& sumItemData!=null)
				{
					String strSumItemData=DoubleUtil.format(sumItemData, precise);
					Double dblDBItemData=DoubleUtil.parse(dbItemData.getStrValue());
					if(Double.compare(dblDBItemData, sumItemData)!=0)
					{
						isFail=true;
						dbItemData.setStrCheckState("2");
						dbItemData.setStrSumCheckResult(getCheckLog(dbItemData,strSumItemData,childrenMergeRule,paramValueMap));
						updateItemDatas.add(dbItemData);
					}
				}
				
			}
		}
		
		if(isFail)
		{
			IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("itemDataCacheDao");
			singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{updateItemDatas,null});
			
			taskRptInst.setStrCheckState("3");
			singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
			singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{taskRptInst,null});
			return "总分校验失败，双击单元格查看具体信息";
		}
		

		return "";
	}
	
	private String getCheckLog(ItemData dbItemData,String strSumItemData,List<MergeRule> childrenMergeRule,Map<String,Object> paramValueMap) throws Exception
	{
		StringBuilder sb=new StringBuilder();
		IExpressionLog expressionLog=(IExpressionLog)FrameworkFactory.CreateBean("expressionLog");
		CalRule calRule=new CalRule("",
				 "",	
				 dbItemData.getItemInfo().getStrItemCode(),
				 dbItemData.getItemProperty().getStrPropertyCode(),
				 dbItemData.getCurrencyType().getStrCurrencyCode(),
				 new Date(),
				 new Date(),
				 dbItemData.getStrExtendDimension1(),
				 dbItemData.getStrExtendDimension2(),
			     1,
			     "",
			     dbItemData.getStrFreq(),
			     "",
			     2);
		
		expressionLog.appendLog(1, calRule, new Object[]{dbItemData.getStrValue()},"calRuleFormatter");
		
		Double dblDBVal=DoubleUtil.parse(dbItemData.getStrValue());
		Double dblSumVal=DoubleUtil.parse(strSumItemData);
		expressionLog.appendLog(0,"=", new Object[]{dblDBVal,dblSumVal,2,0.0,"true"}, "compareFormatter");
		//sb.append(String.format("母项取值：%s\r\n汇总值：%s\r\n差值：%.2f\r\n",dbItemData.getStrValue(),strSumItemData,dblDBVal-dblSumVal));
		//sb.append("汇总子项\r\n------------------------------------------------------------------");
		IExpressionLogFormatter formatter=(IExpressionLogFormatter)FrameworkFactory.CreateBean("itemExpressionFormatter");
		Map<Object,Object> logParams=new HashMap<Object,Object>();
		logParams.putAll(paramValueMap);
		
		logParams.put(ItemExpParamKey.ITEMCODE_NAME,dbItemData.getItemInfo().getStrItemCode());
		
        logParams.put(ItemExpParamKey.CURRENCY_NAME,dbItemData.getCurrencyType().getStrCurrencyCode());
        
        logParams.put(ItemExpParamKey.EXT1_NAME,dbItemData.getStrExtendDimension1());
        logParams.put(ItemExpParamKey.EXT2_NAME,dbItemData.getStrExtendDimension2());
        
        logParams.put(ItemExpParamKey.TIME_NAME,"1");
        logParams.put(ItemExpParamKey.PROPERTY_NAME,dbItemData.getItemProperty().getStrPropertyCode());
        
        logParams.put(ItemExpParamKey.FREQ_NAME,dbItemData.getStrFreq());
		
		for(MergeRule mr:childrenMergeRule)
		{
			expressionLog.log("1".equals(mr.getStrMergeType())?"+":"-");
			String key=getItemDataKey(paramValueMap,mr.getInstInfo().getStrInstCode(),dbItemData);
			logParams.put(ExpressionContextKey.INSTCODE_PARAM_KEY,mr.getInstInfo().getStrInstCode());
			Double childItemData=itemDateValueMap.get(key);
			if(childItemData==null)
			{
				logParams.put(ExpressionContextKey.DATA_PARAM_KEY,0);
			}
			else
			{
				logParams.put(ExpressionContextKey.DATA_PARAM_KEY,childItemData);
			}
			
			
			expressionLog.log(formatter.format(mr, logParams));
			
		}
		
		return expressionLog.logToString();
	}
	private List<MergeRule> getChildrenMergeRule(String strInstCode) {
		List<MergeRule> childrenMergeRule=new ArrayList<MergeRule>();
		
		for(MergeRule mr:mergeRuleList)
		{
			if(mr.getHigherInst()!=null&& strInstCode.equals(mr.getHigherInst().getStrInstCode()))
			{
				childrenMergeRule.add(mr);
			}
		}
		return childrenMergeRule;
	}
	private Map<String,ItemData> getItemDatas(Map<String, Object> paramValueMap)
			throws Exception {
		Set<ItemInfo> lstItemInfo=new HashSet<ItemInfo>();
		Set<ItemProperty> lstItemProperty=new HashSet<ItemProperty>();
		Set<CurrencyType> lstCurrencyType=new HashSet<CurrencyType>();
		for(MergeItemInfo itemInfo:mergeItemInfoList)
		{
			lstItemProperty.add(itemInfo.getItemProperty());
			lstItemInfo.add(itemInfo.getItemInfo());
			lstCurrencyType.add(itemInfo.getCurrencyType());
		}
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ItemData.class);
		detachedCriteria.add(Restrictions.in("itemInfo", lstItemInfo));
		detachedCriteria.add(Restrictions.in("itemProperty", lstItemProperty));
		detachedCriteria.add(Restrictions.in("currencyType", lstCurrencyType));
		detachedCriteria.add(Restrictions.eq("instInfo", taskRptInst.getInstInfo()));
		//wc-20160523
		List<Field> itemCols = ReflectOperation.getColumnFieldList(ItemData.class);
		for(Field itemCol:itemCols){
			if(paramValueMap.get(itemCol.getName()) != null){
				detachedCriteria.add(Restrictions.eq(itemCol.getName(), paramValueMap.get(itemCol.getName())));
			}
		}
		
		List<ItemData> itemDataList = (List<ItemData>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		
		Map<String,ItemData> keyItemDatas=new HashMap<String,ItemData>();
		String strInstCode=taskRptInst.getInstInfo().getStrInstCode();
		for(ItemData itemData:itemDataList)
		{
			ApplicationManager.getActionExcuteLog().debug(itemData.hashCode());
			keyItemDatas.put(getItemDataKey(paramValueMap,strInstCode,itemData), itemData);
		}
		return keyItemDatas;
	}
	private String getItemDataKey(Map<String, Object> paramValueMap,String strInstCode,ItemData itemData){
		String key = "";
		for(Map.Entry<String, Object> entryParam : paramValueMap.entrySet()){
			key += entryParam.getValue().toString() + "-";
		}
		key += strInstCode + "-" + itemData.getItemInfo().getStrItemCode() + "-" + itemData.getItemProperty().getStrPropertyCode() + "-" + itemData.getCurrencyType().getStrCurrencyCode();
		if(!StringUtils.isBlank(itemData.getStrExtendDimension1())){
			key += "-" + itemData.getStrExtendDimension1();
		}
		if(!StringUtils.isBlank(itemData.getStrExtendDimension2())){
			key += "-" + itemData.getStrExtendDimension2();
		}
		if(!StringUtils.isBlank(itemData.getStrFreq()))
		{
			key+="-"+itemData.getStrFreq();
		}
		return key;
	}
	
	protected ItemData getItemData(MergeRule mergeRule,
			MergeItemInfo mergeItemInfo, Map<String, Object> paramValueMap)
			throws Exception {
		ItemData itemData = getNewItemData(mergeRule, mergeItemInfo, paramValueMap);
		return itemData;
	}
	protected void saveItemData(List<ItemData> saveOrUpdateItemDataList)
			throws Exception {
		//汇总计算后修改的是session缓存对象的值，
		//再次查询数据库返回结果是缓存值而不是数据库中的值
		IParamVoidResultExecute voidDap = (IParamVoidResultExecute)FrameworkFactory.CreateBean("clearSessionCacheByEntitysDao");
		voidDap.paramVoidResultExecute(new Object[]{saveOrUpdateItemDataList,null});
		
		
		
	}

	
	
}
