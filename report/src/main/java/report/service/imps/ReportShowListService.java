package report.service.imps;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import framework.services.imps.SingleObjectShowListService;
import framework.services.interfaces.LogicParamManager;

public class ReportShowListService extends SingleObjectShowListService{

	private String suitListCode;
	
	public void setSuitListCode(String suitListCode) {
		this.suitListCode = suitListCode;
	}

	public String getSuitListCode() {
		return suitListCode;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {
		
		DetachedCriteria detachedCriteria = LogicParamManager.getDetachedCriteria();
		detachedCriteria.add(Restrictions.in("suit.strSuitCode", suitListCode.split(",")));
		LogicParamManager.setDetachedCriteria(detachedCriteria);
		
		super.initSuccessResult();

	}
}
