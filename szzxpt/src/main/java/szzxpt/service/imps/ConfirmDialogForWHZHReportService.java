package szzxpt.service.imps;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.service.imps.CommonUpdateReportStatusService;

import com.opensymphony.xwork2.ActionContext;

import coresystem.dto.InstInfo;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.MessageResult;

public class ConfirmDialogForWHZHReportService implements IObjectResultExecute {

	private Map<String, String> downloadJudgeStatusMap;

	public Object objectResultExecute() throws Exception {
		
		IParamObjectResultExecute singleObjectFindByCountDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindCountByCriteriaDao");
		
		String strJRJGCode = (String) ReflectOperation.getFieldValue(RequestManager.getTOject(), "strJRJGCode");
		ActionContext.getContext().getSession().put("strJRJGCode", strJRJGCode);
		
		CommonUpdateReportStatusService commonStatus=new CommonUpdateReportStatusService();
		List<InstInfo> instInfoSubList=commonStatus.getInstInfoSubList(strJRJGCode);
		String[] requstTNameList = new String[]{"szzxpt.dto.AutoDTO_WGJ_ACC_CA","szzxpt.dto.AutoDTO_WGJ_ACC_CB"};
		
		int i=0;
		for (String strTName : requstTNameList) {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName(strTName));
			detachedCriteria.add(Restrictions.in("instInfo", instInfoSubList));
			 for(Map.Entry<String, String> entry : downloadJudgeStatusMap.entrySet()){
					if(entry.getValue().indexOf(",") > -1){
						String[] strValues = entry.getValue().split(",");
						detachedCriteria.add(Restrictions.in(entry.getKey(),strValues));
					}
					else{
						detachedCriteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
					}
			  }
			 int strTNameList=(Integer)singleObjectFindByCountDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			 if(strTNameList==0){
				 i++;
				 if(i==2){
					 return new MessageResult(false,"没有数据，是否继续生成报文");
				 }
			 }
		}
		
		return new MessageResult(true);
	}

	public Map<String, String> getDownloadJudgeStatusMap() {
		return downloadJudgeStatusMap;
	}

	public void setDownloadJudgeStatusMap(Map<String, String> downloadJudgeStatusMap) {
		this.downloadJudgeStatusMap = downloadJudgeStatusMap;
	}
	
	
}
