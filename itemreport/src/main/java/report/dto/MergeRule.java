package report.dto;

import java.util.Date;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import coresystem.dto.InstInfo;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.show.ShowContext;

@Entity
@Table(name = "MergeRule")
public class MergeRule implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = -1561230012906546695L;

	@Id
	@Column(name = "autoMergeRuleID", length = 32)
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String autoMergeRuleID;
	
	@Column(name="strDescription",length = 200)
	@IColumn(description="描述")
	private String strDescription;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "autoMergeID")
	@IColumn(isSingleTagHidden=true)
	private MergeInstance mergeInst;
	
	@IColumn(tagMethodName="getMergeTypeTag",description="汇总类型", isNullable = false)
	@Column(name = "strMergeType", nullable = false)
	private String strMergeType;
	
	public static Map<String,String> getMergeTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("mergeType");
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strHigherInstCode")
	@IColumn(description="上级汇总机构")
	private InstInfo higherInst;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strLowerInstCode", nullable = false)
	@IColumn(description="机构", isNullable = false)
	private InstInfo instInfo;
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="startdate")
	@IColumn(description="参与汇总开始日期")
	private Date startdate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="enddate")
	@IColumn(description="参与汇总结束日期")
	private Date enddate;
	
	
	@Transient
	@IColumn(isKeyName=true)
	private String showName;
	
	@Transient
	@IColumn(isIdListField = true, target="instInfo")
	private String[] instInfoIdList;

	public String getStrDescription() {
		return strDescription;
	}

	public void setStrDescription(String strDescription) {
		this.strDescription = strDescription;
	}

	public void setInstInfoIdList(String[] instInfoIdList) {
		this.instInfoIdList = instInfoIdList;
	}

	public String[] getInstInfoIdList() {
		return instInfoIdList;
	}

	public void setAutoMergeRuleID(String autoMergeRuleID) {
		this.autoMergeRuleID = autoMergeRuleID;
	}

	public String getAutoMergeRuleID() {
		return autoMergeRuleID;
	}

	public void setHigherInst(InstInfo higherInst) {
		this.higherInst = higherInst;
	}

	public InstInfo getHigherInst() {
		return higherInst;
	}

	public void setInstInfo(InstInfo instInfo) {
		this.instInfo = instInfo;
	}

	public InstInfo getInstInfo() {
		return instInfo;
	}

	public void setStrMergeType(String strMergeType) {
		this.strMergeType = strMergeType;
	}

	public String getStrMergeType() {
		return strMergeType;
	}

	public void setMergeInst(MergeInstance mergeInst) {
		this.mergeInst = mergeInst;
	}

	public MergeInstance getMergeInst() {
		return mergeInst;
	}

	public void setShowName(String showName) {
	}

	public String getShowName() {
		if(this.instInfo==null)
		{
			return "";
		}
		return this.instInfo.getStrInstName();
	}
	
	public void setStartdate(String startdate) {
		this.startdate =TypeParse.parseDate(startdate);
	}
	
	public Date getStartdate() {
		return startdate;
	}

	public void setEnddate(String enddate) {
		this.enddate = TypeParse.parseDate(enddate);
	}

	public Date getEnddate() {
		return enddate;
	}

}
