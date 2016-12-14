package dbgssystem.dto.condition;

import java.sql.Timestamp;

import framework.interfaces.ICondition;

public class DBGSCJFKBW_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@ICondition(order=1,isVisible=false,isASC=false)
	private Timestamp dtInportTime;

	public void setDtInportTime(Timestamp dtInportTime) {
		this.dtInportTime = dtInportTime;
	}

	public Timestamp getDtInportTime() {
		return dtInportTime;
	}
	
	public void setStrBWLX(String strBWLX) {
		this.strBWLX = strBWLX;
	}

	public String getStrBWLX() {
		return strBWLX;
	}

	private String strBWLX;


}
