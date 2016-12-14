package jmx.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jmx.dto.J_S_MEM;
import jmx.vo.JCWhereVO;

import org.apache.struts2.ServletActionContext;

import framework.interfaces.IObjectResultExecute;

/**
 * 项目名称：jmx<br>
 * *********************************<br>
 * <P>类名称：ShowMemService</P>
 * *********************************<br>
 * <P>类描述：MEM图表查看</P>
 * 创建人：王川<br>
 * 创建时间：2016-11-23 上午10:13:57<br>
 * 修改人：王川<br>
 * 修改时间：2016-11-23 上午10:13:57<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class ShowMemService implements IObjectResultExecute{
	
	private JmxBaseService baseService;
	
	public JmxBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(JmxBaseService baseService) {
		this.baseService = baseService;
	}
	
	@Override
	public Object objectResultExecute() throws Exception {
		String sid = ServletActionContext.getRequest().getParameter("sid")==null?"0":ServletActionContext.getRequest().getParameter("sid");
		String pid = ServletActionContext.getRequest().getParameter("pid")==null?"0":ServletActionContext.getRequest().getParameter("pid");
		String cfq = ServletActionContext.getRequest().getParameter("cfq")==null?"s":ServletActionContext.getRequest().getParameter("cfq");
		String cdate = ServletActionContext.getRequest().getParameter("cdate")==null?"":ServletActionContext.getRequest().getParameter("cdate");
		String slimit = ServletActionContext.getRequest().getParameter("limit")==null?"1":ServletActionContext.getRequest().getParameter("limit");
		int limit = Integer.parseInt(slimit);
		int cli = 1;
		JCWhereVO where = new JCWhereVO(sid, pid, cli, cfq, cdate,limit);
		List<J_S_MEM> mems = (List<J_S_MEM>) baseService.getJCInfos(where, J_S_MEM.class);
		Map map = new HashMap();
		List data = new ArrayList();
		List category = new ArrayList();
		
		for(int i = mems.size()-1; i>=0; i--){
			J_S_MEM mem = mems.get(i);
			data.add(mem.getUseRate());
			category.add(mem.getCdate());
		}
		map.put("data", data);
		map.put("category", category);
		return map;
	}
	
}
