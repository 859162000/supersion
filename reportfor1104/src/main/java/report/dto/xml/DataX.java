package report.dto.xml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("Data")
public class DataX implements Serializable{
	/**  **/
	private static final long serialVersionUID = 6323175493563237661L;
	@XStreamAsAttribute
	private String part;
	@XStreamImplicit()
	private List<RowX> rows;
	
	public DataX(){
		rows = new ArrayList<RowX>();
	}
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	public List<RowX> getRows() {
		return rows;
	}
	public void setRows(List<RowX> rows) {
		this.rows = rows;
	}
	
}
