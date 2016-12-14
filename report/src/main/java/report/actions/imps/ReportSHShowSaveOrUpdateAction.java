package report.actions.imps;

import org.apache.commons.lang.StringUtils;
import framework.actions.imps.BaseSTAction;
import framework.interfaces.ActionJumpResult;
import framework.interfaces.RequestManager;
import framework.interfaces.TActionRule;

public class ReportSHShowSaveOrUpdateAction extends BaseSTAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private String id;
	
	public void setId(String id) {
		this.id = id;
	}
	
	private String type;

	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	@Override
	public String execute() throws Exception {
		
		String tempType = this.getType();
		
		if(tempType.contains("?") && tempType.contains(",")){
			this.setType(tempType.substring(0,tempType.indexOf("?")));
			
			this.setId(tempType.substring(tempType.indexOf("?") + 4,tempType.indexOf(",")));
		}
		RequestManager.setLevelIdValue(this.id);
		
		if(!StringUtils.isBlank(type)){
			if(type.indexOf("-") >-1){
				type = type.substring(type.indexOf("-") + 1);
			}
		}
		
		RequestManager.setTypeName(type);
		
		super.execute();
		
		TActionRule.initActionName();
		
		ActionJumpResult actionJumpResult = new ActionJumpResult();
		actionJumpResult.setActionNamespace(getNamespace());
		if(RequestManager.getId() == null){
			return "jumpMessage";
		}
		else{
			actionJumpResult.setIdValue(RequestManager.getId().toString());
			actionJumpResult.setWindowId(RequestManager.getWindowId());
			actionJumpResult.setActionName(TActionRule.getCurrentLevelOneToOneShowUpdate(RequestManager.getTName()));
			actionJumpResult.setActionName("ReportSH" + actionJumpResult.getActionName());
			this.setServiceResult(actionJumpResult);
			return "update";
		}
	}

}
