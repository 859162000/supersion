package framework.reportCheck;

import java.util.ArrayList;
import java.util.List;

public class CheckItemRule {

	public CheckItemRule(){
		this.checkSubRuleList = new ArrayList<CheckItemSubRule>();
	}
	private String code;
	private String currency;
	private String propty;
	private String instCode;
	private String dtDate;
	private String ext1;
	private String ext2;
	private String compareType;
	private String description;
	private List<CheckItemSubRule> checkSubRuleList;
	
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
	
	public void setCompareType(String compareType) {
		this.compareType = compareType;
	}
	public String getCompareType() {
		return compareType;
	}
	
	public void setCheckSubRuleList(List<CheckItemSubRule> checkSubRuleList) {
		this.checkSubRuleList = checkSubRuleList;
	}
	public List<CheckItemSubRule> getCheckSubRuleList() {
		return checkSubRuleList;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}
	public String getInstCode() {
		return instCode;
	}
	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}
	public String getDtDate() {
		return dtDate;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getExt2() {
		return ext2;
	}
}
