package report.service.imps;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.AutoTaskFlow;
import report.dto.RptInfo;
import report.dto.RptInfoSet;
import report.dto.RptRptSet;
import report.dto.TaskFlow;
import extend.dto.Suit;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseObjectDaoResultService;

public class SelectShowRptSetService extends BaseObjectDaoResultService {

	@SuppressWarnings( { "null", "unchecked" })
	@Override
	public void initSuccessResult() throws Exception {

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");

		String rptID = RequestManager.getReportCheckParam().get("RptID");
		if (rptID != null && !"".equals(rptID)) {
			String[] sets = rptID.split("-");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.RptInfoSet"));
			detachedCriteria.add(Restrictions.in("strRptSetCode", sets));
			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			List<RptInfoSet> rptInfoSets = (List<RptInfoSet>) singleObjectFindByIdDao.paramObjectResultExecute(new Object[] { detachedCriteria,null });

			detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.RptRptSet"));
			detachedCriteria.add(Restrictions.in("strRptSetCode", rptInfoSets));

			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			List<RptRptSet> rptRptSets = (List<RptRptSet>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria,null });

			// 构造查询主题信息的查询条件对象
			detachedCriteria = DetachedCriteria.forClass(Class.forName("extend.dto.Suit"));
			// 查询所有源主题信息
			List<Suit> suits = (List<Suit>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria,null });

			// 定义数组，用于保存满足频度和时间过滤条件的RptInfo
			List<RptInfo> rptInfos = new ArrayList<RptInfo>();
			// 定义存放当前任务对应主题的所有子孙主题（包括自身在内）的List
			// 获取当前任务对应主题的所有子孙主题（包括自身在内）
			List<Suit> childSuits = new ArrayList<Suit>();
			
			// 查询当前任务的信息（主要是频度信息）
			TaskFlow taskFlow = (TaskFlow) ((IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao"))
						.paramObjectResultExecute(new Object[] {TaskFlow.class.getName(),RequestManager.getReportCheckParam().get("TaskID"),null });
			
			if(null != taskFlow){
				// 获取满足条件的RptInfo信息
				setRptInfos(taskFlow, rptRptSets, rptInfos);
				childSuits = new OperateSuitObject().getChildSuits(suits, taskFlow.getSuit());
			}else{
				AutoTaskFlow autoTaskFlow = (AutoTaskFlow) ((IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao"))
								.paramObjectResultExecute(new Object[] {AutoTaskFlow.class.getName(),RequestManager.getReportCheckParam().get("TaskID"),null });
				if(null != autoTaskFlow){
					// 获取满足条件的RptInfo信息
					setRptInfos(autoTaskFlow, rptRptSets, rptInfos);
					childSuits = new OperateSuitObject().getChildSuits(suits, autoTaskFlow.getSuit());
				}
			}
			
			StringBuffer RptCodes = new StringBuffer();
			// 比较RptInfo的主题信息是否在当前任务所对应的主题及其子孙主题信息中
			for(RptInfo rptInfo : rptInfos){
				if(null != rptInfo.getSuit() && childSuits.contains(rptInfo.getSuit())){
					RptCodes.append(rptInfo.getStrRptCode());
					RptCodes.append(",");
					RptCodes.append(rptInfo.getDescName());
					RptCodes.append(";");
				}
			}
			response.getWriter().write(RptCodes.toString());
		} else {
			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindAllDao");
			List<RptInfoSet> rptInfoSets = (List<RptInfoSet>) singleObjectFindByIdDao.paramObjectResultExecute(new Object[] {"report.dto.RptInfoSet", null });

			StringBuffer StrRptSets = new StringBuffer();
			for (RptInfoSet rptInfoSet : rptInfoSets) {
				StrRptSets.append(rptInfoSet.getStrRptSetCode());
				StrRptSets.append(",");
				StrRptSets.append(rptInfoSet.getStrRptSetName());
				StrRptSets.append(";");
			}
			if (StrRptSets == null) {
				StrRptSets.append("");
			}
			response.getWriter().write(StrRptSets.toString());
		}

	}

	/**
	 * 获取满足条件的rptinfo
	 */
	private void setRptInfos(Object taskFlow, List<RptRptSet> rptRptSets,List<RptInfo> rptInfos) {
		if (rptRptSets.size() != 0) {
			for (RptRptSet rptRptSet : rptRptSets) {
				if (null != rptRptSet.getRptInfo()
						&& compareStrFreq(taskFlow, rptRptSet.getRptInfo())
						&& compareDtTaskDate(taskFlow, rptRptSet.getRptInfo())
						&& compareStrTime(taskFlow, rptRptSet.getRptInfo())) {
					rptInfos.add(rptRptSet.getRptInfo());
				}
			}
		}
		
	}

	// 判断批次
	private boolean compareStrTime(Object taskFlow, RptInfo rptInfo) {
		boolean result = false;
		try {
			String taskStrTime = null;
			String rptInfoStrTime = rptInfo.getStrTime();
			Object taskStrTimeTem = ReflectOperation.getFieldValue(taskFlow, "strTime");
			if(taskStrTimeTem == null) {
				taskStrTime = "";
			} else {
				taskStrTime = taskStrTimeTem.toString();
			}
			
			if( (StringUtils.isBlank(taskStrTime) && StringUtils.isBlank(rptInfoStrTime)) || 
					(!StringUtils.isBlank(taskStrTime) && !StringUtils.isBlank(rptInfoStrTime) && taskStrTime.equals(rptInfoStrTime)) ) {
				result = true;
			}
		} catch (Exception e) {
			result = false;
		}
		
		return result;
	}
	
	// 判断报表频度
	private boolean compareStrFreq(Object taskFlow, RptInfo rptInfo) {
		String strFreq = null;
		try {
			strFreq = ReflectOperation.getFieldValue(taskFlow, "strFreq").toString();
		} catch (Exception e) {
			return false;
		}
		if (strFreq.equals(rptInfo.getStrFreq())) {
			return true;
		} else {
			return false;
		}
	}

	// 判断当前任务(TaskFlow)的数据时间是否在报表(RptInfo)的开始和结束时间之内
	private boolean compareDtTaskDate(Object taskFlow, RptInfo rptInfo) {
		Date dtTaskDate = null;
		try {
			dtTaskDate = (Date) ReflectOperation.getFieldValue(taskFlow, "dtTaskDate");
		} catch (Exception e) {
			return false;
		}
		if (null != dtTaskDate
				&& dtTaskDate.after(rptInfo.getStartDate())
				&& dtTaskDate.before(rptInfo.getEndDate())) {
			return true;
		} else {
			return false;
		}
	}

}
