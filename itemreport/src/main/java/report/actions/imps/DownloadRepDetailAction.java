package report.actions.imps;

import java.io.InputStream;

import framework.actions.imps.BaseSAction;
import framework.services.interfaces.DownloadResult;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：RepDetailDownloadAction</P>
 * *********************************<br>
 * <P>类描述：sql语句查询详情表</P>
 * 创建人：王川<br>
 * 创建时间：2016-10-27 上午10:01:52<br>
 * 修改人：王川<br>
 * 修改时间：2016-10-27 上午10:01:52<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
public class DownloadRepDetailAction extends BaseSAction{
	
	public InputStream getInputStream(){ // 供action返回后，struts/tomcat传输文件给浏览器时调用
		return ((DownloadResult)this.getServiceResult()).getInputStream();
	}

}
