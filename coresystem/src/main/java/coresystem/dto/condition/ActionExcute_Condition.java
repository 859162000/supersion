package coresystem.dto.condition;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import coresystem.dto.UserInfo;
import framework.helper.TypeParse;
import framework.interfaces.ICondition;
import framework.interfaces.ICondition.Comparison;

public class ActionExcute_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private UserInfo userInfo;

	@ICondition(target="actionTime",description="开始时间",comparison=Comparison.GE)
	private Date  startTimestamp;
	
	@ICondition(target="actionTime",description="结束时间",comparison=Comparison.LE)
	private Date endTimestamp;
	
	private String actionShowName;
	
	@ICondition(order=1,isASC=false,isVisible=false)
	private Timestamp actionTime;

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getActionShowName() {
		return actionShowName;
	}

	public void setActionShowName(String actionShowName) {
		this.actionShowName = actionShowName;
	}

	public void setActionTime(Timestamp actionTime) {
		this.actionTime = actionTime;
	}

	public Timestamp getActionTime() {
		return actionTime;
	}

	public void setStartTimestamp(String startTimestamp) {
		this.startTimestamp = TypeParse.parseDate(startTimestamp);
	}

	public String getStartTimestamp() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return startTimestamp==null?null:format.format(startTimestamp);
	}

	public void setEndTimestamp(String endTimestamp) {
		this.endTimestamp = TypeParse.parseDate(endTimestamp);;
	}

	public String getEndTimestamp() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return endTimestamp==null?null:format.format(endTimestamp);
	}
}
