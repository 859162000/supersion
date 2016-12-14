package framework.services.imps;

import framework.helper.FrameworkFactory;
import framework.interfaces.RequestManager;

//需要DAO支持的服务基类，D指DAO
//由xml配置bean时配置，spring建立时传入defaultDaoBeanId,tableDaoBeanId
public abstract class BaseDService extends BaseService{
	
    private String defaultDaoBeanId; 
	
	public void setDefaultDaoBeanId(String defaultDaoBeanId) {
		this.defaultDaoBeanId = defaultDaoBeanId;
	}
	
	private String tableDaoBeanId;
	
	public void setTableDaoBeanId(String tableDaoBeanId) {
		this.tableDaoBeanId = tableDaoBeanId;
	}
	
	private Object baseDaoObject; // 由service根据条件生成的DAO对象
	
	public void setBaseDaoObject(Object baseDaoObject) {
		this.baseDaoObject = baseDaoObject;
	}

	public Object getBaseDaoObject() {
		return baseDaoObject;
	}

	protected void initDao() throws Exception{
		if(this.baseDaoObject == null){
			if(this.baseDaoObject == null)
				this.baseDaoObject = FrameworkFactory.CreateBean(this.defaultDaoBeanId);
		}
	}
	
	@Override
	public void initSuccessResult() throws Exception {
		this.initDao();
	}
}
