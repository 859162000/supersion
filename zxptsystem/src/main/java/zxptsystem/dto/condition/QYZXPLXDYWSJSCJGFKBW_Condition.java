package zxptsystem.dto.condition;

import java.sql.Timestamp;

import coresystem.dto.UserInfo;

import framework.interfaces.ICondition;
import framework.interfaces.ICondition.Comparison;

public class QYZXPLXDYWSJSCJGFKBW_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ICondition(order=1,isVisible=false,isASC=false)
	private Timestamp dtInportTime;
	
	private String strBWLX;
	
	private String strBWCLJG;
	
	private UserInfo userInfo;
	
	@ICondition(target="dtInportTime",description="导入开始时间",comparison=Comparison.GE)
	private Timestamp startDate;
	
	@ICondition(target="dtInportTime",description="导入结束时间",comparison=Comparison.LE)
	private Timestamp endDate;

	public Timestamp getDtInportTime() {
		return dtInportTime;
	}

	public void setDtInportTime(Timestamp dtInportTime) {
		this.dtInportTime = dtInportTime;
	}

	public String getStrBWLX() {
		return strBWLX;
	}

	public void setStrBWLX(String strBWLX) {
		this.strBWLX = strBWLX;
	}

	public String getStrBWCLJG() {
		return strBWCLJG;
	}

	public void setStrBWCLJG(String strBWCLJG) {
		this.strBWCLJG = strBWCLJG;
	}

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

