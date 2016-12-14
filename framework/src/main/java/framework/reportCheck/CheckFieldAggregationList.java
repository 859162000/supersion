package framework.reportCheck;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;

import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;

public class CheckFieldAggregationList {
	private String discription;
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}

	private List<CheckFieldParamField> checkFieldParamFieldList;
	private List<CheckFieldAggregation> checkFieldAggregationList;
	public List<CheckFieldParamField> getCheckFieldParamFieldList() {
		return checkFieldParamFieldList;
	}
	public void setCheckFieldParamFieldList(
			List<CheckFieldParamField> checkFieldParamFieldList) {
		this.checkFieldParamFieldList = checkFieldParamFieldList;
	}
	public List<CheckFieldAggregation> getCheckFieldAggregationList() {
		return checkFieldAggregationList;
	}
	public void setCheckFieldAggregationList(
			List<CheckFieldAggregation> checkFieldAggregationList) {
		this.checkFieldAggregationList = checkFieldAggregationList;
	}
	
	public CheckFieldAggregationList(){
		this.checkFieldAggregationList=new ArrayList<CheckFieldAggregation>();
		this.checkFieldParamFieldList=new ArrayList<CheckFieldParamField>();
	}
	@SuppressWarnings("unchecked")
	private List<Double> getAggregationValueList( List<CheckFieldAggregationField> checkFieldAggregationFieldList,IParamObjectResultExecute objectResultExecute,MessageResult messageResult) throws Exception{
		List<Double> valueList = new ArrayList<Double>();
		for(CheckFieldAggregationField checkFieldAggregationField : checkFieldAggregationFieldList){
			
			if(!StringUtils.isBlank(checkFieldAggregationField.getProcedureName())&&!StringUtils.isBlank(checkFieldAggregationField.getProcedureParam())){
				String aggregationProcedureName = checkFieldAggregationField.getProcedureName();
				String aggregationProcedureParam = checkFieldAggregationField.getProcedureParam();
				
				LogicParamManager.setSqlQuery(getPrcedureString(aggregationProcedureName,aggregationProcedureParam));
				Map<String,Object> lineProcedureParamMap = getProcedureParamMap(aggregationProcedureParam);
				LogicParamManager.setProcedureParam(lineProcedureParamMap);
				
				List<?> aggregationList = (List<?>) objectResultExecute.paramObjectResultExecute(null);
				for(Object obj : aggregationList){
					Map<String,Object> mapObject = (Map<String,Object>)obj;
					Object total = mapObject.get(checkFieldAggregationField.getProcedureResultField().toUpperCase());
					if(total==null){
						valueList.add(0.0);
					}
					else{
						valueList.add(Double.parseDouble(mapObject.get(checkFieldAggregationField.getProcedureResultField().toUpperCase()).toString()));
					}
				}
			}
			else{
				valueList.add(Double.parseDouble(checkFieldAggregationField.getValue()));
			}
		}
		return valueList;
		
	}
	
	// 取出聚合校验每列的值
	@SuppressWarnings("unchecked")
	private List<Map<String,Double>> getAggregationValueMap(String splitField,
			List<CheckFieldAggregationField> checkFieldAggregationFieldList,IParamObjectResultExecute objectResultExecute,
			MessageResult messageResult) throws Exception{
		int nRows = 0;
		List<Map<String,Double>> valueMapList = new ArrayList<Map<String,Double>>();
		
		// 先找出常量列，目前只支持一个常量列
		Map<String,Double> valueMapConst = new HashMap<String,Double>();
		Double constVal = new Double(0);
		for(CheckFieldAggregationField checkFieldAggregationField : checkFieldAggregationFieldList){ // 每列
			if(checkFieldAggregationField.getValue() != null 
					&&!checkFieldAggregationField.getValue().equals("")){ // 如果是常量列
				constVal = Double.parseDouble(checkFieldAggregationField.getValue());
				break;
			}
		}
		
		for(CheckFieldAggregationField checkFieldAggregationField : checkFieldAggregationFieldList){ // 每列
			Map<String,Double> valueMap = new HashMap<String,Double>();
			// 存储过程和参数都正确的情况
			if(!StringUtils.isBlank(checkFieldAggregationField.getProcedureName())
					&&!StringUtils.isBlank(checkFieldAggregationField.getProcedureParam())){
				String aggregationProcedureName = checkFieldAggregationField.getProcedureName();
				String aggregationProcedureParam = checkFieldAggregationField.getProcedureParam();
				
				LogicParamManager.setSqlQuery(getPrcedureString(aggregationProcedureName,aggregationProcedureParam));
				Map<String,Object> lineProcedureParamMap = getProcedureParamMap(aggregationProcedureParam);
				LogicParamManager.setProcedureParam(lineProcedureParamMap);
				
				List<?> aggregationList = (List<?>) objectResultExecute.paramObjectResultExecute(null);
				
				if(nRows < aggregationList.size()) // 记录最大列的行数
					nRows = aggregationList.size();
				
				for(Object obj : aggregationList){ // 每行数据
					Map<String,Object> mapObject = (Map<String,Object>)obj;
					Object total=mapObject.get(checkFieldAggregationField.getProcedureResultField().toUpperCase()); // 结果值
					
					
					String[] keyStrings = splitField.split(",");
					boolean isKeyNull = false;
					String keyStr = "";
					for(int j=0;j<keyStrings.length;j++){ // 每个split字段，组装成一个标识行的keyStr
						String key = keyStrings[j];
						Object keyValue = mapObject.get(key.toUpperCase()); // 根据字段名取得值
						if(keyValue==null || StringUtils.isBlank(keyValue.toString())){
							isKeyNull = true;
							break;
						}
					
						if(!StringUtils.isBlank(keyStr)){
							keyStr += "+";
						}
						keyStr += keyValue.toString();
					}
					if(isKeyNull){
						continue;
					}
			
					if(keyStr!=null){
						if(total==null){
							valueMap.put(keyStr, 0.0);
						}
						else{
							valueMap.put(keyStr, Double.parseDouble(mapObject.get(checkFieldAggregationField.getProcedureResultField().toUpperCase()).toString()));
						}
					}
					
					// 处理常量列
					if(constVal > 0 && valueMapConst.get(keyStr) == null) { // 加过的就不加了
						valueMapConst.put(keyStr, constVal);
					}
				}
				
				valueMapList.add(valueMap);
				if(constVal > 0)
					valueMapList.add(valueMapConst);
			} // end if

		} // end for
		return valueMapList;
	}
	
	private String getPrcedureString(String procedureName,String procedureParam){
		String[] procedureParams = procedureParam.split(",");
		String callprcedure = "";
		
			for(int i =0;i<procedureParams.length;i++){
				if(!StringUtils.isBlank(callprcedure)){
					callprcedure += ",";
				}
				callprcedure += "?";
			}
		String prcedureString = "{call " + procedureName +"(" + callprcedure + ")}";
		return prcedureString;
	}
	
	private Map<String,Object> getProcedureParamMap(String procedureParam){
		String[] procedureParams = procedureParam.split(",");
		Map<String,Object> procedureParamMap = new LinkedHashMap<String,Object>();
		if(!StringUtils.isBlank(procedureParam)){
			for(int i =0;i<procedureParams.length;i++){
				boolean isHaveValue = false;
				for(CheckFieldParamField checkFieldParamField : this.checkFieldParamFieldList){
					if(checkFieldParamField.getParamName().equals(procedureParams[i])){
//						if(StringUtils.isBlank(checkFieldParamField.getValue())){
//							procedureParamMap.put("", "参数" + checkFieldParamField.getParamName() + "：未能获取值。");
//							return procedureParamMap;
//						}
						// lanyuesheng  页面参数传空值，默认值为空，则赋空值
						if(StringUtils.isBlank(checkFieldParamField.getValue())
								&& StringUtils.isBlank(checkFieldParamField.getDefaultValue())){
							procedureParamMap.put(procedureParams[i], null);
						}
						if(checkFieldParamField.getType().equals("") || checkFieldParamField.getType().equals("1")){
							procedureParamMap.put(procedureParams[i], checkFieldParamField.getValue());
						}
						else if(checkFieldParamField.getType().equals("2")){
							try{
								Date procedureDate = java.sql.Date.valueOf(checkFieldParamField.getValue());
								procedureParamMap.put(procedureParams[i], procedureDate);
							}
							catch(Exception ex){
								procedureParamMap.put("", "参数" + checkFieldParamField.getParamName() + "：时间类型错误。");
								return procedureParamMap;              
							}
						}
						isHaveValue = true;
						break;
					}
				}
				if(!isHaveValue){
					procedureParamMap.put(procedureParams[i], null);
				}
			}
		}
		return procedureParamMap;
	}
	
	public Integer Check(MessageResult messageResult) throws Exception{

		String defaultLogicDaoBeanId = LogicParamManager.getDefaultLogicDaoBeanId();
		IParamObjectResultExecute objectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean(defaultLogicDaoBeanId);

		int currentSize = messageResult.getMessageList().size();
		int errorCount = 0;
		for(CheckFieldAggregation checkFieldaggregation : this.checkFieldAggregationList){
			List<String> messageList = null;
			if(!StringUtils.isBlank(checkFieldaggregation.getLeftSplitField()) 
					&& !StringUtils.isBlank(checkFieldaggregation.getRightSplitField())){
				// 调用配置中的存储过程取得左右数据
				List<Map<String,Double>> leftValueMapList = getAggregationValueMap(checkFieldaggregation.getLeftSplitField(),
						checkFieldaggregation.getLeftFieldList(),objectResultExecute,messageResult);
				List<Map<String,Double>> rightValueMapList = getAggregationValueMap(checkFieldaggregation.getRightSplitField(),
						checkFieldaggregation.getRightFieldList(),objectResultExecute,messageResult);
				
				for(Map.Entry<String, Double> map : leftValueMapList.get(0).entrySet()){ // 每一行数据
					String key = map.getKey();
					List<Double> leftValueList = new ArrayList<Double>();
					boolean isContain = true;
					for(int i=0;i<leftValueMapList.size();i++){ // 左值每一列
						Map<String,Double> leftValueMap = leftValueMapList.get(i);
						if(leftValueMap.containsKey(key)){ // key相同的列累加
							leftValueList.add((Double)leftValueMap.get(key));
						}
						else{
							isContain = false;
							break;
						}
					}
					List<Double> rightValueList = new ArrayList<Double>();
					if(isContain && rightValueMapList.size() > 0){ // 右值每一列
						for(int i=0;i<rightValueMapList.size();i++){
							Map<String,Double> rightValueMap = rightValueMapList.get(i);
							if(rightValueMap.containsKey(key)){ // key相同的列累加
								rightValueList.add((Double)rightValueMap.get(key));
							}
							else{
								isContain = false;
								break;
							}
						}
					}
					if(isContain){
						messageList = checkFieldaggregation.Check(key.toString(),leftValueList,rightValueList);
						for(String message : messageList){
							messageResult.getMessageList().add(message);
						}
						errorCount += messageList.size();
					}
				}
			}
			else{
				List<Double> leftValueList = getAggregationValueList(checkFieldaggregation.getLeftFieldList(),objectResultExecute,messageResult);
				List<Double> rightValueList = getAggregationValueList(checkFieldaggregation.getRightFieldList(),objectResultExecute,messageResult);
				messageList = checkFieldaggregation.Check(null,leftValueList,rightValueList);
				for(String message : messageList){
					messageResult.getMessageList().add(message);
				}
				errorCount += messageList.size();
			}
		}
		messageResult.getMessageList().add(currentSize,"错误条数：" + errorCount);
		
		return errorCount;
	}
}