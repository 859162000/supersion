package framework.reportCheck;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.helper.SmallTools;
import framework.helper.SqlConstructor;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.helper.TypeParse;
import framework.helper.SqlConstructor.ConditionClass;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;
import framework.helper.SqlConstructor;

public class CheckItemList {
	
	public CheckItemList() {
		checkFieldParamFieldList = new ArrayList<CheckFieldParamField>();
		setCheckItemRuleList(new ArrayList<CheckItemRule>());
	}
	
	private Object getItemValue(String code, String currency, String property, String instCode, 
			String dtDate, String ext1, String ext2, String lastFreq) throws Exception {
		Object objItem = Class.forName("report.dto.ItemInfo").newInstance();
		ReflectOperation.setFieldValue(objItem, "strItemCode", code);
		Object objItemProperty = Class.forName("report.dto.ItemProperty").newInstance();
		ReflectOperation.setFieldValue(objItemProperty, "strPropertyCode", property);
		Object objCurrencyType = Class.forName("report.dto.CurrencyType").newInstance();
		ReflectOperation.setFieldValue(objCurrencyType, "strCurrencyCode", currency);
		
		Object objInst = Class.forName("coresystem.dto.InstInfo").newInstance();
		if(!instCode.equals(""))
			ReflectOperation.setFieldValue(objInst, "strInstCode", instCode);
		else
			ReflectOperation.setFieldValue(objInst, "strInstCode", checkFieldParamFieldList.get(1).getValue());
		
		DetachedCriteria detachedCriteria = null;
		IParamObjectResultExecute dao = null;
		List<Object> objectList = null;
		detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.ItemData"));
		detachedCriteria.add(Restrictions.eq("instInfo", objInst));
		
		Date realDate;
		if(dtDate.equals(""))
			realDate = TypeParse.parseDate(checkFieldParamFieldList.get(0).getValue()); // 填报任务时间
		else
			realDate = TypeParse.parseDate(dtDate);
		
		// 上期处理
		if(lastFreq != null && lastFreq.equals("true")) {
			String strFreq = "";
			for(CheckFieldParamField checkFieldParamField : this.checkFieldParamFieldList) {
				if(checkFieldParamField.getParamName().equals("strFreq")) {
					strFreq = checkFieldParamField.getValue();
				}
			}
			
			if(strFreq != null && !strFreq.equals("")) {
				realDate = SmallTools.getFreqDate(realDate, strFreq);
			}
		}
		
		detachedCriteria.add(Restrictions.eq("dtDate", realDate));
		
		detachedCriteria.add(Restrictions.eq("itemInfo", objItem));
		detachedCriteria.add(Restrictions.eq("itemProperty", objItemProperty));
		detachedCriteria.add(Restrictions.eq("currencyType", objCurrencyType));
		if(!StringUtils.isBlank(ext1))
			detachedCriteria.add(Restrictions.eq("strExtendDimension1", ext1));
		if(!StringUtils.isBlank(ext2))
			detachedCriteria.add(Restrictions.eq("strExtendDimension2", ext2));
		dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
    	objectList = (List<Object>)dao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
    	String val = "0";
    	Object ObjItemData = null;
    	if(objectList.size() > 1) { // 条件不正确
    		throw new Exception("指标条件不足。");
    	}
    	else if(objectList.size() == 1) {
    		ObjItemData = ((Object)objectList.get(0));
    	}
    	else{
    		throw new Exception("不存在指定指标");
    	}
    	
		return ObjItemData;
	}

