package extend.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "SystemCodeValue")
@IEntity(navigationName="系统编码值",description="系统编码值")
public class SystemCodeValue implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "autoSystemCodeID", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String autoSystemCodeID;
	
	@Column(name = "strCode", length = 50, nullable = false)
	@IColumn(description="代码", isNullable = false)
	private String strCode;

	@Column(name = "strValue", length = 200, nullable = false)
	@IColumn(description="名称", isKeyName=true, isNullable = false)
	private String strValue;
	
	@Column(name="strOrder")
	@IColumn(description="顺序")
	private Integer strOrder;
	
	public Integer getStrOrder() {
		return strOrder;
	}

	public void setStrOrder(String strOrder) {
		this.strOrder = TypeParse.parseInt(strOrder);
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strSetCode", nullable = false)
	@IColumn(description="所属编码集", isNullable = false)
	private SystemCodeSet codeSet;

	public void setStrCode(String strCode) {
		this.strCode = strCode;
	}

	public String getStrCode() {
		return strCode;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	public String getStrValue() {
		return strValue;
	}

	public void setCodeSet(SystemCodeSet codeSet) {
		this.codeSet = codeSet;
	}

	public SystemCodeSet getCodeSet() {
		return codeSet;
	}

	public void setAutoSystemCodeID(String autoSystemCodeID) {
		this.autoSystemCodeID = autoSystemCodeID;
	}

	public String getAutoSystemCodeID() {
		return autoSystemCodeID;
	}

}

