package coresystem.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;
import framework.interfaces.RequestManager;
import framework.security.Function;
import framework.security.SecurityContext;

@Entity
@Table(name = "RoleFunction")
public class RoleFunction implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strRoleCode", nullable = false)
	private RoleInfo roleInfo;
	
	@IColumn(tagMethodName="getFunctionTag",description="模块", isNullable = false)
	@Column(name = "strFunctionCode", nullable = false)
	private String strFunctionCode;

	@Transient
	@IColumn(isIdListField = true, target="strFunctionCode")
	private String[] functionCodeList;
	
	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String id;

	public static Object getFunctionTag() throws Exception{
		try{
			if(RequestManager.getActionName().contains("ShowUpdateList")){	
				List<Function> functionList = new ArrayList<Function>();
				Set<String> setStrFunc = new HashSet<String>();
				for(Function function : SecurityContext.getInstance().getLoginInfo().getFunctionSet()){
					setStrFunc.add(function.getFunctionCode());
				}
				for(Function function : SecurityContext.getInstance().getFunctionList()){
					if(setStrFunc.contains(function.getFunctionCode())){
						functionList.add(function);
					}
				}
				return functionList;
			}
			else{
				return SecurityContext.getInstance().getFunctionMap();
			}
		}
		catch(Exception ex){
			return SecurityContext.getInstance().getFunctionMap();
		}
	
	}
	
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setRoleInfo(RoleInfo roleInfo) {
		this.roleInfo = roleInfo;
	}

	public RoleInfo getRoleInfo() {
		return roleInfo;
	}

	public void setStrFunctionCode(String strFunctionCode) {
		this.strFunctionCode = strFunctionCode;
	}

	public String getStrFunctionCode() {
		return strFunctionCode;
	}

	public void setFunctionCodeList(String[] functionCodeList) {
		this.functionCodeList = functionCodeList;
	}

	public String[] getFunctionCodeList() {
		return functionCodeList;
	}
}
