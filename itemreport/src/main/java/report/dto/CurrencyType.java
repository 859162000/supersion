package report.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "CurrencyType")
@IEntity(navigationName="指标币种管理",description="指标币种管理")
public class CurrencyType implements java.io.Serializable{

	/**  **/
	private static final long serialVersionUID = 3721857489446102586L;

	@Id
	@Column(name = "strCurrencyCode", length = 50)
	@IColumn(description="币种代码")
	private String strCurrencyCode;
	
	@Column(name = "strCurrencyName", length = 50, nullable = false)
	@IColumn(description="币种名称", isKeyName=true, isNullable = false)
	private String strCurrencyName;

	@Column(name = "strAbbrv", length = 50)
	@IColumn(description="缩写", isKeyName=false)
	private String strAbbrv;

	public void setStrCurrencyCode(String strCurrencyCode) {
		this.strCurrencyCode = strCurrencyCode;
	}

	public String getStrCurrencyCode() {
		return strCurrencyCode;
	}

	public void setStrCurrencyName(String strCurrencyName) {
		this.strCurrencyName = strCurrencyName;
	}

	public String getStrCurrencyName() {
		return strCurrencyName;
	}

	public void setStrAbbrv(String strAbbrv) {
		this.strAbbrv = strAbbrv;
	}

	public String getStrAbbrv() {
		return strAbbrv;
	}
	
}

