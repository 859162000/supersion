package report.vo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name="MergeSummary")
public class MergeReVO implements Serializable{
	/**  **/
	private static final long serialVersionUID = 4934234160543240230L;
	@Column
	private String MergeSummary;
	@Column
	private String strDescription;
	@Column
	private String strMergeInstanceid;
	private List<MergeRuleVO> ruleList;
	private List<MergeRuleInfoVO> ruleInfoList;
	public String getMergeSummary() {
		return MergeSummary;
	}
	public void setMergeSummary(String mergeSummary) {
		MergeSummary = mergeSummary;
	}
	public String getStrDescription() {
		return strDescription;
	}
	public void setStrDescription(String strDescription) {
		this.strDescription = strDescription;
	}
	public String getStrMergeInstanceid() {
		return strMergeInstanceid;
	}
	public void setStrMergeInstanceid(String strMergeInstanceid) {
		this.strMergeInstanceid = strMergeInstanceid;
	}
	public List<MergeRuleVO> getRuleList() {
		return ruleList;
	}
	public void setRuleList(List<MergeRuleVO> ruleList) {
		this.ruleList = ruleList;
	}
	public List<MergeRuleInfoVO> getRuleInfoList() {
		return ruleInfoList;
	}
	public void setRuleInfoList(List<MergeRuleInfoVO> ruleInfoList) {
		this.ruleInfoList = ruleInfoList;
	}
	
}
