package autoETLsystem.service.procese;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import autoETLsystem.dto.AutoETL_ActivityNode;
import autoETLsystem.dto.AutoETL_ActivityNodeForSql;
import autoETLsystem.dto.AutoETL_ActivityNodeForSqlC;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.SessionManager;
import framework.services.interfaces.IProcese;

public class ActivityNodeForSqlSaveOrUpdateProcese implements IProcese{

	@SuppressWarnings("unchecked")
	public Object Procese(Object serviceResult) throws Exception {
		
		String currentLevelLevel = SessionManager.getCurrentLevel();
		String id = SessionManager.getLevelIdValue(currentLevelLevel);
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		AutoETL_ActivityNode autoETL_ActivityNode = (AutoETL_ActivityNode)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoETL_ActivityNode.class.getName(),id,null});
		
		AutoETL_ActivityNodeForSql autoETL_ActivityNodeForSql = null;
		for(AutoETL_ActivityNodeForSql tempAutoETL_ActivityNodeForSql :autoETL_ActivityNode.getAutoETL_ActivityNodeForSqls()){
			autoETL_ActivityNodeForSql = tempAutoETL_ActivityNodeForSql;
			break;
		}
		
		List<String> fieldList = new ArrayList<String>();
		
		String strSourceSql = autoETL_ActivityNodeForSql.getStrDataSourceSql();
		while(strSourceSql.indexOf("#") > -1){
			strSourceSql = strSourceSql.substring(strSourceSql.indexOf("#"));
			if(strSourceSql.length() == 1){
				break;
			}
			strSourceSql = strSourceSql.substring(1);
			if(strSourceSql.length() > 1){
				String field = "";
				if(strSourceSql.indexOf(" ") > -1){
					field = strSourceSql.substring(0,strSourceSql.indexOf(" "));
				}
				else{
					field = strSourceSql;
				}
				if(field.indexOf(",") > -1){
					field = field.substring(0,field.indexOf(","));
				}
				if(!StringUtils.isBlank(field)){
					fieldList.add(field);
				}
			}
		}
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AutoETL_ActivityNodeForSqlC.class);
		detachedCriteria.add(Restrictions.eq("autoETL_ActivityNodeForSql", autoETL_ActivityNodeForSql));
		List<AutoETL_ActivityNodeForSqlC> autoETL_ActivityNodeForSqlCList = (List<AutoETL_ActivityNodeForSqlC>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});

		List<AutoETL_ActivityNodeForSqlC> deleteList = new ArrayList<AutoETL_ActivityNodeForSqlC>();
		List<AutoETL_ActivityNodeForSqlC> addList = new ArrayList<AutoETL_ActivityNodeForSqlC>();
		
		for(String field : fieldList){
			boolean isExsist = false;
			for(AutoETL_ActivityNodeForSqlC forSqlC: autoETL_ActivityNodeForSqlCList){
				if(forSqlC.getStrFieldName().equals(field)){
					isExsist = true;
					break;
				}
			}
			if(!isExsist){
				AutoETL_ActivityNodeForSqlC autoETL_ActivityNodeForSqlC = new AutoETL_ActivityNodeForSqlC();
				autoETL_ActivityNodeForSqlC.setAutoETL_ActivityNodeForSql(autoETL_ActivityNodeForSql);
				autoETL_ActivityNodeForSqlC.setStrFieldName(field);
				addList.add(autoETL_ActivityNodeForSqlC);
			}
		}
		
		for(AutoETL_ActivityNodeForSqlC forSqlC: autoETL_ActivityNodeForSqlCList){
			boolean isExsist = false;
			for(String field : fieldList){
				if(forSqlC.getStrFieldName().equals(field)){
					isExsist = true;
					break;
				}
			}
			if(!isExsist){
				deleteList.add(forSqlC);
			}
		}
		
		IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
		singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{addList,null});

		Object[] idList = new Object[deleteList.size()];
		for(int i=0;i<deleteList.size();i++){
			idList[i] = deleteList.get(i).getId();
		}
		
		IParamVoidResultExecute singleObjectDeleteListByIdListDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectDeleteListByIdListDao");
		singleObjectDeleteListByIdListDao.paramVoidResultExecute(new Object[]{AutoETL_ActivityNodeForSqlC.class.getName(),idList,null});
		
		return serviceResult;
	}

}