	@SuppressWarnings("unchecked")
	public Integer Check(MessageResult messageResult) throws Exception {
		
		Integer statistic = 0;
		
		String defaultLogicDaoBeanId = this.getDefaultLogicDaoBeanId();
		if(defaultLogicDaoBeanId == null || defaultLogicDaoBeanId.equals(""))
			defaultLogicDaoBeanId = LogicParamManager.getDefaultLogicDaoBeanId();
		
		// 取数据库类型
		IParamObjectResultExecute objectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("queryDataBaseEditionDao");
		String dbType = (String) objectResultExecute.paramObjectResultExecute(null);
		
		// 加载指标属性、币种、机构名称
		Map<String, String> propertyMap = new LinkedHashMap<String,String>();
		Map<String, String> currencyMap = new LinkedHashMap<String,String>();
		Map<String, String> instMap = new LinkedHashMap<String,String>();
		Map<String, String> itemMap = new LinkedHashMap<String,String>();
		
		objectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindAllDao");
		List<Object> listObj = (List<Object>)objectResultExecute.paramObjectResultExecute(new Object[]{"report.dto.ItemProperty", null});
		if (listObj != null) { // 找不到对象,设置SQL为空
			for(Object obj : listObj) {
				propertyMap.put(ReflectOperation.getFieldValue(obj, "strPropertyCode").toString(),
						ReflectOperation.getFieldValue(obj, "strPropertyName").toString());
			}
		}
		
		objectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindAllDao");
		listObj = (List<Object>)objectResultExecute.paramObjectResultExecute(new Object[]{"report.dto.CurrencyType", null});
		if (listObj != null) { // 找不到对象,设置SQL为空
			for(Object obj : listObj) {
				currencyMap.put(ReflectOperation.getFieldValue(obj, "strCurrencyCode").toString(),
						ReflectOperation.getFieldValue(obj, "strCurrencyName").toString());
			}
		}
		
		objectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindAllDao");
		listObj = (List<Object>)objectResultExecute.paramObjectResultExecute(new Object[]{"coresystem.dto.InstInfo", null});
		if (listObj != null) { // 找不到对象,设置SQL为空
			for(Object obj : listObj) {
				instMap.put(ReflectOperation.getFieldValue(obj, "strInstCode").toString(),
						ReflectOperation.getFieldValue(obj, "strInstName").toString());
			}
		}
		
		objectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindAllDao");
		listObj = (List<Object>)objectResultExecute.paramObjectResultExecute(new Object[]{"report.dto.ItemInfo", null});
		if (listObj != null) { // 找不到对象,设置SQL为空
			for(Object obj : listObj) {
				itemMap.put(ReflectOperation.getFieldValue(obj, "strItemCode").toString(),
						ReflectOperation.getFieldValue(obj, "strItemName").toString());
			}
		}
		
		objectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean(defaultLogicDaoBeanId);
		SqlConstructor SqlConstructor = new SqlConstructor();
		// 对每一个规则进行校验
		for(CheckItemRule chekItemRule : getCheckItemRuleList()) {
			// 取ItemData表中此指标的值
			Object ObjItemData = null;
			String Message = "";
			String ruleMessage = "";
			try {
				ObjItemData = (Object) getItemValue(chekItemRule.getCode(), chekItemRule.getCurrency(),
									chekItemRule.getPropty(), chekItemRule.getInstCode(),
									chekItemRule.getDtDate(), chekItemRule.getExt1(), chekItemRule.getExt2(), "false");
				
				ruleMessage = ReflectOperation.getFieldValue(ObjItemData, "strValue").toString()
					+ " 指标项目 [" + chekItemRule.getCode() + "]" + itemMap.get(chekItemRule.getCode())
					+ " " + currencyMap.get(chekItemRule.getCurrency()) + " " + propertyMap.get(chekItemRule.getPropty())
					+ " 本期  "
					+ " \\n比较符" + chekItemRule.getCompareType() + "\\n";
				ruleMessage += "----------------------------------------------------------------------\\n";
			}
			catch(Exception ex) {
				ObjItemData = null;
				Message = "规则无法取值\\n";
			}
			
			if(dbType == null) {
				Message = "无法取得数据库类型\\n";
			}
			
			boolean bSucess = false;
			if(ObjItemData != null) {
				String itemVal = ReflectOperation.getFieldValue(ObjItemData, "strValue").toString();
				
				ScriptEngineManager factory = new ScriptEngineManager();  
			    ScriptEngine engine = factory.getEngineByName("JavaScript");
			    String expression = "";
			    int nLeftQuato = 0;
			    
				// 计算规则右边的值
				if(chekItemRule.getCheckSubRuleList() != null){
					for( CheckItemSubRule subRule : chekItemRule.getCheckSubRuleList()) {
						// 将子规则转为SQL语句
						if(subRule.getType().equals("operate")) {
							expression += subRule.getValue() + " ";
							ruleMessage += subRule.getValue();
							ruleMessage += " \\n";
						}
						else if(subRule.getType().equals("const")) {
							expression += subRule.getValue() + " \\n";
							ruleMessage += subRule.getValue() + " \\n";
						}
						else if(subRule.getType().equals("item")) {
							Object objVal = null;
							try {
								objVal = getItemValue(subRule.getCode(), subRule.getCurrency(),
								 subRule.getPropty(), subRule.getInstCode(),
								 subRule.getDtDate(), subRule.getExt1(), subRule.getExt2(), subRule.getLastFreq());
							}
							catch(Exception ex) {
							}
							
							if(objVal != null) {
								ruleMessage += " " + ReflectOperation.getFieldValue(objVal, "strValue").toString();
							}
							
							ruleMessage +=  "指标项目 [" + subRule.getCode() + "]" + itemMap.get(subRule.getCode())
							+ " " + currencyMap.get(subRule.getCurrency()) + " " + propertyMap.get(subRule.getPropty());
							if(subRule.getLastFreq() != null && subRule.getLastFreq().equals("true"))
							   ruleMessage += " 上期 \\n";
							else
							   ruleMessage += " 本期 \\n";
							
							if(objVal == null) {
								Message = "规则指标项无法取值 [" + subRule.getCode() + "]" + itemMap.get(subRule.getCode()) + " "
								+ currencyMap.get(subRule.getCurrency()) + " " + propertyMap.get(subRule.getPropty());
								if(subRule.getLastFreq() != null && subRule.getLastFreq().equals("true"))
									Message += " 上期; \\n";
								else
									Message += " 本期; \\n";
								
								break;
							}
							 
							String val = ReflectOperation.getFieldValue(objVal, "strValue").toString();
							expression += val + " ";
						}
						else if(subRule.getType().equals("detail")) {
							String strCondition = "";
							String strSQL = "";
							strSQL += " select " + subRule.getOperate() + "(" + subRule.getField() + ") from " + subRule.getTable() + " ";
							strCondition = SqlConstructor.getConditionSql(subRule.getWhereFieldList());
							if(!strCondition.equals("")) strSQL += " where ";
							strSQL += strCondition;
							
							strCondition = SqlConstructor.getConditionDesc(subRule.getWhereFieldList());
							
							// 替换参数
							for(CheckFieldParamField checkFieldParamField : this.checkFieldParamFieldList) {
								if(checkFieldParamField.getParamName().equals("dtDate") && dbType != null && dbType.equals("oracle")) {
									strSQL = strSQL.replace('@' + checkFieldParamField.getParamName(), "to_date('" + checkFieldParamField.getValue() + "', 'yyyy-mm-dd')");
									strCondition = strCondition.replace('@' + checkFieldParamField.getParamName(), checkFieldParamField.getValue());
								}
								else if(checkFieldParamField.getParamName().equals("strInstCode")) {
									strSQL = strSQL.replace('@' + checkFieldParamField.getParamName(), "'" + checkFieldParamField.getValue() + "'");
									strCondition = strCondition.replace('@' + checkFieldParamField.getParamName(), "[" + checkFieldParamField.getValue() + "]" + instMap.get(checkFieldParamField.getValue()));
								}
								else {
									strSQL = strSQL.replace('@' + checkFieldParamField.getParamName(), "'" + checkFieldParamField.getValue() + "'");
									strCondition = strCondition.replace('@' + checkFieldParamField.getParamName(), "'" + checkFieldParamField.getValue() + "'");
								}
							}
							
							Object obj = null;
							if(strSQL != null && !strSQL.equals("")) {
								// 执行语句取计算结果
								IParamObjectResultExecute createSqlQueryResultSetDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("createSqlQueryResultSetDao");
								ResultSet resultSet = (ResultSet) createSqlQueryResultSetDao.paramObjectResultExecute(new Object[]{strSQL, null});
								
								resultSet.next();
								obj = resultSet.getObject(1);
		    					resultSet.close();
								
								if(obj == null || obj.equals("")) {
									Message = "规则无法 取值!\\n";
									break;
								}
								
								expression += obj.toString();
								
								strCondition = strCondition.replace("'", "");
								ruleMessage += obj.toString() + " 明细表:" + subRule.getTable() + " 字段:" + subRule.getField();
								if(subRule.getOperate().equals("SUM") || subRule.getOperate().equals("sum"))
									ruleMessage += " 方法:求和 ";
								else if(subRule.getOperate().equals("AVG") || subRule.getOperate().equals("avg"))
									ruleMessage += " 方法:求平均 ";
								else if(subRule.getOperate().equals("COUNT") || subRule.getOperate().equals("count"))
									ruleMessage += " 方法:计数 ";
								else if(subRule.getOperate().equals("MAX") || subRule.getOperate().equals("max"))
									ruleMessage += " 方法:最大值 ";
								else if(subRule.getOperate().equals("MIN") || subRule.getOperate().equals("min"))
									ruleMessage += " 方法:最小值 ";
								ruleMessage += " 条件:" + strCondition + "\\n";
							}
						}
					}
				}

				Double rightVal = null;
				try {
					expression = expression.replace("\\n", "");
					rightVal = (Double)engine.eval(expression);
				} catch(Exception ex) {
					Message += "规则表达式无法计算!\\n";
				}
				
				if(itemVal != null && !itemVal.equals("")
						&& Message.equals("")
						&& rightVal != null
						//&& !StringUtils.isBlank(chekItemRule.getCompareType())
						&& !chekItemRule.getCompareType().equals(ApplicationManager.getEmptySelectValue())) {
					if(chekItemRule.getCompareType().equals("=")) {
						 bSucess = (TypeParse.parseDouble(itemVal).equals(rightVal));
						 Message ="指标与规则值不相等!  差值：" + (TypeParse.parseDouble(itemVal)-rightVal) + "\\n";
					}
					else if(chekItemRule.getCompareType().equals(">")) {
						 bSucess = (TypeParse.parseDouble(itemVal) > rightVal); 
						 Message ="指标值应大于规则值!  差值：" + (TypeParse.parseDouble(itemVal)-rightVal) + "\\n";
					}
					else if(chekItemRule.getCompareType().equals(">=")) {
						 bSucess = (TypeParse.parseDouble(itemVal) >= rightVal);
						 Message ="指标值应大于等于规则值!  差值：" + (TypeParse.parseDouble(itemVal)-rightVal) + "\\n"; 
					}
					else if(chekItemRule.getCompareType().equals("<")) {
						 bSucess = (TypeParse.parseDouble(itemVal) < rightVal); 
						 Message ="指标值应小于规则值!  差值：" + (TypeParse.parseDouble(itemVal)-rightVal) + "\\n";
					}
					else if(chekItemRule.getCompareType().equals("<=")) {
						 bSucess = (TypeParse.parseDouble(itemVal) <= rightVal); 
						 Message ="指标值应小于等于规则值!  差值：" + (TypeParse.parseDouble(itemVal)-rightVal) + "\\n";
					}
					else if(chekItemRule.getCompareType().equals("!=")) {
						 bSucess = (TypeParse.parseDouble(itemVal) != rightVal); 
						 Message ="指标值应不等于规则值!  差值：" + (TypeParse.parseDouble(itemVal)-rightVal) + "\\n";
					}else{
						bSucess = false;
						Message = "比较符错误!\\n";
					}
				}
			}
			
			if(!bSucess)
				statistic++;
				
			// 将校验结果写入指标数据Item_Data表
			if(ObjItemData != null) {
				if(!bSucess) {
					ReflectOperation.setFieldValue(ObjItemData, "strCheckState", "3");
					ReflectOperation.setFieldValue(ObjItemData, "strCheckResult", Message + "\\n" + ruleMessage);
				}
				else {
					ReflectOperation.setFieldValue(ObjItemData, "strCheckState", "2");
					ReflectOperation.setFieldValue(ObjItemData, "strCheckResult", "");
				}
			
				//Object oldTObject = RequestManager.getTOject(); // 更新数据到指标数据表
				//RequestManager.setTOject(ObjItemData);
				IParamVoidResultExecute dao2 = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
				dao2.paramVoidResultExecute(new Object[]{ ObjItemData, null});
				//RequestManager.setTOject(oldTObject);
			}
		}

		return statistic;
    }
	
