package report.service.procese;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import report.dto.CalculationExampleInfo;
import report.dto.CalculationRule;
import report.dto.ExampleInfoRule;
import report.dto.ItemsCalculation;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;

public class ReCalcuRuleOrder implements IProcese {

	@SuppressWarnings("unchecked")
	@Override
	public Object Procese(Object serviceResult) throws Exception {
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		
		Set<String> CalculationExampleInfoSet=GetCalcutationInstanceByCondition();
		DetachedCriteria detachedCriteria;	
		List<CalculationRule> saveCalculationRuleList=new ArrayList<CalculationRule>();
		Iterator<String> iterator = CalculationExampleInfoSet.iterator();
		while(iterator.hasNext()){
			String calculationExampleInfoId=iterator.next();
			Map<String,Integer> orderMap = new ConcurrentHashMap<String,Integer>();
			Map<String,List<ItemInfoLevel>> unOrderMap = new ConcurrentHashMap<String,List<ItemInfoLevel>>();
			
			CalculationExampleInfo calculationExampleInfo=new CalculationExampleInfo();
			calculationExampleInfo.setStrCalculationName(calculationExampleInfoId);
			
			detachedCriteria = DetachedCriteria.forClass(ExampleInfoRule.class);
			detachedCriteria.add(Restrictions.eq("calculationExampleInfo", calculationExampleInfo));
			List<ExampleInfoRule> exampleInfoRuleList = (List<ExampleInfoRule>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			for(ExampleInfoRule exampleInfoRule:exampleInfoRuleList){
				CalculationRule calculationRule = exampleInfoRule.getCalculationRule();
				saveCalculationRuleList.add(calculationRule);
				detachedCriteria = DetachedCriteria.forClass(ItemsCalculation.class);
				detachedCriteria.add(Restrictions.eq("calculationRule", calculationRule));
				List<ItemsCalculation> itemsCalculationList 
					= (List<ItemsCalculation>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				
				List<ItemInfoLevel> itemInfoList = new ArrayList<ItemInfoLevel>();
				for(ItemsCalculation itemsCalculation:itemsCalculationList){
					if(itemsCalculation.getStrItemType().equals("item")){
						String strItemCode=itemsCalculation.getStrItemValue();					
						strItemCode=strItemCode.substring(strItemCode.indexOf("ItemCode")+9);
						int endIndex=strItemCode.indexOf("&");
						strItemCode=strItemCode.substring(0, endIndex);
						
						ItemInfoLevel itemInfoLevel = new ItemInfoLevel(strItemCode, 0);
						itemInfoList.add(itemInfoLevel);
					}
				}
				String strItemCode=calculationRule.getItemInfo().getStrItemCode();
				if(itemInfoList.size()==0){
					if(!orderMap.containsKey(strItemCode)){
						orderMap.put(strItemCode, 1);
					}				
				}else{
					if(!unOrderMap.containsKey(strItemCode)){
						unOrderMap.put(strItemCode, itemInfoList);
					}else{
						List<ItemInfoLevel> oldItemInfoLevel = unOrderMap.get(strItemCode);
						for(ItemInfoLevel iil:itemInfoList){
							boolean isExist=false;
							for(ItemInfoLevel iil1:oldItemInfoLevel){
								if(iil.getStrItemCode().equals(iil1.getStrItemCode())){
									isExist=true;
								}
							}
							if(!isExist){
								oldItemInfoLevel.add(iil);
							}
						}
						unOrderMap.put(strItemCode, oldItemInfoLevel);
					}
				}
			}
			/////////////////////////////////
			//找到Level 1
			Iterator<Entry<String, List<ItemInfoLevel>>> _iterator = unOrderMap.entrySet().iterator();			
			while(_iterator.hasNext()){
				Entry<String, List<ItemInfoLevel>> entry1=_iterator.next();
				List<ItemInfoLevel> ItemInfoLevelList=entry1.getValue();
				for(ItemInfoLevel itemInfoLevel:ItemInfoLevelList){
					if(!unOrderMap.containsKey(itemInfoLevel.getStrItemCode())){
						itemInfoLevel.setOrder(1);
						orderMap.put(itemInfoLevel.getStrItemCode(), 1);
					}
				}
			}
			//递归
			Recursion(orderMap,unOrderMap,1);
			/////////////////////////////////
			//保存
			for(CalculationRule cr:saveCalculationRuleList){
				String strItemCode=cr.getItemInfo().getStrItemCode();
				
				if(orderMap.containsKey(strItemCode)){
					int level=orderMap.get(strItemCode);
					cr.setIntOrder(level+"");
				}
			}
			IParamVoidResultExecute singleObjectUpdateListDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateListDao");
			singleObjectUpdateListDao.paramVoidResultExecute(new Object[] {saveCalculationRuleList, null});
			
		}
		return serviceResult;
	}
	void Recursion(Map<String, Integer> orderMap,Map<String, List<ItemInfoLevel>> unOrderMap,int level){
		Iterator<Entry<String, Integer>> iterator = orderMap.entrySet().iterator();
		boolean isChange=false;
		while(iterator.hasNext()){
			Entry<String, Integer> entry=iterator.next();
			if(entry.getValue()==level){
				String strItemCode=entry.getKey();
				Iterator<Entry<String, List<ItemInfoLevel>>> iterator1 = unOrderMap.entrySet().iterator();
				while(iterator1.hasNext()){
					Entry<String, List<ItemInfoLevel>> entry1=iterator1.next();
					String strItemCode1=entry1.getKey();
					boolean isFinish=true;
					List<ItemInfoLevel> itemInfoLevelList1=entry1.getValue();
					for(ItemInfoLevel iil:itemInfoLevelList1){
						if(iil.getStrItemCode().equals(strItemCode)){
							iil.setOrder(level);
							isChange=true;
						}
						if(iil.getOrder()==0){
							isFinish=false;
						}
					}
					if(isFinish){
						orderMap.put(strItemCode1, level+1);
						iterator1.remove();
					}
				}
			}
		}
		if(isChange){
			Recursion(orderMap,unOrderMap,level+1);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Set<String> GetCalcutationInstanceByCondition() throws Exception{
		Set<String> CalculationExampleInfoSet = new HashSet<String>();
		
		List<CalculationRule> calculationRuleList=new ArrayList<CalculationRule>();
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria;
		
		if(null!=RequestManager.getReportCheckParam()){//excel公式转换入口
			CalculationExampleInfo calculationExampleInfo1 = new CalculationExampleInfo();
			calculationExampleInfo1.setStrCalculationName(RequestManager.getReportCheckParam().get("CalcInstName"));
			
			detachedCriteria = DetachedCriteria.forClass(ExampleInfoRule.class);
			detachedCriteria.add(Restrictions.eq("calculationExampleInfo", calculationExampleInfo1));
			List<ExampleInfoRule> calculationExampleInfoList11
				=(List<ExampleInfoRule>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			for(ExampleInfoRule obj:calculationExampleInfoList11){
				calculationRuleList.add(obj.getCalculationRule());
			}
		}
		else{//GUI入口
			String vs=((Map<String,Object>)ServletActionContext.getContext().get("request")).get("vs").toString();
			if(vs.equals("S")){
				String autoCalculationID =ServletActionContext.getContext().getSession().get("CalculationRuleID").toString();
				CalculationRule cr = new CalculationRule();
				cr.setAutoCalculationRuleID(autoCalculationID);
				calculationRuleList.add(cr);
			}			
		}
		
		if(calculationRuleList.size()>0){
			detachedCriteria = DetachedCriteria.forClass(ExampleInfoRule.class);
			detachedCriteria.add(Restrictions.in("calculationRule", calculationRuleList));
			List<ExampleInfoRule> calculationExampleInfoList
				=(List<ExampleInfoRule>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			for(ExampleInfoRule ei:calculationExampleInfoList){
				if(!CalculationExampleInfoSet.contains(ei.getCalculationExampleInfo().getStrCalculationName())){
					CalculationExampleInfoSet.add(ei.getCalculationExampleInfo().getStrCalculationName());
				}
			}
		}
		return CalculationExampleInfoSet;
	}
	class ItemInfoLevel{
		private String strItemCode;
		private int order;
		public ItemInfoLevel(String pStrItemCode,int pOrder){
			strItemCode=pStrItemCode;
			order=pOrder;
		}
		public void setStrItemCode(String strItemCode) {
			this.strItemCode = strItemCode;
		}
		public String getStrItemCode() {
			return strItemCode;
		}
		public void setOrder(int order) {
			this.order = order;
		}
		public int getOrder() {
			return order;
		}
	}
}
