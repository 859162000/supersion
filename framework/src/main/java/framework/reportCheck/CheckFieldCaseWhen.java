package framework.reportCheck;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;

import framework.helper.TypeParse;
import framework.reportCheck.helper.CheckUtils;

public class CheckFieldCaseWhen {
	
	public CheckFieldCaseWhen(){
		checkFieldCaseWhenFieldList = new ArrayList<CheckFieldCaseWhenField>();
	}
	
    private String name;
    private String errorMsg;
    private String discription;
	private String whenType;
	private String whenCompareType;
	private String caseValue;
	private String caseValueScope;
	private String caseCompareType;
	private String whenValue;
	private String whenValueScope;
	private String whenCompareValue;
	private String caseValueDiscription;
	
	private List<CheckFieldCaseWhenField> checkFieldCaseWhenFieldList;
	
	private String getShowValue(Map<String,Map<String,String>> valueMap,String fieldName,String value){
		if(valueMap == null || fieldName == null || StringUtils.isBlank(value) || valueMap.get(fieldName) == null){
			return value;
		}
		else{
			String showValue = valueMap.get(fieldName).get(value);
			if(showValue == null){
				return "无效数据";
			}
			else{
				return showValue;
			}
		}
	}
	
	private String getShowValueScope(Map<String,Map<String,String>> valueMap,String fieldName,String valueScope){
		if(valueMap == null || fieldName == null || StringUtils.isBlank(valueScope)){
			return valueScope;
		}
		else{
			String[] splitStr = valueScope.split(",");
			String showValueScope = "";
			for(int i=0;i<splitStr.length;i++){
				showValueScope += getShowValue(valueMap,fieldName,splitStr[i]);
				if(i != splitStr.length - 1){
					showValueScope += ",";
				}
			}
			return showValueScope;
		}
	}
	
	private List<String> getWhenValueSet(String whenValue,String whenValueScope){
		List<String> whenValueSet = new ArrayList<String>();
		if(!StringUtils.isBlank(whenValue)){
			whenValueSet.add(whenValue);
		}
		else if(!StringUtils.isBlank(whenValueScope)){
			String[] splitStr = whenValueScope.split(",");
			for(int i=0;i<splitStr.length;i++){
				whenValueSet.add(splitStr[i]);
			}
		}
		
		return whenValueSet;
	}
	
	private List<String> getCaseValueSet(String caseValue,String caseValueScope){
		List<String> caseValueSet = new ArrayList<String>();
		if(!StringUtils.isBlank(caseValue)){
			caseValueSet.add(caseValue);
		}
		
		else if(!StringUtils.isBlank(caseValueScope)){
			String[] splitStr = caseValueScope.split(",");
			for(int i=0;i<splitStr.length;i++){
				caseValueSet.add(splitStr[i]);
			}
		}
		return caseValueSet;
	}
	
	private String getMessage(Map<String,Map<String,String>> valueMap,String fieldName,String flag, String value, String valueScope, String compareType){
		if(flag.equals("null")){
			return "为空";
		}
		else if(flag.equals("notNull")){
			return "为非空";
		}
		else if(flag.equals("yearEndDate")){
			return "为年末时间";
		}
		else if(flag.equals("halfYearEndDate")){
			return "为半年末时间";
		}
		else if(flag.equals("seasonEndDate")){
			return "为季末时间";
		}		
		else if(flag.equals("monthEndDate")){
			return "为月末时间";
		}
		
		else{
			String frontStr = "";
			if(StringUtils.isBlank(compareType)){
				frontStr = "为";
			}
			else{
				frontStr = compareType;
			}
			if(!StringUtils.isBlank(value)){
				return frontStr + getShowValue(valueMap,fieldName,value);
			}
			else{
				return frontStr + getShowValueScope(valueMap,fieldName,valueScope);
			}	
		}

	}
	
