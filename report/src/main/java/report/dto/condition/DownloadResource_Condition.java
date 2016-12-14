package report.dto.condition;

import java.text.SimpleDateFormat;
import java.util.Date;

import extend.dto.Suit;
import framework.helper.TypeParse;
import framework.interfaces.ICondition;
import framework.interfaces.ICondition.Comparison;

public class DownloadResource_Condition implements java.io.Serializable{
	/**  **/
	private static final long serialVersionUID = 8984755146501210701L;

	private Suit suit;
	
	@ICondition(comparison=Comparison.GE,order=1, isASC=false)
	private Date startTime;
	
	@ICondition(comparison=Comparison.LE)
	private Date endTime;


	public String getStartTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return startTime==null?null:format.format(startTime);
	}

	public void setStartTime(String startTime) {
		this.startTime = TypeParse.parseDate(startTime);
	}

	public String getEndTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return endTime==null?null:format.format(endTime);
	}

	public void setEndTime(String endTime) {
		this.endTime =TypeParse.parseDate(endTime);
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public Suit getSuit() {
		return suit;
	}


	
}
