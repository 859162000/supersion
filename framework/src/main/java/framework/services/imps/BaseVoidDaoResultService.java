package framework.services.imps;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import framework.interfaces.IParamVoidResultExecute;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;

public class BaseVoidDaoResultService extends BaseDTService{

	private IParamVoidResultExecute baseVoidDao;
	 
    public IParamVoidResultExecute getBaseVoidDao() {
		return baseVoidDao;
	}

	public void setBaseVoidDao(IParamVoidResultExecute baseVoidDao) {
		this.baseVoidDao = baseVoidDao;
	}
	
	private String[] logicPrimaryKey;
	
	@Override
	protected void initDao() throws Exception{
	 	super.initDao();
	 	this.baseVoidDao = (IParamVoidResultExecute)this.getBaseDaoObject();
	}

	@Override
    public void initSuccessResult() throws Exception {
		super.initSuccessResult();
		
		try {
			if(baseVoidDao != null) {
				this.baseVoidDao.paramVoidResultExecute(null);
			}
		}
		catch (DataIntegrityViolationException ex) {
			try{
				ConstraintViolationException exCons = (ConstraintViolationException)ex.getCause();
				String msg = "执行失败：" +exCons.getSQLException().getLocalizedMessage().replace("\r\n", "\\r\\n");
				msg = msg.replace('\'', '|').replace('\"', '|').replace("\n", "\\r\\n");
				setServiceResult(new MessageResult(false, msg));
				return ;
			}
			catch(Exception ex1){
				throw ex;
			}
		}
		
		this.setServiceResult(new MessageResult(this.getSuccessMessage()));
	}
	
	@Override
	public void extendErrorResult() throws Exception {
		MessageResult messageResult = (MessageResult)this.getServiceResult();
		messageResult.setMessage(this.getErrorMessage());
		messageResult.AlertTranslate();
	}
	
	@Override
	public void init() throws Exception{
	 	super.init();
	 	LogicParamManager.setLogicPrimaryKey(logicPrimaryKey);
	}

	public void setLogicPrimaryKey(String[] logicPrimaryKey) {
		this.logicPrimaryKey = logicPrimaryKey;
	}

	public String[] getLogicPrimaryKey() {
		return logicPrimaryKey;
	}
}
