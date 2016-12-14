package framework.services.imps;

import java.util.List;
import java.util.Map;

import framework.interfaces.RequestManager;
import framework.interfaces.SessionManager;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.ShowParamManager;

public class SingleObjectShowListService extends BaseObjectDaoResultService{

    private Integer pageSize;

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageSize() {
		return pageSize;
	}
	
	private Map<String,String> linkedMap;
	
	public void setLinkedMap(Map<String,String> linkedMap) {
		this.linkedMap = linkedMap;
	}
	
	private String typeFieldName;
	
	public void setTypeFieldName(String typeFieldName) {
		this.typeFieldName = typeFieldName;
	}
	
	private Map<String,String> typeFieldValueMap;

	public void setTypeFieldValueMap(Map<String,String> typeFieldValueMap) {
		this.typeFieldValueMap = typeFieldValueMap;
	}
	
	private Map<String,String> typeFieldNameAndValueMap;
	
	@Override
	public void init() throws Exception {
		super.init();
		ShowParamManager.setPageSize(this.pageSize);
		ShowParamManager.setLinkedMap(linkedMap);
		ShowParamManager.setTypeFieldName(typeFieldName);
		ShowParamManager.setTypeFieldValueMap(typeFieldValueMap);
		ShowParamManager.setTypeFieldNameAndValueMap(typeFieldNameAndValueMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initSuccessResult() throws Exception {
		
		super.initSuccessResult();
		
		if(ShowParamManager.getPageSize() != null){
			List<Object> tempList = (List<Object>)this.getServiceResult();
			if(tempList.size() == 0 && LogicParamManager.getTotalCount() != 0){
				if(SessionManager.getCurrentPage(RequestManager.getTName()) == null)
					SessionManager.setCurrentPage(0, RequestManager.getTName());
				else
					SessionManager.setCurrentPage(SessionManager.getCurrentPage(RequestManager.getTName()) - 1, RequestManager.getTName());
				RequestManager.setCurrentPage(RequestManager.getCurrentPage() - 1);
				ShowParamManager.setFirstResult((RequestManager.getCurrentPage() - 1) * ShowParamManager.getPageSize());
				this.setServiceResult(this.getBaseObjectDao().paramObjectResultExecute(null));
			}
		}
	}

	public void setTypeFieldNameAndValueMap(Map<String,String> typeFieldNameAndValueMap) {
		this.typeFieldNameAndValueMap = typeFieldNameAndValueMap;
	}

	public Map<String,String> getTypeFieldNameAndValueMap() {
		return typeFieldNameAndValueMap;
	}
}
