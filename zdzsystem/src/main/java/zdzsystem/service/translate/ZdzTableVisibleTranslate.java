package zdzsystem.service.translate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import extend.dto.ReportModel_Table;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ITranslate;
import framework.services.interfaces.LogicParamManager;
import framework.show.ShowContext;
/**
 * 处理任务层表的显示及隐藏
 * @author Administrator
 *
 */
public class ZdzTableVisibleTranslate implements ITranslate
{

	@SuppressWarnings("unchecked")
	@Override
	public void Translate() throws Exception 
   {
		// TODO Auto-generated method stub
		if(RequestManager.getWindowId().startsWith("ZDZTaskModelInst")){
			Class<?> type = Class.forName(RequestManager.getTName());
			DetachedCriteria detachedCriteria=null;
			if(LogicParamManager.getDetachedCriteria() == null)
			{
				detachedCriteria = DetachedCriteria.forClass(type);
			}
			else
			{
				detachedCriteria = LogicParamManager.getDetachedCriteria();
			}
			//得到ReportModel_Table的detachedCriteria
			DetachedCriteria ReportModel_deta = DetachedCriteria.forClass(Class.forName("extend.dto.ReportModel_Table").newInstance().getClass());
			
			List<String> visibleList=new ArrayList<String>();
			//得到不显示的表格
			Map<String,String>  DisVisibleTable = ShowContext.getInstance().getShowEntityMap().get("ShowAndhiddenTableMap");
			
			//得到所有的表格的名称
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			ReportModel_deta.add(Restrictions.eq("suit.strSuitCode","ZDZ"));
			List<ReportModel_Table> AllReportModel= (List<ReportModel_Table>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{ReportModel_deta,null});
			
			for (ReportModel_Table reportModel_Table : AllReportModel) 
			{
				visibleList.add(reportModel_Table.getStrTableName());
			}
			//数据填报和审核
			if(RequestManager.getWindowId().equals("ZDZTaskModelInst") || RequestManager.getWindowId().equals("ZDZTaskModelInstSH"))
			{
				for (Map.Entry<String, String> entry : DisVisibleTable.entrySet()) 
				{
					if(visibleList.contains(entry.getKey()))
					{   
						visibleList.remove(entry.getKey().trim());
					}
				}
				ReportModel_deta.add(Restrictions.in("strTableName",visibleList));
			}
			
			//数据查看
			if(RequestManager.getWindowId().equals("ZDZTaskModelInstCK")){
				List<String> visibleListTemp=new ArrayList<String>();
				for (String  strTableName : visibleList) {
					if(DisVisibleTable.containsKey(strTableName))
					{   
						visibleListTemp.add(strTableName);
					}
				}
				ReportModel_deta.add(Restrictions.in("strTableName",visibleListTemp));
			}
			
			
			List<ReportModel_Table> resultExecute = (List<ReportModel_Table>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{ReportModel_deta,null});
			
			List<Object> zdzObeject=new ArrayList<Object>();
			for (ReportModel_Table reportModel_Table : resultExecute) 
			{
				zdzObeject.add(reportModel_Table);
			}
			detachedCriteria.add(Restrictions.in("reportModel_Table", zdzObeject));	
		}
		
	}
		
  
}