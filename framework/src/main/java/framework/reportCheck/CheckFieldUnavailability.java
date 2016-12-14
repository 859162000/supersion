package framework.reportCheck;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class CheckFieldUnavailability {
	
	private String name;
	private String discription;
	private Set<String> valueSet;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDiscription() {
		if(StringUtils.isBlank(discription)){
			return this.name;
		}
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public Set<String> getValueSet() {
		return valueSet;
	}
	public void setValueSet(Set<String> valueSet) {
		this.valueSet = valueSet;
	}
	
	public CheckFieldUnavailability(){
		this.valueSet = new LinkedHashSet<String>();
	}
	
	private String getUniqueStr(Object value){
		String valueStr;
		if(value == null || StringUtils.isBlank(value.toString())){
			valueStr = "null";
		}
		else{
			valueStr = value.toString();
		}
		valueStr = this.discription + "：" + valueStr + "，";
		return valueStr;
	}
	
	private String getValueSetStr(){
		String valueStr = "";
		for(String str : this.valueSet){
			valueStr += str + "，";
		}
		return valueStr.substring(0,valueStr.length() - 1) + "范围内。";
	}
	
	public List<String> Check(Map<String,Object> mapObject,String uniqueStr) throws Exception{
			
			List<String> messageList = new ArrayList<String>();
			Object value = mapObject.get(this.name.toUpperCase());
			uniqueStr = uniqueStr + getUniqueStr(value);
			
			if(value != null && !StringUtils.isBlank(value.toString())){
				if(this.valueSet.contains(value)){
					messageList.add(uniqueStr + "不应该在：" + getValueSetStr());
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
