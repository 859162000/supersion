package report.service.procese;

import report.service.show.ShowAnalyseModel;
import framework.services.interfaces.IProcese;

public class IsAnalyseModelProcess implements IProcese{

	@Override
	public Object Procese(Object serviceResult) throws Exception {
		ShowAnalyseModel showAnalyseModel = (ShowAnalyseModel)serviceResult;
		showAnalyseModel.setAnalyse(true);
		return showAnalyseModel;
	}

}