	private boolean isTrue(String flag,Object value){
		if(flag.equals("null")){
			if(value == null || StringUtils.isBlank(value.toString())){
				return true;
			}
		}
		else if(flag.equals("notNull")){
			if(value != null && !StringUtils.isBlank(value.toString())){
				return true;
			}
		}
		
		else if(flag.equals("yearEndDate")){
			if(value != null && !StringUtils.isBlank(value.toString()) ){
				try {
					SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyyMMdd");
					Date date=bartDateFormat.parse(value.toString());
					String value1=bartDateFormat.format(date);
					String year=value1.substring(0, 4);
					if(value1.equals(year+"1231")){
						return true;
					}	
				}
				catch (Exception e) {
					e.fillInStackTrace();
				}
			}
		}
		else if(flag.equals("halfYearEndDate")){
			if(value != null && !StringUtils.isBlank(value.toString()) ){	
				try {
					SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyyMMdd");
					Date date=bartDateFormat.parse(value.toString());
					String  value1=bartDateFormat.format(date);
					String year=value1.substring(0, 4);
					if(value1.equals(year+"0630")||value1.equals(year+"1231")){
						return true;
					}	
				} catch (Exception e) {
					e.fillInStackTrace();
				}
				return false;
			}
		}
		else if(flag.equals("seasonEndDate")){
			if(value != null && !StringUtils.isBlank(value.toString()) ){
				try {
					SimpleDateFormat bartDateFormat1 = new SimpleDateFormat("yyyyMMdd");
					Date date=bartDateFormat1.parse(value.toString());
					String value1=bartDateFormat1.format(date);
					String year=value1.substring(0, 4);
					
					List<String> seasonEnd=new ArrayList<String>() ;
					String year1=year+"0331";
					String year2=year+"0630";
					String year3=year+"0930";
					String year4=year+"1231";
					seasonEnd.add(year1);
					seasonEnd.add(year2);
					seasonEnd.add(year3);
					seasonEnd.add(year4);
					if(seasonEnd.contains(value1)){
						return true;
					}
				} catch (Exception e) {
					e.fillInStackTrace();
				}
				return false;	
			}
		}
		else if(flag.equals("monthEndDate")){
			if(value != null && !StringUtils.isBlank(value.toString())){
				try {	
					SimpleDateFormat bartDateFormat1 = new SimpleDateFormat("yyyyMMdd");
					Date date=bartDateFormat1.parse(value.toString());
					String value1=bartDateFormat1.format(date);
					String year=value1.substring(0, 4);
					String month=value1.substring(4, 6);
					int year1=Integer.parseInt(year);
					int month1=Integer.parseInt(month);
					String years=bartDateFormat1.format(CheckFieldCaseWhen.getLastDayOfMonth(year1,month1));
					if(value1.equals(years)){
						return true;
					}
				} catch (Exception e) {
					e.fillInStackTrace();
				}
					return false;
			}
		}
		return false;
	}
	
	private boolean isWhenTrue(String flag, Object value, List<String> valueSet,String compareType){
		// 先判断比较符
		if(!compareType.equals("")) {
			Double left, right;
			try {
				left = Double.parseDouble(value.toString());
				right = Double.parseDouble(flag.toString());
			}
			catch(Exception ex) {
				if(value != null && flag != null){
					if(compareType.equals("=")){
						if(value.toString().equals(flag)){
							return true;
						}
					}
					else if(compareType.equals("!=")){
						if(!value.toString().equals(flag)){
							return true;
						}
					}
				}
				return false;
			}
			
			if(CheckHelper.isFail(compareType, left, right)){
				return true;
			}
			else
				return false;
		}
		
		// 比较类型为空，为字符串比较
		boolean isTrue = isTrue(flag, value); // 特殊值是否匹配
		if(isTrue){
			return isTrue;
		}
		else{ // 集合匹配
			if(valueSet.contains(value)){
				return true;
			}
		}
		return false;
	}
	
	private boolean isWhenTrue(String flag, Object value, List<String> valueSet){
		return isWhenTrue(flag,value,valueSet,this.getWhenCompareType());
	}
	
	private boolean isCaseFail(String flag, Object value, List<String> valueSet){
		// 先判断比较符
		if(!this.getCaseCompareType().equals("")) {
			Double left, right;
			try {
				left = Double.parseDouble(value.toString());
				right = Double.parseDouble(flag.toString());
			}
			catch(Exception ex) {
				return false;
			}
			
			if(CheckHelper.isFail(this.getCaseCompareType(), left, right)){
				return false;
			}
			else
				return true;
		}
		
		boolean isTrue = isTrue(flag,value);
		if(isTrue){
			return !isTrue;
		}
		else{
			// 如果为bigdecimal类型（oracle中保留两位小数），则比较不出，用tostring解决   value为空，则异常，先判断value不为空
			if(value == null || !valueSet.contains(value.toString())){
				return true;
			}
		}
		return false;
	}
	
