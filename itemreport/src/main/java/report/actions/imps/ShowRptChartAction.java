package report.actions.imps;


import report.dto.RptChartCond;

import com.opensymphony.xwork2.ModelDriven;

import framework.actions.imps.BaseSTAction;
import framework.actions.imps.BaseSTModelDrivenAction;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.interfaces.TActionRule;

// 查询条件放到serviceParam,覆盖getModel接收struts提交的条件数据
public class ShowRptChartAction extends BaseSTAction implements ModelDriven<Object>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6783767667363414789L;
	
	private Object serviceParam;
		
	public void setServiceParam(Object serviceParam) {
		this.serviceParam = serviceParam;
	}

	public Object getServiceParam() {
		return serviceParam;
	}
	
	@Override
	public Object getModel() {
		this.setServiceParam(this.GetTObject());
		return null;
	}
	
	@Override
    public String execute() throws Exception {
		String result = super.execute();
		return result;
	}

	
}
