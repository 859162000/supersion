package zxptsystem.dto.condition;

import extend.dto.Suit;

public class SendedRejectReport_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Suit suit;
	
	private String dtDate;
	
	private String strFileName;
	
	private String RPTFeedbackType;
	
	private String rejectDate;
	
	private String rejectUser;

	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public String getStrFileName() {
		return strFileName;
	}

	public void setStrFileName(String strFileName) {
		this.strFileName = strFileName;
	}

	public String getRPTFeedbackType() {
		return RPTFeedbackType;
	}

	public void setRPTFeedbackType(String rPTFeedbackType) {
		RPTFeedbackType = rPTFeedbackType;
	}

	public String getRejectUser() {
		return rejectUser;
	}

	public void setRejectUser(String rejectUser) {
		this.rejectUser = rejectUser;
	}

	public String getDtDate() {
		return dtDate;
	}

	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}

	public String getRejectDate() {
		return rejectDate;
	}

	public void setRejectDate(String rejectDate) {
		this.rejectDate = rejectDate;
	}
	
	
}
