package zxptsystem.dto;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "SpareRHAccountInfo")
@IEntity(description= "备用人行账户")
public class SpareRHAccountInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3497132700194650281L;

	@Id
	@Column(name = "userId", length = 32)
	@IColumn(description="用户名ID")
	private String userId;
	
	@Column(name = "pwd", length = 50, nullable = false)
	@IColumn(description="用户密码", isNullable = false)
	private String pwd;
	
	@Column(name = "status", length = 1, nullable = false)
	@IColumn(description="是否启用",tagMethodName="getStatusTag", isNullable = false)
	private String status;
	
	public static Map<String,String> getStatusTag() {
		try {
			Map<String,String> map = new LinkedHashMap<String,String>();
			map.put("0", "是");
			map.put("1", "否");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
