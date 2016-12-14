package framework.reportCheck;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.xwork.StringUtils;

public class CheckFieldAggregation {

	private String dataPeriod;
	private String operationType;
	private String leftSplitField;
	private String rightSplitField;
	private Double startRate;
	private Double endRate;
	private Double startValue;
	private Double endValue;
	private String compareType;
	private String splitDiscription;
	private List<CheckFieldParamField> checkFieldParamFieldList;
	private String procedureName;
	private String procedureParam;
	private String procedureDiscription;
	private String fieldType;

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public String getProcedureParam() {
		return procedureParam;
	}

	public void setProcedureParam(String procedureParam) {
		this.procedureParam = procedureParam;
	}

	public String getProcedureDiscription() {
		return procedureDiscription;
	}

	public void setProcedureDiscription(String procedureDiscription) {
		this.procedureDiscription = procedureDiscription;
	}

	public List<CheckFieldParamField> getCheckFieldParamFieldList() {
		return checkFieldParamFieldList;
	}

	public void setCheckFieldParamFieldList(
			List<CheckFieldParamField> checkFieldParamFieldList) {
		this.checkFieldParamFieldList = checkFieldParamFieldList;
	}

	public class MessageAndValue {
		private String message;
		private Double value;

		public void setMessage(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		public void setValue(Double value) {
			this.value = value;
		}

		public Double getValue() {
			return value;
		}
	}

	private MessageAndValue GetMessage(
			List<CheckFieldAggregationField> fieldList, List<Double> valueList) {
		MessageAndValue messageAndValue = new MessageAndValue();
		String message = "";
		Double refValue = 0.0;
		for (int i = 0; i < fieldList.size(); i++) {
			CheckFieldAggregationField checkFieldAggregationField = fieldList
					.get(i);
			if (StringUtils.isBlank(checkFieldAggregationField.getJoinType())
					|| checkFieldAggregationField.getJoinType().equals("1")) {
				refValue += i < valueList.size() ? valueList.get(i) : 0;
				if (!StringUtils.isBlank(message)) {
					message += " + ";
				}
			} else if (checkFieldAggregationField.getJoinType().equals("2")) {
				refValue -= i < valueList.size() ? valueList.get(i) : 0;
				if (!StringUtils.isBlank(message)) {
					message += " - ";
				}
			}
			message += checkFieldAggregationField.getDiscription();
			if (!StringUtils.isBlank(checkFieldAggregationField
					.getProcedureName())) {
				message += checkFieldAggregationField.getProcedureDiscription();
			}
			message += "(" + (i < valueList.size() ? valueList.get(i) : 0)
					+ ")";
		}
		messageAndValue.setMessage(message);

		refValue = new java.math.BigDecimal(refValue).setScale(8,
				BigDecimal.ROUND_HALF_UP).doubleValue();

		messageAndValue.setValue(refValue);
		return messageAndValue;
	}

