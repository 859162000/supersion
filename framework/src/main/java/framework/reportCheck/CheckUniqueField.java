package framework.reportCheck;

import org.apache.commons.lang.xwork.StringUtils;

public class CheckUniqueField {
	private String name;
	private String discription;
	private String originField; // 原名字段名，用户定位原表记录行，以写入出错信息
	
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
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
	
	public void setOriginField(String originField) {
		this.originField = originField;
	}
	public String getOriginField() {
		return originField;
	}
	
	
}
