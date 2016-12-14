package jmx.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 项目名称：jmx<br>
 * *********************************<br>
 * <P>类名称：J_S_MEM</P>
 * *********************************<br>
 * <P>类描述：服务器配置信息--内存采集信息</P>
 * 创建人：王川<br>
 * 创建时间：2016-11-10 上午11:10:04<br>
 * 修改人：王川<br>
 * 修改时间：2016-11-10 上午11:10:04<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@Entity
@Table(name="J_S_MEM")
public class J_S_MEM implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = 3172533363748681472L;
	
	public J_S_MEM(){
		
	}

	public J_S_MEM(String sid, int cli, String cdate) {
		super();
		this.sid = sid;
		this.cli = cli;
		this.cdate = cdate;
	}

	public J_S_MEM(String sid, String pid, int cli, String cdate) {
		super();
		this.sid = sid;
		this.pid = pid;
		this.cli = cli;
		this.cdate = cdate;
	}
	
	public J_S_MEM(String sid, String pid, int cli, String cfq, String cdate) {
		super();
		this.sid = sid;
		this.pid = pid;
		this.cli = cli;
		this.cfq = cfq;
		this.cdate = cdate;
	}

	public J_S_MEM(String sid, String pid, double useRate) {
		super();
		this.sid = sid;
		this.pid = pid;
		this.useRate = useRate;
	}

	/** uuid **/
	@Id
	@Column(name = "uuid", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String uuid;
	
	/** 服务器编号 **/
	@Column(name = "sid", length = 32)
	private String sid;
	
	/** 进程id、区分应用,服务器的为 0,来源于app或db **/
	@Column(name = "pid", length = 32)
	private String pid = "0";
	
	/** 采集步长 **/
	@Column(name = "cli", length = 10)
	private int cli = 1;
	
	/** 采集频度  秒[s]/分[m]/时[h]/天[d]/周[w]/月[t]/年[y]**/
	@Column(name = "cfq", length = 10)
	private String cfq = "s";
	
	/** 使用率 **/
	@Column(name = "useRate")
	private double useRate;
	
	/** 采集时间 **/
	@Column(name = "cdate", length = 30)
	private String cdate;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
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

	public double getUseRate() {
		return useRate;
	}

	public void setUseRate(double useRate) {
		this.useRate = useRate;
	}

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	
	@Override
	public String toString() {
		return sid+"\t"+pid+"\t"+cli+"\t"+cfq+"\t"+useRate+"\t"+cdate;
	}
	
}
