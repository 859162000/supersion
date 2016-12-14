package framework.reportCheck;

import org.apache.commons.lang.xwork.StringUtils;

public class ConsisentGroup {
	private String keyField ;
	private String  keyDiscription;
	private String nameField ;
	private String nameDiscription ;
	private String procedureName;
	private String procedureParam;
	private String procedureDiscription;
	
	public String getKeyField() {
		return keyField;
	}
	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}
	public String getKeyDiscription() {
		if(StringUtils.isBlank(keyDiscription)){
			return this.keyField;	
		}
		return keyDiscription;
	}
	public void setKeyDiscription(String keyDiscription) {
		this.keyDiscription = keyDiscription;
	}
	public String getNameField() {
		return nameField;
	}
	public void setNameField(String nameField) {
		this.nameField = nameField;
	}
	public String getNameDiscription() {
		if(StringUtils.isBlank(nameDiscription)){
			return this.nameField;
		}
		return nameDiscription;
	}
	public void setNameDiscription(String nameDiscription) {
		
		this.nameDiscription = nameDiscription;
	}
	public String getProcedureName() {
		return procedureName;
	}
	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}
	public String getProcedureParam() {
		return procedureParam;
	}
	public void setProcedureParam(String procedureParam) {
		this.procedureParam = procedureParam;
	}
	public String getProcedureDiscription() {
		if(StringUtils.isBlank(procedureDiscription)){
			return this.procedureName;
		}
		return procedureDiscription;
	}
	public void setProcedureDiscription(String procedureDiscription) {
		this.procedureDiscription = procedureDiscription;
	}

}
