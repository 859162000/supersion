package autoETLsystem.service.imps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import autoETLsystem.dto.AutoETL_ActivityNode;
import autoETLsystem.dto.AutoETL_ActivityNodeFieldViewV;
import autoETLsystem.dto.AutoETL_ActivityNodeForCV;
import extend.dto.AutoETL_ViewField;
import extend.dto.ReportModel_Field;
import framework.helper.FrameworkFactory;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.SessionManager;
import framework.services.interfaces.MessageResult;

public class CreateSameSameForViewMappingService implements IObjectResultExecute{

	public Object objectResultExecute() throws Exception {
		
		String currentLevelLevel = SessionManager.getCurrentLevel();
		String id = SessionManager.getLevelIdValue(currentLevelLevel);
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		AutoETL_ActivityNode autoETL_ActivityNode = (AutoETL_ActivityNode)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoETL_ActivityNode.class.getName(),id,null});
		
		AutoETL_ActivityNodeForCV autoETL_ActivityNodeForCV = null;
		for(AutoETL_ActivityNodeForCV tempAutoETL_ActivityNodeForCV :autoETL_ActivityNode.getAutoETL_ActivityNodeForCVs()){
			autoETL_ActivityNodeForCV = tempAutoETL_ActivityNodeForCV;
			break;
		}
		
		Map<AutoETL_ViewField,ReportModel_Field> sameNameMap = new HashMap<AutoETL_ViewField,ReportModel_Field>();
		for(AutoETL_ViewField sourceField: autoETL_ActivityNodeForCV.getAutoETL_SourceView().getAutoETL_ViewFields()){
			for(ReportModel_Field targetField: autoETL_ActivityNodeForCV.getAutoETL_TargetTable().getReportModel_Fields()){
				if(sourceField.getStrFieldName().toLowerCase().equals(targetField.getStrFieldName().toLowerCase())){
					sameNameMap.put(sourceField, targetField);
					break;
				}
			}
		}
		
		List<AutoETL_ActivityNodeFieldViewV> autoETL_ActivityNodeFieldViewVList = new ArrayList<AutoETL_ActivityNodeFieldViewV>();
		for(Map.Entry<AutoETL_ViewField,ReportModel_Field> entry : sameNameMap.entrySet()){
			boolean isExsit = false;
			for(AutoETL_ActivityNodeFieldViewV autoETL_ActivityNodeFieldV : autoETL_ActivityNodeForCV.getAutoETL_ActivityNodeFieldViewVs()){
				if(entry.getKey().getStrFieldName().equals(autoETL_ActivityNodeFieldV.getAutoETL_SourceViewField().getStrFieldName()) && entry.getKey().getStrFieldName().equals(autoETL_ActivityNodeFieldV.getReportModel_TagetField().getStrFieldName())){
					isExsit = true;
					break;
				}
			}
			
			if(!isExsit){
				AutoETL_ActivityNodeFieldViewV tempAutoETL_ActivityNodeFieldViewV = new AutoETL_ActivityNodeFieldViewV();
				
				tempAutoETL_ActivityNodeFieldViewV.setAutoETL_ActivityNodeForCV(autoETL_ActivityNodeForCV);
				tempAutoETL_ActivityNodeFieldViewV.setAutoETL_SourceViewField(entry.getKey());
				tempAutoETL_ActivityNodeFieldViewV.setReportModel_TagetField(entry.getValue());
				
				autoETL_ActivityNodeFieldViewVList.add(tempAutoETL_ActivityNodeFieldViewV);
			}
		}
		
		IParamVoidResultExecute singleObjectSaveOrUpdateAllDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
		singleObjectSaveOrUpdateAllDao.paramVoidResultExecute(new Object[]{autoETL_ActivityNodeFieldViewVList,null});
		
		return new MessageResult("创建成功");
	}
}
