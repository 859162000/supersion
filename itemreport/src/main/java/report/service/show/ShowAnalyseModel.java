package report.service.show;

import framework.show.ShowComponentSaveOrUpdate;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：ShowAnalyseModel</P>
 * *********************************<br>
 * <P>类描述：分析模型显示界面</P>
 * 创建人：王川<br>
 * 创建时间：2016-8-16 上午9:27:13<br>
 * 修改人：王川<br>
 * 修改时间：2016-8-16 上午9:27:13<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
public class ShowAnalyseModel extends ShowComponentSaveOrUpdate {
	
	/**  **/
	private static final long serialVersionUID = -1527334650375422126L;
	/** 是否分析 **/
	private boolean isAnalyse = false;

	public boolean isAnalyse() {
		return isAnalyse;
	}

	public void setAnalyse(boolean isAnalyse) {
		this.isAnalyse = isAnalyse;
	}
	
}
