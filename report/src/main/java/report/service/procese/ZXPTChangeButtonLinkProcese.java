package report.service.procese;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.ReportInstSubInfo;
import report.dto.TaskModelInst;


import extend.dto.SystemParam;
import framework.helper.FrameworkFactory;
import framework.interfaces.ApplicationManager;

import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.IProcese;
import framework.show.ShowContext;
import framework.show.ShowList;
import framework.show.ShowValue;

/**
 * 改变征信平台数据填报的填报按钮链接路径
 * @author lys
 */
public class ZXPTChangeButtonLinkProcese implements IProcese {

	public Object Procese(Object serviceResult) throws Exception {
		
		IParamObjectResultExecute byIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		SystemParam isZXPTPosition = (SystemParam) byIdDao.paramObjectResultExecute(new Object[]{"extend.dto.SystemParam", "isZXPTPosition" , null});
		
		String levelIdValue = RequestManager.getLevelIdValue();
		if(levelIdValue != null && !levelIdValue.equals("")){
			setStrReportInstCode();
		}
		
		if (serviceResult instanceof ShowList && (isZXPTPosition!=null && isZXPTPosition.getStrEnable().equals("1"))) {
			Map<String, String> positionMap = ShowContext.getInstance().getShowEntityMap().get("zxptBlankPosition");
			ShowList showList = (ShowList) serviceResult;
			if(showList.getValueTable() !=null){
				for(int k=0;k<showList.getValueTable().size();k++){
					List<ShowValue> valueRow = showList.getValueTable().get(k);
					String actionName = valueRow.get(1).getValue(); 
					if(!actionName.equals("") && actionName.indexOf("zxptsystem.dto")>-1){
						boolean zxptPosition = true;
						if(positionMap != null){
							for (Entry<String, String> entry : positionMap.entrySet()) {
								if(actionName.indexOf(entry.getKey())>-1){
									zxptPosition = false;
									break;
								}
							}
						}
						if(zxptPosition){
							valueRow.get(1).setValue(actionName.replace(ApplicationManager.getShowlistlevelautodtoprefix(), ApplicationManager.getZxptshowlistlevelautodtoprefix()));
						}
					}
				}
			}
			return showList;
		}
		return serviceResult;
	}
	
	/**
	 * 根据机构获取报送机构信息中的金融机构代码
	 * 将金融机构代码放入session
	 * @throws Exception 
	 */
	public void setStrReportInstCode() throws Exception{
		
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		TaskModelInst taskModelInst = (TaskModelInst)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{TaskModelInst.class.getName(), RequestManager.getLevelIdValue() ,null});
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportInstSubInfo.class);
		detachedCriteria.add(Restrictions.eq("instInfo", taskModelInst.getInstInfo()));
		if(taskModelInst.getTaskFlow().getSuit().getStrSuitCode().equals("21") || taskModelInst.getTaskFlow().getSuit().getStrSuitCode().equals("24")){
			Object[] values = {"24", "21"};
			detachedCriteria.createCriteria("reportInstInfo").add(Restrictions.in("suit.strSuitCode", values));
		}
		@SuppressWarnings("unchecked")
		List<ReportInstSubInfo> reportInstSubInfoList = (List<ReportInstSubInfo>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		String strReportInstCode = ""; // 金融机构代码
		
		for(ReportInstSubInfo reportInstSubInfo : reportInstSubInfoList){
			String suitCode = reportInstSubInfo.getReportInstInfo().getSuit().getStrSuitCode();
			if(suitCode.equals("21") || suitCode.equals("24")){
				strReportInstCode = reportInstSubInfo.getReportInstInfo().getStrReportInstCode();
				break;
			}
			else if(reportInstSubInfo.getReportInstInfo().getSuit().equals(taskModelInst.getTaskFlow().getSuit())){
				strReportInstCode = reportInstSubInfo.getReportInstInfo().getStrReportInstCode();
				break;	
			}
		}
		ServletActionContext.getContext().getSession().put("report.dto.ReportInstInfo.strReportInstCode", strReportInstCode);
		
	}
}
