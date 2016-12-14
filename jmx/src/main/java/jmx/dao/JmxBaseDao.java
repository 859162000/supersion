package jmx.dao;

public interface JmxBaseDao {

	public boolean mergeCPU(String cfq,String cfq_,String cdate) throws Exception;
	
	public boolean mergeMEM(String cfq,String cfq_,String cdate) throws Exception;
	
}
