package coresystem.dto.condition;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import framework.helper.TypeParse;
import framework.interfaces.ICondition;
import framework.interfaces.ICondition.Comparison;

public class UserOperationLog_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String strUserCode;
	private String strUserName;
	
	private String strOperationType;
	
	@ICondition(order=1,isASC=false,isVisible=false)
	private Timestamp dtOperationTime;

	@ICondition(target="dtOperationTime",description="开始时间",comparison=Comparison.GE)
	private Date  startTimestamp;
	
	@ICondition(target="dtOperationTime",description="结束时间",comparison=Comparison.LE)
	private Date endTimestamp;
	
	public void setStrUserCode(String strUserCode) {
		this.strUserCode = strUserCode;
	}

	public String getStrUserCode() {
		return strUserCode;
	}
	
	

	public void setStrUserName(String strUserName) {
		this.strUserName = strUserName;
	}

	public String getStrUserName() {
		return strUserName;
	}
	
	public void setDtOperationTime(String dtOperationTime) {
		this.dtOperationTime = TypeParse.parseTimestamp(dtOperationTime);
	}

	public Timestamp getDtOperationTime() {
		return this.dtOperationTime;
	}
	
	
	public void setStrOperationType(String strOperationType) {
		this.strOperationType = strOperationType;
	}

	public String getStrOperationType() {
		return this.strOperationType;
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
