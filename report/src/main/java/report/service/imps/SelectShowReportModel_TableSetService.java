package report.service.imps;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.AutoTaskFlow;
import report.dto.Reportmodel_tableSet;
import report.dto.TableTableSet;
import report.dto.TaskFlow;
import extend.dto.ReportModel_Table;
import extend.dto.Suit;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseObjectDaoResultService;

public class SelectShowReportModel_TableSetService extends BaseObjectDaoResultService{

	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		
		String reportmodel_tableID = RequestManager.getReportCheckParam().get("ReportModel_TableID");
		if(reportmodel_tableID != null && !"".equals(reportmodel_tableID) ) {
			String[] sets = reportmodel_tableID.split("-");
			// 定义查询条件对象
			DetachedCriteria detachedCriteria = null;
			// 定义查询对象
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			
			// 构造查询明细表集的条件
			detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.Reportmodel_tableSet"));
			detachedCriteria.add(Restrictions.in("strReportmodel_tableSetCode", sets));
			List<Reportmodel_tableSet> Reportmodel_tableSets = (List<Reportmodel_tableSet>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
			
			// 定义查询TableTableSet表的条件
			detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.TableTableSet"));
			detachedCriteria.add(Restrictions.in("strReportmodel_tableSetCode", Reportmodel_tableSets));
			List<TableTableSet> TableTableSets = (List<TableTableSet>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});

			// 获取当前任务信息(主要是主题信息)
			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
			TaskFlow taskFlow = (TaskFlow)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{TaskFlow.class.getName(),RequestManager.getReportCheckParam().get("TaskID"),null});
			
			// 构造查询所有主题信息的查询条件
			detachedCriteria = DetachedCriteria.forClass(Class.forName("extend.dto.Suit"));
			// 查询所有源主题信息
			List<Suit> suits = (List<Suit>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			
			// 定义存放当前任务对应主题的所有子孙主题（包括自身在内）的List
			List<Suit> childSuits = new ArrayList<Suit>();
			if(null != taskFlow){
				childSuits = new OperateSuitObject().getChildSuits(suits, taskFlow.getSuit());
			}else{
				AutoTaskFlow autoTaskFlow = (AutoTaskFlow)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoTaskFlow.class.getName(),RequestManager.getReportCheckParam().get("TaskID"),null});
				if(null != autoTaskFlow){
					childSuits = new OperateSuitObject().getChildSuits(suits, autoTaskFlow.getSuit());
				}
			}
			
			// 定义存放主题于任务对应主题相同或者是其子孙主题的明细信息List对象
			List<ReportModel_Table> reportModelTables = new ArrayList<ReportModel_Table>();
			// 设置reportModelTables的数据
			setReportModelTables(childSuits, reportModelTables, TableTableSets);
			
			StringBuffer InstCodes = new StringBuffer();
			// 判断reportModelTables是否为空，因为它被作为了查询条件，不然控制台会报异常，且界面无响应
			if(reportModelTables.size() != 0){
				for(ReportModel_Table rptdto : reportModelTables){
					
						InstCodes.append(rptdto.getAutoTableID());
						InstCodes.append(",");
						InstCodes.append(rptdto.getStrShowTableName());
						InstCodes.append(";");
					
				}
				
			}
			response.getWriter().write(InstCodes.toString());
		}else{
			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindAllDao");
			List<Reportmodel_tableSet> instInfoSets = (List<Reportmodel_tableSet>)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{"report.dto.Reportmodel_tableSet", null});

			StringBuffer StrInstSets = new StringBuffer();
			for(Reportmodel_tableSet instInfoSet : instInfoSets) {
				StrInstSets.append(instInfoSet.getStrReportmodel_tableSetCode());
				StrInstSets.append(",");
				StrInstSets.append(instInfoSet.getStrReportmodel_tableSetName());
				StrInstSets.append(";");
			}
			if(StrInstSets.length()<=0){
				StrInstSets.append("");
			}

			response.getWriter().write(StrInstSets.toString());
		}
		
	}
	
	/**
	 * 
	 * 根据任务对主题及其子孙主题信息筛选明细信息
	 * 
	 * @param childSuits ： 任务对主题及其子孙主题信息List
	 * @param reportModelTables ：存放筛选之后的明细信息List
	 * @param TableTableSets : 存放当前明细集下所有的明细信息的List
	 */
	private void setReportModelTables(List<Suit> childSuits, List<ReportModel_Table> reportModelTables, List<TableTableSet> tableTableSets){
		if(tableTableSets.size() != 0){
			for(TableTableSet tableTableSet : tableTableSets){
				if(null != tableTableSet.getReportmodel_table()
						&& null != tableTableSet.getReportmodel_table().getSuit()
						&& childSuits.contains(tableTableSet.getReportmodel_table().getSuit())){
					reportModelTables.add(tableTableSet.getReportmodel_table());
				}
			}
		}
	}

}
