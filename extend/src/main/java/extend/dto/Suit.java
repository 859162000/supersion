package extend.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "Suit")
@IEntity(navigationName="主题",description="主题")
public class Suit implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "strSuitCode", length = 50, nullable = false)
	@IColumn(description="主题代码", isNullable = false)
	private String strSuitCode;
	
	@Column(name = "strSuitName", length = 50, nullable = false)
	@IColumn(description="主题名称", isKeyName=true, isNullable = false, isSpecialCharCheck=true)
	private String strSuitName;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "higerSuit")
	@IColumn(description="上级主题")
	private Suit higerSuit;

	public void setStrSuitCode(String strSuitCode) {
		this.strSuitCode = strSuitCode;
	}

	public String getStrSuitCode() {
		return strSuitCode;
	}

	public void setStrSuitName(String strSuitName) {
		this.strSuitName = strSuitName;
	}

	public String getStrSuitName() {
		return strSuitName;
	}

	public void setHigerSuit(Suit higerSuit) {
		this.higerSuit = higerSuit;
	}

	public Suit getHigerSuit() {
		return higerSuit;
	}

}
