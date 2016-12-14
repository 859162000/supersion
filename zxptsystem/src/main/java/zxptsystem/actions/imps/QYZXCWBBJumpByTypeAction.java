package zxptsystem.actions.imps;

import java.util.Map;

import framework.actions.imps.BaseSAction;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.ActionJumpResult;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.TActionRule;

public class QYZXCWBBJumpByTypeAction extends BaseSAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String typeFieldName;
	
	private Map<String, String> typeFieldValueMap;
	
	private ActionJumpResult actionJumpResult;
	
	protected String getTName(){
		String actionName = this.getActionName();
		String tName = null;
		try{
			tName = TActionRule.getTName(actionName);
		}
		catch(Exception ex){
		}
		return tName;
	}
	
	@Override
	public String execute() throws Exception {
		
		super.execute();
		
		actionJumpResult = new ActionJumpResult();
		actionJumpResult.setActionNamespace(getNamespace());
				
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		Object object= singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{this.getTName(),this.getId(),null});
		
		String jumpAction = "";
		if(object!=null && ReflectOperation.getFieldValue(object, typeFieldName)!=null){
			jumpAction = typeFieldValueMap.get(ReflectOperation.getFieldValue(object, typeFieldName));
		}

		actionJumpResult.setActionName(jumpAction);

		return SUCCESS;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setTypeFieldValueMap(Map<String, String> typeFieldValueMap) {
		this.typeFieldValueMap = typeFieldValueMap;
	}
	
	public void setActionJumpResult(ActionJumpResult actionJumpResult) {
		this.actionJumpResult = actionJumpResult;
	}

	public ActionJumpResult getActionJumpResult() {
		return actionJumpResult;
	}

	public void setTypeFieldName(String typeFieldName) {
		this.typeFieldName = typeFieldName;
	}

}
