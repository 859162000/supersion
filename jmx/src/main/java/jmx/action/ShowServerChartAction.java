package jmx.action;

import java.util.Map;

import jmx.dto.J_Task;
import jmx.service.JmxBaseService;
import framework.actions.imps.BaseAction;
import framework.helper.FrameworkFactory;
import framework.show.ShowContext;

public class ShowServerChartAction extends BaseAction {
	
	/**  **/
	private static final long serialVersionUID = 2149084706946486523L;

	private String sid;
	
	private String pid = "0";
	
	private String cfq = "s";
	
	private int cli = 10000;
	
	private String cdate;
	
	private String type;
	
	private String windowId;
	
	@Override
	public String execute() throws Exception {
		JmxBaseService service = (JmxBaseService) FrameworkFactory.CreateBean("jmxBaseService");
		J_Task task = service.getTaskBySid(sid);
		if(task != null){
			cli = task.getCli()*1000;
		}
		return super.execute();
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

	public String getCfq() {
		return cfq;
	}

	public void setCfq(String cfq) {
		this.cfq = cfq;
	}

	public int getCli() {
		return cli;
	}

	public void setCli(int cli) {
		this.cli = cli;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

	public String getWindowId() {
		return windowId;
	}

	public void setWindowId(String windowId) {
		this.windowId = windowId;
	}

	public Map<String, String> getCfqTypes() {
		return ShowContext.getInstance().getShowEntityMap().get("cfqType");
	}


}
