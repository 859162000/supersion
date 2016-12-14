package zxptsystem.dto.condition;

import java.sql.Timestamp;

import coresystem.dto.UserInfo;

import framework.interfaces.ICondition;
import framework.interfaces.ICondition.Comparison;

public class GRZXCJFKBW_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@ICondition(order=1,isVisible=false,isASC=false)
	private Timestamp dtInportTime;

	public void setDtInportTime(Timestamp dtInportTime) {
		this.dtInportTime = dtInportTime;
	}

	public Timestamp getDtInportTime() {
		return dtInportTime;
	}

	public void setStrCBYQ(String strCBYQ) {
		this.strCBYQ = strCBYQ;
	}

	public String getStrCBYQ() {
		return strCBYQ;
	}

	private String strCBYQ;
	
	private UserInfo userInfo;
	
	@ICondition(target="dtInportTime",description="导入开始时间",comparison=Comparison.GE)
	private Timestamp startDate;
	
	@ICondition(target="dtInportTime",description="导入结束时间",comparison=Comparison.LE)
	private Timestamp endDate;
	
	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
}