	private void CheckType1(Object caseValue,List<String> whenValueSet,List<String> caseValueSet,
			List<String> messageList,Map<String,Object> mapObject,String uniqueStr, Boolean bNot,Map<String,Map<String,String>> valueMap){
		if(this.checkFieldCaseWhenFieldList.size() == 1){
			CheckFieldCaseWhenField checkFieldCaseWhenField = this.checkFieldCaseWhenFieldList.get(0);
			Object whenValue = mapObject.get(checkFieldCaseWhenField.getName().toUpperCase());
			if(!bNot) { // 为..校验
				if(isWhenTrue(whenValueSet.get(0),whenValue,whenValueSet) && isCaseFail(caseValueSet.get(0),caseValue,caseValueSet)){
					if(caseValue == null) caseValue="null";
					String whenMessage = getMessage(valueMap,checkFieldCaseWhenField.getName(),whenValueSet.get(0),this.whenValue.toString(),this.whenValueScope, this.getWhenCompareType());
					String caseMessage = getMessage(valueMap,this.getName(),caseValueSet.get(0),this.caseValue.toString(),this.caseValueScope, this.getCaseCompareType());
					messageList.add(uniqueStr + "当" + checkFieldCaseWhenField.getDiscription() + whenMessage + "时，" + this.getDiscription() 
							+ "应" + caseMessage + ", 现为" + getShowValue(valueMap,this.getName(),caseValue.toString()));
					
				}
			}
			else { // 不为...校验
				if(isWhenTrue(whenValueSet.get(0),whenValue,whenValueSet)&& !isCaseFail(caseValueSet.get(0),caseValue,caseValueSet)){
					if(caseValue == null) caseValue="null";
					String whenMessage = getMessage(valueMap,checkFieldCaseWhenField.getName(),whenValueSet.get(0),this.whenValue.toString(),this.whenValueScope, this.getWhenCompareType());
					String caseMessage = getMessage(valueMap,this.getName(),caseValueSet.get(0),this.caseValue.toString(),this.caseValueScope, this.getCaseCompareType());
					messageList.add(uniqueStr + "当" + checkFieldCaseWhenField.getDiscription() + whenMessage + "时，" + this.getDiscription() 
							+ "不应" + caseMessage + ", 现为" + getShowValue(valueMap,this.getName(),caseValue.toString()));
				}
			}
			
		}
	}
	
	// when为多列运算
	private void CheckType2(Object caseValue,List<String> whenValueSet,List<String> caseValueSet,List<String> messageList,Map<String,Object> mapObject,String uniqueStr,Map<String,Map<String,String>> valueMap){
		Double totalWhenValue = null;
		String totalDiscription = "";
		Double whenCompareValue = null;
		try{
			for(CheckFieldCaseWhenField checkFieldCaseWhenField : this.checkFieldCaseWhenFieldList){
				Object whenValue = mapObject.get(checkFieldCaseWhenField.getName().toUpperCase());
				if(whenValue != null && !StringUtils.isBlank(whenValue.toString())){
					Double doubleWhenValue = Double.parseDouble(whenValue.toString());
					if(!StringUtils.isBlank(checkFieldCaseWhenField.getWhenValueRule()) && checkFieldCaseWhenField.getWhenValueRule().equals("2")){
						doubleWhenValue = Math.abs(doubleWhenValue);
					}
					if(totalWhenValue == null){
						totalWhenValue = 0.0;
					}
					totalWhenValue += doubleWhenValue;
				}

				if(checkFieldCaseWhenField.getJoinType() == null || checkFieldCaseWhenField.getJoinType().equals("1")){
					if(!StringUtils.isBlank(totalDiscription)){
						totalDiscription += " + ";
					}
				}
				else if(checkFieldCaseWhenField.getJoinType().equals("2")){
					totalDiscription += " - ";
				}
				
				totalDiscription += checkFieldCaseWhenField.getDiscription();
				if(whenValue != null && !StringUtils.isBlank(whenValue.toString())){
					totalDiscription += "(" + whenValue.toString() +")";
				}
				else{
					totalDiscription += "(null)";
				}
				if(!StringUtils.isBlank(checkFieldCaseWhenField.getWhenValueRule()) && checkFieldCaseWhenField.getWhenValueRule().equals("2")){
					totalDiscription += "的绝对值";
				}
			}
			whenCompareValue = Double.parseDouble(this.getWhenCompareValue());
		}
		catch(Exception ex){
			messageList.add(uniqueStr + "运算异常");
			return;
		}
		totalDiscription += " (运算结果" + totalWhenValue + ")";
		totalDiscription += " " + this.getWhenCompareType() + " " + this.getWhenCompareValue();
		if(CheckHelper.isFail(this.getWhenCompareType(),totalWhenValue,whenCompareValue)){
			if(isCaseFail(caseValueSet.get(0),caseValue,caseValueSet)){
				String caseMessage = getMessage(valueMap,this.getName(),caseValueSet.get(0),this.caseValue,this.caseValueScope, this.getCaseCompareType());
				messageList.add(uniqueStr + "当" + totalDiscription + "时，" + this.getDiscription() + "应" + caseMessage);
			}
		}
	}

