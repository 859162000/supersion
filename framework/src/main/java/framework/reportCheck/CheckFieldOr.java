package framework.reportCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class CheckFieldOr {
	
	public CheckFieldOr(){
		checkFieldBasicList = new ArrayList<CheckFieldBasic>();
		checkFieldCaseWhenList = new ArrayList<CheckFieldCaseWhen>();
	}
	
	public void setCheckFieldBasicList(List<CheckFieldBasic> checkFieldBasicList) {
		this.checkFieldBasicList = checkFieldBasicList;
	}
	public List<CheckFieldBasic> getCheckFieldBasicList() {
		return checkFieldBasicList;
	}

	public void setCheckFieldCaseWhenList(List<CheckFieldCaseWhen> checkFieldCaseWhenList) {
		this.checkFieldCaseWhenList = checkFieldCaseWhenList;
	}

	public List<CheckFieldCaseWhen> getCheckFieldCaseWhenList() {
		return checkFieldCaseWhenList;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	private String errorMsg;
	private List<CheckFieldBasic> checkFieldBasicList;
	private List<CheckFieldCaseWhen> checkFieldCaseWhenList;

	public List<String> Check(Map<String,Object> mapObject,String uniqueStr) throws Exception{
		List<String> messageList = new ArrayList<String>();
		
		for(CheckFieldBasic checkFieldBasic : checkFieldBasicList){
			List<String> tempMessageList = checkFieldBasic.Check(mapObject, uniqueStr);
			if(tempMessageList.size() == 0){
				messageList.clear();
				break;
			}
			else{
				if(this.getErrorMsg() != null && !StringUtils.isBlank(this.getErrorMsg())) {
					if(messageList.size() == 0){
						messageList.add(this.getErrorMsg());
					}
				}else{
					if(messageList.size() > 0){
						messageList.add("或者");
					}
					for(String str : tempMessageList){
						messageList.add(str);
					}
				}
				
			}
		}
		
		for(CheckFieldCaseWhen checkFieldCaseWhen : checkFieldCaseWhenList){
			List<String> tempMessageList = checkFieldCaseWhen.Check(mapObject, uniqueStr,null);
			if(tempMessageList.size() == 0){
				messageList.clear();
				break;
			}
			else{
				if(this.getErrorMsg() != null && !StringUtils.isBlank(this.getErrorMsg())) {
					if(messageList.size() == 0){
						messageList.add(this.getErrorMsg());
					}
				} else {
					if(messageList.size() > 0){
						messageList.add("或者");
					}
					for(String str : tempMessageList){
						messageList.add(str);
					}
				}
			}
		}
		List<String> strMessageList = new ArrayList<String>();
		if(messageList.size()>0){
			for (String strMessage : messageList) {
				if(strMessage.contains("!=") || strMessage.contains(">=") || strMessage.contains("<=") || strMessage.contains("<") || strMessage.contains(">") || strMessage.contains("=") || strMessage.contains("notNull") || strMessage.contains("null")){
					strMessageList.add(strMessage.replace("!=","不等于").replace(">=", "大于等于").replace("<=", "小于等于").replace("<", "小于").replace(">", "大于").replace("=", "等于").replace("notNull", "非空").replace("null", "空"));
				}
				else {
					strMessageList.add(strMessage);
				}
			}
		}
		
		return strMessageList;
	}
}
