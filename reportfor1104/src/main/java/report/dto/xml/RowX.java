package report.dto.xml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("Row")
public class RowX implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = -7347328198580078865L;
	@XStreamAsAttribute()
	private String id;
	@XStreamImplicit()
	private List<CellX> cells;
	
	public RowX(){
		cells = new ArrayList<CellX>();
	}
	public RowX(String id){
		this.id = id;
		cells = new ArrayList<CellX>();
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<CellX> getCells() {
		return cells;
	}
	public void setCells(List<CellX> cells) {
		this.cells = cells;
	}
	
}