	// 多个when条件
	private void CheckType3(Object caseValue,List<String> caseValueSet,List<String> messageList,
			Map<String,Object> mapObject,String uniqueStr, Boolean bNot,Map<String,Map<String,String>> valueMap){
		boolean isWhenConditionTrue = true;
		String totalDiscription = ""; 
		for(CheckFieldCaseWhenField checkFieldCaseWhenField : this.checkFieldCaseWhenFieldList){
			List<String> whenValueSet = getWhenValueSet(checkFieldCaseWhenField.getWhenValue(),checkFieldCaseWhenField.getWhenValueScope());
			if(!StringUtils.isBlank(totalDiscription)){
				totalDiscription += " 并且 ";
			}
			Object whenValue = mapObject.get(checkFieldCaseWhenField.getName().toUpperCase());
			
			if(isWhenTrue(whenValueSet.get(0),whenValue,whenValueSet,checkFieldCaseWhenField.getWhenCompareType())){
				String whenMessage = getMessage(valueMap,checkFieldCaseWhenField.getName(),whenValueSet.get(0),checkFieldCaseWhenField.getWhenValue(),
						checkFieldCaseWhenField.getWhenValueScope(), checkFieldCaseWhenField.getWhenCompareType());
				totalDiscription += checkFieldCaseWhenField.getDiscription() + whenMessage;
			}
			else{
				isWhenConditionTrue = false;
			}
		}
		
		if(isWhenConditionTrue){
			if(!bNot &&  caseValueSet.size()>0 && isCaseFail(caseValueSet.get(0),caseValue,caseValueSet)){
				String caseMessage = getMessage(valueMap,this.getName(),caseValueSet.get(0),this.caseValue,this.caseValueScope, this.getCaseCompareType());
				messageList.add(uniqueStr + "当" + totalDiscription + "时，" + this.getDiscription() + "应" + caseMessage);
			}
			else if(bNot && caseValueSet.size()>0 && !isCaseFail(caseValueSet.get(0),caseValue,caseValueSet)){
				String caseMessage = getMessage(valueMap,this.getName(),caseValueSet.get(0),this.caseValue,this.caseValueScope, this.getCaseCompareType());
				messageList.add(uniqueStr + "当" + totalDiscription + "时，" + this.getDiscription() + "不应" + caseMessage);
			}
		}
	}

	// when为两个时间比较
	private void CheckType4(Object caseValue,List<String> whenValueSet,List<String> caseValueSet,List<String> messageList,Map<String,Object> mapObject,String uniqueStr,Map<String,Map<String,String>> valueMap){
		if(this.checkFieldCaseWhenFieldList.size() == 2){
    		CheckFieldCaseWhenField checkFieldCaseWhenField1 = this.checkFieldCaseWhenFieldList.get(0);
    		CheckFieldCaseWhenField checkFieldCaseWhenField2 = this.checkFieldCaseWhenFieldList.get(1);
    		
    		Object whenValue1 = null;
    		if(StringUtils.isBlank(checkFieldCaseWhenField1.getName())){
    			whenValue1 = checkFieldCaseWhenField1.getWhenValue();
    		}
    		else{
    			whenValue1 = mapObject.get(checkFieldCaseWhenField1.getName().toUpperCase());
    		}
    		
    		Object whenValue2 = null;
    		if(StringUtils.isBlank(checkFieldCaseWhenField2.getName())){
    			whenValue2 = checkFieldCaseWhenField2.getWhenValue();
    		}
    		else{
    			whenValue2 = mapObject.get(checkFieldCaseWhenField2.getName().toUpperCase());
    		}
    		
    		if(whenValue1 != null && !StringUtils.isBlank(whenValue1.toString()) && whenValue2 != null && !StringUtils.isBlank(whenValue2.toString())){
    			Date date1 = TypeParse.parseDate(whenValue1.toString());
    			Date date2 = TypeParse.parseDate(whenValue2.toString());
    			if(date1.equals(TypeParse.maxDate()) || date2.equals(TypeParse.maxDate())){
    				messageList.add(uniqueStr + "时间运算异常");
    			}
    			if(CheckHelper.isReverseTrue(this.getWhenCompareType(),date1,date2)){
    				if(isCaseFail(caseValueSet.get(0),caseValue,caseValueSet)){
    					String caseMessage = getMessage(valueMap,this.getName(),caseValueSet.get(0),this.caseValue,this.caseValueScope, this.getCaseCompareType());
    					messageList.add(uniqueStr + "当" + checkFieldCaseWhenField1.getDiscription() + "(" + whenValue1.toString() + ") " + this.getWhenCompareType() + " " + checkFieldCaseWhenField2.getDiscription() + "(" + whenValue2.toString() + ")" + "时，" + this.getDiscription() + "应" + caseMessage);
    				}
    			}
    		}
		}
	}
	
