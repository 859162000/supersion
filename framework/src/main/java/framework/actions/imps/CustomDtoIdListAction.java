package framework.actions.imps;

import framework.interfaces.RequestManager;


public class CustomDtoIdListAction extends BaseSTNameAndIdListAction {

	/**
	 * action 重新设置正确的dtoname，
	 * 解决security.xml文件中*-report.dto.TaskRptInst的配置，导致具体限制某个功能时权限无效的问题
	 * （因Service中operationMap在没有指定dto的情况下，会自动加上对应的dto,
	 * 所以operationMap配置时需指定dtoname（该dtoname在系统不存在），这样权限过滤时，
	 * 就不会因为通配符的设置导致权限无效，再在此action中重新设置正确的action）
	 */
	private static final long serialVersionUID = -351305266115069820L;
	@Override
	public String execute() throws Exception {
	
		RequestManager.setTName(dto);
		
		return super.execute();
	}
	@Override
	protected String getTName(){
		return dto;
	}
	private String dto;
	public void setDtoName(String dto)
	{
		this.dto=dto;
	}

}
