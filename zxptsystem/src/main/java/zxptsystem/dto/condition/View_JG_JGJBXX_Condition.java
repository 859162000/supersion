package zxptsystem.dto.condition;

import framework.interfaces.ICondition;


public class View_JG_JGJBXX_Condition implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String DKKBM;
	
	private String JGZWMC;

	@ICondition(isVisible=false)
	private String INSTINFO;
	
	public String getDKKBM() {
		return DKKBM;
	}

	public void setDKKBM(String dKKBM) {
		DKKBM = dKKBM;
	}

	public String getJGZWMC() {
		return JGZWMC;
	}

	public void setJGZWMC(String jGZWMC) {
		JGZWMC = jGZWMC;
	}

	public String getINSTINFO() {
		return INSTINFO;
	}

	public void setINSTINFO(String iNSTINFO) {
		INSTINFO = iNSTINFO;
	}
}
