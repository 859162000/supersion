package report.dto.condition;

import java.util.Date;

import com.opensymphony.xwork2.conversion.annotations.TypeConversion;

import extend.dto.Suit;
import framework.helper.TypeParse;
import framework.interfaces.ICondition;

public class TaskFlow_Condition implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1128289835664160848L;

	
	@ICondition(order=1,isASC=false)
	private Date dtTaskDate;
	
	@ICondition(order=2)
	private Suit suit;
	
	@ICondition(order=3)
	private String strFreq;
	
	@ICondition(order=4)
	private String strTime;
	
	@ICondition(order=5)
	private String strTaskName;
	
	@ICondition(order=6)
	private String strTaskState;
	
	
	
	
	
	public void setDtTaskDate(String dtTaskDate) {
		this.dtTaskDate = TypeParse.parseDate(dtTaskDate);
	}
	@TypeConversion(converter = "framework.struts.ext.DateConverter")
	public Date getDtTaskDate() {
		return dtTaskDate;
	}
	
	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public Suit getSuit() {
		return suit;
	}
	
	public String getStrFreq() {
		return strFreq;
	}

	public void setStrFreq(String strFreq) {
		this.strFreq = strFreq;
	}
	
	public void setStrTime(String strTime)
	{
		this.strTime=strTime;
	}
	
	public String getStrTime()
	{
		return this.strTime;
	}
	
	
	
	public String getStrTaskName() {
		return strTaskName;
	}

	public void setStrTaskName(String strTaskName) {
		this.strTaskName = strTaskName;
	}

	
	public String getStrTaskState() {
		return strTaskState;
	}

	public void setStrTaskState(String strTaskState) {
		this.strTaskState = strTaskState;
	}
}
