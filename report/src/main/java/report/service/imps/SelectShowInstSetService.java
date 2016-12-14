package report.service.imps;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.InstInfoSet;
import report.dto.InstInstSet;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseObjectDaoResultService;

public class SelectShowInstSetService extends BaseObjectDaoResultService{

	@Override
	public void initSuccessResult() throws Exception {

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");

		String instID = RequestManager.getReportCheckParam().get("InstID");
		if(instID != null && !"".equals(instID) ) {// 指定了机构集id,则取此机构集下的机构
			// 将instID以减号分割成多个id
			String[] sets = instID.split("-");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.InstInfoSet"));
			detachedCriteria.add(Restrictions.in("strInstSetCode", sets));
			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			List<InstInfoSet> instInfoSets = (List<InstInfoSet>)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
			
			detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.InstInstSet"));
			detachedCriteria.add(Restrictions.in("strInstSetCode", instInfoSets));
			
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			List<InstInstSet> InstInstSets = (List<InstInstSet>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});

			StringBuffer InstCodes = new StringBuffer();
			for (InstInstSet InstInstSet:InstInstSets) {
				InstCodes.append(InstInstSet.getInstInfo().getStrInstCode());
				InstCodes.append(",");
			}
			response.getWriter().write(InstCodes.toString());
		
		}else{
			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindAllDao");
			List<InstInfoSet> instInfoSets = (List<InstInfoSet>)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{"report.dto.InstInfoSet", null});

			StringBuffer StrInstSets = new StringBuffer();
			for(InstInfoSet instInfoSet : instInfoSets) {
				StrInstSets.append(instInfoSet.getStrInstSetCode());
				StrInstSets.append(",");
				StrInstSets.append(instInfoSet.getStrInstSetName());
				StrInstSets.append(";");
			}
			if(StrInstSets.length()<=0){
				StrInstSets.append("");
			}

			response.getWriter().write(StrInstSets.toString());
		}
		
	}
}
