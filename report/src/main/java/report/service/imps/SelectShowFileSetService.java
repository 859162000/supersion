package report.service.imps;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import report.dto.FileFileSet;
import report.dto.FileInfoSet;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseObjectDaoResultService;

public class SelectShowFileSetService extends BaseObjectDaoResultService{

	@Override
	public void initSuccessResult() throws Exception {

		
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");

		String fileID = RequestManager.getReportCheckParam().get("fileID");
		if(fileID != null && !"".equals(fileID) ) {// 指定了文件集id,则取此文件集下的文件
			// 将fileID以减号分割成多个id
			String[] sets = fileID.split("-");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.FileInfoSet"));
			detachedCriteria.add(Restrictions.in("strFileSetCode", sets));
			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			List<FileInfoSet> fileInfoSets = (List<FileInfoSet>)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
			
			detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.FileFileSet"));
			detachedCriteria.add(Restrictions.in("strFileSetCode", fileInfoSets));
			
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			List<FileFileSet> FileFileSets = (List<FileFileSet>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});

			StringBuffer FileCodes = new StringBuffer();
			for (FileFileSet FileFileSet:FileFileSets) {
				FileCodes.append(FileFileSet.getFileInfo().getStrFileCode());
				FileCodes.append(",");
			}
			
			response.getWriter().write(FileCodes.toString());
		}
		else { // 取文件集列表
			IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindAllDao");
			List<FileInfoSet> fileInfoSets = (List<FileInfoSet>)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{"report.dto.FileInfoSet", null});

			StringBuffer StrFileSets = new StringBuffer();
			for(FileInfoSet fileInfoSet : fileInfoSets) {
				StrFileSets.append(fileInfoSet.getStrFileSetCode());
				StrFileSets.append(",");
				StrFileSets.append(fileInfoSet.getStrFileSetName());
				StrFileSets.append(";");
			}
			if(StrFileSets==null){
				StrFileSets.append("");
			}

			response.getWriter().write(StrFileSets.toString());
		}
		
	}
}
