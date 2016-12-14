package extend.dto;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import framework.interfaces.IColumn;
import framework.show.ShowContext;

@Entity
@Table(name = "SystemParam")
public class SystemParam implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "strParamCode", length = 50)
	@IColumn(tagMethodName="getParamTag", description="参数代码")
	private String strItemCode;
	
	public static Map<String,String> getParamTag(){
		return ShowContext.getInstance().getShowEntityMap().get("configParam");
	}
	
	@IColumn(description="参数描述", isKeyName=true)
	@Column(name = "strParamName", length = 100)
	private String strParamName;
	
	@IColumn(description="参数值",isSpecialCharCheck=true)
	@Column(name = "strParamValue", length = 500)
	private String strParamValue;

	@IColumn(tagMethodName="getUsable", description="是否可用",isNullable = false)
	@Column(name = "strEnable", length = 5)
	private String strEnable;
	
	public static Map<String,String> getUsable(){
		return ShowContext.getInstance().getShowEntityMap().get("isPrimaryKey");
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "suit")
	@IColumn(description="主题")
	private Suit suit;
	
	public void setStrItemCode(String strItemCode) {
		this.strItemCode = strItemCode;
	}

	public String getStrItemCode() {
		return strItemCode;
	}

	public void setStrParamName(String strParamName) {
		this.strParamName = strParamName;
	}

	public String getStrParamName() {
		return strParamName;
	}

	public void setStrParamValue(String strParamValue) {
		this.strParamValue = strParamValue;
	}

	public String getStrParamValue() {
		return strParamValue;
	}

	public void setStrEnable(String strEnable) {
		this.strEnable = strEnable;
	}

	public String getStrEnable() {
		return strEnable;
	}

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public Suit getSuit() {
		return suit;
	}
}
