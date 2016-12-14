package zxptsystem.service.imps;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.ReportInstSubInfo;
import report.dto.TaskModelInst;
import extend.dto.SystemParam;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseService;
import framework.show.ShowList;
import framework.show.ShowValue;

public class ZXPTShowListLevelAUTODTOService extends BaseService{
	
	private String frameOneUrl;
	private String frameTowUrl;
	
	@Override
	public void initSuccessResult() throws Exception {
		
		/**
		 * 组建展示页面的请求路径
		 */
		ShowList showList = new ShowList();
		List<ShowValue> showValueList = new ArrayList<ShowValue>();

		ShowValue showValue1 = new ShowValue();
		String viewDTO = frameOneUrl;
		IParamObjectResultExecute byIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		String id = "qyzx_strVersion";
		SystemParam qyzxVersionObj = (SystemParam)byIdDao.paramObjectResultExecute(new Object[]{"extend.dto.SystemParam", id , null});
		if (qyzxVersionObj != null && qyzxVersionObj.getStrParamValue().equals("2.2")) {
			viewDTO = frameTowUrl;
		}

		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		TaskModelInst taskModelInst = (TaskModelInst)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{TaskModelInst.class.getName(), RequestManager.getLevelIdValue() ,null});
		
		ServletActionContext.getContext().getSession().put(viewDTO+"instinfo", taskModelInst.getInstInfo().getStrInstCode());
		
		showValue1.setValue(RequestManager.getNamespace()+"/"+viewDTO+".action");
		ShowValue showValue2 = new ShowValue();
		showValue2.setValue(RequestManager.getNamespace()+"/ShowListLevelAUTODTO-"+RequestManager.getTName()+".action");

		showValueList.add(showValue1);
		showValueList.add(showValue2);
		
		showList.getValueTable().add(showValueList);

		this.setServiceResult(showList);
	}

	public String getFrameOneUrl() {
		return frameOneUrl;
	}

	public void setFrameOneUrl(String frameOneUrl) {
		this.frameOneUrl = frameOneUrl;
	}

	public String getFrameTowUrl() {
		return frameTowUrl;
	}

	public void setFrameTowUrl(String frameTowUrl) {
		this.frameTowUrl = frameTowUrl;
	}
}
