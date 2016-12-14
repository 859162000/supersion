package report.service.procese;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.CurrencyType;
import report.dto.ItemBindInfo;
import report.dto.ItemInfo;
import report.dto.ItemProperty;
import report.dto.MergeItemInfo;
import report.dto.Period;
import report.dto.RptInfo;
import report.service.imps.ItemTemplate;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;

/*@author:liutao
 * */
public class BindItemInfoProcese implements IProcese {

	@Override
	public Object Procese(Object serviceResult) throws Exception {
		
		Date startdate=null,enddate=null;
		
		// 获取当前汇总操作的DTO对象
		Object tObject = RequestManager.getTOject();
		// 获取设置的报表信息
		String[] rptInfosIdList = (String[]) ReflectOperation.getFieldValue(tObject, "rptInfosIdList");
		// 判断报表信息是否为空
		if(rptInfosIdList != null & rptInfosIdList.length > 0){
			// 定义存放绑定指标值的数组（最终结果）
			List<ItemBindInfo> itemBindInfoList = new ArrayList<ItemBindInfo>();
			// 定义查询条件对象
			DetachedCriteria detachedCriteria = null;
			// 定义查询对象
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			// 获取报表信息
			detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.RptInfo"));
			detachedCriteria.add(Restrictions.in("strRptCode", rptInfosIdList));
			List<RptInfo> rptInfos = (List<RptInfo>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria,null });
			for(RptInfo rptInfo : rptInfos){
				// 获取报表绑定的指标信息
				List<ItemBindInfo> list = ItemTemplate.getItemBindInfos(rptInfo.getStrRptPath());
				if(list != null && list.size() > 0){
					itemBindInfoList.addAll(list);
					
					startdate = rptInfo.getStartDate();
					enddate = rptInfo.getEndDate();
				}
			}
			// 获取有用的绑定指标信息
			this.getUsefulItemInfo(itemBindInfoList);
			
			// 去除重复的数据
			Map<String,ItemBindInfo> bindInfoMap=removeRepeatItemBindInfoMap(itemBindInfoList);
			
		
			
			// 获取当前汇总实例下的指标信息
			detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.MergeItemInfo"));
			detachedCriteria.add(Restrictions.eq("mergeSummary", tObject));
			List<MergeItemInfo> mergeItemInfos = (List<MergeItemInfo>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria,null });
			
			// 从当前需要保存的指标数组中去除当前汇总实例下已经存在的指标信息，并返回重复的数据
			removeExistedItemInfo(mergeItemInfos, bindInfoMap);
			
			List<MergeItemInfo> mergeItemInfosToSave = new ArrayList<MergeItemInfo>();
			// 定义保存数据的查询对象
			IParamVoidResultExecute singleObjectSaveAllDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveAllDao");
			
			for(ItemBindInfo itemBindInfo : bindInfoMap.values()){
				Object obj = new MergeItemInfo();
				
				Object iiObj = new ItemInfo();
				Object ipObj = new ItemProperty();
				Object ctObj = new CurrencyType();
				
				ReflectOperation.setFieldValue(iiObj, "strItemCode", itemBindInfo.itemCode);
				ReflectOperation.setFieldValue(ipObj, "strPropertyCode", itemBindInfo.property);
				ReflectOperation.setFieldValue(ctObj, "strCurrencyCode", itemBindInfo.currency);
				
				ReflectOperation.setFieldValue(obj, "id", UUID.randomUUID());
				ReflectOperation.setFieldValue(obj, "mergeSummary", tObject);
				ReflectOperation.setFieldValue(obj, "itemInfo", iiObj);
				ReflectOperation.setFieldValue(obj, "itemProperty", ipObj);
				ReflectOperation.setFieldValue(obj, "currencyType", ctObj);
				ReflectOperation.setFieldValue(obj, "strExtendDimension1", itemBindInfo.ext1);
				ReflectOperation.setFieldValue(obj, "strExtendDimension2", itemBindInfo.ext2);
				ReflectOperation.setFieldValue(obj, "strFreq", itemBindInfo.freq);
				
				ReflectOperation.setFieldValue(obj, "startdate", startdate);
				ReflectOperation.setFieldValue(obj, "enddate", enddate);
				
				mergeItemInfosToSave.add((MergeItemInfo) obj);
			}
			
				singleObjectSaveAllDao.paramVoidResultExecute(new Object[] {mergeItemInfosToSave, null});
		}
		
		return serviceResult;
	}

	/**
	 * 去除重复数据
	 * @param list 获取的当前报表的保定指标数据
	 */
	private Map<String,ItemBindInfo> removeRepeatItemBindInfoMap(List<ItemBindInfo> list){
		
		Map<String,ItemBindInfo> bindInfoMap=new HashMap<String,ItemBindInfo>();
		for(ItemBindInfo ib : list){
			String tempKey =getKey(ib.itemCode,ib.property,ib.currency,ib.ext1,ib.ext2,ib.freq);
			if(!bindInfoMap.containsKey(tempKey))
			{
				bindInfoMap.put(tempKey, ib);
			}
			
		}
		return bindInfoMap;
		
	}
	
	private String getKey(String strItemCode,String itemPropertyCode,String currType,String ext1,String ext2,String freq)
	{
		return strItemCode+"_"+itemPropertyCode+"_"+currType+"_"+ext1+"_"+ext2+"_"+freq;
	}
	
	/**
	 * 获取当前绑定指标中有用的指标信息（只获取本期指标以及为保定机构和时间的的指标信息）
	 * @param itemBindInfoList 待过滤的指标数组
	 * @param existFlag 当前数组中是否还存在相同的数据标志
	 * @throws Exception 
	 */
	private void getUsefulItemInfo(List<ItemBindInfo> list){
		if(list != null && list.size() > 0){
			// 定义临时数组
			List<ItemBindInfo> tempList = new ArrayList<ItemBindInfo>();
			for(ItemBindInfo ib : list){
				if(!ib.period.equals(Period.CurPeriod.toString())){
					tempList.add(ib);
				}else{
					if(!StringUtils.isBlank(ib.instCode) || !StringUtils.isBlank(ib.date)){
						tempList.add(ib);
					}
				} 
			}
			if(tempList.size() > 0){
				list.removeAll(tempList);
			}
		}
	}
	
	/**
	 * 从当前需要保存的指标数组中去除当前汇总关系下已经存在的指标信息，并返回当前汇总实例下已经存在的绑定指标信息
	 * @param MergeItemInfos 当前汇总实例下存在的指标信息
	 * @param itemBindInfoList 当前需要保存的指标信息
	 */
	private void removeExistedItemInfo(List<MergeItemInfo> mergeItemInfos, Map<String,ItemBindInfo> itemBindInfoMap){
		
		for(MergeItemInfo mergeItemInfo : mergeItemInfos){
			String key = getKey(mergeItemInfo.getItemInfo().getStrItemCode(),
					mergeItemInfo.getItemProperty().getStrPropertyCode(),
					mergeItemInfo.getCurrencyType().getStrCurrencyCode(),
				    mergeItemInfo.getStrExtendDimension1(),
				    mergeItemInfo.getStrExtendDimension2(),
				    mergeItemInfo.getStrFreq());
			if(itemBindInfoMap.containsKey(key))
			{
				itemBindInfoMap.remove(key);
			}
			
		}
	}
	
}
