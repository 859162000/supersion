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
 * <P>类名称：J_App</P>
 * *********************************<br>
 * <P>类描述：服务器部署应用</P>
 * 创建人：王川<br>
 * 创建时间：2016-11-10 上午11:08:37<br>
 * 修改人：王川<br>
 * 修改时间：2016-11-10 上午11:08:37<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@Entity
@Table(name="J_App")
public class J_App implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = 4169503135009569141L;

	/** uuid **/
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
	
	/** 应用名称 **/
	@Column(name = "name", length = 200)
	@IColumn(description="应用名称")
	private String name;
	
	/** 应用类型 **/
	@Column(name = "appType", length = 32)
	@IColumn(tagMethodName="getAppTypeTag",description="类型")
	private String appType;
	
	public static Map<String,String> getAppTypeTag(){
		return ShowContext.getInstance().getShowEntityMap().get("appType");
	}
	
	/** 应用目录 **/
	@Column(name = "dir", length = 150)
	@IColumn(description="安装目录",isSpecialCharCheck=true)
	private String dir;
	
	/** web应用访问地址 **/
	@Column(name = "url", length = 200)
	@IColumn(description="访问地址")
	private String url;
	
	/** 详情 **/
	@Column(name = "info", length = 800)
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

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
