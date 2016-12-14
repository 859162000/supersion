package framework.reportCheck;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;

import framework.helper.TypeParse;

public class CheckFieldLine {

	public class MessageAndValue {
		private String message;
		private BigDecimal value;

		public void setMessage(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		public BigDecimal getValue() {
			return value;
		}

		public void setValue(BigDecimal value) {
			this.value = value;
		}

		
	}

	public CheckFieldLine() {
		leftFieldList = new ArrayList<CheckFieldLineField>();
		rightFieldList = new ArrayList<CheckFieldLineField>();
		
	}

	private String errorMsg;
	private String fieldType;
	private String compareType;
	private BigDecimal startRate;
	private BigDecimal endRate;
	private BigDecimal startValue;
	private BigDecimal endValue;
	private List<CheckFieldLineField> leftFieldList;
	private List<CheckFieldLineField> rightFieldList;
	private String procedureName;
	private String procedureParam;
	private String procedureDiscription;
	private String ignoreVal;

	private MessageAndValue GetMessage(Map<String, Object> mapObject,
			List<CheckFieldLineField> fieldList) {
		MessageAndValue messageAndValue = new MessageAndValue();
		String message = "";
		BigDecimal refValue = null;
		for (CheckFieldLineField checkFieldLineField : fieldList) {
			Object value = null;
			if (StringUtils.isBlank(checkFieldLineField.getName())) {
				value = checkFieldLineField.getValue();
			} else {
				value = mapObject.get(checkFieldLineField.getName().toUpperCase());
			}
			if (value != null && !StringUtils.isBlank(value.toString())) {
				BigDecimal doubleValue = new BigDecimal(0.0);
				String valuex=value.toString();
				String regex = "([a-zA-Z]*)";
				
				////ZK 20141103 modify  判断是否为单个字符之间的比较////////
				if(((String) valuex).trim().matches(regex) && String.valueOf(valuex.length()).equals("1")){

					char c=valuex.toCharArray()[0];

					 doubleValue = new BigDecimal(c);
				}
				else{
					 doubleValue = new BigDecimal(valuex);
				}
				
				if (!StringUtils.isBlank(checkFieldLineField.getValueRule())
						&& checkFieldLineField.getValueRule().equals("2")) {
					
					//doubleValue =new BigDecimal(Math.abs(doubleValue.doubleValue()));
					doubleValue =doubleValue.abs();
				}
				if (refValue == null) {
					refValue =new BigDecimal(0.0);
				}

				if (StringUtils.isBlank(checkFieldLineField.getJoinType())
						|| checkFieldLineField.getJoinType().equals("1")) {
					//refValue+=doubleValue  测试是否对等
					refValue=refValue.add(doubleValue);
				} else if (checkFieldLineField.getJoinType().equals("2")) {
					//refValue-=doubleValue  测试是否对等
					refValue=refValue.subtract(doubleValue);
				}
			}

			if (StringUtils.isBlank(checkFieldLineField.getJoinType())
					|| checkFieldLineField.getJoinType().equals("1")) {
				if (!StringUtils.isBlank(message)) {
					message += " + ";
				}
			} else if (checkFieldLineField.getJoinType().equals("2")) {
				message += " - ";
			}

			message += checkFieldLineField.getDiscription();

			if (value != null && !StringUtils.isBlank(value.toString())) {
				message += "(" + value.toString() + ")";
			} else {
				message += "(null)";
			}
			if (!StringUtils.isBlank(checkFieldLineField.getValueRule())
					&& checkFieldLineField.getValueRule().equals("2")) {
				message += "的绝对值";
			}
		}
		messageAndValue.setMessage(message);

		/*if (refValue != null) {
			refValue = new java.math.BigDecimal(refValue).setScale(8,
					BigDecimal.ROUND_HALF_UP).doubleValue();
		}*/

		messageAndValue.setValue(refValue);
		return messageAndValue;
	}

