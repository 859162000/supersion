package bwdrsystem.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import extend.dto.ReportModel_Table;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;

/**
 * 
 * @description <p>业务表历史数据清除工具<br />由于报表导入和数据清空都会清除历史数据，故此写入单独的一个类中</P>
 * @createTime 2016-8-13 下午05:42:26
 * @updateTime 2016-8-13 下午05:42:26
 * @author Liutao
 * @updater Liutao
 */
public class BusinessDataClearTool {
	
	/**
	 * 
	 * @description <p>获取历史业务数据表名</P>
	 * @createTime 2016-8-13 下午05:18:05
	 * @updateTime 2016-8-13 下午05:18:05
	 * @author Liutao
	 * @throws Exception 
	 * @updater Liutao
	 */
	private Map<String, List<String>> getBusinessDataTableName() throws Exception{
		Map<String, List<String>> tableNameMap = new HashMap<String, List<String>>();
		tableNameMap.put("JC", new ArrayList<String>());
		tableNameMap.put("MX", new ArrayList<String>());
		try {
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria dc = DetachedCriteria.forClass(ReportModel_Table.class);
			dc.add(Restrictions.or(Restrictions.like("strTableName", "QY", MatchMode.START), 
					Restrictions.or(Restrictions.like("strTableName", "JG", MatchMode.START), 
							Restrictions.like("strTableName", "GR", MatchMode.START))));
			
			List<ReportModel_Table> tableList = (List<ReportModel_Table>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] {dc, null});
			for(ReportModel_Table table : tableList) {
				if(table.getStrAddFields().equals("1")) {
					tableNameMap.get("JC").add(table.getStrTableName());
				} else {
					tableNameMap.get("MX").add(table.getStrTableName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("获取业务数据表名异常");
		}
		return tableNameMap;
	}
	
	/**
	 * 
	 * @description <p>清除业务表历史数据</P>
	 * @createTime 2016-8-13 下午05:47:11
	 * @updateTime 2016-8-13 下午05:47:11
	 * @author Liutao
	 * @throws Exception 
	 * @updater Liutao
	 */
	public static void deleteBusinessTableHistoryData() throws Exception {
		try {
			Map<String, List<String>> tableNameMap = BusinessDataClearTool.class.newInstance().getBusinessDataTableName();
			if(tableNameMap != null && !tableNameMap.isEmpty()) {
				String sql = "";
				IParamVoidResultExecute excuteSqlDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("excuteSqlDao");
				for(String str : tableNameMap.get("MX")) {
					sql = "delete from "+str;
					excuteSqlDao.paramVoidResultExecute(new Object[] {sql, null});
				}
				
				for(String str : tableNameMap.get("JC")) {
					sql = "delete from "+str;
					excuteSqlDao.paramVoidResultExecute(new Object[] {sql, null});
				}
			} else {
				throw new Exception("系统无法找到业务表");
			}
		} catch (Exception e) {
			Pattern p = Pattern.compile("[\u4e00-\u9fa5]+");
			Matcher m = p.matcher(e.getMessage());
			if (m.find()) {
				throw new Exception(e.getMessage());
			} else {
				throw new Exception("清空业务表历史数据异常");
			}
		}
	}
	
}
