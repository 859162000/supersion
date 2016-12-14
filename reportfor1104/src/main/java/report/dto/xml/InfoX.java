package report.dto.xml;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Info") 
public class InfoX implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = 3779972844413950836L;
	private String Tabulator;
	private String Auditor;
	private String Principal;
	public String getTabulator() {
		return Tabulator;
	}
	public void setTabulator(String tabulator) {
		Tabulator = tabulator;
	}
	public String getAuditor() {
		return Auditor;
	}
	public void setAuditor(String auditor) {
		Auditor = auditor;
	}
	public String getPrincipal() {
		return Principal;
	}
	public void setPrincipal(String principal) {
		Principal = principal;
	}
}
