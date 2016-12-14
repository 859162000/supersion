package autoETLsystem.service.imps;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import autoETLsystem.dto.AutoETL_ActivityNode;
import autoETLsystem.dto.AutoETL_ActivityNodeForFile;
import autoETLsystem.dto.AutoETL_ActivityNodeForFileF;
import extend.dto.ReportModel_Field;
import framework.helper.FrameworkFactory;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.SessionManager;
import framework.services.interfaces.MessageResult;

public class CreateAllFieldExportService implements IObjectResultExecute{

	@SuppressWarnings("unchecked")
	public Object objectResultExecute() throws Exception {
		
		String currentLevelLevel = SessionManager.getCurrentLevel();
		String id = SessionManager.getLevelIdValue(currentLevelLevel);
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		AutoETL_ActivityNode autoETL_ActivityNode = (AutoETL_ActivityNode)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoETL_ActivityNode.class.getName(),id,null});
		
		AutoETL_ActivityNodeForFile autoETL_ActivityNodeForFile = null;
		for(AutoETL_ActivityNodeForFile tempAutoETL_ActivityNodeForFile :autoETL_ActivityNode.getAutoETL_ActivityNodeForFiles()){
			autoETL_ActivityNodeForFile = tempAutoETL_ActivityNodeForFile;
			break;
		}
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportModel_Field.class);
		detachedCriteria.add(Restrictions.eq("reportModel_Table", autoETL_ActivityNodeForFile.getAutoETL_Table()));
		detachedCriteria.addOrder(Order.asc("nSeq"));
		List<ReportModel_Field> reportModel_FieldList = (List<ReportModel_Field>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		
		List<AutoETL_ActivityNodeForFileF> autoETL_ActivityNodeForFileFList = new ArrayList<AutoETL_ActivityNodeForFileF>();
		for(int i=0;i<reportModel_FieldList.size();i++){
			ReportModel_Field field = reportModel_FieldList.get(i);
			boolean isExsit = false;
			for(AutoETL_ActivityNodeForFileF autoETL_ActivityNodeForFileF : autoETL_ActivityNodeForFile.getAutoETL_ActivityNodeForFileFs()){
				if(field.getAutoFieldID().equals(autoETL_ActivityNodeForFileF.getAutoETL_SourceField().getAutoFieldID())){
					isExsit = true;
					break;
				}
			}
			
			if(!isExsit){
				AutoETL_ActivityNodeForFileF tempAutoETL_ActivityNodeForFileF = new AutoETL_ActivityNodeForFileF();
				
				tempAutoETL_ActivityNodeForFileF.setAutoETL_ActivityNodeForFile(autoETL_ActivityNodeForFile);
				tempAutoETL_ActivityNodeForFileF.setAutoETL_SourceField(field);
				tempAutoETL_ActivityNodeForFileF.setIntOrder(field.getNSeq().toString());
				
				autoETL_ActivityNodeForFileFList.add(tempAutoETL_ActivityNodeForFileF);
			}
		}
		
		IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
		singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{autoETL_ActivityNodeForFileFList,null});
		
		return new MessageResult("创建成功");
	}
}
