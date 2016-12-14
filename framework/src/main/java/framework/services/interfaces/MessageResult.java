package framework.services.interfaces;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

public class MessageResult implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	public static final String COLORRED = "red";
	public static final String COLORYELLOW = "yellow";
	
	public static class ErrorField implements java.io.Serializable{
		
		private static final long serialVersionUID = 1L;
		public ErrorField(String fieldName,String color,String message){
			this.fieldName = fieldName;
			this.color = color;
			this.message = message;
		}
		
		private String fieldName;
		private String color;
		private String message;
		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}
		public String getFieldName() {
			return fieldName;
		}
		public void setColor(String color) {
			this.color = color;
		}
		public String getColor() {
			return color;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getMessage() {
			return message;
		}
	}
	
	public static final String SHORTSPLITFLAG = "------------------------";
	private static final String LINESPLITFLAG = "--------------------------------------------------------------------------------------------";
	
	public MessageResult(){
		this.success  = true;
		this.message = "";
		this.messageSet = new LinkedHashSet<String>();
		this.messageList = new ArrayList<String>();
		this.errorFieldList = new ArrayList<ErrorField>();
	}
	
	public MessageResult(boolean success){
		this.success  = success;
		this.message = "";
		this.messageSet = new LinkedHashSet<String>();
		this.messageList = new ArrayList<String>();
		this.errorFieldList = new ArrayList<ErrorField>();
	}
	
	public MessageResult(String message){
		this.success = true;
		this.message  = message;
		this.messageSet = new LinkedHashSet<String>();
		this.messageList = new ArrayList<String>();
		this.errorFieldList = new ArrayList<ErrorField>();
	}
	
	public MessageResult(String message, String idVal){
		this.success = true;
		this.message  = message;
		this.idValue = idVal;
		this.messageSet = new LinkedHashSet<String>();
		this.messageList = new ArrayList<String>();
		this.errorFieldList = new ArrayList<ErrorField>();
	}
	
	public MessageResult(boolean success, String message){
		this.success  = success;
		this.message  = message;
		this.messageSet = new LinkedHashSet<String>();
		this.messageList = new ArrayList<String>();
		this.errorFieldList = new ArrayList<ErrorField>();
	}
	
	public MessageResult(boolean success, String message, String idVal){
		this.success  = success;
		this.message  = message;
		this.idValue = idVal;
		this.messageSet = new LinkedHashSet<String>();
		this.messageList = new ArrayList<String>();
		this.errorFieldList = new ArrayList<ErrorField>();
	}
	
    private	boolean success;
    
    public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}
    
    private String message;
	
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	
	private String idValue;
	
	public void setIdValue(String val) {
		this.idValue = val;
	}
	public String getIdValue() {
		return idValue;
	}
	
	private Set<String> messageSet;
	
	private List<String> messageList;
	
	private List<ErrorField> errorFieldList;
	
	public List<String> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<String> messageList) {
		this.messageList = messageList;
	}

	public void AlertTranslate(){
		String translateMessage = "";
		for(ErrorField errorField : this.errorFieldList){
			translateMessage += "\\r\\n" + errorField.getMessage();
		}
		for(String str : this.messageSet){
			translateMessage += "\\r\\n" + str;
		}
		for(String str : this.messageList){
			translateMessage += "\\r\\n" + str;
		}
		if(!translateMessage.equals("")){
			this.message = this.message + translateMessage;
		}
	}
	
	public void AddLineSplitFlag(){
		this.getMessageList().add(LINESPLITFLAG);
	}
	
	public void AddShortSplitFlag(){
		this.getMessageList().add(SHORTSPLITFLAG);
	}
	
	public void TxtFileTranslate(String path) throws IOException{
		path = ServletActionContext.getServletContext().getRealPath(path);
		FileWriter fileWriter = new FileWriter(path);
		fileWriter.write(this.message);
		for(ErrorField errorField : this.errorFieldList){
			fileWriter.write("\r\n" + errorField.getMessage());
		}
		for(String str : this.messageSet){
			fileWriter.write("\r\n" + str);
		}
		for(String str : this.messageList){
			fileWriter.write("\r\n" + str);
		}
		fileWriter.close();
	}

	public void setMessageSet(Set<String> messageSet) {
		this.messageSet = messageSet;
	}

	public Set<String> getMessageSet() {
		return messageSet;
	}

	public void setErrorFieldList(List<ErrorField> errorFieldList) {
		this.errorFieldList = errorFieldList;
	}

	public List<ErrorField> getErrorFieldList() {
		return errorFieldList;
	}

}
