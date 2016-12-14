package coresystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "PF_LegalInfo")
@IEntity(description="平台法人信息")
public class PF_LegalInfo implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", length = 36)
	@IColumn(description="AutoId")
	private String id;
	
	@Column(name = "strLegalCode", length = 20, nullable = false)
	@IColumn(description="法人Code", isNullable = false)
	private String strLegalCode;
	
	@Column(name = "strLegalName", length = 100, nullable = false)
	@IColumn(description="法人中文名", isNullable = false)
	private String strLegalName;
	
	@Column(name = "strSessionFactory", length = 50, nullable = false)
	@IColumn(description="SessionFactory", isNullable = false)
	private String strSessionFactory;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStrLegalCode() {
		return strLegalCode;
	}

	public void setStrLegalCode(String strLegalCode) {
		this.strLegalCode = strLegalCode;
	}

	public String getStrLegalName() {
		return strLegalName;
	}

	public void setStrLegalName(String strLegalName) {
		this.strLegalName = strLegalName;
	}

	public String getStrSessionFactory() {
		return strSessionFactory;
	}

	public void setStrSessionFactory(String strSessionFactory) {
		this.strSessionFactory = strSessionFactory;
	}
}
