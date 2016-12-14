package report.actions.imps;

import org.apache.struts2.ServletActionContext;
import framework.actions.imps.BaseSTAction;

public class AjaxRequestInstanceAction extends BaseSTAction {
	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		ServletActionContext.getContext().getSession().put("instanceId", this.getInstanceId());	
		ServletActionContext.getContext().getSession().put("itemCode", this.getItemCode());	
		
		ServletActionContext.getContext().getSession().put("Cur", this.getBz());	
		ServletActionContext.getContext().getSession().put("Prop", this.getSx());	
		ServletActionContext.getContext().getSession().put("DtDate", this.getRq());	
		ServletActionContext.getContext().getSession().put("Freq", this.getPd());	
		ServletActionContext.getContext().getSession().put("Jg", this.getJg());	
		ServletActionContext.getContext().getSession().put("Kz1", this.getKz1());	
		ServletActionContext.getContext().getSession().put("Kz2", this.getKz2());	
		ServletActionContext.getContext().getSession().put("Qs", this.getQs());	
		ServletActionContext.getContext().getSession().put("Kbj", this.getKbj());	
		
		super.execute();
		this.setResult(this.getServiceResult().toString());
		return SUCCESS;
	}
	
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getRid() {
		return rid;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getBz() {
		return bz;
	}

	private String instanceId;
	private String itemCode;
	private String result;
	private String rid;
	
	private String bz;
	private String sx;
	private String rq;
	private String pd;
	private String jg;
	private String kz1;
	private String kz2;
	private String qs;
	private String kbj;

	public String getSx() {
		return sx;
	}

	public void setSx(String sx) {
		this.sx = sx;
	}

	public String getRq() {
		return rq;
	}

	public void setRq(String rq) {
		this.rq = rq;
	}

	public String getPd() {
		return pd;
	}

	public void setPd(String pd) {
		this.pd = pd;
	}

	public String getJg() {
		return jg;
	}

	public void setJg(String jg) {
		this.jg = jg;
	}

	public String getKz1() {
		return kz1;
	}

	public void setKz1(String kz1) {
		this.kz1 = kz1;
	}

	public String getKz2() {
		return kz2;
	}

	public void setKz2(String kz2) {
		this.kz2 = kz2;
	}

	public String getQs() {
		return qs;
	}

	public void setQs(String qs) {
		this.qs = qs;
	}

	public String getKbj() {
		return kbj;
	}

	public void setKbj(String kbj) {
		this.kbj = kbj;
	}
}