	// when(当条件)为模糊匹配串
	private void CheckType5(Object caseValue, List<String> whenValueSet, List<String> caseValueSet,
			List<String> messageList, Map<String,Object> mapObject, String uniqueStr,Map<String,Map<String,String>> valueMap){
		if(this.checkFieldCaseWhenFieldList.size() == 1){
			CheckFieldCaseWhenField checkFieldCaseWhenField = this.checkFieldCaseWhenFieldList.get(0);
			Object whenValue = mapObject.get(checkFieldCaseWhenField.getName().toUpperCase());
			{
				for(String whenVal:whenValueSet) {
					if(whenValue != null && whenValue.toString().contains(whenVal)
							&& isCaseFail(caseValueSet.get(0),caseValue,caseValueSet)) {
						if(caseValue == null) caseValue = "null";
						//String whenMessage = getMessage(whenValueSet.get(0),this.whenValue.toString(),this.whenValueScope, this.getWhenCompareType());
						String caseMessage = getMessage(valueMap,this.getName(),caseValueSet.get(0),this.caseValue.toString(),this.caseValueScope, this.getCaseCompareType());
						messageList.add(uniqueStr + "当" + checkFieldCaseWhenField.getDiscription() + "含" + getShowValue(valueMap,this.getName(),whenVal.toString()) + "时，" + this.getDiscription() 
								+ "应" + caseMessage+ ", 现为" + getShowValue(valueMap,this.getName(),caseValue.toString()));
						                                               
					}
				}
			}
		}
	}
	//caseValue 为一个特殊的需要校验的值
	private void CheckType8(Object caseValue,List<String> whenValueSet,List<String> caseValueSet,
			List<String> messageList,Map<String,Object> mapObject,String uniqueStr, Boolean bNot,Map<String,Map<String,String>> valueMap){
		if(this.checkFieldCaseWhenFieldList.size() == 1){
			CheckFieldCaseWhenField checkFieldCaseWhenField = this.checkFieldCaseWhenFieldList.get(0);
			Object whenValue = mapObject.get(checkFieldCaseWhenField.getName().toUpperCase());
			if(!bNot) { // 为..校验
				if(isWhenTrue(whenValueSet.get(0),whenValue,whenValueSet) && isCaseFail(caseValueSet.get(0),caseValue,caseValueSet)){
					if(caseValue == null){
						caseValue="null";
					}else if(caseValueSet.get(0).equals("systemIDCardNo")){//systemIDCardNo身份证号码
						if(getShowValue(valueMap,this.getName(),caseValue.toString()) != null && !StringUtils.isBlank(getShowValue(valueMap,this.getName(),caseValue.toString()))){
							try {
								String checkMessage=CheckUtils.IDCardCheck(getShowValue(valueMap,this.getName(),caseValue.toString()));
								if(!checkMessage.equals("")){
									String whenMessage = getMessage(valueMap,checkFieldCaseWhenField.getName(),whenValueSet.get(0),this.whenValue.toString(),this.whenValueScope, this.getWhenCompareType());
									messageList.add(uniqueStr + "当" + checkFieldCaseWhenField.getDiscription() + whenMessage + "时，" + this.getDiscription() + checkMessage );
								}
							} catch (Exception e) {
								e.fillInStackTrace();
							}
						}
					}
					else if(caseValueSet.get(0).equals("systemZZJGDM")){//systemZZJGDM组织机构代码
						if(getShowValue(valueMap,this.getName(),caseValue.toString()) != null && !StringUtils.isBlank(getShowValue(valueMap,this.getName(),caseValue.toString()))){
							try {
								String checkMessage=CheckUtils.OrganizationCodeCheck(caseValue.toString());
								if(!checkMessage.equals("")){
									String whenMessage = getMessage(valueMap,checkFieldCaseWhenField.getName(),whenValueSet.get(0),this.whenValue.toString(),this.whenValueScope, this.getWhenCompareType());
									messageList.add(uniqueStr + "当" + checkFieldCaseWhenField.getDiscription() + whenMessage + "时，" + this.getDiscription() + checkMessage );
								}
							} catch (Exception e) {
								e.fillInStackTrace();
							}
						}
					}
					else if(caseValueSet.get(0).equals("systemDKKBM")){//systemDKKBM贷款卡编码
						if(getShowValue(valueMap,this.getName(),caseValue.toString()) != null && !StringUtils.isBlank(getShowValue(valueMap,this.getName(),caseValue.toString()))){
							try {
								String checkMessage=CheckUtils.CheckDKKBM(caseValue.toString());
								if(!checkMessage.equals("")){
									String whenMessage = getMessage(valueMap,checkFieldCaseWhenField.getName(),whenValueSet.get(0),this.whenValue.toString(),this.whenValueScope, this.getWhenCompareType());
									messageList.add(uniqueStr + "当" + checkFieldCaseWhenField.getDiscription() + whenMessage + "时，" + this.getDiscription() + checkMessage );
								}
							} catch (Exception e) {
								e.fillInStackTrace();
							}
						}
					}
					else if(caseValueSet.get(0).equals("systemJGXYDM")){//systemJGXYDM机构信用代码
						if(getShowValue(valueMap,this.getName(),caseValue.toString()) != null && !StringUtils.isBlank(getShowValue(valueMap,this.getName(),caseValue.toString()))){
							try {
								String checkMessage="";
								byte[] byteArry = new byte[0];
				        		byteArry=caseValue.toString().getBytes();
				        		if(!CheckUtils.checkCreditCode(byteArry).equals("")){
				        			checkMessage=CheckUtils.checkCreditCode(byteArry);
				        		}
								if(!checkMessage.equals("")){
									String whenMessage = getMessage(valueMap,checkFieldCaseWhenField.getName(),whenValueSet.get(0),this.whenValue.toString(),this.whenValueScope, this.getWhenCompareType());
									messageList.add(uniqueStr + "当" + checkFieldCaseWhenField.getDiscription() + whenMessage + "时，" + this.getDiscription() + checkMessage );
								}
							} catch (Exception e) {
								e.fillInStackTrace();
							}
						}
					}
					else if(caseValueSet.get(0).equals("systemJRJGDM")){//systemJRJGDM金融机构代码
						if(getShowValue(valueMap,this.getName(),caseValue.toString()) != null && !StringUtils.isBlank(getShowValue(valueMap,this.getName(),caseValue.toString()))){
							try {
								String checkMessage="";
								byte[] byteArry = new byte[0];
			            		byteArry=caseValue.toString().getBytes();
			            		if(!CheckUtils.checkJRJG(byteArry).equals("")){
			            			checkMessage=CheckUtils.checkJRJG(byteArry);
			            		}
								
								if(!checkMessage.equals("")){
									String whenMessage = getMessage(valueMap,checkFieldCaseWhenField.getName(),whenValueSet.get(0),this.whenValue.toString(),this.whenValueScope, this.getWhenCompareType());
									messageList.add(uniqueStr + "当" + checkFieldCaseWhenField.getDiscription() + whenMessage + "时，" + this.getDiscription() + checkMessage );
								}
							} catch (Exception e) {
								e.fillInStackTrace();
							}
						}
					}
					else if(caseValueSet.get(0).equals("systemKHXKZHZH")){//systemKHXKZHZH开户许可证核准号
						if(getShowValue(valueMap,this.getName(),caseValue.toString()) != null && !StringUtils.isBlank(getShowValue(valueMap,this.getName(),caseValue.toString()))){
							try {
								String checkMessage="";
								byte[] byteArry = new byte[0];
			            		byteArry=caseValue.toString().getBytes();
			            		if(!CheckUtils.checkSaccBaseLicNO(byteArry).equals("")){
			            			checkMessage=CheckUtils.checkSaccBaseLicNO(byteArry);
			            		}
								
								if(!checkMessage.equals("")){
									String whenMessage = getMessage(valueMap,checkFieldCaseWhenField.getName(),whenValueSet.get(0),this.whenValue.toString(),this.whenValueScope, this.getWhenCompareType());
									messageList.add(uniqueStr + "当" + checkFieldCaseWhenField.getDiscription() + whenMessage + "时，" + this.getDiscription() + checkMessage );
								}
							} catch (Exception e) {
								e.fillInStackTrace();
							}
						}
					}
					else if(caseValueSet.get(0).equals("systemNSRSBH")){//systemNSRSBH纳税人识别号
						if(getShowValue(valueMap,this.getName(),caseValue.toString()) != null && !StringUtils.isBlank(getShowValue(valueMap,this.getName(),caseValue.toString()))){
							try {
								String checkMessage="";
								byte[] byteArry = new byte[0];
			            		byteArry=caseValue.toString().getBytes();
			            		if(!CheckUtils.checkTaxNO(byteArry).equals("")){
			            			checkMessage=CheckUtils.checkTaxNO(byteArry);
			            		}
								
								if(!checkMessage.equals("")){
									String whenMessage = getMessage(valueMap,checkFieldCaseWhenField.getName(),whenValueSet.get(0),this.whenValue.toString(),this.whenValueScope, this.getWhenCompareType());
									messageList.add(uniqueStr + "当" + checkFieldCaseWhenField.getDiscription() + whenMessage + "时，" + this.getDiscription() + checkMessage );
								}
							} catch (Exception e) {
								e.fillInStackTrace();
							}
						}
					}
					
				}
			}
		}
	}
	
