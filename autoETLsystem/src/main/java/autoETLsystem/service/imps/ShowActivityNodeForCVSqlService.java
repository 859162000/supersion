package autoETLsystem.service.imps;

import java.util.ArrayList;
import java.util.List;

import autoETLsystem.dto.AutoETL_ActivityNode;
import autoETLsystem.dto.AutoETL_ActivityNodeFieldViewV;
import autoETLsystem.dto.AutoETL_ActivityNodeForCV;
import framework.helper.FrameworkFactory;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.show.ShowField;
import framework.show.ShowFieldValue;
import framework.show.ShowOperation;
import framework.show.ShowSaveOrUpdate;

public class ShowActivityNodeForCVSqlService implements IObjectResultExecute{

	public Object objectResultExecute() throws Exception {
		
		String previousLevel = SessionManager.getPreviousLevel();
		String id = SessionManager.getLevelIdValue(previousLevel);
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		AutoETL_ActivityNode autoETL_ActivityNode = (AutoETL_ActivityNode)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoETL_ActivityNode.class.getName(),id,null});
		
		AutoETL_ActivityNodeForCV autoETL_ActivityNodeForCV = null;
		for(AutoETL_ActivityNodeForCV tempAutoETL_ActivityNodeForCV :autoETL_ActivityNode.getAutoETL_ActivityNodeForCVs()){
			autoETL_ActivityNodeForCV = tempAutoETL_ActivityNodeForCV;
			break;
		}
		
		List<AutoETL_ActivityNodeFieldViewV> autoETL_ActivityNodeFieldViewVList = new ArrayList<AutoETL_ActivityNodeFieldViewV>();
		for(AutoETL_ActivityNodeFieldViewV autoETL_ActivityNodeFieldViewV : autoETL_ActivityNodeForCV.getAutoETL_ActivityNodeFieldViewVs()){
			autoETL_ActivityNodeFieldViewVList.add(autoETL_ActivityNodeFieldViewV);
		}

		ThreadWorkflowRunnable threadWorkflowRunnable = new ThreadWorkflowRunnable();
		String sql = "";
		try{
			sql = threadWorkflowRunnable.getExceuteViewSql(autoETL_ActivityNodeForCV,autoETL_ActivityNodeFieldViewVList,null);
		}
		catch(Exception ex){
			sql = ex.getMessage();
		}
		
		ShowSaveOrUpdate showSaveOrUpdate = new ShowSaveOrUpdate();
		
		List<ShowFieldValue> showFieldValueList = new ArrayList<ShowFieldValue>();
		
		ShowFieldValue showFieldValue = new ShowFieldValue();
		
		ShowField showField = new ShowField();
		showField.setFieldShowName("SQL");
		showField.setSingleTag(ApplicationManager.getSingleTagMultipleTextField());
		
		showFieldValue.setReadOnly(true);
		showFieldValue.setShowField(showField);
		showFieldValue.setFieldValue(sql);
		
		List<ShowOperation> operationList = new ArrayList<ShowOperation>();
		ShowOperation showOperation = new ShowOperation();
		showOperation.setOperationName("返回");
		showOperation.setOperationType("Get");
		showOperation.setOperationNamespace(RequestManager.getNamespace());
		showOperation.setOperationAction("BackPreviousFirst");
		
		
		operationList.add(showOperation);
		showFieldValueList.add(showFieldValue);
		showSaveOrUpdate.setOperationList(operationList);
		showSaveOrUpdate.setShowFieldValueList(showFieldValueList);
		
		return showSaveOrUpdate;
	}

}

