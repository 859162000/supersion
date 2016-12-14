package zxptsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;
	@Entity
	@Table(name = "QYZXGrantUserInfo")
	@IEntity(description="企业征信人行账户信息")
	public class QYZXGrantUserInfo implements java.io.Serializable{

		private static final long serialVersionUID = 1L;

		@Id
		@Column(name = "strUserCode", length = 50)
		@IColumn(description="系统用户名")
		private String strUserCode;
		
		@Column(name = "UserId", length = 50)
		@IColumn(description="用户名ID")
		private String UserId;

		@Column(name = "UserPwd", length = 50, nullable = false)
		@IColumn(description="用户名密码", isKeyName=true, isNullable = false)
		private String UserPwd;
		
		@Column(name = "strInstCode", length = 50, nullable = false)
		@IColumn(description="机构代码", isNullable = false)
		private String strInstCode;
		
		@Column(name = "strPBOCUrl", length = 50)
		@IColumn(description="征信查询人行URL")
		private String strPBOCUrl;
		
		public String getUserId() {
			return UserId;
		}


		public void setUserId(String userId) {
			UserId = userId;
		}


		public String getUserPwd() {
			return UserPwd;
		}


		public void setUserPwd(String userPwd) {
			UserPwd = userPwd;
		}


		/**
		 * @param strUserCode the strUserCode to set
		 */
		public void setStrUserCode(String strUserCode) {
			this.strUserCode = strUserCode;
		}


		/**
		 * @return the strUserCode
		 */
		public String getStrUserCode() {
			return strUserCode;
		}


		public void setStrInstCode(String strInstCode) {
			this.strInstCode = strInstCode;
		}


		public String getStrInstCode() {
			return strInstCode;
		}


		public void setStrPBOCUrl(String strPBOCUrl) {
			this.strPBOCUrl = strPBOCUrl;
		}


		public String getStrPBOCUrl() {
			return strPBOCUrl;
		}
}
