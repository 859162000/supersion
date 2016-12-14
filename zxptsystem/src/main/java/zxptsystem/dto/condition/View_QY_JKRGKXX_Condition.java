package zxptsystem.dto.condition;

import framework.interfaces.ICondition;


public class View_QY_JKRGKXX_Condition implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String DKKBM;
	
	private String JKRZWMC;

	@ICondition(isVisible=false)
	private String INSTINFO;
	
	public String getDKKBM() {
		return DKKBM;
	}

	public void setDKKBM(String dKKBM) {
		DKKBM = dKKBM;
	}


	public String getJKRZWMC() {
		return JKRZWMC;
	}

	public void setJKRZWMC(String jKRZWMC) {
		JKRZWMC = jKRZWMC;
	}

	public String getINSTINFO() {
		return INSTINFO;
	}

	public void setINSTINFO(String iNSTINFO) {
		INSTINFO = iNSTINFO;
	}

}
