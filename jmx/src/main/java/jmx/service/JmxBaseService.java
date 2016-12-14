package jmx.service;

import jmx.dto.J_Server;
import jmx.dto.J_Task;
import jmx.vo.JCWhereVO;

public interface JmxBaseService {
	
	/**
	 * <p>方法描述:根据id获取server </p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param sid
	 * @return
	 * @throws Exception
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-11-23 上午10:42:10</p>
	 */
	public J_Server getServerById(String sid) throws Exception;
	
	/**
	 * <p>方法描述: 根据查询条件获取采集信息</p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param where
	 * @param row
	 * @return
	 * @throws Exception
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-11-23 上午10:42:22</p>
	 */
	public Object getJCInfos(JCWhereVO where,Class<?> clazz) throws Exception;
	
	//汇总
	public boolean merge(String cfq,String cfq_,String cdate) throws Exception;
	//获取任务
	public J_Task getTaskById(String id) throws Exception;
	public J_Task getTaskBySid(String sid) throws Exception;
	
	
}
