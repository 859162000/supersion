package report.service.imps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import report.dto.TaskModelInst;
import extend.dto.ReportModel_Table;
import framework.helper.FrameworkFactory;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.reportCheck.CheckContext;
import framework.reportCheck.CheckInstance;
import framework.services.imps.BaseTShowService;
import framework.services.interfaces.MessageResult;

public class RptCheckService extends BaseTShowService{
	
	private String checkInstanceType;
	
    private String dtDateParamName;
	
	private String strInstCodeParamName;
	

	public String getDtDateParamName() {
		return dtDateParamName;
	}
	public void setDtDateParamName(String dtDateParamName) {
		this.dtDateParamName = dtDateParamName;
	}
	public String getStrInstCodeParamName() {
		return strInstCodeParamName;
	}
	public void setStrInstCodeParamName(String strInstCodeParamName) {
		this.strInstCodeParamName = strInstCodeParamName;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Object objectResultExecute() throws Exception {
		
		super.init();
		
		String strTableName=RequestManager.getTName().substring(RequestManager.getTName().indexOf("_") +1);
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportModel_Table.class);
		detachedCriteria.add(Restrictions.eq("strTableName", strTableName));
		List<ReportModel_Table> reportModel_TableList = (List<ReportModel_Table>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		
		ReportModel_Table reportModel_Table=reportModel_TableList.get(0);
		
		String checkInstanceName=reportModel_Table.getStrCheckInstance();
		if(StringUtils.isBlank(checkInstanceName)){
			return new MessageResult(false,"校验实例不能为空。");
		}
		
		// 从已加载的CheckInstance中找到当前请求的instance
        CheckInstance checkInstance = CheckContext.getInstance().getCheckInstanceMap().get(checkInstanceName);
		if(checkInstance == null){
			IParamObjectResultExecute paramObjectResultExecute = (IParamObjectResultExecute)FrameworkFactory.CreateBean("checkInstanceCopyService");
			checkInstance = (CheckInstance)paramObjectResultExecute.paramObjectResultExecute(checkInstanceName);
		}
	
		if(checkInstance == null){
			return new MessageResult(false,"校验实例不存在。");
		}
		Map<String,String> rptMapList=new HashMap<String,String>();
		
		String currentLevelLevel = SessionManager.getCurrentLevel();
		String id = SessionManager.getLevelIdValue(currentLevelLevel);
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		TaskModelInst taskModelInst = (TaskModelInst)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{TaskModelInst.class.getName(),id,null});
		
		rptMapList.put(dtDateParamName, TypeParse.parseString(taskModelInst.getTaskFlow().getDtTaskDate(),"yyyy-MM-dd"));
		rptMapList.put(strInstCodeParamName, taskModelInst.getInstInfo().getStrInstCode());
		
		MessageResult messageResult = (MessageResult)checkInstance.paramObjectResultExecute(rptMapList);
		IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
		if(messageResult.isSuccess()){
			taskModelInst.setStrCheckState("2");
		}
		else{
			taskModelInst.setStrCheckState("3");
		}
		
		singleObjectSaveDao.paramVoidResultExecute(new Object[]{taskModelInst,null});
		
		
		return messageResult;
	}
	public void setCheckInstanceType(String checkInstanceType) {
		this.checkInstanceType = checkInstanceType;
	}

	public String getCheckInstanceType() {
		return checkInstanceType;
	}
}
