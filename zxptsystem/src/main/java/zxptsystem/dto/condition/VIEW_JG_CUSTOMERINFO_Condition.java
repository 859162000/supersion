package zxptsystem.dto.condition;

import java.util.Date;

import framework.helper.TypeParse;

public class VIEW_JG_CUSTOMERINFO_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String JGDM;
	
	private String KHH;
	
	private String JGZWMC;
	
	private String KHLX;
	
	private String DKKBM;
	
	private String KHXKZHZH;
	
	private Date XXGXRQ;

	public String getJGDM() {
		return JGDM;
	}

	public void setJGDM(String jGDM) {
		JGDM = jGDM;
	}

	public String getKHH() {
		return KHH;
	}

	public void setKHH(String kHH) {
		KHH = kHH;
	}

	public String getJGZWMC() {
		return JGZWMC;
	}

	public void setJGZWMC(String jGZWMC) {
		JGZWMC = jGZWMC;
	}

	public String getKHLX() {
		return KHLX;
	}

	public void setKHLX(String kHLX) {
		KHLX = kHLX;
	}

	public String getDKKBM() {
		return DKKBM;
	}

	public void setDKKBM(String dKKBM) {
		DKKBM = dKKBM;
	}

	public String getKHXKZHZH() {
		return KHXKZHZH;
	}

	public void setKHXKZHZH(String kHXKZHZH) {
		KHXKZHZH = kHXKZHZH;
	}

	public void setXXGXRQ(String xXGXRQ) {
		XXGXRQ =TypeParse.parseDate(xXGXRQ);
	}

	public Date getXXGXRQ() {
		return XXGXRQ;
	}

}
