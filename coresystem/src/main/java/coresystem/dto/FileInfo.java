package coresystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import extend.dto.Suit;
import framework.interfaces.IColumn;

@Entity
@Table(name = "FileInfo")
public class FileInfo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "strFileCode", length = 50)
	@IColumn(description="文件代码")
	private String strFileCode;
	
	@Column(name = "strFileName", length = 50, nullable = false)
	@IColumn(description="文件名称", isKeyName=true, isNullable = false)
	private String strFileName;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "suit")
	@IColumn(description="主题")
	private Suit suit;

	public void setSuit(Suit suit) {
		this.suit = suit;
	}

	public Suit getSuit() {
		return suit;
	}

	public void setStrFileName(String strFileName) {
		this.strFileName = strFileName;
	}

	public String getStrFileName() {
		return strFileName;
	}

	public void setStrFileCode(String strFileCode) {
		this.strFileCode = strFileCode;
	}

	public String getStrFileCode() {
		return strFileCode;
	}
	
}

