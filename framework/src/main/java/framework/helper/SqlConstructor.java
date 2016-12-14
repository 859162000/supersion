package framework.helper;

import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;

import framework.interfaces.ApplicationManager;

public class SqlConstructor {
	
	public static class ConditionClass{
		
		private String fieldName;
		private String compareType;
		private String strValue;
		private String conditionJoinType;
		
		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
		public String getFieldName() {
			return fieldName;
		}
		public void setStrValue(String strValue) {
			this.strValue = strValue;
		}
		public String getStrValue() {
			return strValue;
		}
		public void setConditionJoinType(String conditionJoinType) {
			this.conditionJoinType = conditionJoinType;
		}
		public String getConditionJoinType() {
			return conditionJoinType;
		}
		public void setCompareType(String compareType) {
			this.compareType = compareType;
		}
		public String getCompareType() {
			return compareType;
		}
	}

	public static String getConditionSql(List<ConditionClass> conditionClassList){
		
		String condition = "";
		if(conditionClassList == null || conditionClassList.size() == 0){
			return condition;
		}
		
		for(ConditionClass conditionClass : conditionClassList){
			if(!StringUtils.isBlank(conditionClass.getConditionJoinType()) && !conditionClass.getConditionJoinType().equals(ApplicationManager.getEmptySelectValue())){
				if(conditionClass.getConditionJoinType().equals("1")){
					condition += " AND ";
				}
				else if(conditionClass.getConditionJoinType().equals("2")){
					condition += " OR ";
				}
				else if(conditionClass.getConditionJoinType().equals("11")){
					condition += " ( ";
				}
				else if(conditionClass.getConditionJoinType().equals("12")){
					condition += " ) ";
				}
				else
					condition += " " + conditionClass.getConditionJoinType() + " ";
			}
			else{
				condition += conditionClass.getFieldName();
				
				if(conditionClass.getCompareType().equals("1")){
					condition += " = " + conditionClass.getStrValue();
				}
				else if(conditionClass.getCompareType().equals("2")){
					condition += " > " + conditionClass.getStrValue();
				}
				else if(conditionClass.getCompareType().equals("3")){
					condition += " >= " + conditionClass.getStrValue();
				}
				else if(conditionClass.getCompareType().equals("4")){
					condition += " < " + conditionClass.getStrValue();
				}
				else if(conditionClass.getCompareType().equals("5")){
					condition += " <= " + conditionClass.getStrValue();
				}
				else if(conditionClass.getCompareType().equals("6")){
					condition += " LIKE " + conditionClass.getStrValue();
				}
				else if(conditionClass.getCompareType().equals("7")){
					condition += " IN (" + conditionClass.getStrValue() + ")";
				}
				else if(conditionClass.getCompareType().equals("8")){
					condition += " EXISTS (" + conditionClass.getStrValue() + ")";
				}
				else if(conditionClass.getCompareType().equals("11")){
					condition += " != " + conditionClass.getStrValue();
				}
				else if(conditionClass.getCompareType().equals("12")){
					condition += " NOT LIKE " + conditionClass.getStrValue();
				}
				else if(conditionClass.getCompareType().equals("13")){
					condition += " NOT IN (" + conditionClass.getStrValue() + ")";
				}
				else if(conditionClass.getCompareType().equals("14")){
					condition += " NOT EXISTS (" + conditionClass.getStrValue() + ")";
				}
				else
					condition += " " + conditionClass.getCompareType() + " " + conditionClass.getStrValue();
			}
		}
		
		return condition;
	}
	
	public static String getConditionDesc(List<ConditionClass> conditionClassList) {
		
		String condition = "";
		if(conditionClassList == null || conditionClassList.size() == 0){
			return condition;
		}
		
		for(ConditionClass conditionClass : conditionClassList){
			if(!StringUtils.isBlank(conditionClass.getConditionJoinType()) 
				&& !conditionClass.getConditionJoinType().equals(ApplicationManager.getEmptySelectValue())) { // 连接符
				if(conditionClass.getConditionJoinType().equals("AND")
						|| conditionClass.getConditionJoinType().equals("and"))
					condition += ",";
				else
					condition += conditionClass.getConditionJoinType();
			}
			else {
				if(conditionClass.getFieldName().equals("dtDate"))
					condition += "时间";
				else if(conditionClass.getFieldName().equals("instInfo"))
					condition += "机构";
				else
					condition += conditionClass.getFieldName();
				
				condition += " " + conditionClass.getCompareType() + " " + conditionClass.getStrValue();
			}
		}
		
		return condition;
	}
}
