package bwdrsystem.service.procese;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import bwdrsystem.dto.ReportFileImportLog;

import report.dto.ReportInstInfo;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldCondition;
import framework.show.ShowList;
import framework.show.ShowValue;

/**
 * 
 * @description <p>处理报文导入日志中的报送机构信息</P>
 * @createTime 2016-8-18 上午11:55:44
 * @updateTime 2016-8-18 上午11:55:44
 * @author Liutao
 * @updater Liutao
 */
public class BWDRProcese implements IProcese{

	@Override
	public Object Procese(Object serviceResult) throws Exception {
		int reportInstNameIndex = -1;
		ShowList showList = (ShowList) serviceResult;
		List<List<ShowValue>> valueTable = showList.getValueTable();
		Map<String, String> reportInstInfoMap = getReportInstInfoMap();
		
		if(valueTable != null && !valueTable.isEmpty()) {
			// 寻找报送机构代码以及报送机构名称所在列的下标
			for(List<ShowValue> rowData : valueTable) {// 行
				for(ShowValue colData : rowData) {// 列
					if(!StringUtils.isBlank(colData.getPostFieldName()) && colData.getPostFieldName().indexOf("strReportInstName") > -1) {
						reportInstNameIndex = rowData.indexOf(colData);
						break;
					}
				}
				
				if(reportInstNameIndex > -1) {
					break;
				}
			}
			
			if(reportInstNameIndex > -1) {
				DetachedCriteria dc = null;
				List<ReportFileImportLog> logList = null;
				List<ReportInstInfo> reportInstInfos = null;
				IParamObjectResultExecute findByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				for(List<ShowValue> rowData : valueTable) {
					dc = DetachedCriteria.forClass(ReportFileImportLog.class);
					dc.add(Restrictions.eq(ReflectOperation.getPrimaryKeyField(ReportFileImportLog.class).getName(), rowData.get(0).getValue()));
					logList = (List<ReportFileImportLog>) findByCriteriaDao.paramObjectResultExecute(new Object[]{dc, null});
					
					dc = DetachedCriteria.forClass(ReportInstInfo.class);
					dc.add(Restrictions.eq(ReflectOperation.getPrimaryKeyField(ReportInstInfo.class).getName(), logList.get(0).getStrReportInstCode()));
					reportInstInfos = (List<ReportInstInfo>) findByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
					if(reportInstInfos != null && !reportInstInfos.isEmpty()) {
						rowData.get(reportInstNameIndex).setValue(reportInstInfos.get(0).getStrReportInstName());
					}
				}
			}
		}
		
		List<ShowFieldCondition> showCondition = showList.getShowCondition();
		if(showCondition != null && !showCondition.isEmpty()) {
			for(ShowFieldCondition condition : showCondition) {
				if(condition.getFieldName().equals("strReportInstCode")) {
					condition.setTag(reportInstInfoMap);
					condition.setSingleTag("selectField");
					break;
				}
			}
		}
		
		return serviceResult;
	}
	
	/**
	 * 
	 * @description <p>获取报送机构数据，并组装为一个Map对象</P>
	 * @createTime 2016-8-18 上午10:55:26
	 * @updateTime 2016-8-18 上午10:55:26
	 * @author Liutao
	 * @throws Exception 
	 * @updater Liutao
	 */
	private Map<String, String> getReportInstInfoMap() throws Exception {
		Map<String, String> instInfoMap = new HashMap<String, String>();
		IParamObjectResultExecute findAllDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindAllDao");
		List<ReportInstInfo> reportInstInfos = (List<ReportInstInfo>) findAllDao.paramObjectResultExecute(new Object[] {ReportInstInfo.class.getSimpleName(), null});
		if(reportInstInfos != null && !reportInstInfos.isEmpty()) {
			for(ReportInstInfo reportInstInfo : reportInstInfos) {
				instInfoMap.put(reportInstInfo.getStrReportInstCode(), reportInstInfo.getStrReportInstName());
			}
		}
		return instInfoMap;
	}
}
