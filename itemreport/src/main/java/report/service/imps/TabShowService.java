package report.service.imps;

import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.imps.ComponentObjectShowSaveService;

public class TabShowService extends ComponentObjectShowSaveService{
	
	private String defaultDao;
	
	public String getDefaultDao() {
		return defaultDao;
	}

	public void setDefaultDao(String defaultDao) {
		this.defaultDao = defaultDao;
	}

	@Override
	public void initSuccessResult() throws Exception {
		IParamObjectResultExecute defaultLogicDaoDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean(defaultDao);
		this.setServiceResult(defaultLogicDaoDao.paramObjectResultExecute(null));
	}

}
