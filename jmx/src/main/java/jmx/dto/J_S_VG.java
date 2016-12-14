package jmx.dto;

import java.io.Serializable;

/**
 * 项目名称：jmx<br>
 * *********************************<br>
 * <P>类名称：J_S_VG</P>
 * *********************************<br>
 * <P>类描述：服务器配置信息--磁盘采集信息</P>
 * 创建人：王川<br>
 * 创建时间：2016-11-10 上午11:09:17<br>
 * 修改人：王川<br>
 * 修改时间：2016-11-10 上午11:09:17<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
public class J_S_VG implements Serializable{
	/** uuid **/
	private String uuid;
	
	/** 服务器编号 **/
	private String sid;
	
	/** 进程id、区分应用,服务器的为 0 **/
	private String pid = "0";
	
	/** 采集步长 **/
	private String cli;
	
	/** 采集频度  秒[s]/分[m]/时[h]/天[d]/周[w]/月[t]/年[y]**/
	private String cfq;
	
	/** 使用率 **/
	private double useRate;
	
	/** 采集时间 **/
	private String cdate;
}
