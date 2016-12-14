package autoETLsystem.service.imps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import autoETLsystem.dto.AutoETL_ActivityNode;
import autoETLsystem.dto.AutoETL_ActivityNodeFieldV;
import autoETLsystem.dto.AutoETL_ActivityNodeForCT;
import extend.dto.ReportModel_Field;
import framework.helper.FrameworkFactory;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.SessionManager;
import framework.services.interfaces.MessageResult;

public class CreateSameSameMappingService implements IObjectResultExecute{

	public Object objectResultExecute() throws Exception {
		
		String currentLevelLevel = SessionManager.getCurrentLevel();
		String id = SessionManager.getLevelIdValue(currentLevelLevel);
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		AutoETL_ActivityNode autoETL_ActivityNode = (AutoETL_ActivityNode)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoETL_ActivityNode.class.getName(),id,null});
		
		AutoETL_ActivityNodeForCT autoETL_ActivityNodeForCT = null;
		for(AutoETL_ActivityNodeForCT tempAutoETL_ActivityNodeForCT :autoETL_ActivityNode.getAutoETL_ActivityNodeForCTs()){
			autoETL_ActivityNodeForCT = tempAutoETL_ActivityNodeForCT;
			break;
		}
		
		Map<ReportModel_Field,ReportModel_Field> sameNameMap = new HashMap<ReportModel_Field,ReportModel_Field>();
		for(ReportModel_Field sourceField: autoETL_ActivityNodeForCT.getAutoETL_SourceTable().getReportModel_Fields()){
			for(ReportModel_Field targetField: autoETL_ActivityNodeForCT.getAutoETL_TargetTable().getReportModel_Fields()){
				if(sourceField.getStrFieldName().toLowerCase().equals(targetField.getStrFieldName().toLowerCase())){
					sameNameMap.put(sourceField, targetField);
					break;
				}
			}
		}
		
		List<AutoETL_ActivityNodeFieldV> autoETL_ActivityNodeFieldVList = new ArrayList<AutoETL_ActivityNodeFieldV>();
		for(Map.Entry<ReportModel_Field,ReportModel_Field> entry : sameNameMap.entrySet()){
			boolean isExsit = false;
			for(AutoETL_ActivityNodeFieldV autoETL_ActivityNodeFieldV : autoETL_ActivityNodeForCT.getAutoETL_ActivityNodeFieldVs()){
				if(entry.getKey().getStrFieldName().equals(autoETL_ActivityNodeFieldV.getAutoETL_SourceField().getStrFieldName()) && entry.getKey().getStrFieldName().equals(autoETL_ActivityNodeFieldV.getAutoETL_TargetField().getStrFieldName())){
					isExsit = true;
					break;
				}
			}
			
			if(!isExsit){
				AutoETL_ActivityNodeFieldV tempAutoETL_ActivityNodeFieldV = new AutoETL_ActivityNodeFieldV();
				
				tempAutoETL_ActivityNodeFieldV.setAutoETL_ActivityNodeForCT(autoETL_ActivityNodeForCT);
				tempAutoETL_ActivityNodeFieldV.setAutoETL_SourceField(entry.getKey());
				tempAutoETL_ActivityNodeFieldV.setAutoETL_TargetField(entry.getValue());
				
				autoETL_ActivityNodeFieldVList.add(tempAutoETL_ActivityNodeFieldV);
			}
		}
		
		IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
		singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{autoETL_ActivityNodeFieldVList,null});
		
		return new MessageResult("创建成功");
	}
}
