package extend.dto.condition;

import framework.interfaces.ICondition;

public class ReportModel_Field_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	public void setStrFieldName(String strFieldName) {
		this.strFieldName = strFieldName;
	}

	public String getStrFieldName() {
		return strFieldName;
	}

	public void setStrChinaName(String strChinaName) {
		this.strChinaName = strChinaName;
	}

	public String getStrChinaName() {
		return strChinaName;
	}

	public void setNSeq(Integer nSeq) {
		this.nSeq = nSeq;
	}

	public Integer getNSeq() {
		return nSeq;
	}

	public void setStrFieldType(String strFieldType) {
		this.strFieldType = strFieldType;
	}

	public String getStrFieldType() {
		return strFieldType;
	}

	private String strFieldName;
	
	@ICondition(order=2,isVisible=false)
	private String strChinaName;
	
	private String strFieldType;
	
	@ICondition(order=1,isVisible=false)
	private Integer nSeq;
	
	
}

