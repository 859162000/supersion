package zxptsystem.helper.GR;

public class ReturnResult implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1810827959992571926L;
	
	private String enquiryType;
	private String message;
	private boolean success;
	public ReturnResult(){
		success=true;
		enquiryType="";
		message="";
	}
	public void setEnquiryType(String enquiryType) {
		this.enquiryType = enquiryType;
	}
	public String getEnquiryType() {
		return enquiryType;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public boolean isSuccess() {
		return success;
	}
}
