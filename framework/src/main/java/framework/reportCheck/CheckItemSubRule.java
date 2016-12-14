package framework.reportCheck;

import java.util.ArrayList;
import java.util.List;

import framework.helper.SqlConstructor.ConditionClass;

public class CheckItemSubRule {
	
	public CheckItemSubRule() {
		this.whereFieldList = new ArrayList<ConditionClass>();
	}
	private String type;
	private String value;
	private String table;
	private String field;
	private String operate;
	private String compareType;
	private List<ConditionClass> whereFieldList;
	
	// 子指标相关属性
	private String code;
	private String currency;
	private String propty;
	private String instCode;
	private String dtDate;
	private String ext1;
	private String ext2;
	private String lastFreq;
	
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		return type;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	
	public void setTable(String table) {
		this.table = table;
	}
	public String getTable() {
		return table;
	}
	
	public void setField(String field) {
		this.field = field;
	}
	public String getField() {
		return field;
	}
	
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public String getOperate() {
		return operate;
	}
	
	public void setCompareType(String compareType) {
		this.compareType = compareType;
	}
	public String getCompareType() {
		return compareType;
	}
	
	public void setWhereFieldList(List<ConditionClass> whereFieldList) {
		this.whereFieldList = whereFieldList;
	}
	public List<ConditionClass> getWhereFieldList() {
		return whereFieldList;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCode() {
		return code;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCurrency() {
		return currency;
	}
	public void setPropty(String propty) {
		this.propty = propty;
	}
	public String getPropty() {
		return propty;
	}
	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}
	public String getInstCode() {
		return instCode;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt1() {
		return ext1;
	}
	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}
	public String getDtDate() {
		return dtDate;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getExt2() {
		return ext2;
	}
	public void setLastFreq(String lastFreq) {
		this.lastFreq = lastFreq;
	}
	public String getLastFreq() {
		return lastFreq;
	}
}
