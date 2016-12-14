package report.dto.xml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
@XStreamAlias(value="Report")
public class ReportX implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = -8998212283325405980L;
	@XStreamAsAttribute()
	private String RepCode = "";
	@XStreamAsAttribute()
	private String VersionId = "";
	@XStreamAsAttribute()
	private String ReportDate = "";
	@XStreamAsAttribute()
	private String Range = "";
	@XStreamAsAttribute()
	private String Currency = "";
	@XStreamAsAttribute()
	private String Unit = "";
	
	private InfoX info;
	
	@XStreamImplicit
	private List<DataX> datas;
	
	public ReportX(){
		datas = new ArrayList<DataX>();
	}

	public String getRepCode() {
		return RepCode;
	}

	public void setRepCode(String repCode) {
		RepCode = repCode;
	}

	public String getVersionId() {
		return VersionId;
	}

	public void setVersionId(String versionId) {
		VersionId = versionId;
	}

	public String getReportDate() {
		return ReportDate;
	}

	public void setReportDate(String reportDate) {
		ReportDate = reportDate;
	}

	public String getRange() {
		return Range;
	}

	public void setRange(String range) {
		Range = range;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public String getUnit() {
		return Unit;
	}

	public void setUnit(String unit) {
		Unit = unit;
	}

	public InfoX getInfo() {
		return info;
	}

	public void setInfo(InfoX info) {
		this.info = info;
	}

	public List<DataX> getDatas() {
		return datas;
	}

	public void setDatas(List<DataX> datas) {
		this.datas = datas;
	}
	
}
