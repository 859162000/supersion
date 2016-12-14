package jmx.jsch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jmx.dto.J_S_CPU;
import jmx.dto.J_S_MEM;
import jmx.helper.NowDate;

/**
 * 项目名称：jmx<br>
 * *********************************<br>
 * <P>类名称：Top</P>
 * *********************************<br>
 * <P>类描述：使用top命令对 linux服务器进行CPU和内存的采集</P>
 * 创建人：王川<br>
 * 创建时间：2016-11-21 下午3:25:30<br>
 * 修改人：王川<br>
 * 修改时间：2016-11-21 下午3:25:30<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
public class Top {

	private static DecimalFormat df = new DecimalFormat("#.00");
	
	/**
	 * <p>方法描述:cpu、内存收集 </p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param sid  服务器id
	 * @param shell shell 连接方式
	 * @param applys 监控的应用  key[dir]+val[pid]
	 * @param cpuList 返回的j_s_cpu列表
	 * @param memList 返回的j_s_mem列表
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-11-21 下午2:38:50</p>
	 */
	public static void get(String sid,int cli, Shell shell, Map<String,String> applys, List<J_S_CPU> cpuList, List<J_S_MEM> memList){
		BufferedReader pline = null;
		String line = null;
		String command = null;
		//[PID,pid]
		Map<String,String> pidMap = new HashMap<String, String>();
		try {
			//连接超时5秒
			shell.connect(5000);
			if(applys.size()>0){
				//获取应用的pid
				command = "ps -s | grep -E '";
				for(String dir:applys.keySet()){
					command += dir+"|"; 
				}
				command = command.substring(0, command.length()-1)+"'";
				pline = new BufferedReader(new InputStreamReader(shell.exec(command)));
				while ((line = pline.readLine()) != null) {
					for(String dir:applys.keySet()){
						if(line.indexOf(dir) > -1){
							String[] temp = line.split("\\s+");
							pidMap.put(temp[2], applys.get(dir));
							break;
						}
					}
				}
				pline.close();
				shell.disconnect();
			}
			//获取cpu+mem
			command = "top -b -n 1 -p 0";
			for (String pid : pidMap.keySet()) {
				command += "," + pid;
			}
			int l = 1;
			pline = new BufferedReader(new InputStreamReader(shell.exec(command)));
			//采集频度 10秒,自动校验时间(int) ss/10*10
			String cdate = NowDate.dateStr();
			while ((line = pline.readLine()) != null) {
				if (line.startsWith("top")) {
					//注：我这儿偷了懒，直接将最后一秒替换为  0 ,如果换位其它采集步长会有错误
					/*String[] temp = line.split("\\s+");
					String time = temp[2].substring(0, temp[2].length()-1)+"0";
					cdate += " "+ time;*/
					cdate = cdate.substring(0, cdate.length()-1)+"0";
				}
				if (line.startsWith("%Cpu")) {
					//服务器CPU负荷
					String[] temp = line.split("\\s+");
					J_S_CPU cpu = new J_S_CPU(sid,cli,cdate);
					cpu.setUseRate(Double.parseDouble(temp[1]));
					cpuList.add(cpu);
				}
				if (line.startsWith("Cpu")) {
					//服务器CPU负荷
					line = line.replaceAll("%", " ");
					String[] temp = line.split("\\s+");
					J_S_CPU cpu = new J_S_CPU(sid,cli,cdate);
					cpu.setUseRate(Double.parseDouble(temp[1]));
					cpuList.add(cpu);
				}
				if (line.startsWith("KiB Mem")) {
					//服务器MEM负荷  kb
					String[] temp = line.split("\\s+");
					J_S_MEM mem = new J_S_MEM(sid,cli,cdate);
					Double total = Double.parseDouble(temp[3]);
					Double used = Double.parseDouble(temp[7]);
					Double userRate = used/total*100;
					mem.setUseRate(Double.parseDouble(df.format(userRate)));
					memList.add(mem);
				}
				if (line.startsWith("Mem")) {
					//服务器MEM负荷  kb
					line = line.replaceAll("k", " ");
					String[] temp = line.split("\\s+");
					J_S_MEM mem = new J_S_MEM(sid,cli,cdate);
					Double total = Double.parseDouble(temp[1]);
					Double used = Double.parseDouble(temp[3]);
					Double userRate = used/total*100;
					mem.setUseRate(Double.parseDouble(df.format(userRate)));
					memList.add(mem);
				}
				if (l > 8) {
					//应用CPU负荷 + MEM负荷  kb
					if("".equals(line))
						continue;
					String[] temp = line.split("\\s+");
					String pid = pidMap.get(temp[1]);
					if(pid != null){
						J_S_CPU cpu = new J_S_CPU(sid,pid,cli,cdate);
						cpu.setUseRate(Double.parseDouble(temp[9]));
						cpuList.add(cpu);
						
						J_S_MEM mem = new J_S_MEM(sid,pid,cli,cdate);
						mem.setUseRate(Double.parseDouble(temp[10]));
						memList.add(mem);
					}
				}
				l++;
			}
			pline.close();
			shell.disconnect();
		} catch (Exception e) {
			System.out.println(e);
		}finally{
			try {
				shell.disconnect();
				if(pline != null)
					pline.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
