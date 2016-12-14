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
 * <P>类名称：J_Task</P>
 * *********************************<br>
 * <P>类描述：监控任务定时调度信息</P>
 * 创建人：王川<br>
 * 创建时间：2016-11-10 下午4:27:06<br>
 * 修改人：王川<br>
 * 修改时间：2016-11-10 下午4:27:06<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@Entity
@Table(name="J_Task")
public class J_Task implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = -164867105435584372L;

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
	
	/** 采集步长 **/
	@Column(name = "cli", length = 10)
	@IColumn(description="采集步长")
	private int cli = 10;
	
	/** 采集频度  秒[s]/分[m]/时[h]/天[d]/周[w]/月[t]/年[y]**/
	@Column(name = "cfq", length = 10)
	@IColumn(description="采集频度 ",tagMethodName="getCfqTypes")
	private String cfq = "s";
	
	public static Map<String, String> getCfqTypes() {
		return ShowContext.getInstance().getShowEntityMap().get("cfqType");
	}
	
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

	public int getCli() {
		return cli;
	}

	public void setCli(int cli) {
		this.cli = cli;
	}

	public String getCfq() {
		return cfq;
	}

	public void setCfq(String cfq) {
		this.cfq = cfq;
	}

}
