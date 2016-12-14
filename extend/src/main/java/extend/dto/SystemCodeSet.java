package extend.dto;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "SystemCodeSet")
@IEntity(navigationName="系统编码设置",description="系统编码设置")
public class SystemCodeSet implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "strCodeID", length = 50, nullable = false)
	@IColumn(description="编码ID", isNullable = false)
	private String strCodeID;
	
	@IColumn(description="编码名称", isKeyName=true, isNullable = false)
	@Column(name = "strSetName", length = 200, nullable = false)
	private String strSetName;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "codeSet")
	private Set<SystemCodeValue> codes = new HashSet<SystemCodeValue>(0);

	public void setStrCodeID(String strCodeID) {
		this.strCodeID = strCodeID;
	}

	public String getStrCodeID() {
		return strCodeID;
	}

	public void setStrSetName(String strSetName) {
		this.strSetName = strSetName;
	}

	public String getStrSetName() {
		return strSetName;
	}

	public void setCodes(Set<SystemCodeValue> codes) {
		this.codes = codes;
	}

	public Set<SystemCodeValue> getCodes() {
		return codes;
	}

	
}
