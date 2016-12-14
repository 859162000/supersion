package report.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import framework.interfaces.IColumn;

@Entity
@Table(name = "ItemProperty")
public class ItemProperty implements java.io.Serializable{
	
	/**  **/
	private static final long serialVersionUID = 322953779419707667L;

	@Id
	@Column(name = "strPropertyCode", length = 50)
	@IColumn(description="属性代码", isNullable = false)
	private String strPropertyCode;
	
	@IColumn(description="属性名称", isKeyName=true, isNullable = false)
	@Column(name = "strPropertyName", length = 50)
	private String strPropertyName;

	public void setStrPropertyCode(String strPropertyCode) {
		this.strPropertyCode = strPropertyCode;
	}

	public String getStrPropertyCode() {
		return strPropertyCode;
	}

	public void setStrPropertyName(String strPropertyName) {
		this.strPropertyName = strPropertyName;
	}

	public String getStrPropertyName() {
		return strPropertyName;
	}

}
