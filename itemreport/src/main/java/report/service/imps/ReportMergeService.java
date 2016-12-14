package report.service.imps;



import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;



import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;



import coresystem.dto.InstInfo;
import report.dao.imps.ItemDataCacheManger;
import report.dto.CalculationExampleInfo;
import report.dto.CalRule;
import report.dto.CurrencyType;

import report.dto.CalRoundMethod;
import report.dto.ItemData;
import report.dto.ItemInfo;
import report.dto.ItemProperty;
import report.dto.ItemRuleDetail;
import report.dto.RptInfo;
import report.dto.TaskRptInst;
import report.helper.DoubleUtil;
import report.service.expression.ExpressionContextKey;
import report.service.expression.interfaces.IExpressionBuilder;
import report.service.expression.interfaces.IExpressionCalculater;

import extend.dto.SystemParam;
import extend.helper.HelpTool;

import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;

import framework.interfaces.ApplicationManager;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseTShowService;
import framework.services.interfaces.MessageResult;

public class ReportMergeService extends BaseTShowService {

	private Map<String,ItemData> cacheCalItemData;
	private ItemDataCacheManger itemDataCacheManger;

	
	@Override
	public Object objectResultExecute() throws Exception {
		
		super.init();
		HttpServletRequest request=ServletActionContext.getRequest();
		String isRebuild = request.getParameter("isRebuild");
		TaskRptInst taskRptInst = null;
			
		if(RequestManager.getId() != null){
			String id = RequestManager.getId().toString();
			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
			taskRptInst = (TaskRptInst)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{TaskRptInst.class.getName(),id,null});
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date dtStart=new Date();
			ApplicationManager.getActionExcuteLog().debug(String.format("==========start date=============%s====",df.format(new Date())));
			String message = DoMerge(taskRptInst,isRebuild);
			Date dtEnd=new Date();
			
			ApplicationManager.getActionExcuteLog().debug(String.format("==========end date=============%d====", dtEnd.getTime()-dtStart.getTime()));
	        if(message.equals("")){
				return new MessageResult(this.getSuccessMessage(), RequestManager.getId().toString());  
			}
			else{
				return new MessageResult(false, this.getErrorMessage() + "\\r\\n" + message, RequestManager.getId().toString());  
			}
		}
		else
			return new MessageResult(false,this.getErrorMessage() + "\\r\\n" + "参数为空"); 

	}

	
	public String DoMerge(TaskRptInst taskRptInst,String isRebuild) throws Exception{
		Map<String,Object> paramValueMap = null;
		paramValueMap = new HashMap<String,Object>();
		paramValueMap.put(ExpressionContextKey.DTDATE_PARAM_KEY, taskRptInst.getTaskFlow().getDtTaskDate());
		paramValueMap.put(ExpressionContextKey.INSTCODE_PARAM_KEY, taskRptInst.getInstInfo());
		paramValueMap.put("reBuildFlag", isRebuild);
		
		String calculationName = taskRptInst.getRptInfo().getStrCalcInst();
		Integer precise = taskRptInst.getRptInfo().getIntPrecise();
		String rptFreq=taskRptInst.getTaskFlow().getStrFreq();
	
		//不能在这里获取rptUnit，因为工作流调用DoMerge方法时，界面上没有提供rptUnit参数
//		Integer rptUnit = taskRptInst.getRptInfo().getIntRptUnit();		
//		paramValueMap.put(ExpressionContextKey.RptUnit, rptUnit);
		
		SaveRpt();
		return DoMerge(calculationName,paramValueMap,precise,rptFreq); 
	}
	
	private void SaveRpt() throws Exception
	{
		IObjectResultExecute submitService=(IObjectResultExecute)FrameworkFactory.CreateBean("SubmitItemsService");
		Object obj=submitService.objectResultExecute();
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
	
	/*
	 * 在ItemData中查找计算规则母项的指标数据是否存在，如果存在就放入ItemData对象Cache,以便后面更新操作时使用，更新操作时需要用到ItemData对象的ID字段
	 */
	private void CacheCalRuleItemData(String calculationName,List<CalRule> calRuleList ,Map<String,Object> paramValueMap) throws Exception
	{
		if(!paramValueMap.containsKey("dtDate"))
		{
			throw new Exception("指标计算缺少dtDate参数");
		}
		if(!paramValueMap.containsKey("instInfo"))
		{
			throw new Exception("指标计算缺少instInfo参数");
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
		//20160804 Liaoxl 以下这句查询要对calculationrule表中的开始日期（startdate）和结束日期（enddate）进行限制，不然一个指标有过期的计算规则存在时就会出问题。
		detachedCriteria.add(Restrictions.sqlRestriction("{alias}.strItemcode in(select stritemcode from calculationrule where startdate <= {alias}.dtDate and  {alias}.dtDate <= enddate and autocalculationruleid in(select autocalculationruleid from ExampleInfoRule where autocalculationid=?))",calculationName,Hibernate.STRING));
		//20160523 wc
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
		
		ApplicationManager.getActionExcuteLog().debug("==========cache itemdate============="+i);
		ApplicationManager.getActionExcuteLog().debug("==========mapCalculationRule ============="+mapCalculationRule.size());

	}
	
		
	private Map<String,CalculationExampleInfo> getCalInstance(String[] calInstanceNames) throws Exception
	{
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CalculationExampleInfo.class);
		detachedCriteria.add(Restrictions.in("strCalculationName",calInstanceNames));
		List<CalculationExampleInfo> calInstanceList = (List<CalculationExampleInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		Map<String,CalculationExampleInfo> mapCalInstance=new HashMap<String,CalculationExampleInfo>();
		for(CalculationExampleInfo instance:calInstanceList)
		{
			mapCalInstance.put(instance.getStrCalculationName(), instance);
		}
		return mapCalInstance;
	}
	
	
	@SuppressWarnings("unchecked")
	public String DoMerge (String calculationNames,Map<String,Object> paramValueMap,Integer precise,String rptFreq) throws Exception{

		String isRebuildFlag="Y";	//系统默认为重新生成数据（即map转换和指标间计算都要做）
		if(paramValueMap.containsKey("reBuildFlag"))
		{
			Object tempObject = paramValueMap.get("reBuildFlag");
			if(tempObject != null)
				isRebuildFlag = tempObject.toString();
		}
		
		
		itemDataCacheManger = ItemDataCacheManger.getInsance();
		String[] strAryCalInstanceName=calculationNames.split(",");
		Map<String,CalculationExampleInfo> mapCalInstances=getCalInstance(strAryCalInstanceName);
		String errorMessage="";
		Logger log=ApplicationManager.getActionExcuteLog();
		
		IExpressionBuilder builder=(IExpressionBuilder)FrameworkFactory.CreateBean("jsExpressionBuilder");
		IExpressionCalculater calculater=(IExpressionCalculater)FrameworkFactory.CreateBean("jsExpressionCalculater");
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria;
		
		
		/*IExpressionCalculater calculater2=(IExpressionCalculater)FrameworkFactory.CreateBean("jsExpCheckCal");
		try
		{
			String exp="89+@item(time=1&stritemcode=2334)+@sql()+@sql(update a set a.id=b where a.id=1 and b=1)";
			calculater2.calculate(exp);
		}
		catch(Exception ex)
		{
			msg=ex.getMessage();
			//ex.printStackTrace();
		}*/
		
		Map<String,Object> context=new HashMap<String,Object>();
		for(Entry<String, Object> e : paramValueMap.entrySet()){
			context.put(e.getKey(), e.getValue());
		}
		
		/*20160824 Liaoxl repair 为应对指标map使用的会计表中日期字段为字符串类型的情况，需要把DTDATE_PARAM_KEY参数转换为指导格式的日期字符串,
		 * 指定的格式从系统参数表(SystemParam)中获取，如果获取不到，就采用系统原来默认的处理方式
		*/	
		
		//context.put(ExpressionContextKey.DTDATE_PARAM_KEY,paramValueMap.get(ExpressionContextKey.DTDATE_PARAM_KEY));
		
		Object tempObj = paramValueMap.get(ExpressionContextKey.DTDATE_PARAM_KEY);
		if(tempObj instanceof Date)
		{
			SystemParam systemParam = HelpTool.getSystemParam("MapTableDateFormat");
			
			if(systemParam!=null && systemParam.getStrEnable().equals("1"))
				context.put(ExpressionContextKey.DTDATE_PARAM_KEY,TypeParse.parseString((Date)tempObj, systemParam.getStrParamValue()));
			else
				context.put(ExpressionContextKey.DTDATE_PARAM_KEY,tempObj);
		}	
		else
			context.put(ExpressionContextKey.DTDATE_PARAM_KEY,tempObj);
		
		//20160824 Liaoxl repair end
		
		
		
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
		
		context.put(ExpressionContextKey.RPT_FREQ_PARAM_KEY, rptFreq);
		
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
			
		
		IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("itemDataCacheDao");
		
		for(String calculationName:strAryCalInstanceName)
		{
			CalculationExampleInfo calInstance=mapCalInstances.get(calculationName);
			if(calInstance==null)
			{
				continue;
			}
			
			Integer rptUnit = 1;	//默认报表单位为元
			detachedCriteria = DetachedCriteria.forClass(RptInfo.class);
			detachedCriteria.add(Restrictions.eq("strCalcInst", calculationName));
			detachedCriteria.add(Restrictions.eq("strFreq", rptFreq));
			detachedCriteria.addOrder(Order.asc("strBCode"));
			List<RptInfo> rptInfoList=(List<RptInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			if(rptInfoList.size()>0)
			{
				rptUnit = rptInfoList.get(0).getIntRptUnit();
			}
			context.put(ExpressionContextKey.RptUnit, rptUnit);
			
			List<CalRule> exampleInfoRuleList = getCalRuleList(calculationName,strDtDate);
			if(exampleInfoRuleList!=null)
			{
				List<ItemData> saveOrUpdateItemDataList = new ArrayList<ItemData>();   
				
				//只是作为备用，当CalculationRule的strExpression字段为空而此计算规则下又有计算规则明细时使用，一般不会发生这种情况，所以把此语句放到发生此种情况的条件下才执行，以提高执行效率
				//Map<String,List<ItemRuleDetail>> mapListItemCals=GetItemCalListByExampleRuleInfo(calculationName);
				
				
				CacheCalRuleItemData(calculationName,exampleInfoRuleList,paramValueMap);
				int intOrder=-1;
				
				ItemInfo itemInfo = null;
				for(CalRule calculationRule : exampleInfoRuleList){
					try{
						
						String calRoundMethod=calculationRule.getStrCalRoundMethod();
						if(calRoundMethod==null)
						{
							calRoundMethod=calInstance.getStrCalRoundMethod();
						}
						
						
						Integer intPrecise=calculationRule.getIntPrecise();
						if(intPrecise==null)
						{
							intPrecise=calInstance.getIntPrecise();
						}
						if(intPrecise==null)
						{
							intPrecise=precise;
						}
						
						
						//当exampleInfoRuleList中的计算规则的计算优先级（intOrder）发生变化时，需要先把前面计算出的结果存到数据库里，后面的计算规则中才能引用到前面计算的结果
						 if(calculationRule.getIntOrder()!=null && calculationRule.getIntOrder()!=intOrder)
						{
							if(intOrder!=-1)
							{
								singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{saveOrUpdateItemDataList,null});
								saveOrUpdateItemDataList.clear();
							}
							intOrder=calculationRule.getIntOrder();
						}
					
						
						log.debug("========="+calculationRule.getAutoCalculationRuleID());
						
						ItemData itemData= getItemDataDefault(calculationRule,paramValueMap);
						
						//根据计算规则生成计算结果并赋值给itemData对象
						if(StringUtils.isBlank(calculationRule.getStrExpression()))
						{
							Map<String,List<ItemRuleDetail>> mapListItemCals=GetItemCalListByExampleRuleInfo(calculationName);
							List<ItemRuleDetail> itemsCalculationList = mapListItemCals.get(calculationRule.getAutoCalculationRuleID());
							context.put(ExpressionContextKey.DATA_PARAM_KEY,itemsCalculationList);
							builder.setContext(context);
						    String exp=builder.build();
						    
						    if(isRebuildFlag.equals("N") && !exp.contains("@item"))
						    	continue;
						    
						    calculationRule.setStrExpression(exp);
						}
						
						context.put(ExpressionContextKey.CALROUNDMETHOD_PARAM_KEY, calRoundMethod);
						context.put(ExpressionContextKey.PRECISE_PARAM_KEY, intPrecise);
						
						
						if(!StringUtils.isBlank(calculationRule.getStrExpression()))
						{
							/*当点击指标报表填报界面上的“数据计算”按钮时（isRebuildFlag=N）,如果不涉及指标间运算的计算规则就直接跳过不计算了，
							 * 因为这些指标的值已经通过SQL语句或常量值转换好了
							 */
							if(isRebuildFlag.equals("N") && !calculationRule.getStrExpression().contains("@item"))
						    	continue;
							
							calculater.setContext(context);
							
							/*20160825 Liaoxl repair 这里需要把计算规则所属的指标的类型一起传入，以方便对计算结果的处理，
							 * 因为需要根据指标类型来判断是否需要把计算结果转换为数值类型，比如文本类型就不需要转换。
							 * 因为IExpressionCalculater接口的calculate方法只传递了一个字符串参数，所以只能采用字符串拼接
							 * 的方式来传递需要的参数值了
							 */
							//String value=calculater.calculate(calculationRule.getStrExpression()).toString();
							
							itemInfo = itemDataCacheManger.getItem(calculationRule.getStrItemCode());
							String express = itemInfo.getStrItemCode()+"#"+itemInfo.getStrDataType()+"#"+calculationRule.getStrExpression();
							String value=calculater.calculate(express).toString();
							
							if(value.equals("NaN"))
								value = "0";
							else if(value.equals("未通过校验"))
								value = "\"未通过校验\"";
							
							//20160825 Liaoxl repair end
							
							if(!StringUtils.isEmpty(value))
							{
							
								/*if((itemInfo.getStrDataType().equals("2") || itemInfo.getStrDataType().equals("3")) && CalRoundMethod.FirstCal.toString().equals(calRoundMethod))
								{
									itemData.setStrValue(DoubleUtil.format(Double.parseDouble(value), intPrecise));
								}
								else
								{
									itemData.setStrValue(value);
								}*/
								
								boolean CalRoundMethod_Flag;
								if(CalRoundMethod.FirstCal.toString().equals(calRoundMethod))
									CalRoundMethod_Flag = true;
								else
									CalRoundMethod_Flag = false;
								
								
								switch(TypeParse.parseInt(itemInfo.getStrDataType()))
								{
									/*case 2:
										try{
											if(value.indexOf("E")>0 || value.indexOf("e")>0)	//判断是否为科学计数法			
												value = (new BigDecimal(value)).toPlainString();
											if(CalRoundMethod_Flag)
												itemData.setStrValue(DoubleUtil.format(Double.parseDouble(value), intPrecise));
											else
												itemData.setStrValue(value);
										}
										catch(Exception ex){
											itemData.setStrValue(value);
										}

										break;*/
									case 5:
										String temp;
										BigDecimal  b1;
										try{
											if(value.indexOf("E")>0 || value.indexOf("e")>0)	//判断是否为科学计数法			
												value = (new BigDecimal(value)).toPlainString();
											
											if(CalRoundMethod_Flag)
											{
												temp = value;
												b1 = new BigDecimal(temp);
												temp = b1.multiply(new BigDecimal(100)).toString();	//先放大100倍再进行精度截取
												temp = DoubleUtil.format(Double.parseDouble(temp), intPrecise);
																							
												itemData.setStrValue(temp);
											}
											else
											{
												temp = value;
												b1 = new BigDecimal(temp);
												temp = b1.multiply(new BigDecimal(100)).toString();
												
												itemData.setStrValue(temp);
											}
										}
										catch(Exception ex){
											itemData.setStrValue(value);
										}
										
										break;
									
									default:
										itemData.setStrValue(value);
								}
								
								
								//2016-07-12 wc update
								itemData.setValue1(itemData.getStrValue());
								saveOrUpdateItemDataList.add(itemData);
							}
							else
							{
								errorMessage+="指标:" + calculationRule.getStrCalculationRuleName() + "-" + itemInfo.getStrItemName() + "无计算值";
								
							}
						}

					}
					catch(Exception ex){
						errorMessage+="指标:" + calculationRule.getStrCalculationRuleName() + "-" + itemInfo.getStrItemName() + " 计算异常";
						ExceptionLog.CreateLog(ex);
						break;
					}

			   }
			
			   singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{saveOrUpdateItemDataList,null});
			   saveOrUpdateItemDataList.clear();

			}
		}

		setErrorMessage(getErrorMessage()+errorMessage);
		return errorMessage;   
	}

	/*
	 * 根据计算实例名称获取其下所有计算规则使用到的明细规则
	 */
	private Map<String,List<ItemRuleDetail>> GetItemCalListByExampleRuleInfo(String calculationName) throws Exception {
	
		
		IParamObjectResultExecute hqlQueryListDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("hqlQueryListDao");
		StringBuilder hql=new StringBuilder();
    	hql.append("select new report.dto.ItemRuleDetail(");
		hql.append("ic.strItemType,");
		hql.append("ic.strItemValue,ic.calculationRule.autoCalculationRuleID)");
		hql.append(" from ItemsCalculation  ic");
		hql.append(" where ic.calculationRule.autoCalculationRuleID in(");
		hql.append(" select eir.calculationRule.autoCalculationRuleID from ExampleInfoRule eir where eir.calculationExampleInfo.strCalculationName='");
		hql.append(calculationName);
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
	

    /*
     * 给计算规则中的母项指标通过计算后形成的ItemData对象的除计算结果字段外的其他字段赋值
     */
	private ItemData getItemDataDefault(CalRule calculationRule , Map<String, Object> paramValueMap)
			throws Exception {

		ItemData itemData=null;
		
		/*20160815 这里要注意一下，if和else两种方法得到的ItemData对象是不一样的，if中得到的ItemData对象的ID字段是有值的，表示此对象已经存在，
		 * else中得到的ItemData对象的ID字段是空的，表示此对象不存在。对象是否存在会影响后面的操作，存在时更新，不存在则写入。
		 * */
		if(cacheCalItemData.containsKey(calculationRule.getAutoCalculationRuleID())){
			itemData = cacheCalItemData.get(calculationRule.getAutoCalculationRuleID());
			itemData.setStrCheckState("1");
			itemData.setStrCheckResult(null);
		}
		else{
			
			itemData = new ItemData();
			ItemInfo itemInfo=new ItemInfo();
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
			//wc-2016-06-23
			List<Field> itemCols = ReflectOperation.getColumnFieldList(ItemData.class);
			for(Field itemCol:itemCols){
				if(paramValueMap.get(itemCol.getName()) != null){
					ReflectOperation.setFieldValue(itemData, itemCol.getName(), paramValueMap.get(itemCol.getName()));
				}
			}
			itemData.setStrCheckState("1");
			itemData.setStrCheckResult(null);
			
		}
		
		return itemData;
	}

	private List<CalRule> getCalRuleList(String calculationName,String strDtDate)
			throws Exception {
		
		if(StringUtils.isBlank(calculationName)){
			 this.setErrorMessage("计算实例不能为空");
			return null; 
		}

		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		CalculationExampleInfo calculationExampleInfo = (CalculationExampleInfo)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{CalculationExampleInfo.class.getName(),calculationName,null});
		if(calculationExampleInfo == null){
			 this.setErrorMessage("计算实例不能为空");
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
		hql.append("cr.strFreq,");
		hql.append("cr.strCalRoundMethod,");
		hql.append("cr.intPrecise)");
		hql.append(" from CalculationRule cr");
		hql.append(" where ");
		hql.append(" cr.autoCalculationRuleID in(select eir.calculationRule.autoCalculationRuleID from ExampleInfoRule eir where eir.calculationExampleInfo.strCalculationName='");
		hql.append(calculationName);
		hql.append("') ");
		hql.append(" and ");
		hql.append("(?>=cr.startdate and ?<=cr.enddate) ");
		//hql.append(String.format("('%s' >= cr.startdate and '%s' <=cr.enddate)", strDtDate,strDtDate));
		hql.append(" order by case when (intOrder='' OR intOrder is null) then 0 else intOrder end,cr.itemInfo.strItemCode");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date dtDate = sf.parse(strDtDate);
		
		List<CalRule> calruleList = (List<CalRule>)hqlQueryListDao.paramObjectResultExecute(new Object[]{hql.toString(),new Object[]{dtDate,dtDate},null});
		
		return calruleList;

	}
}
