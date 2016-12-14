package framework.dao.imps;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import framework.helper.FrameworkFactory;


public class BaseHibernateDao extends HibernateDaoSupport{
	
	private String defafaultSessionFactory;

	public void setDefafaultSessionFactory(String defafaultSessionFactory) {
		this.defafaultSessionFactory = defafaultSessionFactory;
	}

	public String getDefafaultSessionFactory() {
		return defafaultSessionFactory;
	}
	
	protected void initSessionFactory(Object param) {
		if(param != null){
			if(!param.toString().equals("sessionFactory")){
				SessionFactory sessionFactory = (SessionFactory)FrameworkFactory.CreateBean(param.toString());
				this.setSessionFactory(sessionFactory);
			}
		}
	}
}

