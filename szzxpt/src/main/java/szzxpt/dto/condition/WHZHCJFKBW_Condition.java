package szzxpt.dto.condition;

import java.sql.Timestamp;

import coresystem.dto.UserInfo;

import framework.interfaces.ICondition;
import framework.interfaces.ICondition.Comparison;

public class WHZHCJFKBW_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ICondition(order=1,isASC=false,isVisible=false)
	private Timestamp dtInportTime;

	@ICondition(target="dtInportTime",description="导入开始时间",comparison=Comparison.GE)
	private String startDate;
	
	@ICondition(target="dtInportTime",description="导入结束时间",comparison=Comparison.LE)
	private String endDate;
	
	private UserInfo userInfo;
	
	private String strBWCCWJM;
	
	public Timestamp getDtInportTime() {
		return dtInportTime;
	}

	public void setDtInportTime(Timestamp dtInportTime) {
		this.dtInportTime = dtInportTime;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getStrBWCCWJM() {
		return strBWCCWJM;
	}

	public void setStrBWCCWJM(String strBWCCWJM) {
		this.strBWCCWJM = strBWCCWJM;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
}
