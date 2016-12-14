package report.service.imps;

import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;

import report.dao.imps.ItemDataCacheManger;
import report.dto.CalRoundMethod;
import report.dto.CalRule;
import report.dto.CalculationExampleInfo;
import report.dto.CheckInstance;
import report.dto.CurrencyType;
import report.dto.ItemData;
import report.dto.ItemDataType;
import report.dto.ItemInfo;
import report.dto.ItemProperty;
import report.dto.ItemRuleDetail;
import report.dto.RptInfo;
import report.dto.TaskRptInst;
import report.helper.DoubleUtil;
import report.service.expression.ExpressionContextKey;
import report.service.expression.interfaces.IExpressionBuilder;
import report.service.expression.interfaces.IExpressionCalculater;
import report.service.expression.interfaces.IExpressionLog;
import coresystem.dto.InstInfo;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseTShowService;
import framework.services.interfaces.MessageResult;

@SuppressWarnings("unchecked")
public class ReportItemCheckService  extends BaseTShowService  {
	
	String checkResultPath;
	Map<String,ItemData> cacheCalItemData;
	String strTaskRptInstID;
	ItemDataCacheManger itemDataCacheManger;
	
	public void setCheckResultPath(String checkResultPath) {
		this.checkResultPath = checkResultPath;
	}
	@Override
	public Object objectResultExecute() throws Exception {
		super.init();
		// 结果文件路径，存在则先删除
		String path = ServletActionContext.getServletContext().getRealPath(checkResultPath);
		File file = new File(path);
		if(file.exists()){
			file.delete();
		}
		String returnMsg="";
		try {
			// 执行数据校验
			strTaskRptInstID = RequestManager.getReportCheckParam().get("strTaskRptInstID");
			if(StringUtils.isBlank(strTaskRptInstID))
				returnMsg=this.getErrorMessage() + "\r\n" + "参数为空"; 
			else{
				IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
				TaskRptInst taskRptInst = (TaskRptInst)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{TaskRptInst.class.getName(),strTaskRptInstID,null});
				if(null == taskRptInst)
					returnMsg = "任务【"+strTaskRptInstID+"】不存在！";
				else 
					returnMsg=DoCheck(taskRptInst);
			}
		} catch(Exception ex){
			ExceptionLog.CreateLog(ex);
			returnMsg=ex.getMessage();
		}
		MessageResult mr;
		if(!StringUtils.isBlank(returnMsg))
		{
			mr=new MessageResult(false,returnMsg,strTaskRptInstID);
			mr.TxtFileTranslate(checkResultPath);
			mr.setMessage(this.getErrorMessage());
		}
		else
		{
			mr=new MessageResult(true,this.getSuccessMessage(),strTaskRptInstID); 
		}
		return mr;
	}
	
	public String DoCheck(TaskRptInst taskRptInst) throws Exception{
		String returnMsg="";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date dtStart=new Date();
		ApplicationManager.getActionExcuteLog().info(String.format("==========start date=============%s====",df.format(new Date())));
		Map<String,Object> paramValueMap = null;
		paramValueMap = new HashMap<String,Object>();
		paramValueMap.put(ExpressionContextKey.DTDATE_PARAM_KEY, taskRptInst.getTaskFlow().getDtTaskDate());
		paramValueMap.put(ExpressionContextKey.INSTCODE_PARAM_KEY, taskRptInst.getInstInfo());
		
		String checkInstanceName = taskRptInst.getRptInfo().getStrCheckInst();
		Integer precise = taskRptInst.getRptInfo().getIntPrecise();
		String rptFreq=taskRptInst.getTaskFlow().getStrFreq();
		returnMsg =DoCheck(checkInstanceName,paramValueMap,precise,rptFreq);
		Date dtEnd=new Date();
		
		ApplicationManager.getActionExcuteLog().info(String.format("==========end date=============%d====", dtEnd.getTime()-dtStart.getTime()));
		
		if(StringUtils.isBlank(returnMsg))
		{
			taskRptInst.setStrCheckState("2");
		}
		else
		{
			taskRptInst.setStrCheckState("3");
		}
		IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
		singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{taskRptInst,null});
			
		return returnMsg;
		
	}
	
		
	private String getKey(String strInstCode,String dtDate,
            String strItemCode,String currencyType,
            String strPropertyCode,String strExtendDimension1,
            String strExtendDimension2,String strFreq)
	{

		return String.format("%s_%s_%s_%s_%s_%s_%s_%s",
				            strInstCode,dtDate,strItemCode,
				            currencyType,strPropertyCode,
				            strExtendDimension1==null?"":strExtendDimension1,
				            		strExtendDimension2==null?"":strExtendDimension2,
				            				strFreq==null?"":strFreq);
		
	}
	private void CacheCalRuleItemData(String checkName,List<CalRule> calRuleList ,Map<String,Object> paramValueMap) throws Exception
	{
		if(!paramValueMap.containsKey("dtDate"))
		{
			throw new Exception("指标校验缺少dtDate参数");
		}
		if(!paramValueMap.containsKey("instInfo"))
		{
			throw new Exception("指标校验缺少instInfo参数");
		}
        
		Set<String>  listItemProperty=new HashSet<String>();
		Set<String>  listCurrencyType=new HashSet<String>();
		Map<String,Set<CalRule>> mapCalculationRule=new HashMap<String,Set<CalRule>>();
		String dtDate=TypeParse.parseString((Date) paramValueMap.get("dtDate"),"yyyy-MM-dd");
		String strInstCode=((InstInfo)paramValueMap.get("instInfo")).getStrInstCode();
		
		StringBuilder propertyWhere=new StringBuilder();
		StringBuilder currencyTypeWhere=new StringBuilder();
		Set<CalRule> setCalRules;
		for(CalRule cr: calRuleList)
		{
			String key=getKey(strInstCode,
					dtDate,
					cr.getStrItemCode(),
					cr.getStrPropertyCode(),
					cr.getCurrencyType(),
					cr.getStrExtendDimension1(),
					cr.getStrExtendDimension2(),
					cr.getStrFreq()
                  );
		
			if(!mapCalculationRule.containsKey(key))
			{
				setCalRules=new HashSet<CalRule>();
				mapCalculationRule.put(getKey(strInstCode,
						dtDate,
						cr.getStrItemCode(),
						cr.getStrPropertyCode(),
						cr.getCurrencyType(),
						cr.getStrExtendDimension1(),
						cr.getStrExtendDimension2(),
						cr.getStrFreq()
	                  ),setCalRules);
			}
				
			else
				setCalRules=mapCalculationRule.get(key);
			setCalRules.add(cr);
			
			
			
			listItemProperty.add(cr.getStrPropertyCode());
			listCurrencyType.add(cr.getCurrencyType());
			
			
		}
		
		int propertyCnt=listItemProperty.size();
		
		Type[] propertyType=new Type[propertyCnt];
		
		for(int i=0;i<propertyCnt;i++)
		{
			propertyType[i]=Hibernate.STRING;
			propertyWhere.append("?,");
		}
		
		int currencyTypeCnt=listCurrencyType.size();
		
		Type[] currencyType=new Type[currencyTypeCnt];
		for(int i=0;i<currencyTypeCnt;i++)
		{
			currencyType[i]=Hibernate.STRING;
			currencyTypeWhere.append("?,");
		}
		
		DetachedCriteria detachedCriteria;
	    detachedCriteria = DetachedCriteria.forClass(ItemData.class);
		if(propertyWhere.length()>1)
		{
			propertyWhere.insert(0,"{alias}.strPropertyCode in(");
			propertyWhere.replace(propertyWhere.length()-1,propertyWhere.length(),")");
			detachedCriteria.add(Restrictions.sqlRestriction(propertyWhere.toString(),listItemProperty.toArray(),propertyType));
		}
			
		if(currencyTypeWhere.length()>1)
		{
			currencyTypeWhere.insert(0,"{alias}.currencyType in(");
			currencyTypeWhere.replace(currencyTypeWhere.length()-1, currencyTypeWhere.length(), ")");
			detachedCriteria.add(Restrictions.sqlRestriction(currencyTypeWhere.toString(),listCurrencyType.toArray(),currencyType));
		}
		
		detachedCriteria.add(Restrictions.sqlRestriction("{alias}.strItemcode in(select stritemcode from CheckRule where autocalculationruleid in(select autocalculationruleid from CheckInstanceRule where autoCheckInstanceID=?))",checkName,Hibernate.STRING));
		
		//wc-20160623
		List<Field> itemCols = ReflectOperation.getColumnFieldList(ItemData.class);
		for(Field itemCol:itemCols){
			if(paramValueMap.get(itemCol.getName()) != null){
				detachedCriteria.add(Restrictions.eq(itemCol.getName(), paramValueMap.get(itemCol.getName())));
			}
		}
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		List<ItemData> itemDataList = (List<ItemData>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		cacheCalItemData=new HashMap<String,ItemData>();
		int i=1;
		for(ItemData itemdata:itemDataList)
		{
			String key=getKey(itemdata.getInstInfo().getStrInstCode(),
											dtDate,
											itemdata.getItemInfo().getStrItemCode(),
                                            itemdata.getItemProperty().getStrPropertyCode(),
                                            itemdata.getCurrencyType().getStrCurrencyCode(),
                                            itemdata.getStrExtendDimension1(),
                                            itemdata.getStrExtendDimension2(),
                                            itemdata.getStrFreq()
					                      );
			if(mapCalculationRule.containsKey(key))
			{
				for(CalRule cr:mapCalculationRule.get(key))
				{
					i++;
					cacheCalItemData.put(cr.getAutoCalculationRuleID(),itemdata);
				}
				
			}
			
		}
		ApplicationManager.getActionExcuteLog().debug("==========cache itemdate============"+String.valueOf(i));
		ApplicationManager.getActionExcuteLog().debug("==========cache itemdate============"+String.valueOf(mapCalculationRule.size()));
		
	}
	private Map<String,CheckInstance> getCheckInstance(String[] checkNames) throws Exception
	{
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CheckInstance.class);
		detachedCriteria.add(Restrictions.in("instanceName",checkNames));
		List<CheckInstance> checkInstanceList = (List<CheckInstance>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		Map<String,CheckInstance> mapCheckInstance=new HashMap<String,CheckInstance>();
		for(CheckInstance instance:checkInstanceList)
		{
			mapCheckInstance.put(instance.getInstanceName(), instance);
		}
		return mapCheckInstance;
	}
	
	public String DoCheck (String checkNames,Map<String,Object> paramValueMap,Integer precise,String rptFreq) throws Exception{
 
		itemDataCacheManger = ItemDataCacheManger.getInsance();
		int failCnt=0;
		if(precise==null)
		{
			precise=2;
		}
		String[] strAryCheckName=checkNames.split(",");
		Map<String,CheckInstance> mapCheckInstances=getCheckInstance(strAryCheckName);
		IExpressionLog expressionLog=(IExpressionLog)FrameworkFactory.CreateBean("expressionLog");
		IExpressionLog allExpressionLog=(IExpressionLog)FrameworkFactory.CreateBean("expressionLog");
		
		IExpressionBuilder builder=(IExpressionBuilder)FrameworkFactory.CreateBean("jsExpressionBuilder");
		IExpressionCalculater calculater=(IExpressionCalculater)FrameworkFactory.CreateBean("jsExpressionCalculater");
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria;
		
		Map<String,Object> context=new HashMap<String,Object>();
		context.put(ExpressionContextKey.DTDATE_PARAM_KEY,paramValueMap.get(ExpressionContextKey.DTDATE_PARAM_KEY));
		
		Object objInstPara=paramValueMap.get(ExpressionContextKey.INSTCODE_PARAM_KEY);
		if(objInstPara!=null )
		{
			if(objInstPara instanceof String)
			{
				context.put(ExpressionContextKey.INSTCODE_PARAM_KEY,((String)objInstPara));
				InstInfo ii=new InstInfo();
				ii.setStrInstCode((String)objInstPara);
				paramValueMap.put(ExpressionContextKey.INSTCODE_PARAM_KEY,ii);
				
			}
			else
			{
				context.put(ExpressionContextKey.INSTCODE_PARAM_KEY,((InstInfo)objInstPara).getStrInstCode());
			}
				
		}
		else
		{
			throw new Exception("缺少机构参数instInfo");
		}
		
		Object objDtDate=paramValueMap.get(ExpressionContextKey.DTDATE_PARAM_KEY);
		Date dtDate=null;
		if(objDtDate!=null) 
		{
			if(objDtDate instanceof Date)
			{
				dtDate=(Date)objDtDate;
			}
			else
			{ 
				dtDate=TypeParse.parseDate(objDtDate.toString());
				 paramValueMap.put(ExpressionContextKey.DTDATE_PARAM_KEY,dtDate);
			}
				
		}
		else
		{
			throw new Exception("缺少数据日期参数dtDate");
		}
		String strDtDate=TypeParse.parseString(dtDate, "yyyy-MM-dd");
		
		
		context.put(ExpressionContextKey.RPT_FREQ_PARAM_KEY, rptFreq);
		context.put(ExpressionContextKey.PRECISE_PARAM_KEY, precise);
		
		Logger log=ApplicationManager.getActionExcuteLog();
		
		List<ItemData> checkWrongItemDataList = new ArrayList<ItemData>();
		List<ItemData> checkRightItemDataList = new ArrayList<ItemData>();
		Map<String,ItemData> itemDataMap = new HashMap<String,ItemData>();	//有校验规则的指标的itemData
		String itemData_Key = null;
		
		//item校验失败集合
		Map<String,String> checkErrItem = new HashMap<String,String>();
		//执行多个校验实例
		for(String checkName:strAryCheckName)
		{
			CheckInstance instance=mapCheckInstances.get(checkName);
			if(instance==null)
			{
				continue;
			}
			
			RptInfo rptInfo=null;			
			Integer rptUnit=null;	//默认报表单位为元
			String calRoundMethod = null;	//默认四舍五入方式
			
			//根据校验实例获取报表编号
			detachedCriteria = DetachedCriteria.forClass(RptInfo.class);
			detachedCriteria.add(Restrictions.eq("strCheckInst", checkName));
			detachedCriteria.add(Restrictions.eq("strFreq", rptFreq));
			detachedCriteria.addOrder(Order.asc("strBCode"));
			List<RptInfo> rptInfoList=(List<RptInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			
			if(rptInfoList.size()>0)
			{
				rptInfo = rptInfoList.get(0);
			}
			
			if(rptInfo != null)
			{
				//获取报表单位
				rptUnit = rptInfo.getIntRptUnit();
				if(StringUtils.isBlank(rptUnit.toString()))
					rptUnit = 1;
					
				String strCalcInst = rptInfo.getStrCalcInst();
				
				if(!StringUtils.isBlank(strCalcInst))
				{
					detachedCriteria = DetachedCriteria.forClass(CalculationExampleInfo.class);
					detachedCriteria.add(Restrictions.eq("strCalculationName", strCalcInst.split(",")[0]));
					detachedCriteria.addOrder(Order.asc("strCalculationName"));
					
					List<CalculationExampleInfo> calculationExampleInfoList=(List<CalculationExampleInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
					if(calculationExampleInfoList.size() > 0)
					{
						calRoundMethod = calculationExampleInfoList.get(0).getStrCalRoundMethod();
						if(StringUtils.isBlank(calRoundMethod))
							calRoundMethod = CalRoundMethod.FirstRound.toString();
					}
					
				}
			}
						
			context.put(ExpressionContextKey.RptUnit, rptUnit);
			context.put(ExpressionContextKey.CALROUNDMETHOD_PARAM_KEY, calRoundMethod);
					
			
			List<CalRule> exampleInfoRuleList = getCheckRuleList(checkName,strDtDate);
			if(exampleInfoRuleList!=null)
			{				
				Map<String,List<ItemRuleDetail>> mapListItemCals=getItemsCheckByCheckInstance(checkName);
				CacheCalRuleItemData(checkName,exampleInfoRuleList,paramValueMap);
				
				ItemInfo itemInfo = null;
				
				//校验公式				
				for(CalRule calculationRule : exampleInfoRuleList){
					boolean isSuccess=false;
					try{
						//调试用
						/*if(calculationRule.getStrCalculationRuleName().equals("12M01_1560001_01_02"))
						{
							log.debug("========="+calculationRule.getAutoCalculationRuleID());
							
						}*/
						log.debug("========="+calculationRule.getAutoCalculationRuleID());
						expressionLog.clear();
						List<ItemRuleDetail> itemsCalculationList =mapListItemCals.get(calculationRule.getAutoCalculationRuleID());
						if(StringUtils.isBlank(calculationRule.getStrExpression()))
						{
							context.put(ExpressionContextKey.DATA_PARAM_KEY,itemsCalculationList);
							builder.setContext(context);
						    String exp=builder.build();
						    calculationRule.setStrExpression(exp);
						   
						}
						
						ItemData itemData= getItemDataDefault(calculationRule,paramValueMap);
						
						itemData_Key = this.getKey(itemData.getInstInfo().getStrInstCode(), strDtDate, itemData.getItemInfo().getStrItemCode(), 
													itemData.getCurrencyType().getStrCurrencyCode(), itemData.getItemProperty().getStrPropertyCode(), 
													itemData.getStrExtendDimension1(), itemData.getStrExtendDimension2(), itemData.getStrFreq());
						
						if(!itemDataMap.containsKey(itemData_Key))
						{
							itemDataMap.put(itemData_Key, itemData);
						}
													
						
//						expressionLog.insertLog(0,calculationRule, new Object[]{itemData.getStrValue()},"calRuleFormatter");
						
						
						if(!StringUtils.isBlank(calculationRule.getStrExpression()))
						{
							calculater.setContext(context);
							
							
							String rightValue="";
							
							itemInfo = itemDataCacheManger.getItem(calculationRule.getStrItemCode());
							String itemDataType = itemInfo.getStrDataType();
							String express = itemInfo.getStrItemCode()+"#"+itemDataType+"#"+calculationRule.getStrExpression();
							
							if(itemDataType.equals("2"))
							{
								BigDecimal b = new BigDecimal(itemData.getStrValue());
								b = b.divide(new BigDecimal(rptUnit));
								
								String tempResult=DoubleUtil.format(TypeParse.parseDouble(b.toString()), precise);
								expressionLog.insertLog(0,calculationRule, new Object[]{tempResult},"calRuleFormatter");
							}
								
							else
								expressionLog.insertLog(0,calculationRule, new Object[]{itemData.getStrValue()},"calRuleFormatter");
							
							calculater.setExpressionLog(expressionLog);
							
							try
							{
								rightValue=calculater.calculate(express).toString();
								
								if(rightValue.equals("NaN"))
									rightValue = "0";
								else if(rightValue.equals("未通过校验"))
									rightValue = "\"未通过校验\"";
								
								
								
								if(!StringUtils.isBlank(rightValue))
								{																			
									switch(TypeParse.parseInt(itemDataType))
									{
										/*case 2:
											try{
												if(rightValue.indexOf("E")>0 || rightValue.indexOf("e")>0)	//判断是否为科学计数法			
													rightValue = (new BigDecimal(rightValue)).toPlainString();
												
											}
											catch(Exception ex){
												rightValue = rightValue;
											}

											break;*/
										case 5:
											String temp;
											BigDecimal  b1;
											try{
												if(rightValue.indexOf("E")>0 || rightValue.indexOf("e")>0)	//判断是否为科学计数法			
													rightValue = (new BigDecimal(rightValue)).toPlainString();
																								
													temp = rightValue;
													b1 = new BigDecimal(temp);
													temp = b1.multiply(new BigDecimal(100)).toString();
													
													rightValue = temp;
												
											}
											catch(Exception ex){
												rightValue = rightValue;
											}
											
											break;
										
										default:
											rightValue = rightValue;
									}
								}
							}
							catch(Exception ex)
							{
								ExceptionLog.CreateLog(ex);
								expressionLog.log("表达式无法计算");
								rightValue="";
							}
							
							String compareType=calculationRule.getCompareType();
							if(!StringUtils.isBlank(rightValue) &&
									!StringUtils.isBlank(itemData.getStrValue())&&
									!StringUtils.isBlank(compareType))
							{
								if(itemDataType.equals("1"))
								{
									if(("=".equals(compareType)&&rightValue.equals(itemData.getStrValue())))
									{
										isSuccess=true;
									}
									else{
										expressionLog.appendLog(0,compareType, new Object[]{itemData.getStrValue(),rightValue}, "compareFormatter");
									}
								}
								else
								{
									Double dblLeftVal=Double.valueOf(itemData.getStrValue());
									dblLeftVal = Double.valueOf(DoubleUtil.format(dblLeftVal/rptUnit, precise));
								    Double dblRightVal=Double.valueOf(rightValue);
								    dblRightVal=Double.valueOf(DoubleUtil.format(dblRightVal/rptUnit, precise));
								    
								    Double dblTolerance;	//容差值
								    if(calculationRule.getDblTolerance()==null)
								    {
								    	dblTolerance=Math.abs(instance.getDblTolerance());
								    }
								    else
								    	dblTolerance=Math.abs(calculationRule.getDblTolerance());
									if(("=".equals(compareType)&& 
											Double.compare(Math.abs(dblLeftVal-dblRightVal),dblTolerance)<=0)||
									   (">".equals(compareType)&&
											   Double.compare(dblLeftVal+dblTolerance,dblRightVal)>0)||
										(">=".equals(compareType)&&
												Double.compare(dblLeftVal+dblTolerance,dblRightVal)>=0)||
										("<".equals(compareType)&&
												Double.compare(dblLeftVal,dblRightVal+dblTolerance)<0)||
										("<=".equals(compareType)&&
												Double.compare(dblLeftVal,dblRightVal+dblTolerance)<=0)||
										("!=".equals(compareType)&&
												Double.compare(Math.abs(dblLeftVal-dblRightVal),dblTolerance)>0))
								
									{
										isSuccess=true;
									}
									
									
									/*BigDecimal dblLeftVal = new BigDecimal(itemData.getStrValue());
									dblLeftVal =  dblLeftVal.divide(new BigDecimal(rptUnit));
									
									BigDecimal dblRightVal = new BigDecimal(rightValue);
									dblRightVal = dblRightVal.divide(new BigDecimal(rptUnit));
									dblRightVal = dblRightVal.setScale(precise,BigDecimal.ROUND_HALF_UP);
									
									BigDecimal dblTolerance;	//容差值
								    if(calculationRule.getDblTolerance()==null)
								    {
								    	dblTolerance= new BigDecimal(Math.abs(instance.getDblTolerance()));
								    }
								    else
								    	dblTolerance=new BigDecimal(Math.abs(calculationRule.getDblTolerance()));

								    
								    if(("=".equals(compareType)&& dblLeftVal.subtract(dblRightVal).abs().compareTo(dblTolerance)<= 0)||
									   (">".equals(compareType)&& dblLeftVal.compareTo(dblRightVal)== 1)||
									   (">=".equals(compareType)&& dblLeftVal.compareTo(dblRightVal)== 0)||		
									   ("<".equals(compareType)&& dblLeftVal.compareTo(dblRightVal) < 0)||
									   ("<=".equals(compareType)&& dblLeftVal.compareTo(dblRightVal) <= 0)||
									   ("!=".equals(compareType)&& dblLeftVal.compareTo(dblRightVal) != 0))				
									{
										isSuccess=true;
									}*/
						
									else{
										expressionLog.appendLog(0,compareType, new Object[]{dblLeftVal,dblRightVal,precise,dblTolerance}, "compareFormatter");
									}
								}
							}
							

							if(isSuccess)
							{
								/*//一个指标有多组校验规则时，只要有一组校验规则错误，这个指标就应该被标记为校验失败
								if(checkErrItem.containsKey(itemData_Key))
								{
									itemData.setStrCheckState("2");
									itemData.setStrCheckResult(checkErrItem.get(itemData.getId()));									
								}
								else
								{
									itemData.setStrCheckState("3");
									itemData.setStrCheckResult("");
								}*/
								
							}
							else
							{
								String checkResult;
								
								if(checkErrItem.containsKey(itemData_Key))
								{
//									checkResult = checkErrItem.get(itemData_Key)+expressionLog.logToString(); 
									checkResult = checkErrItem.get(itemData_Key);
									checkResult += "\r\n=========================================\r\n";
									checkResult += expressionLog.logToString(); 
									
//									if(checkResult.length() > 2000)
//										checkResult = checkResult.substring(0, 1990)+"……";
								}
								else
								{	
									failCnt++;
									checkResult = expressionLog.logToString();
								}
								
//								itemData.setStrCheckState("2");
//								itemData.setStrCheckResult(checkResult);
								checkErrItem.put(itemData_Key, checkResult);
						
							}
							
//							saveOrUpdateItemDataList.add(itemData);
						}
											
						
					}
					catch(Exception ex){
						expressionLog.log(String.format("校验规则:%s 校验出错，错误如下：%s",calculationRule.getStrCalculationRuleName(),ex.getMessage()));
						ExceptionLog.CreateLog(ex);
						failCnt++;
						
					}
					
					allExpressionLog.log(expressionLog.logToString());
			   }

		    }
	
		}
		
		Iterator iter = itemDataMap.entrySet().iterator();
		String tempResult;
		while (iter.hasNext()) 
		{
			Entry<String,ItemData> entry = (Entry<String, ItemData>)iter.next();
			
			if(checkErrItem.containsKey(entry.getKey()))
			{
				entry.getValue().setStrCheckState("2");
				
				tempResult = checkErrItem.get(entry.getKey());
				if(tempResult.length() > 2000)
					tempResult = tempResult.substring(0, 1990)+"……";
				
				entry.getValue().setStrCheckResult(tempResult);
				
				checkWrongItemDataList.add(entry.getValue());
			}
			else
			{
				entry.getValue().setStrCheckState("3");
				checkRightItemDataList.add(entry.getValue());
			}
		}
		
		IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("itemDataCacheDao");
		
		if(checkWrongItemDataList.size()>0)
		{
			singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{checkWrongItemDataList,null});
		}
		
		if(checkRightItemDataList.size()>0)
		{
			singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{checkRightItemDataList,null});
		}
		
		if(failCnt>0)
		{
			return allExpressionLog.logToString();
			
		}
		return "";
		
		   
	}

	private Map<String,List<ItemRuleDetail>> getItemsCheckByCheckInstance(String checkName) throws Exception {
	
		
		IParamObjectResultExecute hqlQueryListDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("hqlQueryListDao");
		StringBuilder hql=new StringBuilder();
    	hql.append("select new report.dto.ItemRuleDetail(");
		hql.append("ic.strItemType,");
		hql.append("ic.strItemValue,ic.calculationRule.autoCalculationRuleID)");
		hql.append(" from ItemsCheck  ic");
		hql.append(" where ic.calculationRule.autoCalculationRuleID in(");
		
		hql.append(" select eir.checkRule.autoCalculationRuleID from CheckInstanceRule eir where eir.autoCheckInstanceID.instanceName='");
		hql.append(checkName);
    	hql.append("') order by ic.calculationRule.autoCalculationRuleID,ic.intOrder");
		
		List<ItemRuleDetail> itemsCalculationList = (List<ItemRuleDetail>)hqlQueryListDao.paramObjectResultExecute(new Object[]{hql.toString(),null});
		
	
		Map<String,List<ItemRuleDetail>> result=new HashMap<String,List<ItemRuleDetail>>();
		String preAutoCalRuleID="";
		ArrayList<ItemRuleDetail> preList=null;
		for(ItemRuleDetail ic:itemsCalculationList)
		{
			if(!preAutoCalRuleID.equals(ic.getRuleID()))
			{
				
				
				preAutoCalRuleID=ic.getRuleID();
				preList=new  ArrayList<ItemRuleDetail>();
				result.put(preAutoCalRuleID, preList);
			}
			
			preList.add(ic);
		}
		return result;
	}
	

    
	private ItemData getItemDataDefault(CalRule calculationRule , Map<String, Object> paramValueMap)
			throws Exception {
		
		
		
		ItemData itemData=null;
		if(cacheCalItemData.containsKey(calculationRule.getAutoCalculationRuleID())){
			itemData = cacheCalItemData.get(calculationRule.getAutoCalculationRuleID());
			itemData.setStrCheckState("1");
			itemData.setStrCheckResult(null);
		}
		else{
			ItemInfo itemInfo=ItemDataCacheManger.getInsance().getItem(calculationRule.getStrItemCode());
			if(itemInfo!=null 
					&& (ItemDataType.Amount.toString().equals(itemInfo.getStrDataType())
					||ItemDataType.Num.toString().equals(itemInfo.getStrDataType())
					||ItemDataType.Seq.toString().equals(itemInfo.getStrDataType())
					||ItemDataType.Ratio.toString().equals(itemInfo.getStrDataType())
					||ItemDataType.Percent.toString().equals(itemInfo.getStrDataType())
					||ItemDataType.Change_Amt_Str.toString().equals(itemInfo.getStrDataType())
				    )
				)
			{
				itemData = new ItemData();
				itemInfo.setStrItemCode(calculationRule.getStrItemCode());
				
				ItemProperty itemproperty=new ItemProperty();
				itemproperty.setStrPropertyCode(calculationRule.getStrPropertyCode());
				
				CurrencyType ct=new CurrencyType();
				ct.setStrCurrencyCode(calculationRule.getCurrencyType());
				
				itemData.setItemInfo(itemInfo);
				itemData.setItemProperty(itemproperty);
				itemData.setCurrencyType(ct);
				itemData.setStrFreq(calculationRule.getStrFreq());
				
				if(!StringUtils.isBlank(calculationRule.getStrExtendDimension1())){
					itemData.setStrExtendDimension1(calculationRule.getStrExtendDimension1());
				}
				if(!StringUtils.isBlank(calculationRule.getStrExtendDimension2())){
					itemData.setStrExtendDimension2(calculationRule.getStrExtendDimension2());
				}
				//wc-20160523
				List<Field> itemCols = ReflectOperation.getColumnFieldList(ItemData.class);
				for(Field itemCol:itemCols){
					if(paramValueMap.get(itemCol.getName()) != null){
						ReflectOperation.setFieldValue(itemData, itemCol.getName(), paramValueMap.get(itemCol.getName()));
					}
				}
				itemData.setStrValue("0");
				itemData.setStrCheckState("1");
				itemData.setStrCheckResult(null);
			}
			else
			{
				throw new Exception(String.format("校验规则%s对应指标不存在值,详细信息：%s", calculationRule.getStrCalculationRuleName(),calculationRule.toString()));
			}
			
		}
		return itemData;
	}

	private List<CalRule> getCheckRuleList(String checkName,String strDtDate)
			throws Exception {
		
		if(StringUtils.isBlank(checkName)){
			 this.setErrorMessage("检验实例不能为空");
			return null; 
		}

		IParamObjectResultExecute hqlQueryListDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("hqlQueryListDao");
		StringBuilder hql=new StringBuilder();
    	hql.append("select new report.dto.CalRule(");
		hql.append("cr.autoCalculationRuleID,");
		hql.append("cr.strCalculationRuleName,");
		hql.append("cr.itemInfo.strItemCode,");
		hql.append("cr.itemProperty.strPropertyCode,");
		hql.append("cr.currencyType.strCurrencyCode,");
		hql.append("cr.startdate,");
		hql.append("cr.enddate,");
		hql.append("cr.strExtendDimension1,");
		hql.append("cr.strExtendDimension2,");
		hql.append("cr.intOrder,");
		hql.append("cr.strExpression,");
		hql.append("cr.compareType,");
		hql.append("cr.strFreq,");
		hql.append("cr.dblTolerance)");
		hql.append(" from CheckRule cr");
		hql.append(" where ");
		hql.append("cr.autoCalculationRuleID in( select eir.checkRule.autoCalculationRuleID from CheckInstanceRule eir where eir.autoCheckInstanceID.instanceName='");
		hql.append(checkName);
		hql.append("') ");
		hql.append(" and ");
//		hql.append(String.format("('%s' >= cr.startdate and '%s' <=cr.enddate)", strDtDate,strDtDate));
		hql.append("(?>=cr.startdate and ?<=cr.enddate)");
//		hql.append(" order by  case when (intOrder='' OR intOrder is null) then 0 else intOrder end");
		hql.append(" order by cr.itemInfo.strItemCode,cr.currencyType.strCurrencyCode,cr.itemProperty.strPropertyCode,case when (intOrder='' OR intOrder is null) then 0 else intOrder end");
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date dtDate = sf.parse(strDtDate);
				
		List<CalRule> calruleList = (List<CalRule>)hqlQueryListDao.paramObjectResultExecute(new Object[]{hql.toString(),new Object[]{dtDate,dtDate},null});
		
		
		return calruleList;

	}

}
