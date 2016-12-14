package report.dto.condition;

import report.dto.RptInfo;
import coresystem.dto.InstInfo;
import extend.dto.Suit;
import framework.interfaces.ICondition;

public class InstRptRela_Condition implements java.io.Serializable {


	/**  **/
	private static final long serialVersionUID = 1449340172641011400L;

	@ICondition(target="instInfo", targetField="strInstCode",isASC = true,order=1,description="机构名称")
	private InstInfo instInfo;
	
	@ICondition(target="rptInfo", targetField="strBCode",isASC = true,order=3,description="报表代码")
	private RptInfo rptInfo1;
	
	@ICondition(target="suit", targetField="strSuitCode",isASC = true,order=2,description="主题名称")
	private Suit suit;

	public InstInfo getInstInfo() {
		return instInfo;
	}

	public void setInstInfo(InstInfo instInfo) {
		this.instInfo = instInfo;
	}

	public RptInfo getRptInfo1() {
		return rptInfo1;
	}

	public void setRptInfo1(RptInfo rptInfo1) {
		this.rptInfo1 = rptInfo1;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

}
