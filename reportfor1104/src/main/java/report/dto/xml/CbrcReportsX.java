package report.dto.xml;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("CbrcReports") 
public class CbrcReportsX implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = 6748796450771122322L;

	@XStreamAsAttribute()
	private String xmlns;
	
	private ReportX report;
	
	public CbrcReportsX(){
		xmlns = "http://www.cbrc.gov.cn/report/1104";
	}

	public String getXmlns() {
		return xmlns;
	}

	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}

	public ReportX getReport() {
		return report;
	}

	public void setReport(ReportX report) {
		this.report = report;
	}
	
}
