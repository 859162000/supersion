package report.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Table;
@Table(name="MergeRule")
public class MergeRuleVO implements Serializable{
	/**  **/
	private static final long serialVersionUID = -341942614579754469L;
	@Column
	private String autoMergeRuleID;
	@Column
	private String strDescription;
	@Column
	private String autoMergeID;
	@Column
	private String strMergeType;
	@Column
	private String strHigherInstCode;
	@Column
	private String strLowerInstCode;
	@Column
	private Date startdate;
	@Column
	private Date enddate;
	public String getAutoMergeRuleID() {
		return autoMergeRuleID;
	}
	public void setAutoMergeRuleID(String autoMergeRuleID) {
		this.autoMergeRuleID = autoMergeRuleID;
	}
	public String getStrDescription() {
		return strDescription;
	}
	public void setStrDescription(String strDescription) {
		this.strDescription = strDescription;
	}
	public String getAutoMergeID() {
		return autoMergeID;
	}
	public void setAutoMergeID(String autoMergeID) {
		this.autoMergeID = autoMergeID;
	}
	public String getStrMergeType() {
		return strMergeType;
	}
	public void setStrMergeType(String strMergeType) {
		this.strMergeType = strMergeType;
	}
	public String getStrHigherInstCode() {
		return strHigherInstCode;
	}
	public void setStrHigherInstCode(String strHigherInstCode) {
		this.strHigherInstCode = strHigherInstCode;
	}
	public String getStrLowerInstCode() {
		return strLowerInstCode;
	}
	public void setStrLowerInstCode(String strLowerInstCode) {
		this.strLowerInstCode = strLowerInstCode;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	
}
