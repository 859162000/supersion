package zxptsystem.service.imps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IObjectResultExecute;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseService;

public class GetAsynDataService extends BaseService {

	@Override
	public void initSuccessResult() throws Exception {
		super.initSuccessResult();
		
		Object tObj = RequestManager.getTOject();
		Object request = ServletActionContext.getContext().get("request");
		Map<String,Object> paramMap = (Map<String,Object>)request;
		
		IParamObjectResultExecute singleObjectFindByCriteriaD = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(tObj.getClass());
		detachedCriteria.add(Restrictions.ilike(this.getFieldName(), paramMap.get("key").toString(), MatchMode.ANYWHERE));
		List<String> resultList	=new ArrayList<String>();
		List<Object> objectList = (List<Object>)singleObjectFindByCriteriaD.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		int index=0;
		for(Object obj:objectList){
			if(index<this.getResultCount()){
				resultList.add(ReflectOperation.getFieldValue(obj, this.getFieldName()).toString());
			}
			index++;
		}
		this.setServiceResult(resultList);
	}

	private String fieldName;
	private int resultCount;
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}
	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}

	public int getResultCount() {
		return resultCount;
	}

}
