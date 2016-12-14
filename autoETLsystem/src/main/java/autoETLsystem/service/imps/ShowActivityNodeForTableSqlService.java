package autoETLsystem.service.imps;

import java.util.ArrayList;
import java.util.List;

import autoETLsystem.dto.AutoETL_ActivityNode;
import autoETLsystem.dto.AutoETL_ActivityNodeFieldV;
import autoETLsystem.dto.AutoETL_ActivityNodeForCT;
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

public class ShowActivityNodeForTableSqlService implements IObjectResultExecute{

	public Object objectResultExecute() throws Exception {
		
		String previousLevel = SessionManager.getPreviousLevel();
		String id = SessionManager.getLevelIdValue(previousLevel);
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		AutoETL_ActivityNode autoETL_ActivityNode = (AutoETL_ActivityNode)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{AutoETL_ActivityNode.class.getName(),id,null});
		
		AutoETL_ActivityNodeForCT autoETL_ActivityNodeForCT = null;
		for(AutoETL_ActivityNodeForCT tempAutoETL_ActivityNodeForCT :autoETL_ActivityNode.getAutoETL_ActivityNodeForCTs()){
			autoETL_ActivityNodeForCT = tempAutoETL_ActivityNodeForCT;
			break;
		}
		
		List<AutoETL_ActivityNodeFieldV> autoETL_ActivityNodeFieldVList = new ArrayList<AutoETL_ActivityNodeFieldV>();
		for(AutoETL_ActivityNodeFieldV autoETL_ActivityNodeFieldV : autoETL_ActivityNodeForCT.getAutoETL_ActivityNodeFieldVs()){
			autoETL_ActivityNodeFieldVList.add(autoETL_ActivityNodeFieldV);
		}

		ThreadWorkflowRunnable threadWorkflowRunnable = new ThreadWorkflowRunnable();
		String sql = "";
		try{
			sql = threadWorkflowRunnable.getExceuteTableSql(autoETL_ActivityNodeForCT,autoETL_ActivityNodeFieldVList,null);
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
