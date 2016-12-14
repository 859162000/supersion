package framework.reportCheck;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.xwork.StringUtils;

public class CheckFieldEffective {
	
	public CheckFieldEffective(){
		this.valueSet = new LinkedHashSet<String>();
	}
	
	private String getUniqueStr(Object value){
		String valueStr;
		if(value == null || StringUtils.isBlank(value.toString())){
			//valueStr = "null";
			valueStr = "";
		}
		else{
			valueStr = value.toString();
		}
		
		if(StringUtils.isBlank(valueStr)){
			valueStr = this.discription + "：";
		}
		else{
			valueStr = this.discription + "：" + valueStr + "， ";
		}
		
		return valueStr;
	}
	
	private String getValueSetStr(){
		String valueStr = "";
		for(String str : this.valueSet){
			valueStr += str + "，";
		}
		return valueStr.substring(0,valueStr.length() - 1) + "范围内。";
	}
	
	public List<String> Check(Map<String,Object> mapObject,String uniqueStr, Map<String,String> valueMap) throws Exception{
		
		List<String> messageList = new ArrayList<String>();
		Object value = mapObject.get(this.name.toUpperCase());
		uniqueStr = uniqueStr + getUniqueStr(value);
		
		if(value != null && !StringUtils.isBlank(value.toString())){
			if(!this.valueSet.contains(value)){
				if(valueMap == null){
					messageList.add(uniqueStr + "应在：" + getValueSetStr());
				}
				else{
					messageList.add("无效数据");
				}
			}
		}
		List<String> strMessageList = new ArrayList<String>();
		if(messageList.size()>0){
			if(this.getErrorMsg() != null && !StringUtils.isBlank(this.getErrorMsg())) {
				strMessageList.add(this.getErrorMsg());
			} else {
				for (String strMessage : messageList) {
					if(strMessage.contains("!=") || strMessage.contains(">=") || strMessage.contains("<=") || strMessage.contains("<") || strMessage.contains(">") || strMessage.contains("=") || strMessage.contains("notNull") || strMessage.contains("null")){
						strMessageList.add(strMessage.replace("!=","不等于").replace(">=", "大于等于").replace("<=", "小于等于").replace("<", "小于").replace(">", "大于").replace("=", "等于").replace("notNull", "非空").replace("null", "空"));
					}
					else {
						strMessageList.add(strMessage);
					}
				}
			}
		}
		
		return strMessageList;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

	public void setValueSet(Set<String> valueSet) {
		this.valueSet = valueSet;
	}

	public Set<String> getValueSet() {
		return valueSet;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public String getDiscription() {
		if(StringUtils.isBlank(discription)){
			return this.name;
		}
		return discription;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}


	private String name;
	private String errorMsg;
	private String discription;
	private Set<String> valueSet;
}
