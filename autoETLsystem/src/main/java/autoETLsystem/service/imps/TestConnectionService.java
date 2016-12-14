package autoETLsystem.service.imps;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.web.context.ContextLoader;

import extend.dto.AutoETL_DataSource;
import framework.helper.FrameworkFactory;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.interfaces.MessageResult;

public class TestConnectionService implements IObjectResultExecute{

	public Object objectResultExecute() throws Exception {
    	IParamObjectResultExecute defaultLogicDaoDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
    	AutoETL_DataSource autoETL_DataSource = (AutoETL_DataSource)defaultLogicDaoDao.paramObjectResultExecute(null);
    	
    	try{
    		SessionFactory sessionFactory = (SessionFactory)ContextLoader.getCurrentWebApplicationContext().getBean(autoETL_DataSource.getSessionFactory());
    		Session session = sessionFactory.openSession();
    		Transaction  trans = session.beginTransaction();
    		trans.rollback();    		
    		session.close();
    		
    		return new MessageResult("连接成功");

    	}
    	catch(Exception ex){
    		return new MessageResult(ex.getMessage().replace("'", ""));
    	}
	}
}
