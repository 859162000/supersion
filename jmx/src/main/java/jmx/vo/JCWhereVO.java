package jmx.vo;

import java.io.Serializable;

/**
 * 项目名称：jmx<br>
 * *********************************<br>
 * <P>类名称：AuToken</P>
 * *********************************<br>
 * <P>类描述：图形化采集查询条件where</P>
 * 创建人：王川<br>
 * 创建时间：2016-11-21 下午4:59:05<br>
 * 修改人：王川<br>
 * 修改时间：2016-11-21 下午4:59:05<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
public class JCWhereVO implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = 6771249178533297078L;
	private String sid = "0";
	private String pid = "0";
	private int cli = 1;
	private String cfq = "s";
	private String cdate;
	private int limit = 1;
	private String order = "cdate";
	private boolean desc = true;
	
	public JCWhereVO(){}
	
	public JCWhereVO(String sid, String pid, int cli, String cfq, String cdate) {
		super();
		this.sid = sid;
		this.pid = pid;
		this.cli = cli;
		this.cfq = cfq;
		this.cdate = cdate;
	}
	
	public JCWhereVO(String sid, String pid, int cli, String cfq, String cdate, int limit) {
		super();
		this.sid = sid;
		this.pid = pid;
		this.cli = cli;
		this.cfq = cfq;
		this.cdate = cdate;
		this.limit = limit;
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
	public String getCdate() {
		return cdate;
	}
	public void setCdate(String cdate) {
		this.cdate = cdate;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}

	public boolean isDesc() {
		return desc;
	}

	public void setDesc(boolean desc) {
		this.desc = desc;
	}
	
}