	public String getConditionSql(List<ConditionClass> conditionClassList){
		
		String condition = " where ";
		if(conditionClassList == null || conditionClassList.size() == 0){
			return condition;
		}
		
		for(ConditionClass conditionClass : conditionClassList){
			if(!StringUtils.isBlank(conditionClass.getConditionJoinType()) && !conditionClass.getConditionJoinType().equals(ApplicationManager.getEmptySelectValue())){
				condition = condition +" "+conditionClass.getConditionJoinType()+" ";
			}
			
			if(!StringUtils.isBlank(conditionClass.getCompareType()) && !conditionClass.getCompareType().equals(ApplicationManager.getEmptySelectValue())){
				condition = condition +conditionClass.getFieldName()+" = " + conditionClass.getStrValue()+" ";
			}
		}
		return condition;
	}
	
	private List<CheckFieldParamField> checkFieldParamFieldList;
	private List<CheckItemRule> checkItemRuleList;

	private String defaultLogicDaoBeanId;

	public List<CheckFieldParamField> getCheckFieldParamFieldList() {
		return checkFieldParamFieldList;
	}

	public void setCheckFieldParamFieldList(
			List<CheckFieldParamField> checkFieldParamFieldList) {
		this.checkFieldParamFieldList = checkFieldParamFieldList;
	}

	public void setDefaultLogicDaoBeanId(String defaultLogicDaoBeanId) {
		this.defaultLogicDaoBeanId = defaultLogicDaoBeanId;
	}

	public String getDefaultLogicDaoBeanId() {
		return defaultLogicDaoBeanId;
	}

	public void setCheckItemRuleList(List<CheckItemRule> checkItemRuleList) {
		this.checkItemRuleList = checkItemRuleList;
	}

	public List<CheckItemRule> getCheckItemRuleList() {
		return checkItemRuleList;
	}


}
