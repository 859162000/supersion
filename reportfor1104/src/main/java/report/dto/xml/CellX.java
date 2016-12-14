package report.dto.xml;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

@XStreamAlias("Cell")
@XStreamConverter(value=ToAttributedValueConverter.class, strings={"text"})
public class CellX implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = -3102695902878729771L;
	@XStreamAsAttribute()
	private String id;
	private String text;
	public CellX(){
		
	}
	public CellX(String id){
		this.id = id;
	}
	public CellX(String id,String text){
		this.id = id;
		this.text = text;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
