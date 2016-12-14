package jmx.dao.impl;

import java.util.List;

import jmx.dao.JmxBaseDao;
import jmx.dto.J_S_CPU;
import jmx.dto.J_S_MEM;

import org.hibernate.Query;
import org.hibernate.Session;

import framework.dao.imps.BaseHibernateDao;

@SuppressWarnings("unchecked")
public class JmxBaseDaoImpl extends BaseHibernateDao implements JmxBaseDao {
	
	
	public boolean mergeCPU(String cfq,String cfq_,String cdate) throws Exception{
		String hql = "select new jmx.dto.J_S_CPU(c.sid,c.pid,avg(c.useRate))  from J_S_CPU c where c.cfq=? and c.cdate like ? group by c.sid,c.pid";
		Session session = getSession();
		Query query = session.createQuery(hql);
		query.setString(0, cfq);
		query.setString(1, cdate+"%");
		List<J_S_CPU> cpus = query.list();
		if(cpus.size()>0){
			String sql = "delete from J_S_CPU where cfq='"+cfq_+"' and cdate='"+cdate+"'";
			session.createSQLQuery(sql).executeUpdate();
			for(J_S_CPU cpu:cpus){
				cpu.setCli(1);
				cpu.setCfq(cfq_);
				cpu.setCdate(cdate);
				session.save(cpu);
			}
		}
		session.flush();
		session.clear();
		session.close();
		return true;
	}
	
	public boolean mergeMEM(String cfq,String cfq_,String cdate) throws Exception{
		String hql = "select new jmx.dto.J_S_MEM(c.sid,c.pid,avg(c.useRate))  from J_S_MEM c where c.cfq=? and c.cdate like ? group by c.sid,c.pid";
		Session session = getSession();
		Query query = session.createQuery(hql);
		query.setString(0, cfq);
		query.setString(1, cdate+"%");
		List<J_S_MEM> mems = query.list();
		
		if(mems.size()>0){
			String sql = "delete from J_S_MEM where cfq='"+cfq_+"' and cdate='"+cdate+"'";
			session.createSQLQuery(sql).executeUpdate();
			for(J_S_MEM mem:mems){
				mem.setCli(1);
				mem.setCfq(cfq_);
				mem.setCdate(cdate);
				session.save(mem);
			}
		}
		session.flush();
		session.clear();
		session.close();
		return true;
	}
	
}
