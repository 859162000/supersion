package report.service.imps;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import coresystem.dto.InstInfo;
import report.dto.CalculationRule;
import report.dto.CheckRule;
import report.dto.CurrencyType;
import report.dto.ItemInfo;
import report.dto.ItemProperty;
import report.dto.ItemsCheck;
import extend.dto.ReportModel_Table;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseDTService;
import framework.show.ShowContext;

public class ShowCalcRuleCfgService extends BaseDTService{
	/* (non-Javadoc)
	 * @see framework.services.imps.BaseDService#initSuccessResult()
	 */
	/* (non-Javadoc)
	 * @see framework.services.imps.BaseDService#initSuccessResult()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception{
		String type = RequestManager.getTName();
		String id = RequestManager.getLevelIdValue();
		Map<String,Object> result = new HashMap<String,Object>();
		ServletActionContext.getContext().getSession().put("CalculationRuleID", id);
		
		Map<String,String> symbol = new LinkedHashMap<String,String>();
		symbol.put("+", "加");symbol.put("-", "减");symbol.put("*", "乘");symbol.put("/", "除");
		symbol.put("(", "左括号");symbol.put(")", "右括号");symbol.put("!=", "不等于");//symbol.put("=", "等于");
		symbol.put(">", "大于");symbol.put("<", "小于");symbol.put("AND", "AND");symbol.put("OR", "OR");
		symbol.put("if", "IF");symbol.put("else", "ELSE");symbol.put("{", "{");symbol.put("}", "}");
		symbol.put(">=", "大于等于");symbol.put("<=", "小于等于");symbol.put("==", "双等于");symbol.put("abs", "ABS");
		Map<String,String> extendProperty1 = ShowContext.getInstance().getShowEntityMap().get("extendProperty1");
		Map<String,String> extendProperty2 = ShowContext.getInstance().getShowEntityMap().get("extendProperty2");
		Map<String,String> timeProperty = ShowContext.getInstance().getShowEntityMap().get("timeProperty");
		Map<String,String> freqProperty = ShowContext.getInstance().getShowEntityMap().get("reportFreq");
		Map<String,String> ItemType= new HashMap<String,String>();		
		ItemType.put("item", "指标");ItemType.put("symbol", "符号");ItemType.put("const", "常量");ItemType.put("detail", "明细表");
		ItemType.put("sql", "SQL");
		Object rule;
		if(Class.forName(type).newInstance() instanceof ItemsCheck){
			rule=new CheckRule();
		}else{
			rule=new CalculationRule();
		}
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(rule.getClass());
		detachedCriteria.add(Restrictions.eq("autoCalculationRuleID", id));
		
		IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
    	List<Object> objectListItemInfo = (List<Object>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
    	for(Object cr : objectListItemInfo){
    		if(null!=ReflectOperation.getFieldValue(cr, "strExtendDimension1") && !ReflectOperation.getFieldValue(cr, "strExtendDimension1").equals("")){
    			ReflectOperation.setFieldValue(cr, "strExtendDimension1",ShowContext.getInstance().getShowEntityMap().get("extendProperty1")
        				.get(ReflectOperation.getFieldValue(cr, "strExtendDimension1").toString()));
    		}
    		if(null!=ReflectOperation.getFieldValue(cr, "strExtendDimension2") && !ReflectOperation.getFieldValue(cr, "strExtendDimension2").equals("")){
    			ReflectOperation.setFieldValue(cr, "strExtendDimension2", ShowContext.getInstance().getShowEntityMap().get("extendProperty2")
        				.get(ReflectOperation.getFieldValue(cr, "strExtendDimension2").toString()));
    		}
    	}	
    	detachedCriteria = DetachedCriteria.forClass(CurrencyType.class);
    	List<CurrencyType> currencyType= (List<CurrencyType>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
    	
    	detachedCriteria = DetachedCriteria.forClass(ItemProperty.class);
    	List<ItemProperty> itemProperty= (List<ItemProperty>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
    	
    	detachedCriteria = DetachedCriteria.forClass(ReportModel_Table.class);
    	List<ReportModel_Table> reportModelTable= (List<ReportModel_Table>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
   	
    	detachedCriteria = DetachedCriteria.forClass(InstInfo.class);
    	List<InstInfo> instInfoList=(List<InstInfo>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});

    	detachedCriteria = DetachedCriteria.forClass(Class.forName(type));
    	ReflectOperation.setFieldValue(rule, "autoCalculationRuleID", id); 
    	detachedCriteria.add(Restrictions.eq("calculationRule", rule));
    	detachedCriteria.addOrder(Order.asc("intOrder"));
    	List<Object> itemsCalculationList= (List<Object>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
    	    	
    	for(Object itemsCalculation :itemsCalculationList){
    		String strItemType=ReflectOperation.getFieldValue(itemsCalculation, "strItemType").toString();
    		ReflectOperation.setFieldValue(itemsCalculation, "strItemTypeDesc", ItemType.get(strItemType));
    		if(strItemType.equals("item")){
    			Map<String,String> itemMap = new HashMap<String,String>();
    			String[] items =ReflectOperation.getFieldValue(itemsCalculation, "strItemValue").toString().split("&");
    			for(String item : items){
    				if(item.split("=").length>1){
    					itemMap.put(item.split("=")[0], item.split("=")[1]);
    				}else{
    					itemMap.put(item.split("=")[0], "");
    				}    				
    			}
    			if(itemMap.containsKey("ItemCode")){
    				ReflectOperation.setFieldValue(itemsCalculation, "itemCode", itemMap.get("ItemCode"));
    			}
    			if(itemMap.containsKey("Curr")){
    				String currName="";
    				if(null!=itemMap.get("Curr") && !itemMap.get("Curr").toString().equals("")){
    					for(CurrencyType curr :currencyType){
        					if(curr.getStrCurrencyCode().equals(itemMap.get("Curr").toString())){
        						ReflectOperation.setFieldValue(itemsCalculation, "curr",curr.getStrCurrencyCode());
        						currName=curr.getStrCurrencyName();
        						break;
        					}
        				}
    				}
    				
    				ReflectOperation.setFieldValue(itemsCalculation, "currName",currName);
    			}
				if(itemMap.containsKey("Property")){
					String proName = "";
					if(null!=itemMap.get("Property") && !itemMap.get("Property").toString().equals("")){
    					for(ItemProperty property :itemProperty){
        					if(property.getStrPropertyCode().equals(itemMap.get("Property").toString())){
        						ReflectOperation.setFieldValue(itemsCalculation, "property",property.getStrPropertyCode());
        						proName=property.getStrPropertyName();
        						break;
        					}
        				}
    				}					
					ReflectOperation.setFieldValue(itemsCalculation, "proName",proName);				
				}
				if(itemMap.containsKey("Extend1")){
					ReflectOperation.setFieldValue(itemsCalculation, "extend1",itemMap.get("Extend1"));
					ReflectOperation.setFieldValue(itemsCalculation, "extend1Name",extendProperty1.get(itemMap.get("Extend1")) );
				}
				if(itemMap.containsKey("Extend2")){
					ReflectOperation.setFieldValue(itemsCalculation, "extend2", itemMap.get("Extend2"));
					ReflectOperation.setFieldValue(itemsCalculation, "extend2Name", extendProperty2.get(itemMap.get("Extend2")));
				}
				if(itemMap.containsKey("Time")){
					ReflectOperation.setFieldValue(itemsCalculation, "time", itemMap.get("Time"));
					ReflectOperation.setFieldValue(itemsCalculation, "timeName", timeProperty.get(itemMap.get("Time")));
				}
				if(itemMap.containsKey("Freq")){
					ReflectOperation.setFieldValue(itemsCalculation, "freq", itemMap.get("Freq"));
					ReflectOperation.setFieldValue(itemsCalculation, "freqName", freqProperty.get(itemMap.get("Freq")));
				}
				if(itemMap.containsKey("dtDate")){
					ReflectOperation.setFieldValue(itemsCalculation, "dtDate", itemMap.get("dtDate"));
				}
				if(itemMap.containsKey("instInfo")){
					if(null!=itemMap.get("instInfo") && !itemMap.get("instInfo").toString().equals("")){
						ReflectOperation.setFieldValue(itemsCalculation, "instInfo", itemMap.get("instInfo").toString());
						for(InstInfo ii :instInfoList){
							if(ii.getStrInstCode().equals(itemMap.get("instInfo").toString())){
								ReflectOperation.setFieldValue(itemsCalculation, "instName", ii.getStrInstName());
								break;
							}
						}		
					}								
				}
    			    			
    	    	detachedCriteria = DetachedCriteria.forClass(ItemInfo.class);
    			detachedCriteria.add(Restrictions.eq("strItemCode", itemMap.get("ItemCode").toString()));    			
    			List<ItemInfo> itemInfos= (List<ItemInfo>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
    			if(itemInfos.size()>0){
    				ReflectOperation.setFieldValue(itemsCalculation, "strItemCodeName", itemInfos.get(0).getStrItemName());
    			}
    			
    		}else if(strItemType.equals("detail")){
    			String strItemValue = ReflectOperation.getFieldValue(itemsCalculation, "strItemValue").toString();
    			java.util.Queue<String> queue = new java.util.LinkedList<String>();
    			boolean isFlag=false;
    			for(int index=0;index<strItemValue.length();index++){
    				if(isFlag){
    					if(strItemValue.charAt(index)=='#'){
    						isFlag=false;
    						queue.add(strItemValue.charAt(index)+"");
    						continue;
    					}
    					continue;
    				}else{
    					if(strItemValue.charAt(index)=='|'){
    						isFlag=true;
    						continue;
    					}
    					queue.add(strItemValue.charAt(index)+"");
    				}
    			}
    			
    			String rrr="";
    			while(!queue.isEmpty()){
    				rrr+=queue.poll();
    			}
    			ReflectOperation.setFieldValue(itemsCalculation, "strItemValue", rrr);
    			/*String strItemValue1="";
    			int lBracketIndex=strItemValue.indexOf("[");
    			strItemValue1=strItemValue.substring(0,lBracketIndex);
    			int rBracketIndex=strItemValue.indexOf("]");
    			String tmp[] = strItemValue.substring(lBracketIndex, rBracketIndex).split("@");
    			for(String str :tmp){
    				if(str.split("#")[0].contains("|")){
    					
    				}
    			}
    			strItemValue1+=strItemValue.substring(rBracketIndex);
    			ReflectOperation.setFieldValue(itemsCalculation, "strItemValue", strItemValue1);
    			*/
    			String va = strItemValue.substring(1, strItemValue.length()-1);
    			String[] vas= va.split(":");
    			ReflectOperation.setFieldValue(itemsCalculation, "acctableName", vas[0]);
    			int index = vas[1].indexOf("(");
    			ReflectOperation.setFieldValue(itemsCalculation, "calcType", vas[1].substring(0, index));
    			ReflectOperation.setFieldValue(itemsCalculation, "calcField", vas[1].substring(index+1,vas[1].indexOf(")")));
    			ReflectOperation.setFieldValue(itemsCalculation, "whereCondition",vas[1].substring(vas[1].indexOf("[")+1,vas[1].indexOf("]")));
    		}
    	}
    	result.put("type", type);
    	result.put("header", objectListItemInfo.get(0));
    	result.put("symbol", symbol);
    	result.put("currencyType", currencyType);
    	result.put("itemProperty", itemProperty);
    	result.put("timeProperty", timeProperty);
    	result.put("freqProperty", freqProperty);
    	result.put("extendProperty1", extendProperty1);
    	result.put("extendProperty2", extendProperty2);
    	result.put("ReportModel_Table", reportModelTable);
    	result.put("ItemsCalculation", itemsCalculationList);
    	result.put("InstInfo", instInfoList);
		this.setServiceResult(result);
	}
}
