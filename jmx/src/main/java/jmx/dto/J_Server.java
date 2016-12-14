package jmx.dto;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;

/**
 * 项目名称：jmx<br>
 * *********************************<br>
 * <P>类名称：J_Server</P>
 * *********************************<br>
 * <P>类描述：服务器信息</P>
 *  说明：
 *  	采集频度默认按5秒一次采集一次，一分钟采集12次，秒的采集放到内存中不做数据库插入（时分秒指的是自然时间  如： 05 10 15 20 ....采集根据qurz设置定时调度采集）
 * 		当采集周期达到一分钟【m】算出平均使用率，存入数据库
 * 		当采集周期达到一小时【h】后，以分算出平均使用率，并删除前2小时的采集数据，不包括前一小时的数据（界面要做查询分析用）
 * 		当采集周期达到一天【d】后，以小时为单位算出平均使用率，存入数据库
 * 		当采集周期达到一周【w】后，以天为单位算出平均使用率，存入数据库
 * 		当采集周期达到一月【M】后，以天为单位算出平均使用率，存入数据库
 * 		当采集周期达到一年【M】后，以月为单位算出平均使用率，存入数据库
 * 创建人：王川<br>
 * 创建时间：2016-11-10 上午11:02:40<br>
 * 修改人：王川<br>
 * 修改时间：2016-11-10 上午11:02:40<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@Entity
@Table(name="J_Server")
public class J_Server implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = 906723462005780313L;

	/** uuid **/
	@Id
	@Column(name = "uuid", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String uuid;
	
	/** 服务器名 **/
	@Column(name="name",length=150)
	@IColumn(description="服务器名")
	private String name;
	
	/** 主机 **/
	@Column(name="host",length=100)
	@IColumn(description="主机",isKeyName=true, isSpecialCharCheck=true)
	private String host;
	
	/** 端口 **/
	@Column(name="port",length=10)
	@IColumn(description="端口")
	private String port;
	
	/** 登录用户名 **/
	@Column(name="userName",length=50)
	@IColumn(description="登录用户名")
	private String userName;
	
	/** 密码 **/
	@Column(name="passwd",length=50)
	@IColumn(description="密码")
	private String passwd;
	
	/** 服务器配置总信息 **/
	@Column(name="info",length=800)
	@IColumn(description="详情")
	private String info;
	
	/** cpu **/
	@Transient
	private List<J_S_CPU> cpus;
	
	/** 内存 **/
	@Transient
	private List<J_S_MEM> mems;
	
	/** 磁盘 **/
	@Transient
	private List<J_S_VG> vgs;
	
	/** 数据库信息 **/
	@Transient
	private List<J_Db> dbs;
	
	/** 部署应用信息 **/
	@Transient
	private List<J_App> apps;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public List<J_S_CPU> getCpus() {
		return cpus;
	}

	public void setCpus(List<J_S_CPU> cpus) {
		this.cpus = cpus;
	}

	public List<J_S_MEM> getMems() {
		return mems;
	}

	public void setMems(List<J_S_MEM> mems) {
		this.mems = mems;
	}

	public List<J_S_VG> getVgs() {
		return vgs;
	}

	public void setVgs(List<J_S_VG> vgs) {
		this.vgs = vgs;
	}

	public List<J_Db> getDbs() {
		return dbs;
	}

	public void setDbs(List<J_Db> dbs) {
		this.dbs = dbs;
	}

	public List<J_App> getApps() {
		return apps;
	}

	public void setApps(List<J_App> apps) {
		this.apps = apps;
	}

}