	public List<String> Check(Map<String, Object> mapObject, String uniqueStr)
			throws Exception {
		List<String> messageList = new ArrayList<String>();

		if (StringUtils.isBlank(compareType)) {
			compareType = "=";
		}
		
		if ( (StringUtils.isBlank(this.getFieldType())
				|| this.getFieldType().equals("1"))
				&& compareType.equals("=")
				&& leftFieldList.size() == 1
				&& rightFieldList.size() == 1) { // 串、数值并且两边只有一个字段直接判断相等
			
			Object leftValue;
			if (StringUtils.isBlank(leftFieldList.get(0).getName())) {
				leftValue = leftFieldList.get(0).getValue();
			} else {
				leftValue = mapObject.get(leftFieldList.get(0).getName().toUpperCase());
			}
			
			Object rightValue;
			if (StringUtils.isBlank(rightFieldList.get(0).getName())) {
				rightValue = rightFieldList.get(0).getValue();
			} else {
				rightValue = mapObject.get(rightFieldList.get(0).getName().toUpperCase());
			}
			
			if( (leftValue == null && rightValue != null)
					|| (leftValue != null && rightValue == null)
					|| (leftValue !=null && rightValue !=null && !leftValue.toString().equals(rightValue.toString()))) {
				if(leftValue == null) leftValue = "null";
				if(rightValue == null) rightValue = "null";
				messageList.add(leftFieldList.get(0).getDiscription() + "("
						+ leftValue.toString() + ") 和  "
						+ rightFieldList.get(0).getDiscription() 
						+ "(" + rightValue.toString() + ")" + " 应相等");
				}
		}
		else if (StringUtils.isBlank(this.getFieldType())
				|| this.getFieldType().equals("1")) { // 串、数值
			try {
				MessageAndValue leftMessageAndValue = GetMessage(mapObject,
						leftFieldList);
				MessageAndValue rightMessageAndValue = GetMessage(mapObject,
						rightFieldList);
				String leftMessage = leftMessageAndValue.getMessage();
				String rightMessage = rightMessageAndValue.getMessage();
			
				
				BigDecimal leftValue =leftMessageAndValue.getValue();             
				BigDecimal rightValue =rightMessageAndValue.getValue(); 
				if (compareType.equals("1")) {
					if (leftValue != null && rightValue != null) {
						BigDecimal operationValue =  leftValue.subtract(rightValue);
						boolean isTrue = true;
						String startMessage;
						String endMessage;
						if (this.getStartValue() != null) {
							if (operationValue.compareTo(this.getStartValue())==-1) {
								isTrue = false;
							}
							startMessage = this.getStartValue().toString();
						} else {
							startMessage = "负无穷";
						}
						if (this.getEndValue() != null) {
							if (operationValue.compareTo(this.getEndValue())==1) {
								isTrue = false;
							}
							endMessage = this.getEndValue().toString();
						} else {
							endMessage = "正无穷";
						}
						
						if (!isTrue) {
							messageList.add(uniqueStr + leftMessage + " (左运算结果"
									+ leftValue.toString() + ") - " + rightMessage
									+ " (右运算结果" + rightValue.toString() + ") 的差值("
									+ operationValue.toString() + ")应在" + startMessage
									+ "到" + endMessage + "之间");
						}
					}
				} else if (compareType.equals("2")) {
					if (leftValue != null && rightValue != null
							&& leftValue.intValue() != 0) {
						BigDecimal operationValue = ((leftValue.subtract(rightValue)).divide(leftValue)).multiply(new BigDecimal(100));
						boolean isTrue = true;
						String startMessage;
						String endMessage;
						if (this.getStartRate() != null) {
							if (operationValue.compareTo(this.getStartRate())==-1) {
								isTrue = false;
							}
							startMessage = this.getStartRate().toString() + "%";
						} else {
							startMessage = "负无穷";
						}

						if (this.getEndRate() != null) {
							if (operationValue.compareTo( this.getEndRate())==1) {
								isTrue = false;
							}
							endMessage = this.getEndRate().toString() + "%";
						} else {
							endMessage = "正无穷";
						}

						if (!isTrue) {
							messageList.add(uniqueStr + leftMessage + " (左运算结果"
									+ leftValue.toString() + ") 与 " + rightMessage
									+ " (右运算结果" + rightValue.toString() + ") 的变动率("
									+ operationValue.toString() + "%)应在" + startMessage
									+ "到" + endMessage + "之间");
						}
					}
				} else if (compareType.equals("3")) {
					if (leftValue != null && rightValue != null
							&& leftValue.intValue() != 0) {
						//Double operationValue = ((leftValue - rightValue) / leftValue) * 100;
						BigDecimal operationValue = ((leftValue.subtract(rightValue)).divide(leftValue)).multiply(new BigDecimal(100));
						boolean isTrue = true;
						String startMessage;
						String endMessage;
						if (this.getStartRate() != null) {
							if (operationValue.compareTo( this.getStartRate())==-1) {
								isTrue = false;
							}
							startMessage = this.getStartRate().toString() + "%";
						} else {
							startMessage = "负无穷";
						}
						if (this.getEndRate() != null) {
							if (operationValue.compareTo(this.getEndRate())==1) {
								isTrue = false;
							}
							endMessage = this.getEndRate().toString() + "%";
						} else {
							endMessage = "正无穷";
						}

						if (!isTrue) {
							messageList.add(uniqueStr + leftMessage + " (左运算结果"
									+ leftValue.toString() + ") 与 " + rightMessage
									+ " (右运算结果" + rightValue.toString() + ") 的变动率("
									+ operationValue.toString() + "%)应在" + startMessage
									+ "到" + endMessage + "之间");
						}
					}
				} else {
					if (leftValue != null && rightValue != null) {
						if (!CheckHelper.isFail(this.getCompareType(),
								leftValue, rightValue)) {
							BigDecimal absValue = (leftValue.subtract( rightValue)).abs();
							messageList.add(uniqueStr + leftMessage + " (左运算结果"
									+ leftValue.toString() + ")应该 "
									+ this.getCompareType() + " "
									+ rightMessage + " (右运算结果" + rightValue.toString()
									+ ") " + "，差值(" + absValue.toString() + ")，校验失败");
						}
					}
				}
			} catch (Exception ex) {
				messageList.add(uniqueStr + "运算异常");
				List<String> strMessageList = new ArrayList<String>();
				if(messageList.size()>0){
					for (String strMessage : messageList) {
						if(strMessage.contains("!=") || strMessage.contains(">=") || strMessage.contains("<=") || strMessage.contains("<") || strMessage.contains(">") || strMessage.contains("=") || strMessage.contains("notNull") || strMessage.contains("null")){
							strMessageList.add(strMessage.replace("!=","不等于").replace(">=", "大于等于").replace("<=", "小于等于").replace("<", "小于").replace(">", "大于").replace("=", "等于").replace("notNull", "非空").replace("null", "空"));
						}
						else {
							strMessageList.add(strMessage);
						}
					}
				}
				
				return strMessageList;
			}
		} else if (this.getFieldType().equals("2")) { // 日期
			// 左右都只有一个日期列
			if (leftFieldList.size() == 1 && rightFieldList.size() == 1) {
				CheckFieldLineField leftField = leftFieldList.get(0);
				CheckFieldLineField rightField = rightFieldList.get(0);
				
				Object leftValue = null;
				if (StringUtils.isBlank(leftField.getName())) {
					leftValue = leftField.getValue();
				} else {
					leftValue = mapObject.get(leftField.getName().toUpperCase());
				}
				
				Object rightValue = null;
				if (StringUtils.isBlank(rightField.getName())) {
					rightValue = rightField.getValue();
				} else {
					rightValue = mapObject.get(rightField.getName().toUpperCase());
				}
				
				// 左右运算
				if (leftValue != null
						&& !StringUtils.isBlank(leftValue.toString())
						&& rightValue != null
						&& !StringUtils.isBlank(rightValue.toString())
						&& !leftValue.toString().equals(getIgnoreVal()) // 跳过忽略值
						&& !rightValue.toString().equals(getIgnoreVal()) // 跳过忽略值
						) {
					Date leftDate = TypeParse.parseDate(leftValue.toString());
					Date rightDate = TypeParse.parseDate(rightValue.toString());

					if (!isValidDate(leftValue.toString())
							|| !isValidDate(rightValue.toString())) {
						messageList.add(uniqueStr + "日期运算异常");
					} else {
						if (!CheckHelper.isReverseTrue(compareType, leftDate,
								rightDate)) {
							messageList.add(uniqueStr
									+ leftField.getDiscription() + "应该 "
									+ compareType + " "
									+ rightField.getDiscription() + " 校验失败");
						}
					}
				}
			}

			// 左边或右边有两个时间字段运算
			if (leftFieldList.size() == 2 || rightFieldList.size() == 2) {
				Object date1 = null;
				Object date2 = null;
				String days = null;

				if (leftFieldList.size() == 2) {
					CheckFieldLineField leftField = leftFieldList.get(0);
					CheckFieldLineField leftField1 = leftFieldList.get(1);
					CheckFieldLineField rightField = rightFieldList.get(0);
					if (StringUtils.isBlank(leftField.getName())
							&& StringUtils.isBlank(leftField1.getName())) {
						date1 = leftField.getValue();
						date2 = leftField1.getValue();
					} else {
						date1 = mapObject.get(leftField.getName().toUpperCase());
						date2 = mapObject.get(leftField1.getName().toUpperCase());
					}

					if (StringUtils.isBlank(rightField.getName())) {
						days = rightField.getValue();
					} else {
						days = (String) mapObject.get(rightField.getName().toUpperCase());
					}
					if (date1 != null && !StringUtils.isBlank(date1.toString())
							&& date2 != null
							&& !StringUtils.isBlank(date2.toString())
							&& !StringUtils.isBlank(days)) {
						Date dat1 = TypeParse.parseDate(date1.toString());
						Date dat2 = TypeParse.parseDate(date2.toString());
						int day = Integer.parseInt(days);
						if (!isValidDate(date1.toString())
								|| !isValidDate(date2.toString())) {
							messageList.add(uniqueStr + "日期运算异常");
						} else {
							if (!CheckFieldLine.isReverseTrue1(dat1, dat2, day)) {
								messageList
										.add(uniqueStr
												+ leftField.getDiscription()
												+ "-"
												+ leftField1.getDiscription()
												+ " " + "应该"
												+ this.getCompareType() + " "
												+ rightField.getDiscription()
												+ " 校验失败");
							}
						}
					}
				}
				
				if (rightFieldList.size() == 2) {
					CheckFieldLineField rightField = rightFieldList.get(0);
					CheckFieldLineField rightField1 = rightFieldList.get(1);
					CheckFieldLineField leftField = leftFieldList.get(0);
					if (StringUtils.isBlank(rightField.getName())
							&& StringUtils.isBlank(rightField1.getName())) {
						date1 = rightField.getValue();
						date2 = rightField1.getValue();
					} else {
						date1 = mapObject.get(rightField.getName().toUpperCase());
						date2 = mapObject.get(rightField1.getName().toUpperCase());
					}

					if (StringUtils.isBlank(rightField.getName())) {
						days = leftField.getValue();
					} else {
						days = (String) mapObject.get(leftField.getName().toUpperCase());
					}
					if (date1 != null && !StringUtils.isBlank(date1.toString())
							&& date2 != null
							&& !StringUtils.isBlank(date2.toString())
							&& !StringUtils.isBlank(days)) {

						Date dat1 = TypeParse.parseDate(date1.toString());
						Date dat2 = TypeParse.parseDate(date2.toString());

						int day = Integer.parseInt(days);
						if (!isValidDate(date1.toString())
								|| !isValidDate(date2.toString())) {
							messageList.add(uniqueStr + "日期运算异常");
						} else {
							if (!CheckFieldLine.isReverseTrue1(dat1, dat2, day)) {
								messageList.add(uniqueStr
										+ rightField.getDiscription() + "-"
										+ rightField1.getDiscription() + " "
										+ "应该" + this.getCompareType() + " "
										+ leftField.getDiscription() + " 校验失败");
							}
						}
					}
				}
			}
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

	public static boolean isReverseTrue1(Date enddate, Date begindate, int days) {
		long millisecond = enddate.getTime() - begindate.getTime();
		int day = (int) (millisecond / 24L / 60L / 60L / 1000L);
		int day1 = Math.abs(day);// 转换成绝对值
		if (days <= day1) {
			return true;
		}
		return false;
	}

	public static boolean isValidDate(String date) {
		try {
			String[] strVals = date.split("-");
			if (strVals.length == 1) {
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
				format.setLenient(false);
    			format.parse(date.toString().trim());
			} else if (strVals.length == 3) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				format.setLenient(false);
    			format.parse(date.toString().trim());
			}
			else
				return false;

		}
		catch (Exception ex) {
			return false;
		}

		return true;
	}

	public String getCompareType() {
		return compareType;
	}

	public void setCompareType(String compareType) {
		this.compareType = compareType;
	}

	public BigDecimal getStartRate() {
		return startRate;
	}

	public void setStartRate(BigDecimal startRate) {
		this.startRate = startRate;
	}
	
	public void setStartRate(Double startRate) {
		if(startRate != null){
			this.startRate = new BigDecimal(startRate);
		}
	}

	public BigDecimal getEndRate() {
		return endRate;
	}

	public void setEndRate(BigDecimal endRate) {
		this.endRate = endRate;
	}
	
	public void setEndRate(Double endRate) {
		if(endRate !=null)
		{
			this.endRate = new BigDecimal(endRate);
		}
		
	}

	public BigDecimal getStartValue() {
		return startValue;
	}

	public void setStartValue(BigDecimal startValue) {
		this.startValue = startValue;
	}
	
	public void setStartValue(Double startValue) {
		if(startValue!=null)
		{
			this.startValue =new BigDecimal(startValue);
		}
	}

	public BigDecimal getEndValue() {
		return endValue;
	}

	public void setEndValue(BigDecimal endValue) {
		this.endValue = endValue;
	}
	
	public void setEndValue(Double endValue) {
		if(endValue!=null)
		{
			this.endValue =new BigDecimal(endValue);
		}
		
	}

	public List<CheckFieldLineField> getLeftFieldList() {
		return leftFieldList;
	}

	public void setLeftFieldList(List<CheckFieldLineField> leftFieldList) {
		this.leftFieldList = leftFieldList;
	}

	public List<CheckFieldLineField> getRightFieldList() {
		return rightFieldList;
	}

	public void setRightFieldList(List<CheckFieldLineField> rightFieldList) {
		this.rightFieldList = rightFieldList;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureParam(String procedureParam) {
		this.procedureParam = procedureParam;
	}

	public String getProcedureParam() {
		return procedureParam;
	}

	public void setProcedureDiscription(String procedureDiscription) {
		this.procedureDiscription = procedureDiscription;
	}

	public String getProcedureDiscription() {
		if (StringUtils.isBlank(procedureDiscription)) {
			return this.procedureName;
		}
		return procedureDiscription;
	}

	public void setIgnoreVal(String ignoreVal) {
		this.ignoreVal = ignoreVal;
	}

	public String getIgnoreVal() {
		return ignoreVal;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
