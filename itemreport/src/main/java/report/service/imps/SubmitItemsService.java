package report.service.imps;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import report.dao.imps.ItemDataCacheManger;
import report.dto.CurrencyType;
import coresystem.dto.InstInfo;
import coresystem.dto.UserInfo;
import report.dto.ItemData;
import report.dto.ItemDataHistory;
import report.dto.ItemDataType;
import report.dto.ItemInfo;
import report.dto.ItemProperty;
import report.dto.RptInfo;
import report.dto.RptSubmitInfo;
import report.dto.TaskRptInst;
import report.helper.DoubleUtil;

import extend.helper.HelpTool;
import framework.helper.FrameworkFactory;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.security.SecurityContext;
import framework.services.imps.BaseObjectDaoResultService;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;

// 主要功能：获取request中提交来的指标数据，存到数据库
public class SubmitItemsService extends BaseObjectDaoResultService {

	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {

		String strInstCode = ServletActionContext.getRequest().getParameter("strInstCode").toString();
		String strRptCode = ServletActionContext.getRequest().getParameter("strRptCode").toString();
		String strDate = ServletActionContext.getRequest().getParameter("dtDate").toString();
		String strOldURL = ServletActionContext.getRequest().getParameter("strOldAction").toString();
		String taskRptInstId = ServletActionContext.getRequest().getParameter("strTaskRptInstID").toString();
		UserInfo userInfo = (UserInfo) SecurityContext.getInstance().getLoginInfo().getTag();
		String author  = userInfo==null?"系统":userInfo.getStrUserName();
		Date updateTime = new Date();
		Integer nRptUnit = 1; // 单位
		
		// 根据strRptCode取得报表单位
		DetachedCriteria detachedCriteria2 = DetachedCriteria.forClass(Class.forName("report.dto.RptInfo"));
		detachedCriteria2.add(Restrictions.eq("strRptCode", strRptCode));

		IParamObjectResultExecute dao3 = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		List<RptInfo> rptInfoList = (List<RptInfo>) dao3.paramObjectResultExecute(new Object[] { detachedCriteria2, null });
		if (rptInfoList != null && rptInfoList.size() > 0 && rptInfoList.get(0).getIntRptUnit() != null)
			nRptUnit = rptInfoList.get(0).getIntRptUnit();
		boolean rptSubmitInfo_isupdate = false;
		detachedCriteria2 = DetachedCriteria.forClass(Class.forName("report.dto.RptSubmitInfo"));
		detachedCriteria2.add(Restrictions.eq("taskRptInstId", taskRptInstId));
		List<RptSubmitInfo> rptSubmitInfoList = (List<RptSubmitInfo>) dao3.paramObjectResultExecute(new Object[] { detachedCriteria2, null });
		RptSubmitInfo rptSubmitInfo;
		if (rptSubmitInfoList.size() > 0) {
			rptSubmitInfo = rptSubmitInfoList.get(0);
		} else {
			rptSubmitInfo = new RptSubmitInfo();
			rptSubmitInfo.setTaskRptInstId(taskRptInstId);
		}
		Integer nRptPrecise = 2; // 精度
		if (rptInfoList.get(0).getIntPrecise() != null)
			nRptPrecise = rptInfoList.get(0).getIntPrecise();

		Set<String> itemCodeSet = new HashSet<String>();
		Set<String> currencySet = new HashSet<String>();
		Set<String> instSet = new HashSet<String>();
		Set<Date> dateSet = new HashSet<Date>();
		Set<String> propertySet = new HashSet<String>();

		dateSet.add(TypeParse.parseDate(strDate));
		instSet.add(strInstCode);

		
		Enumeration enum0 = ServletActionContext.getRequest().getParameterNames();
		while (enum0.hasMoreElements()) { // 遍历一遍，获得所有的指标查询条件（itemCode + 数据日期  + 币种）
			String strName = enum0.nextElement().toString();
			if (strName.startsWith("ItemCode_")) { // 指标前缀
				// 解析出指标代码，属性编码
				String strItemCode = strName.substring(9, strName.indexOf("Property_"));
				String strProperty = strName.substring(strName.indexOf("Property_") + "Property_".length(), strName.indexOf("Currency_"));
				String strCurrency = strName.substring(strName.indexOf("Currency_") + "Currency_".length(), strName.indexOf("Date_"));
				String strItemDate = strName.substring(strName.indexOf("Date_") + "Date_".length(), strName.indexOf("InstCode_"));
				
				//报表数据保存时，只保存当期数据，其他期数的数据不保存
				if (!StringUtils.isBlank(strItemDate) && !strItemDate.equals(strDate.replace("-", "")))	//strDate是yyyy-MM-dd格式的，而strItemDate是yyyyMMdd格式
						continue;
				
				String strItemInstCode = strName.substring(strName.indexOf("InstCode_") + "InstCode_".length(), strName.indexOf("ext1_"));
				if (!StringUtils.isBlank(strItemCode))
					itemCodeSet.add(strItemCode);
				if (!StringUtils.isBlank(strCurrency))
					currencySet.add(strCurrency);
				if (!StringUtils.isBlank(strItemDate))
					dateSet.add(TypeParse.parseDate(strItemDate));
				if (!StringUtils.isBlank(strItemInstCode))
					instSet.add(strItemInstCode);
				if (!StringUtils.isBlank(strProperty))
					propertySet.add(strProperty);
			}
		}

		if (itemCodeSet.size() <= 0 || currencySet.size() <= 0) { // 没有指标更新
			HttpServletResponse response = ServletActionContext.getResponse();
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");
			StringBuffer str = new StringBuffer("<script>alert('" + "提交成功！" + "');");
			str.append("window.location.href='" + strOldURL + "';</script>");
			out.print(str.toString());
			out.close();
			return;
		}

		// 取出所有指标值到内存
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.ItemData"));
		IParamObjectResultExecute dao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		detachedCriteria.add(Restrictions.in("dtDate", dateSet));
		detachedCriteria.add(Restrictions.in("currencyType.strCurrencyCode", currencySet));
		detachedCriteria.add(Restrictions.in("itemProperty.strPropertyCode", propertySet));
		detachedCriteria.add(Restrictions.in("instInfo.strInstCode", instSet));
		
		//这里可能会因为报表上包含的指标太多而超出1000的数量，从而导致Oracle数据库报出“ORA-01795: 列表中的最大表达式数为 1000”的异常
		//detachedCriteria.add(Restrictions.in("itemInfo.strItemCode", itemCodeSet));
		
		if(itemCodeSet.size()>1000)
		{
			List<String> itemCodeList = new ArrayList<String>();
			itemCodeList.addAll(itemCodeSet);
			HelpTool.splitSourceInDc(itemCodeList, detachedCriteria, "in", "itemInfo.strItemCode", 1000);
		}
		else
			detachedCriteria.add(Restrictions.in("itemInfo.strItemCode", itemCodeSet));

		detachedCriteria.addOrder(Order.asc("itemInfo"));
		dao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		List<ItemData> objectList = (List<ItemData>) dao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
		//数据库已保存指标值
		Map<String, ItemData> mapItemDatas = new HashMap<String, ItemData>();
		ItemDataCacheManger itemDataCacheManager = ItemDataCacheManger.getInsance();
		for (ItemData obj : objectList) {
			String key = itemDataCacheManager.getKey(obj);
			mapItemDatas.put(key, obj);
		}
		
		//数据变更集
		List<ItemDataHistory> dataHistoryList = new ArrayList<ItemDataHistory>();

		List listToSave = new ArrayList();
		Enumeration enum1 = ServletActionContext.getRequest().getParameterNames();
		while (enum1.hasMoreElements()) {
			String strName = enum1.nextElement().toString();
			String strValue = ServletActionContext.getRequest().getParameter(strName).toString();
			if (strName.startsWith("ItemCode_")) 
			{ // 指标前缀
				// 解析出指标代码，属性编码
				String strItemCode = strName.substring(9, strName.indexOf("Property_"));
				String strProperty = strName.substring(strName.indexOf("Property_") + "Property_".length(), strName.indexOf("Currency_"));
				String strCurrency = strName.substring(strName.indexOf("Currency_") + "Currency_".length(), strName.indexOf("Date_"));
				String strItemDate = strName.substring(strName.indexOf("Date_") + "Date_".length(), strName.indexOf("InstCode_"));
				String strItemInstCode = strName.substring(strName.indexOf("InstCode_") + "InstCode_".length(), strName.indexOf("ext1_"));
				String strExt1 = strName.substring(strName.indexOf("ext1_") + "ext1_".length(), strName.indexOf("ext2_"));
				String strExt2 = strName.substring(strName.indexOf("ext2_") + "ext2_".length(), strName.indexOf("freq_"));
				String strFreq = strName.substring(strName.indexOf("freq_") + "freq_".length());

				ItemInfo itemInfo = itemDataCacheManager.getItem(strItemCode);
				if (itemInfo == null)
					continue;
				
				//报表数据保存时，只保存当期数据，其他期数的数据不保存
				if(!strItemDate.equals(strDate.replace("-", "")))	//strDate是yyyy-MM-dd格式的，而strItemDate是yyyyMMdd格式
					continue;
				
				String itemDataType = itemInfo.getStrDataType();

				if (StringUtils.isBlank(strValue) && 
						(
							ItemDataType.Amount.toString().equals(itemDataType) 
							|| ItemDataType.Num.toString().equals(itemDataType)
							|| ItemDataType.Percent.toString().equals(itemDataType)
						)
					) 
				{
					strValue = "0";
				}
				
				if (!StringUtils.isBlank(strValue)) {
					if (strItemDate.equals(""))
						strItemDate = strDate;
					if (strItemInstCode.equals(""))
						strItemInstCode = strInstCode;
					String key = itemDataCacheManager.getKey(strInstCode, TypeParse.parseString(TypeParse.parseDate(strItemDate), "yyyy-MM-dd"), strItemCode, strCurrency, strProperty, strExt1, strExt2, strFreq);
					ItemData itemData = null;
					if (mapItemDatas.containsKey(key)) 
					{
						itemData = mapItemDatas.get(key);
					} 
					else 
					{
						itemData = new ItemData();
						InstInfo instInfo = new InstInfo();
						instInfo.setStrInstCode(strItemInstCode);
						itemData.setInstInfo(instInfo);
						if (strItemDate.equals(""))
							itemData.setDtDate(TypeParse.parseDate(strDate));
						else {
							itemData.setDtDate(TypeParse.parseDate(strItemDate));
						}
						ItemProperty property = new ItemProperty();
						property.setStrPropertyCode(strProperty);
						CurrencyType currencyType = new CurrencyType();
						currencyType.setStrCurrencyCode(strCurrency);
						itemInfo.setStrItemCode(strItemCode);
						itemData.setItemProperty(property);
						itemData.setCurrencyType(currencyType);
						itemData.setItemInfo(itemInfo);
						itemData.setStrFreq(strFreq);
						itemData.setStrExtendDimension1(strExt1);
						itemData.setStrExtendDimension2(strExt2);
					}
					//上一次记录
					String oldValue = itemData.getStrValue();
					itemData.setStrCheckState("1");
					itemData.setStrCheckResult("");
					itemData.setStrSumCheckResult("");
					
					// 进行类型校验
					boolean bCheckOK = true;
//					String itemDataType = itemData.getItemInfo().getStrDataType();
					if (ItemDataType.Amount.toString().equals(itemDataType) // 金额
							|| ItemDataType.Num.toString().equals(itemDataType)	// 数值
							|| ItemDataType.Percent.toString().equals(itemDataType)	//百分比
						) 
					{ 
						Double dblValue = DoubleUtil.parse(strValue);
						if (dblValue.equals(Double.MAX_VALUE)) 
						{
							// 记录校验结果
							itemData.setStrCheckState("2");
							String errMsg = null;
							switch(Integer.parseInt(itemDataType))
							{
								case 2:
									errMsg = "金额";
									break;
								case 3:
									errMsg = "数值";
									break;
								case 5:
									errMsg = "百分比";
									break;	
							}
							
							itemData.setStrCheckResult(errMsg+"类型结果格式错误:" + strValue);
							itemData.setStrValue("0");
							bCheckOK = false;
						} 
						else 
						{
							itemData.setStrCheckState("1");
							itemData.setStrCheckResult("");
						}
					} 
					else if (ItemDataType.Date.toString().equals(itemDataType)) 
					{
						if (TypeParse.parseDate(strValue).equals(TypeParse.maxDate())) {
							// 记录校验结果
							itemData.setStrCheckState("2");
							itemData.setStrCheckResult("日期格式错误:" + strValue);
							itemData.setStrValue("");
							bCheckOK = false;
						} 
						else 
						{
							itemData.setStrCheckState("1");
							itemData.setStrCheckResult("");
						}
					}

					if (bCheckOK) 
					{
						// 换算金额指标单位
						if (nRptUnit != 0 && ItemDataType.Amount.toString().equals(itemDataType) && bCheckOK) {
							Double dVal = DoubleUtil.parse(strValue);
							dVal *= nRptUnit;
							strValue = String.format("%." + nRptPrecise + "f", dVal);
						}
						//百分比类型指标需要把百分号去掉
						else if (ItemDataType.Percent.toString().equals(itemDataType) && bCheckOK) {
							strValue = strValue.replace("%", "");
						}
						
						itemData.setStrValue(strValue);
						// 添加到更新列表中
						listToSave.add(itemData);
					} 
					else 
					{
						String strSaveErr = ShowContext.getInstance().getShowEntityMap().get("sysParam").get("saveErrorItem");
						if (strSaveErr != null && strSaveErr.equals("true")) 
						{							
							listToSave.add(itemData);
						}
					}
					
					//添加变更数据  【如果先提交的数据与老数据不相等，表示变更】
//					if (StringUtils.isNotBlank(oldValue) && !oldValue.equals(strValue)) 
					if (StringUtils.isNotBlank(oldValue) && bCheckOK) 
					{
						boolean isDiff = false;
						
						if (ItemDataType.Amount.toString().equals(itemDataType) // 金额
								|| ItemDataType.Num.toString().equals(itemDataType)	// 数值
								|| ItemDataType.Percent.toString().equals(itemDataType)	//百分比
							)
						{
							try{
								if((new BigDecimal(oldValue)).compareTo((new BigDecimal(strValue)))!=0)
									isDiff = true;
							}
							catch(Exception ex){
								
							}
							
						}
						else
						{
							if(!oldValue.equals(strValue))
								isDiff = true;
						}
						
						if(isDiff)
						{
							ItemDataHistory dataHistory = new ItemDataHistory(itemData);
							dataHistory.setAuthor(author);
							dataHistory.setStrPreValue(oldValue);
							dataHistory.setUpdateTime(updateTime);
							dataHistoryList.add(dataHistory);
						}
					}
				}

			} else if (strName.equals(ItemTemplate.BankName)) {
				rptSubmitInfo.setStrBankName(strValue);
				rptSubmitInfo_isupdate = true;
			} else if (strName.equals(ItemTemplate.RptDate)) {
				rptSubmitInfo.setStrRptDate(strValue);
				rptSubmitInfo_isupdate = true;
			} else if (strName.equals(ItemTemplate.Reporter)) {
				rptSubmitInfo.setStrReporter(strValue);
				rptSubmitInfo_isupdate = true;
			} else if (strName.equals(ItemTemplate.Checker)) {
				rptSubmitInfo.setStrChecker(strValue);
				rptSubmitInfo_isupdate = true;
			} else if (strName.equals(ItemTemplate.Manager)) {
				rptSubmitInfo.setStrManager(strValue);
				rptSubmitInfo_isupdate = true;
			}
		}

		// 执行批量更新和新增
		IParamVoidResultExecute dao2 = (IParamVoidResultExecute) FrameworkFactory.CreateBean("itemDataCacheDao");
		dao2.paramVoidResultExecute(new Object[] { listToSave, null });

		IParamVoidResultExecute dao1 = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
		dao1.paramVoidResultExecute(new Object[] { dataHistoryList, null });
		
		List<Object> savaObj = new ArrayList<Object>();
		if (rptSubmitInfo_isupdate){
			savaObj.add(rptSubmitInfo);
			dao1.paramVoidResultExecute(new Object[] { savaObj, null });
		}
		
		if (listToSave.size() > 0) {
			detachedCriteria2 = DetachedCriteria.forClass(Class.forName("report.dto.TaskRptInst"));
			detachedCriteria2.add(Restrictions.eq("id", taskRptInstId));
			List<TaskRptInst> lstTaskRptInst = (List<TaskRptInst>) dao3.paramObjectResultExecute(new Object[] { detachedCriteria2, null });
			if (lstTaskRptInst.size() > 0) {
				lstTaskRptInst.get(0).setStrCheckState("1");
				dao1.paramVoidResultExecute(new Object[] { lstTaskRptInst, null });
			}

		}
		this.setServiceResult(new MessageResult("保存成功", taskRptInstId));

	}
}
