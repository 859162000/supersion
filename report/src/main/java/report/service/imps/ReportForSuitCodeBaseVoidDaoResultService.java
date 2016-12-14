package report.service.imps;

import framework.interfaces.RequestManager;
import framework.services.imps.BaseVoidDaoResultService;

public class ReportForSuitCodeBaseVoidDaoResultService  extends BaseVoidDaoResultService{

    private String strSuitCode;

	public String getStrSuitCode() {
		return strSuitCode;
	}

	public void setStrSuitCode(String strSuitCode) {
		this.strSuitCode = strSuitCode;
	}
	   
	@Override
	public Object objectResultExecute() throws Exception {
		report.dto.TaskFlow tObjct = (report.dto.TaskFlow)RequestManager.getTOject();
		tObjct.getSuit().setStrSuitCode(this.getStrSuitCode());
		return super.objectResultExecute();
	}
	
}
