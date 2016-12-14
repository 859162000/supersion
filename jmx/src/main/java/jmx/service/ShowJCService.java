package jmx.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jmx.dto.J_S_CPU;
import jmx.dto.J_S_MEM;
import jmx.vo.JCWhereVO;

import org.apache.struts2.ServletActionContext;

import framework.interfaces.IObjectResultExecute;

/**
 * 项目名称：jmx<br>
 * *********************************<br>
 * <P>类名称：ShowCpuService</P>
 * *********************************<br>
 * <P>类描述：采集信息图表查看</P>
 * 创建人：王川<br>
 * 创建时间：2016-11-23 上午10:13:42<br>
 * 修改人：王川<br>
 * 修改时间：2016-11-23 上午10:13:42<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class ShowJCService  implements IObjectResultExecute{
	
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
		String slimit = ServletActionContext.getRequest().getParameter("limit")==null?"s":ServletActionContext.getRequest().getParameter("limit");
		//监控设备用  , 分开，"CPU,MEM,VG"
		String sdevices = ServletActionContext.getRequest().getParameter("devices")==null?"CPU":ServletActionContext.getRequest().getParameter("devices");
		int limit = Integer.parseInt(slimit);
		int cli = 1;
		
		Map map = new HashMap();
		JCWhereVO where = new JCWhereVO(sid, pid, cli, cfq, cdate,limit);
		
		if(sdevices.indexOf(",")<0){
			List data = new ArrayList();
			List<String> category = new ArrayList();
			if("CPU".equals(sdevices.toUpperCase())){
				List<J_S_CPU> list = (List<J_S_CPU>) baseService.getJCInfos(where, J_S_CPU.class);
				for(int i = list.size()-1; i>=0; i--){
					J_S_CPU o = list.get(i);
					data.add(o.getUseRate());
					category.add(o.getCdate());
				}
			}
			if("MEM".equals(sdevices.toUpperCase())){
				List<J_S_MEM> list = (List<J_S_MEM>) baseService.getJCInfos(where, J_S_MEM.class);
				for(int i = list.size()-1; i>=0; i--){
					J_S_MEM o = list.get(i);
					data.add(o.getUseRate());
					category.add(o.getCdate());
				}
			}
			map.put("data", data);
			map.put("category", category);
		}
		else{
			String[] devices = sdevices.split(",");
			for(String device : devices){
				List data = new ArrayList();
				List<String> category = (List) (map.get("category")==null?new ArrayList():map.get("category"));
				if("CPU".equals(device.toUpperCase())){
					List<J_S_CPU> list = (List<J_S_CPU>) baseService.getJCInfos(where, J_S_CPU.class);
					if(category.size()>0){
						int i = 0;
						int j = list.size()-1;
						for(String c:category){
							for(;j>=0;j--){
								J_S_CPU o = list.get(j);
								if(c.equals(o.getCdate())){
									data.add(i, o.getUseRate());
									break;
								}
							}
							i++;
						}
					}else{
						for(int i = list.size()-1; i>=0; i--){
							J_S_CPU o = list.get(i);
							data.add(o.getUseRate());
							category.add(o.getCdate());
						}
						map.put("category", category);
					}
					map.put("CPU", data);
				}
				if("MEM".equals(device.toUpperCase())){
					List<J_S_MEM> list = (List<J_S_MEM>) baseService.getJCInfos(where, J_S_MEM.class);
					if(category.size()>0){
						int i = 0;
						int j = list.size()-1;
						for(String c:category){
							for(;j>=0;j--){
								J_S_MEM o = list.get(j);
								if(c.equals(o.getCdate())){
									data.add(i, o.getUseRate());
									break;
								}
							}
							i++;
						}
					}else{
						for(int i = list.size()-1; i>=0; i--){
							J_S_MEM o = list.get(i);
							data.add(o.getUseRate());
							category.add(o.getCdate());
						}
						map.put("category", category);
					}
					map.put("MEM", data);
				}
			}
		}
		return map;
	}
	

}