	//caseValue为一个字段
	private void CheckType9(Object caseValue,List<String> caseValueSet,List<String> messageList,
			Map<String,Object> mapObject,String uniqueStr, Boolean bNot,Map<String,Map<String,String>> valueMap){
		boolean isWhenConditionTrue = true;
		String totalDiscription = ""; 
		Object caseCaseFieldValue = mapObject.get(this.getCaseValue().toUpperCase()); 
		if(!bNot) { // 为..校验
			for(CheckFieldCaseWhenField checkFieldCaseWhenField : this.checkFieldCaseWhenFieldList){
				List<String> whenValueSet = getWhenValueSet(checkFieldCaseWhenField.getWhenValue(),checkFieldCaseWhenField.getWhenValueScope());
				if(!StringUtils.isBlank(totalDiscription)){
					totalDiscription += " 并且 ";
				}
				Object whenValue = mapObject.get(checkFieldCaseWhenField.getName().toUpperCase());
				
				if(isWhenTrue(whenValueSet.get(0),whenValue,whenValueSet,checkFieldCaseWhenField.getCaseCompareType())){
					String whenMessage = getMessage(valueMap,checkFieldCaseWhenField.getName(),whenValueSet.get(0),checkFieldCaseWhenField.getWhenValue(),
							checkFieldCaseWhenField.getWhenValueScope(), checkFieldCaseWhenField.getCaseCompareType());
					totalDiscription += checkFieldCaseWhenField.getDiscription() + whenMessage;
				}
				else{
					isWhenConditionTrue = false;
				}
			}
		}
		
		
		if(isWhenConditionTrue){
			if(!bNot && caseCaseFieldValue!=null && isCaseFail(caseCaseFieldValue.toString(),caseValue,caseValueSet)){
				String caseMessage = getMessage(valueMap,this.getName(),caseValueSet.get(0),this.caseValueDiscription,this.caseValueScope, this.getCaseCompareType());
				messageList.add(uniqueStr + "当" + totalDiscription + "时，" + this.getDiscription() + "应" + caseMessage);
			}
			else if(bNot  && caseCaseFieldValue!=null && !isCaseFail(caseCaseFieldValue.toString(),caseValue,caseValueSet)){
				String caseMessage = getMessage(valueMap,this.getName(),caseValueSet.get(0),this.caseValueDiscription,this.caseValueScope, this.getCaseCompareType());
				messageList.add(uniqueStr + "当" + totalDiscription + "时，" + this.getDiscription() + "不应" + caseMessage);
			}
		}
	}
	
