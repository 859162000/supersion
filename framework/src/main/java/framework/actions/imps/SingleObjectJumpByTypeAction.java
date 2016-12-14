package framework.actions.imps;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.ActionJumpResult;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.interfaces.TActionRule;
import framework.services.interfaces.MessageResult;

public class SingleObjectJumpByTypeAction extends BaseSAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String type;

	public void setType(String type) {
		this.type = type;
	}
	
	private ActionJumpResult actionJumpResult;
	
	public void setActionJumpResult(ActionJumpResult actionJumpResult) {
		this.actionJumpResult = actionJumpResult;
	}

	public ActionJumpResult getActionJumpResult() {
		return actionJumpResult;
	}
	
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
		
		if(type.equals("")) {
			setServiceResult(new MessageResult(false, "没有要跳转的动作。"));
			return ERROR;
		}
		else if(type.indexOf("-") > -1){
			if(this.getActionName().indexOf("JumpByTypeFor")>-1){
				
				IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
				Object object= singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{this.getTName(),this.getId(),null});
				
				String jumpAction = "";
				String targetField = this.getActionName().substring(this.getActionName().indexOf("JumpByTypeFor") + 13,this.getActionName().indexOf("-") - this.getActionName().indexOf("JumpByTypeFor"));
				for(String keyValue : type.split("\\$\\$\\$\\$\\$")){
					if(keyValue.startsWith(targetField)){
						String values = keyValue.substring(targetField.length());
						for(String value : values.split(",")){
							String fieldValue = value.substring(0,value.indexOf("@"));
							if(ReflectOperation.getFieldValue(object, targetField).toString().equals(fieldValue)){
								jumpAction = value.substring(value.indexOf("@") + 1);
								break;
							}
						}
						break;
					}
				}

				if(jumpAction.indexOf("?type=") > -1){
					actionJumpResult.setActionName(jumpAction + "&id=" + this.id);
					return "success1";
				}
				else{
					actionJumpResult.setActionName(jumpAction);
				}
			}
			else if(type.indexOf("?type=") > -1){
				actionJumpResult.setActionName(type + "&id=" + this.id);
				return "success1";
			}
			else{
				actionJumpResult.setActionName(type);
			}
		}
		else{
			actionJumpResult.setActionName(type + "-" + getTName());
		}

		return SUCCESS;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

}
