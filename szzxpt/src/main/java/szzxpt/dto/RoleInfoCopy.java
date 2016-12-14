package szzxpt.dto;

import javax.persistence.Column;
import javax.persistence.Id;

import framework.interfaces.IColumn;

public class RoleInfoCopy implements java.io.Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Id
		@Column(name = "strRoleCode", length = 50)
		@IColumn(description="角色代码")
		private String strRoleCode;
		
		@Column(name = "strRoleName", length = 50, nullable = false)
		@IColumn(description="角色名称", isKeyName=true, isNullable = false)
		private String strRoleName;
		

		public void setStrRoleCode(String strRoleCode) {
			this.strRoleCode = strRoleCode;
		}

		public String getStrRoleCode() {
			return strRoleCode;
		}

		public void setStrRoleName(String strRoleName) {
			this.strRoleName = strRoleName;
		}

		public String getStrRoleName() {
			return strRoleName;
		}

}
