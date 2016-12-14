package framework.services.imps;

import framework.interfaces.IParamObjectResultExecute;


public class BaseObjectDaoResultService extends BaseDTService{
	
	private IParamObjectResultExecute baseObjectDao;
	
	public IParamObjectResultExecute getBaseObjectDao() {
		return baseObjectDao;
	}

	public void setBaseObjectDao(IParamObjectResultExecute baseObjectDao) {
		this.baseObjectDao = baseObjectDao;
	}
	
    @Override
	protected void initDao() throws Exception{
    	super.initDao();
    	baseObjectDao = (IParamObjectResultExecute)this.getBaseDaoObject();
	}

	@Override
	public void initSuccessResult() throws Exception {
		super.initSuccessResult();
		this.setServiceResult(this.baseObjectDao.paramObjectResultExecute(null));
	}
}
