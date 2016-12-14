package zxptsystem.dto.condition;

import java.sql.Timestamp;

import coresystem.dto.UserInfo;

import framework.interfaces.ICondition;
import framework.interfaces.ICondition.Comparison;

public class GRZXZHBSBGCJFKBW_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@ICondition(order=1,isVisible=false,isASC=false)
	private Timestamp dtInportTime;
	
	private String strGRZXZHBSBGCCXX;
	
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

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getStrGRZXZHBSBGCCXX() {
		return strGRZXZHBSBGCCXX;
	}

	public void setStrGRZXZHBSBGCCXX(String strGRZXZHBSBGCCXX) {
		this.strGRZXZHBSBGCCXX = strGRZXZHBSBGCCXX;
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
