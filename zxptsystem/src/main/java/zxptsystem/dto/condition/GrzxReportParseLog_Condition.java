package zxptsystem.dto.condition;

import zxptsystem.dto.GRZXQueryLog;

public class GrzxReportParseLog_Condition implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String parseFlag;
	
	private String sendFlag;
	
	private GRZXQueryLog grzxQueryLog;
	
	public String getParseFlag() {
		return parseFlag;
	}
	public void setParseFlag(String parseFlag) {
		this.parseFlag = parseFlag;
	}
	public String getSendFlag() {
		return sendFlag;
	}
	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}
	public GRZXQueryLog getGrzxQueryLog() {
		return grzxQueryLog;
	}
	public void setGrzxQueryLog(GRZXQueryLog grzxQueryLog) {
		this.grzxQueryLog = grzxQueryLog;
	}
	
}