	public List<String> Check(Map<String,Object> mapObject,String uniqueStr,Map<String,Map<String,String>> valueMap) throws Exception{
		List<String> messageList = new ArrayList<String>();
		
		Object caseValue = mapObject.get(this.name.toUpperCase());
		List<String> whenValueSet = getWhenValueSet(this.whenValue,this.whenValueScope);
		List<String> caseValueSet = getCaseValueSet(this.caseValue,this.caseValueScope);
	
		
		if(StringUtils.isBlank(this.whenType) || this.whenType.equals("1")){
			CheckType1(caseValue,whenValueSet,caseValueSet,messageList,mapObject,uniqueStr, false, valueMap);
		}
		else if(this.whenType.equals("2")){ // 多个字段运算
			CheckType2(caseValue,whenValueSet,caseValueSet,messageList,mapObject,uniqueStr, valueMap);
		}
		else if(this.whenType.equals("3")){ // 多个when的并关系
			CheckType3(caseValue,caseValueSet,messageList,mapObject,uniqueStr, false, valueMap);
		}
        else if(this.whenType.equals("4")){ // 两个时间比较关系
        	CheckType4(caseValue,whenValueSet,caseValueSet,messageList,mapObject,uniqueStr, valueMap);
        }
        else if(this.whenType.equals("5")){ // "5"为"不为", 与"1"相反
        	CheckType1(caseValue,whenValueSet,caseValueSet,messageList,mapObject,uniqueStr, true, valueMap);
        }
        else if(this.whenType.equals("6")){ // "6"为"不为", 与"3"相反
        	CheckType3(caseValue,caseValueSet,messageList,mapObject,uniqueStr, true, valueMap);
        }
        else if(this.whenType.equals("7")){ // "7"当...含有串 case的内容需要为真
        	CheckType5(caseValue, whenValueSet, caseValueSet, messageList, mapObject, uniqueStr, valueMap);
        }
        else if(this.whenType.equals("8")){ // "8"当..为..，caseValue为一个特定的校验值
        	CheckType8(caseValue,whenValueSet,caseValueSet,messageList,mapObject,uniqueStr, false, valueMap);
        }
        else if(this.whenType.equals("9")){ // "9"为当字段A为…，且字段B为..,字段C大于字段D (caseValue为一个字段)
			CheckType9(caseValue,caseValueSet,messageList,mapObject,uniqueStr, false, valueMap);
		}
		List<String> strMessageList = new ArrayList<String>();
		if(messageList.size()>0){
			if(this.getErrorMsg() != null && !StringUtils.isBlank(this.getErrorMsg())) {
				strMessageList.add(this.getErrorMsg());
			} else {
				for (String strMessage : messageList) {
					if(strMessage.contains("!=") || strMessage.contains(">=") || strMessage.contains("<=") || strMessage.contains("<") || strMessage.contains(">") || strMessage.contains("=") || strMessage.contains("notNull") || strMessage.contains("null")){
						strMessageList.add(strMessage.replace("!=","不等于").replace(">=", "大于等于").replace("<=", "小于等于").replace("<", "小于").replace(">", "大于").replace("=", "等于").replace("notNull", "非空").replace("null", "空"));
					}
					else {
						strMessageList.add(strMessage);
					}
				}
			}
		}
		return strMessageList;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWhenType() {
		return whenType;
	}

	public void setWhenType(String whenType){
		this.whenType = whenType;
	}

	public String getCaseValue() {
		return caseValue;
	}

	public void setCaseValue(String caseValue) {
		this.caseValue = caseValue;
	}

	public String getCaseValueScope() {
		return caseValueScope;
	}

	public void setCaseValueScope(String caseValueScope) {
		this.caseValueScope = caseValueScope;
	}

	public String getWhenValue() {
		return whenValue;
	}

	public void setWhenValue(String whenValue) {
		this.whenValue = whenValue;
	}

	public String getWhenValueScope() {
		return whenValueScope;
	}

	public void setWhenValueScope(String whenValueScope) {
		this.whenValueScope = whenValueScope;
	}

	public String getWhenCompareValue() {
		return whenCompareValue;
	}

	public void setWhenCompareValue(String whenCompareValue) {
		this.whenCompareValue = whenCompareValue;
	}

	public List<CheckFieldCaseWhenField> getCheckFieldCaseWhenFieldList() {
		return checkFieldCaseWhenFieldList;
	}

	public void setCheckFieldCaseWhenFieldList(
			List<CheckFieldCaseWhenField> checkFieldCaseWhenFieldList) {
		this.checkFieldCaseWhenFieldList = checkFieldCaseWhenFieldList;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getDiscription() {
		if(StringUtils.isBlank(discription)){
			return this.name;
		}
		return discription;
	}

	public void setWhenCompareType(String whenCompareType) {
		this.whenCompareType = whenCompareType;
	}

	public String getWhenCompareType() {
		return whenCompareType;
	}
	
	
	public static Date getLastDayOfMonth(int year, int month) { 
		Calendar cal = Calendar.getInstance(); 
			cal.set(Calendar.YEAR, year);// 年 
		    cal.set(Calendar.MONTH, month - 1);// 月，因为Calendar里的月是从0开始，所以要减1 
		    cal.set(Calendar.DATE, 1);// 日，设为一号 
		    cal.add(Calendar.MONTH, 1);// 月份加一，得到下个月的一号 
		    cal.add(Calendar.DATE, -1);// 下一个月减一为本月最后一天 
			   return cal.getTime();// 获得月末是几号 
	}

	public void setCaseCompareType(String caseeCompareType) {
		this.caseCompareType = caseeCompareType;
	}

	public String getCaseCompareType() {
		return caseCompareType;
	}

	public String getCaseValueDiscription() {
		return caseValueDiscription;
	}

	public void setCaseValueDiscription(String caseValueDiscription) {
		this.caseValueDiscription = caseValueDiscription;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
