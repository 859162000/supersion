package coresystem.dto;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import coresystem.dto.UserInfo;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
import framework.show.ShowContext;

@Entity
@Table(name = "InstInfo")
@IEntity(description="机构",navigationName="机构信息")
public class InstInfo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "strInstCode", length = 50)
	@IColumn(description="机构代码")
	private String strInstCode;
	
	@Column(name = "strInstName", length = 50, nullable = false)
	@IColumn(description="机构名称", isKeyName=true, isNullable = false, isSpecialCharCheck=true)
	private String strInstName;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strHigerInstCode")
	@IColumn(description="上级机构")
	private InstInfo higherInst;
	
	@Column(name = "strAllowState", length = 1)
	@IColumn(description="审批状态", isSingleTagHidden = true)
	private String strAllowState;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "instInfo")
	private Set<UserInfo> userInfos = new HashSet<UserInfo>(0);

	
	@IColumn(tagMethodName="getInstStatusTag",description="机构状态", isNullable = false)
	@Column(name = "strInstStatus", nullable = true)
	private String strInstStatus;
	
	public static Map<String,String> getInstStatusTag(){
		return ShowContext.getInstance().getShowEntityMap().get("strInstStatus");
	}
	
	@Column(name = "UpdateTime")
	@IColumn(isSystemDate=true,description="修改时间",isSingleTagHidden=true)
	private Timestamp UpdateTime;
	
	@Column(name = "jrxkzh", length = 50, nullable = true)
	@IColumn(description="金融许可证号", isNullable = true)
	private String jrxkzh;

	@Column(name = "rxzfhh", length = 50, nullable = true)
	@IColumn(description="人行支付行号", isNullable = true)
	private String rxzfhh;

	@Column(name = "jgdz", length = 255, nullable = true)
	@IColumn(description="机构地址", isNullable = true, isSpecialCharCheck=true)
	private String jgdz;

	@Column(name = "fxcjgdm", length = 50, nullable = true)
	@IColumn(description="非现场机构代码", isNullable = true)
	private String fxcjgdm;

	@Column(name = "yzbm", length = 50, nullable = true)
	@IColumn(description="邮政编码", isNullable = true)
	private String yzbm;

	@Column(name = "clsj", length = 50, nullable = true)
	@IColumn(description="成立时间", isNullable = true)
	private String clsj;
	
	//@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "instInfo")
	//private Set<TaskRptInst> taskRptInsts = new HashSet<TaskRptInst>(0);
	
	
	//@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "instInfo")
	//private Set<InstInstSet> instInfoAndList = new HashSet<InstInstSet>(0);
	
	/*public Set<InstInstSet> getInstInfoAndList() {
		return instInfoAndList;
	}

	public void setInstInfoAndList(Set<InstInstSet> instInfoAndList) {
		this.instInfoAndList = instInfoAndList;
	}*/

	public String getJrxkzh() {
		return jrxkzh;
	}

	public void setJrxkzh(String jrxkzh) {
		this.jrxkzh = jrxkzh;
	}

	public String getRxzfhh() {
		return rxzfhh;
	}

	public void setRxzfhh(String rxzfhh) {
		this.rxzfhh = rxzfhh;
	}

	public String getJgdz() {
		return jgdz;
	}

	public void setJgdz(String jgdz) {
		this.jgdz = jgdz;
	}

	public void setUpdateTime(Timestamp updateTime) {
		UpdateTime = updateTime;
	}

	public void setStrInstCode(String strInstCode) {
		this.strInstCode = strInstCode;
	}

	public String getStrInstCode() {
		return strInstCode;
	}

	public void setStrInstName(String strInstName) {
		this.strInstName = strInstName;
	}

	public String getStrInstName() {
		return strInstName;
	}

	public void setUserInfos(Set<UserInfo> userInfos) {
		this.userInfos = userInfos;
	}

	public Set<UserInfo> getUserInfos() {
		return userInfos;
	}

	/*public void setTaskRptInsts(Set<TaskRptInst> taskRptInsts) {
		this.taskRptInsts = taskRptInsts;
	}

	public Set<TaskRptInst> getTaskRptInsts() {
		return taskRptInsts;
	}*/

	public void setHigherInst(InstInfo higherInst) {
		this.higherInst = higherInst;
	}

	public InstInfo getHigherInst() {
		return higherInst;
	}

	public void setStrAllowState(String strAllowState) {
		this.strAllowState = strAllowState;
	}

	public String getStrAllowState() {
		return strAllowState;
	}

	public void setStrInstStatus(String strInstStatus) {
		this.strInstStatus = strInstStatus;
	}

	public String getStrInstStatus() {
		return strInstStatus;
	}

	public void setUpdateTime(String updateTime) {
		UpdateTime = TypeParse.parseTimestamp(updateTime);
	}

	public Timestamp getUpdateTime() {
		return UpdateTime;
	}

	public void setFxcjgdm(String fxcjgdm) {
		this.fxcjgdm = fxcjgdm;
	}

	public String getFxcjgdm() {
		return fxcjgdm;
	}

	public String getYzbm() {
		return yzbm;
	}

	public void setYzbm(String yzbm) {
		this.yzbm = yzbm;
	}

	public String getClsj() {
		return clsj;
	}

	public void setClsj(String clsj) {
		this.clsj = clsj;
	}
	
}

