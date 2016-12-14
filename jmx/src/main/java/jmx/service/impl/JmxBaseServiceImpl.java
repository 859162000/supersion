package jmx.service.impl;

import java.util.List;

import jmx.dao.JmxBaseDao;
import jmx.dto.J_App;
import jmx.dto.J_Db;
import jmx.dto.J_Server;
import jmx.dto.J_Task;
import jmx.service.JmxBaseService;
import jmx.vo.JCWhereVO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import framework.interfaces.IParamObjectResultExecute;

@SuppressWarnings("unchecked")
public class JmxBaseServiceImpl implements JmxBaseService{
	
	/** singleObjectFindByIdDao **/
	private IParamObjectResultExecute dao;
	
	/** singleObjectFindByCriteriaDao **/
	private IParamObjectResultExecute cDao;
	
	/** singleObjectFindByLimitCriteriaDao **/
	private IParamObjectResultExecute cLimitDao;
	
	private JmxBaseDao jmxBaseDao;
	
	public IParamObjectResultExecute getDao() {
		return dao;
	}

	public void setDao(IParamObjectResultExecute dao) {
		this.dao = dao;
	}

	public IParamObjectResultExecute getcDao() {
		return cDao;
	}

	public void setcDao(IParamObjectResultExecute cDao) {
		this.cDao = cDao;
	}

	public IParamObjectResultExecute getcLimitDao() {
		return cLimitDao;
	}

	public void setcLimitDao(IParamObjectResultExecute cLimitDao) {
		this.cLimitDao = cLimitDao;
	}

	public JmxBaseDao getJmxBaseDao() {
		return jmxBaseDao;
	}

	public void setJmxBaseDao(JmxBaseDao jmxBaseDao) {
		this.jmxBaseDao = jmxBaseDao;
	}

	public J_Server getServerById(String sid) throws Exception{
		J_Server server = (J_Server) dao.paramObjectResultExecute(new Object[]{J_Server.class.getName(),sid,null});
		if(server != null){
			//dbs
			DetachedCriteria cri = DetachedCriteria.forClass(J_Db.class);
			cri.add(Restrictions.eq("server", server));
			List<J_Db> dbs = (List<J_Db>) cDao.paramObjectResultExecute(new Object[]{cri,null});
			server.setDbs(dbs);
			//apps
			cri = DetachedCriteria.forClass(J_App.class);
			cri.add(Restrictions.eq("server", server));
			
			List<J_App> apps = (List<J_App>) cDao.paramObjectResultExecute(new Object[]{cri,null});
			server.setApps(apps);
		}
		return server;
	}

	@Override
	public Object getJCInfos(JCWhereVO where,Class<?> clazz) throws Exception {
		DetachedCriteria cri = DetachedCriteria.forClass(clazz);
		if(!"0".equals(where.getSid()))
		cri.add(Restrictions.eq("sid", where.getSid()));
		cri.add(Restrictions.eq("pid", where.getPid()));
		cri.add(Restrictions.eq("cfq", where.getCfq()));
		if(StringUtils.isNotBlank(where.getCdate())){
			cri.add(Restrictions.le("cdate", where.getCdate()));
		}
		if(where.isDesc())
			cri.addOrder(Order.desc(where.getOrder()));
		else
			cri.addOrder(Order.asc(where.getOrder()));
		return  cLimitDao.paramObjectResultExecute(new Object[]{cri,where.getLimit(),null});
	}
	
	public boolean merge(String cfq,String cfq_,String cdate) throws Exception {
		jmxBaseDao.mergeCPU(cfq, cfq_, cdate);
		jmxBaseDao.mergeMEM(cfq, cfq_, cdate);
		return true;
	}
	
	public J_Task getTaskById(String id) throws Exception{
		return (J_Task) dao.paramObjectResultExecute(new Object[]{J_Task.class.getName(),id,null});
	}
	
	public J_Task getTaskBySid(String sid) throws Exception{
		DetachedCriteria cri = DetachedCriteria.forClass(J_Task.class);
		/*cri.add(Property.forName("sid").eq(sid));*/
		cri.add(Restrictions.eq("server.uuid", sid));
		List<J_Task> tasks = (List<J_Task>) cDao.paramObjectResultExecute(new Object[]{cri,null});
		if(tasks != null&&tasks.size()>0)
			return tasks.get(0);
		return null;
	}
	
}
