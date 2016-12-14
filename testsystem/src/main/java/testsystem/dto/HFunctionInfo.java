package testsystem.dto;

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

@Entity
@Table(name = "HFunctionInfo")
public class HFunctionInfo implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "strFunctionCode", length = 50)
	@IColumn(description="模块代码")
	private String strFunctionCode;
	
	@Column(name = "strFunctionName", length = 50, nullable = false)
	@IColumn(description="模块名称", isKeyName=true)
	private String strFunctionName;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "functionInfo")
	private Set<HRoleFunctionInst> roleFunctionInsts = new HashSet<HRoleFunctionInst>(0);

	public void setStrFunctionCode(String strFunctionCode) {
		this.strFunctionCode = strFunctionCode;
	}

	public String getStrFunctionCode() {
		return strFunctionCode;
	}

	public void setStrFunctionName(String strFunctionName) {
		this.strFunctionName = strFunctionName;
	}

	public String getStrFunctionName() {
		return strFunctionName;
	}

	public void setRoleFunctionInsts(Set<HRoleFunctionInst> roleFunctionInsts) {
		this.roleFunctionInsts = roleFunctionInsts;
	}

	public Set<HRoleFunctionInst> getRoleFunctionInsts() {
		return roleFunctionInsts;
	}
}