	public List<String> Check(String splitValue, List<Double> leftValueList,
			List<Double> rightValueList) {

		List<String> messageList = new ArrayList<String>();
		if (StringUtils.isBlank(compareType)) {
			compareType = "=";
		}
		
		MessageAndValue leftMessageAndValue = GetMessage(this
				.getLeftFieldList(), leftValueList);
		MessageAndValue rightMessageAndValue = GetMessage(this
				.getRightFieldList(), rightValueList);
		String leftMessage = leftMessageAndValue.getMessage();
		String rightMessage = rightMessageAndValue.getMessage();
		
		Double leftValue = leftMessageAndValue.getValue();
		if (!StringUtils.isBlank(splitValue)) {
			leftMessage = "相同" + this.getSplitDiscription() + "(" + splitValue
					+ ")下，" + leftMessage;
		}
		
		Double rightValue = rightMessageAndValue.getValue();
		if (compareType.equals("1")) {
			Double operationValue = leftValue - rightValue;
			boolean isTrue = true;
			String startMessage;
			String endMessage;
			if (this.getStartValue() != null) {
				if (operationValue < this.getStartValue()) {
					isTrue = false;
				}
				startMessage = this.getStartValue().toString();
			} else {
				startMessage = "负无穷";
			}
			if (this.getEndValue() != null) {
				if (operationValue > this.getEndValue()) {
					isTrue = false;
				}
				endMessage = this.getEndValue().toString();
			} else {
				endMessage = "正无穷";
			}

			if (!isTrue) {
				messageList.add(leftMessage + " (左运算结果" + new BigDecimal(leftValue).toString() + ") - "
						+ rightMessage + " (右运算结果" + new BigDecimal(rightValue).toString() + ") 的差值("
						+ new BigDecimal(operationValue).toString() + ")应在" + startMessage + "到"
						+ endMessage + "之间");
			}
		} else if (compareType.equals("2")) {
			if (leftValue != null && rightValue != null && leftValue != 0) {
				Double operationValue = ((leftValue - rightValue) / leftValue) * 100;
				boolean isTrue = true;
				String startMessage;
				String endMessage;
				if (this.getStartRate() != null) {
					if (operationValue < this.getStartRate()) {
						isTrue = false;
					}
					startMessage = this.getStartRate().toString() + "%";
				} else {
					startMessage = "负无穷";
				}

				if (this.getEndRate() != null) {
					if (operationValue > this.getEndRate()) {
						isTrue = false;
					}
					endMessage = this.getEndRate().toString() + "%";
				} else {
					endMessage = "正无穷";
				}

				if (!isTrue) {
					messageList.add(leftMessage + " (左运算结果" + new BigDecimal(leftValue).toString()
							+ ") 与 " + rightMessage + " (右运算结果" + new BigDecimal(rightValue).toString()
							+ ") 的变动率(" + new BigDecimal(operationValue).toString() + "%)应在"
							+ startMessage + "到" + endMessage + "之间");
				}
			}
		} else {
			if (leftValue != null && rightValue != null) {
				if (!CheckHelper.isFail(this.getCompareType(),
						leftValue, rightValue)) {
					Double absValue = new java.math.BigDecimal(Math
							.abs(leftValue - rightValue)).setScale(8,
							BigDecimal.ROUND_HALF_UP).doubleValue();
					messageList.add(leftMessage + " (左运算结果" + new BigDecimal(leftValue).toString() + ") "
							+ this.getCompareType() + " " + rightMessage
							+ " (右运算结果" + new BigDecimal(rightValue).toString() + ") " + "，差值(" + new BigDecimal(absValue).toString()
							+ ")，校验失败");
				}
			}
		}
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

	public static boolean isValidDate(String date) {
		String[] strVals = date.split("-");
		try {
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

	private List<CheckFieldAggregationField> leftFieldList;
	private List<CheckFieldAggregationField> rightFieldList;

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public Double getEndRate() {
		return endRate;
	}

	public void setDataPeriod(String dataPeriod) {
		this.dataPeriod = dataPeriod;
	}

	public void setStartRate(Double startRate) {
		this.startRate = startRate;
	}

	public String getCompareType() {
		return compareType;
	}

	public void setCompareType(String compareType) {
		this.compareType = compareType;
	}

	public CheckFieldAggregation() {
		leftFieldList = new ArrayList<CheckFieldAggregationField>();
		rightFieldList = new ArrayList<CheckFieldAggregationField>();
		checkFieldParamFieldList = new ArrayList<CheckFieldParamField>();
		checkFieldAggregationList = new ArrayList<CheckFieldAggregation>();
	}

	public List<CheckFieldAggregationField> getRightFieldList() {
		return rightFieldList;
	}

	public void setRightFieldList(
			List<CheckFieldAggregationField> rightFieldList) {
		this.rightFieldList = rightFieldList;
	}

	public String getDataPeriod() {
		return dataPeriod;
	}

	public Double getStartRate() {
		return startRate;
	}

	public void setEndRate(Double endRate) {
		this.endRate = endRate;
	}

	public Double getStartValue() {
		return startValue;
	}

	public void setStartValue(Double startValue) {
		this.startValue = startValue;
	}

	public Double getEndValue() {
		return endValue;
	}

	public void setEndValue(Double endValue) {
		this.endValue = endValue;
	}

	public void setLeftSplitField(String leftSplitField) {
		this.leftSplitField = leftSplitField;

	}

	public String getLeftSplitField() {
		return leftSplitField;
	}

	public void setRightSplitField(String rightSplitField) {
		this.rightSplitField = rightSplitField;
	}

	public String getRightSplitField() {
		return rightSplitField;
	}

	public void setLeftFieldList(List<CheckFieldAggregationField> leftFieldList) {
		this.leftFieldList = leftFieldList;
	}

	public List<CheckFieldAggregationField> getLeftFieldList() {
		return leftFieldList;
	}

	public void setSplitDiscription(String splitDiscription) {
		this.splitDiscription = splitDiscription;
	}

	public String getSplitDiscription() {
		return splitDiscription;
	}

	private List<CheckFieldAggregation> checkFieldAggregationList;

	public List<CheckFieldAggregation> getCheckFieldAggregationList() {
		return checkFieldAggregationList;
	}

	public void setCheckFieldAggregationList(
			List<CheckFieldAggregation> checkFieldAggregationList) {
		this.checkFieldAggregationList = checkFieldAggregationList;
	}

}
