package zxptsystem.service.procese;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import report.dto.ReportInstInfo;

import autoETLsystem.dto.AutoETL_Workflow;

import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.interfaces.IProcese;
import framework.show.ShowFieldCondition;
import framework.show.ShowList;
/**
 * 1、企业征信业务统计查询条件数据截止日期绑定日期控件
 * 2、过滤企业征信以外的主题
 * @author xiajieli
 *
 */
public class PROC_EndsData_CheckProcese implements IProcese{

	@SuppressWarnings("unchecked")
	@Override
	public Object Procese(Object serviceResult) throws Exception {
		
		ShowList showlist = (ShowList)serviceResult;
		
		HashMap<String,String> ReportCodeMapTemp = new HashMap<String,String>();
		
		for(ShowFieldCondition showFieldCondition : showlist.getShowCondition()){
			if(showFieldCondition.getFieldName().equals("SJJZRQ")){
				showFieldCondition.setSingleTag("dateFieldNoSlash");
			}
			if(showFieldCondition.getFieldName().equals("DJJGDM")){
				HashMap<String,String> ReportCodeMap = new HashMap<String,String>();
				ReportCodeMap=(HashMap) showFieldCondition.getTag();
				
				for (Map.Entry<String, String> entry:ReportCodeMap.entrySet()) {
					IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
					ReportInstInfo reportInstInfo = (ReportInstInfo)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{ReportInstInfo.class.getName(),entry.getKey(),null});
					if(reportInstInfo.getSuit()!=null && reportInstInfo.getSuit().getStrSuitCode().equals("21")){
						ReportCodeMapTemp.put(entry.getKey(), entry.getValue());
					}
				}
				
				showFieldCondition.setTag(ReportCodeMapTemp);
			}
		}
		return serviceResult;
	}

}
