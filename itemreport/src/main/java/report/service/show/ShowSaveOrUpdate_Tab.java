package report.service.show;

import java.io.Serializable;

import framework.show.ShowSaveOrUpdate;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：ShowSaveOrUpdate_Tab</P>
 * *********************************<br>
 * <P>类描述：tab返回结果对象</P>
 * 创建人：王川<br>
 * 创建时间：2016-8-16 上午9:33:15<br>
 * 修改人：王川<br>
 * 修改时间：2016-8-16 上午9:33:15<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
public class ShowSaveOrUpdate_Tab extends ShowSaveOrUpdate implements Serializable{
	

	/**  **/
	private static final long serialVersionUID = -6730201572529305751L;
	/** tabUrl连接 **/
	private String tabUrl;
	
	public ShowSaveOrUpdate_Tab(){
		super();
	}
	
	public ShowSaveOrUpdate_Tab(String navigationName) {
		super(navigationName);
	}

	public String getTabUrl() {
		return tabUrl;
	}

	public void setTabUrl(String tabUrl) {
		this.tabUrl = tabUrl;
	}
}
