package coresystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AutoOrder")
public class AutoOrder  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "strkey", length = 200)
	private String strkey;
	
	@Column(name = "autoOrder")
	private Integer autoOrder;
	
	
	public String getStrkey() {
		return strkey;
	}
	public void setStrkey(String strkey) {
		this.strkey = strkey;
	}
	public void setAutoOrder(Integer autoOrder) {
		this.autoOrder = autoOrder;
	}
	public Integer getAutoOrder() {
		return autoOrder;
	}
}
