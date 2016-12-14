package zxptsystem.dto.condition;

import coresystem.dto.InstInfo;
import coresystem.dto.UserInfo;

public class QYZXQueryLog_Condition  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private UserInfo userInfo;
	private InstInfo instInfo;

	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public InstInfo getInstInfo() {
		return instInfo;
	}
	public void setInstInfo(InstInfo instInfo) {
		this.instInfo = instInfo;
	}
}
