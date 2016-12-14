package jmx.dto;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;
import framework.show.ShowContext;

/**
 * 项目名称：jmx<br>
 * *********************************<br>
 * <P>类名称：J_Db</P>
 * *********************************<br>
 * <P>类描述：服务器数据库</P>
 * 创建人：王川<br>
 * 创建时间：2016-11-10 上午11:08:52<br>
 * 修改人：王川<br>
 * 修改时间：2016-11-10 上午11:08:52<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@Entity
@Table(name="J_Db")
public class J_Db implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = 6989867698305865270L;

	@Id
	@Column(name = "uuid", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String uuid;
	
	/** 服务器编号 **/
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sid", nullable = false)
	@IColumn(description="服务器",isNullable = false)
	private J_Server server;
	
	@Column(name = "name", length = 200)
	@IColumn(description="数据库")
	private String name;
	
	@Column(name = "dbType", length = 32)
	@IColumn(tagMethodName="getDbTypeTag",description="类型")
	private String dbType;
	
	public static Map<String,String> getDbTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("dbType");
	}
	
	@Column(name = "dir", length = 150)
	@IColumn(description="安装目录",isSpecialCharCheck=true)
	private String dir;
	
	@Column(name = "info", length = 800)
	@IColumn(description="详情")
	private String info;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public J_Server getServer() {
		return server;
	}

	public void setServer(J_Server server) {
		this.server = server;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
}
